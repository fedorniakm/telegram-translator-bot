package bot;

import bot.keyboard.TextLanguagesInlineKeyboard;
import bot.keyboard.ReplyKeyboardBuilder;
import bot.keyboard.SourceInlineKeyboard;
import database.User;
import database.UserConfigs;
import database.services.UserService;
import gcp.*;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;

import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder {
    private Update update;
    private UserService userService;
    private Bot bot;

    private final String NOTHING_DETECTED = "Вибач, але я нічого не розпізнав :с";

    public ResponseBuilder(Update update, Bot bot) {
        this.update = update;
        this.bot = bot;
        userService = new UserService();
    }

    public void build() {

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                this.handleMessageText();
            } else if (update.getMessage().hasPhoto()) {
                this.handleMessagePhoto();
            } else if (update.getMessage().hasVoice()) {
                this.handleMessageVoice();
            }

        } else if (update.hasCallbackQuery()) {
            this.handleCallbackQuery();
        } else {
            this.getUnsupportedFormat();
        }

    }

    private void handleCallbackQuery() {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        CallbackQueryProcessor callbackQueryProcessor = new CallbackQueryProcessor(bot, callbackQuery);
        callbackQueryProcessor.process();
    }

    private void handleMessageText() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        User user = ResponseBuilder.getValidatedUser( update.getMessage().getFrom() );

        String inputText = update.getMessage().getText();

        String outputLang = ReplyKeyboardBuilder.TO_SYMBOL + " " + TextLanguage.getLanguageByCode( user.getUserConfigs().getToLang() ).getName();
        String inputLang = ReplyKeyboardBuilder.FROM_SYMBOL;

        if (TextLanguage.getLanguageByCode( user.getUserConfigs().getFromLang() ).getName().equals("")) {
            inputLang += " Авто";
        } else {
            inputLang += " " + TextLanguage.getLanguageByCode( user.getUserConfigs().getFromLang() ).getName();
        }

        if (inputLang.equals(inputText)) {
            TextLanguagesInlineKeyboard keyboardBuilder = new TextLanguagesInlineKeyboard("setf");

            sendMessage.setText("Обери мову вводу. Це допоможе мені краще перекласти твій текст чи голос. Також ти можеш обрати" +
                    " Автовизначення мови - в такому разі я самостійно визначу мову твого тексту." +
                    "\n\nЩоб обрати - натисни на потрібну мову. Стрілками << >> < > гортай список мов.");
            sendMessage.setReplyMarkup(keyboardBuilder.getFirst());
        } else if (outputLang.equals(inputText)) {
            TextLanguagesInlineKeyboard keyboardBuilder = new TextLanguagesInlineKeyboard("sett");
            keyboardBuilder.setAuto(false);

            sendMessage.setText("Обери мову виводу." +
                    "\n\nЩоб обрати - натисни на потрібну мову. Стрілками << >> < > гортай список мов.");
            sendMessage.setReplyMarkup(keyboardBuilder.getFirst());
        } else if (ReplyKeyboardBuilder.CHANGE_SYMBOL.equals(inputText)) {

            if (user.getUserConfigs().getFromLang().equals("")) {
                sendMessage.setText("Не можу змінити. Обери мову вводу для початку :)");
            } else {
                String temp;

                temp = user.getUserConfigs().getFromLang();
                user.getUserConfigs().setFromLang( user.getUserConfigs().getToLang() );
                user.getUserConfigs().setToLang( temp );

                userService.update(user);

                ReplyKeyboardBuilder replyKeyboard = new ReplyKeyboardBuilder();

                sendMessage.setText("Готово! Що далі?");
                sendMessage.setReplyMarkup( replyKeyboard.getReplyKeyboard(user) );
            }
        } else {
            String translated = this.getTranslatedText( update.getMessage().getText() );
            sendMessage.setText(translated);
        }

        bot.executeMethod(sendMessage);
    }

    private void handleMessagePhoto() {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();
        String detectedText = this.getTextFromPhoto();

        if (!NOTHING_DETECTED.equals( detectedText )) {
            User user = ResponseBuilder.getValidatedUser( message.getFrom() );
            SourceInlineKeyboard sourceInlineKeyboard = new SourceInlineKeyboard("trt");


            sendMessage.setReplyMarkup(sourceInlineKeyboard.getKeyboard(user));
        }


        sendMessage.setText(detectedText);
        sendMessage.setChatId(message.getChatId());
        bot.executeMethod( sendMessage );
    }

    private void handleMessageVoice() {
        Message message = update.getMessage();
        User user = ResponseBuilder.getValidatedUser( message.getFrom() );

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(new InlineKeyboardButton()
                .setText("Розпізнати з " + SpeechLanguage.getLanguageByCode(user.getUserConfigs().getSpeechLang()))
                .setCallbackData("vf_lang_" + user.getUserConfigs().getSpeechLang()));
        row.add(new InlineKeyboardButton().setText("Перекласти з...").setCallbackData("vf_langs"));
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId( update.getMessage().getChatId() );
        sendMessage.setText("Обери мову, якою ти говорив.");
        sendMessage.setReplyToMessageId( update.getMessage().getMessageId() );
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        bot.executeMethod(sendMessage);
    }

    private void getUnsupportedFormat() {
        SendMessage sendMessage = new SendMessage()
                .setChatId( update.getMessage().getChatId() )
                .setText("Не можу зрозуміти цього, спробуй щось інше :)");
        bot.executeMethod( sendMessage );
    }

    public static User getValidatedUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        UserService userService = new UserService();
        User user = userService.get( telegramUser.getId() );

        String fromLang = TextLanguage.UKRAINIAN.getCode();
        String toLang = TextLanguage.ENGLISH.getCode();
        String speechLang = SpeechLanguage.UKRAINIAN.getCode();
        if (telegramUser.getLanguageCode() != null && TextLanguage.hasLang( telegramUser.getLanguageCode() )) {
            fromLang = telegramUser.getLanguageCode();
        }

        if (user == null) {
            user = new User(
                    telegramUser.getId(),
                    telegramUser.getUserName(),
                    telegramUser.getFirstName(),
                    telegramUser.getLastName(),
                    new UserConfigs(
                            TextLanguage.UKRAINIAN.getCode(),
                            fromLang,
                            toLang,
                            speechLang
                    )
            );
            userService.add(user);

        } else if (!user.equals(telegramUser)) {
            User toUpdateUser = new User(
                    user.getId(),
                    telegramUser.getUserName(),
                    telegramUser.getFirstName(),
                    telegramUser.getLastName(),
                    user.getUserConfigs()
            );

            userService.update(toUpdateUser);
            user = toUpdateUser;
        }

        return user;
    }

    private String getTranslatedText(String text) {
        String translatedText;

        Message message = update.getMessage();
        User user = getValidatedUser(message.getFrom());

        TranslateText translateText = new TranslateText(text, user.getUserConfigs().getToLang());
        translatedText = translateText.getTranslate();

        System.out.println("Translate:\n" + "user_id:\t" + message.getFrom().getId()
                + "\nusername:\t" + user.getUserName()
                + "\nfirst_name:\t" + user.getFirstName()
                + "\nlast_name:\t" + user.getLastName()
                + "\ntext:\t" + text
                + "\ntranslated_text:\t" + translatedText + "\n");

        return translatedText;
    }

    private String getTextFromPhoto() {
        try {
            Message message = update.getMessage();
            List<PhotoSize> photoSizeList = message.getPhoto();

            java.io.File f = this.downloadFileById(photoSizeList.get(0).getFileId());

            String detectedText = ImageToText.detectText(f.getPath());

            if (detectedText.equals("")) {
                detectedText = NOTHING_DETECTED;
            }

            System.out.println("Speech-to-text:\n"
                    + "user_id:\t" + message.getFrom().getId()
                    + "\nusername:\t" + message.getFrom().getUserName()
                    + "\nfirst_name:\t" + message.getFrom().getFirstName()
                    + "\nlast_name:\t" + message.getFrom().getLastName()
                    + "\ntext:\t" + detectedText +
                    "\n");

            return detectedText;

        } catch (TelegramApiException e) {
            System.out.println("TelegramApiException: cannot to get file");
        } catch (NullPointerException e) {
            System.out.println("Exception: cannot to find a file");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return NOTHING_DETECTED;
    }

    private java.io.File downloadFileById(String id) {
        java.io.File result = null;

        try {
            GetFile getFile = new GetFile();
            getFile.setFileId(id);

            File telegramFile = bot.execute(getFile);

            result = bot.downloadFile(telegramFile);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static java.io.File getFileWithText(String text) {
        java.io.File file = new java.io.File("translated_text.txt");

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
