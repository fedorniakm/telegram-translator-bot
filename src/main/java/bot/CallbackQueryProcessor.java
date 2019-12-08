package bot;

import bot.keyboard.*;
import database.User;
import database.services.UserService;
import gcp.SpeechLanguage;
import gcp.SpeechToText;
import gcp.TextLanguage;

import gcp.TranslateText;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CallbackQueryProcessor {

    private CallbackQuery callbackQuery;
    private List<BotApiMethod> botApiMethods;
    private Bot bot;

    public CallbackQueryProcessor(Bot bot, CallbackQuery callbackQuery) {
        this.callbackQuery = callbackQuery;
        this.bot = bot;
        botApiMethods = new ArrayList<>();
    }

       /*
    markers: trt -- translate text from message
     setf, sett -- set user default "from" and "to" languages
     vf -- detect reply voice from language


     */

    public void process() {
        CallbackArguments callbackArguments = new CallbackArguments( callbackQuery.getData() );
        User user = ResponseBuilder.getValidatedUser( callbackQuery.getFrom() );
        UserService userService = new UserService();

        if (!" ".equals(callbackArguments.getMarker()) &&
            !(callbackArguments.getControl() == null)) {
            if (callbackArguments.getControl() == LanguagesInlineKeyboardControls.AUTO) {
                if ("setf".equals(callbackArguments.getMarker())) {
                    user.getUserConfigs().setFromLang( "" );
                    botApiMethods.add(this.getEditMessageText("Ти обрав автовизначення мови вводу!"));
                    botApiMethods.add(this.getSendMessageDefaultReply());
                } else if ("sett".equals(callbackArguments.getMarker())) {
                    botApiMethods.add(this.getEditMessageText("Ти не можеш обрати автовизначення мови виводу!"));
                    botApiMethods.add(this.getSendMessageDefaultReply());
                } else if ("trt".equals(callbackArguments.getMarker())) {
                    botApiMethods.add(getLoopback());
                }
            } else if (callbackArguments.getControl() == LanguagesInlineKeyboardControls.BACK) {
                if ("trt".equals(callbackArguments.getMarker())) {
                    EditMessageText editMessageText = new EditMessageText();

                    SourceInlineKeyboard keyboard = new SourceInlineKeyboard(callbackArguments.getMarker());
                    System.out.println("LOOOL");
                    editMessageText.setReplyMarkup( keyboard.getKeyboard(user));
                    editMessageText.setChatId( callbackQuery.getMessage().getChatId() );
                    editMessageText.setMessageId( callbackQuery.getMessage().getMessageId() );
                    editMessageText.setText( callbackQuery.getMessage().getText() );

                    botApiMethods.add(editMessageText);
                } else if ("vf".equals(callbackArguments.getMarker())) {
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    List<List<InlineKeyboardButton>> rows = new ArrayList<>();
                    List<InlineKeyboardButton> row = new ArrayList<>();

                    row.add(new InlineKeyboardButton()
                            .setText("Розпізнати з " + SpeechLanguage.getLanguageByCode(user.getUserConfigs().getSpeechLang()))
                            .setCallbackData("vf_lang_" + user.getUserConfigs().getSpeechLang()));
                    row.add(new InlineKeyboardButton().setText("Перекласти з...").setCallbackData("vf_langs"));
                    rows.add(row);
                    inlineKeyboardMarkup.setKeyboard(rows);

                    EditMessageText editMessageText = new EditMessageText();

                    editMessageText.setReplyMarkup(inlineKeyboardMarkup);
                    editMessageText.setChatId( callbackQuery.getMessage().getChatId() );
                    editMessageText.setMessageId( callbackQuery.getMessage().getMessageId() );
                    editMessageText.setText( callbackQuery.getMessage().getText() );
                    try {
                        bot.execute(editMessageText);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    botApiMethods.add(getEditMessageText("Твої налаштування не змінились."));
                }
            } else if (callbackArguments.getControl() == LanguagesInlineKeyboardControls.LANG) {
                if ("setf".equals(callbackArguments.getMarker())) {
                    user.getUserConfigs().setFromLang( callbackArguments.getValue() );
                    userService.update(user);

                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setText("Ти обрав " +
                            TextLanguage.getLanguageByCode( callbackArguments.getValue() ).getName() +
                            " мовою вводу!");
                    editMessageText.setChatId(callbackQuery.getMessage().getChatId());
                    editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());

                    botApiMethods.add(editMessageText);
                    botApiMethods.add(this.getSendMessageDefaultReply());
                } else if ("sett".equals(callbackArguments.getMarker())) {
                    user.getUserConfigs().setToLang( callbackArguments.getValue() );
                    userService.update(user);

                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setText("Ти обрав " +
                            TextLanguage.getLanguageByCode( callbackArguments.getValue() ).getName() +
                            " мовою виводу!");
                    editMessageText.setChatId(callbackQuery.getMessage().getChatId());
                    editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());

                    botApiMethods.add(editMessageText);
                    botApiMethods.add(this.getSendMessageDefaultReply());
                } else if ("trt".equals(callbackArguments.getMarker())) {
                    try {
                        if (! (callbackQuery.getMessage().getReplyMarkup() == new SourceInlineKeyboard(callbackArguments.getMarker()).getKeyboard(user)) ) {
                            bot.execute(getEditMessageText(callbackQuery.getMessage().getText(),
                                    new SourceInlineKeyboard("trt").getKeyboard(user)));
                        }
                    } catch (TelegramApiException e) {

                    }

                    String before = callbackQuery.getMessage().getText();
                    TranslateText translateText = new TranslateText( before, callbackArguments.getValue() );
                    String translatedText  = translateText.getTranslate();

                    if (translatedText.length() >= 4096) {
                        File file = ResponseBuilder.getFileWithText(translatedText);


                        SendDocument sendDocument = new SendDocument();
                        sendDocument.setChatId( callbackQuery.getMessage().getChatId() );
                        sendDocument.setDocument(file);

                        try {
                            bot.execute(sendDocument);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId( callbackQuery.getMessage().getChatId() );
                    sendMessage.setText(translatedText);

                    botApiMethods.add(sendMessage);
                } else if ("vf".equals(callbackArguments.getMarker())) {
                    Message message = callbackQuery.getMessage().getReplyToMessage();
                    try {
                        GetFile getFile = new GetFile();
                        getFile.setFileId(message.getVoice().getFileId());

                        org.telegram.telegrambots.meta.api.objects.File s = bot.execute(getFile);
                        System.out.println(s.getFilePath());
                        java.io.File f = bot.downloadFile(s);

                        SpeechToText speechToText = new SpeechToText();
                        String detectedText = speechToText.toText(f, callbackArguments.getValue());

                        if (detectedText.equals("")) {
                            detectedText = "Вибач, але я нічого не розпізнав :с";
                        }

                        System.out.println("Speech-to-text:\n"
                                + "user_id:\t" + message.getFrom().getId()
                                + "\nusername:\t" + message.getFrom().getUserName()
                                + "\nfirst_name:\t" + message.getFrom().getFirstName()
                                + "\nlast_name:\t" + message.getFrom().getLastName()
                                + "\ntext:\t" + detectedText +
                                "\n");

                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId( message.getChatId() );
                        sendMessage.setText( detectedText );

                        SourceInlineKeyboard sourceInlineKeyboard = new SourceInlineKeyboard("trt");
                        sendMessage.setReplyMarkup(sourceInlineKeyboard.getKeyboard(user));

                        bot.executeMethod(sendMessage);
                    } catch (TelegramApiException e) {
                        System.out.println("TelegramApiException: cannot to get file");;
                    } catch (NullPointerException e) {
                        System.out.println("Exception: cannot to find a file");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else if (
                    callbackArguments.getControl() == LanguagesInlineKeyboardControls.PAGE
                            || callbackArguments.getControl() == LanguagesInlineKeyboardControls.START
                            || callbackArguments.getControl() == LanguagesInlineKeyboardControls.END
            ) {
                if ("trt".equals(callbackArguments.getMarker())) {
                    TextLanguagesInlineKeyboard textLanguagesInlineKeyboard = new TextLanguagesInlineKeyboard( callbackArguments.getMarker() );
                    textLanguagesInlineKeyboard.setAuto(false);
                    InlineKeyboardMarkup inlineKeyboardMarkup = textLanguagesInlineKeyboard.getState(
                            callbackArguments.getControl(),
                            callbackArguments.getValue()
                    );
                    botApiMethods.add(getEditMessageText(inlineKeyboardMarkup));
                } else if ("vf".equals(callbackArguments.getMarker())) {
                    VoiceLanguagesInlineKeyboard voiceLanguagesInlineKeyboard = new VoiceLanguagesInlineKeyboard(callbackArguments.getMarker());
                    InlineKeyboardMarkup inlineKeyboardMarkup = voiceLanguagesInlineKeyboard.getState(
                            callbackArguments.getControl(),
                            callbackArguments.getValue()
                    );
                    botApiMethods.add(getEditMessageText(inlineKeyboardMarkup));
                } else {
                    botApiMethods.add(getEditMessageText(new TextLanguagesInlineKeyboard(callbackArguments.getMarker())
                    .getState(callbackArguments.getControl(), callbackArguments.getValue())));
                }
            } else if (callbackArguments.getControl() == LanguagesInlineKeyboardControls.LANGS) {
                if ("trt".equals(callbackArguments.getMarker())) {
                    EditMessageText editMessageText = new EditMessageText();
                    TextLanguagesInlineKeyboard textLanguagesInlineKeyboard = new TextLanguagesInlineKeyboard("trt");
                    textLanguagesInlineKeyboard.setAuto(false);

                    editMessageText.setReplyMarkup(textLanguagesInlineKeyboard.getFirst());
                    editMessageText.setChatId( callbackQuery.getMessage().getChatId() );
                    editMessageText.setMessageId( callbackQuery.getMessage().getMessageId() );
                    editMessageText.setText( callbackQuery.getMessage().getText() );
                    botApiMethods.add(editMessageText);
                } else if ("vf".equals(callbackArguments.getMarker())) {
                    EditMessageText editMessageText = new EditMessageText();
                    VoiceLanguagesInlineKeyboard voiceLanguagesInlineKeyboard = new VoiceLanguagesInlineKeyboard("vf");

                    editMessageText.setChatId( callbackQuery.getMessage().getChatId() );
                    editMessageText.setMessageId( callbackQuery.getMessage().getMessageId() );
                    editMessageText.setText( callbackQuery.getMessage().getText() );
                    editMessageText.setReplyMarkup(voiceLanguagesInlineKeyboard.getFirst());

                    botApiMethods.add(editMessageText);
                }
            }
        }


        bot.execute(botApiMethods);
    }

    private BotApiMethod getLoopback() {
        return null;
    }

    private BotApiMethod getEditMessageText(String text) {
        return getEditMessageText(callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                text);
    }

    private BotApiMethod getEditMessageText(InlineKeyboardMarkup inlineKeyboardMarkup) {
        return getEditMessageText(callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                callbackQuery.getMessage().getText(),
                inlineKeyboardMarkup);
    }

    private BotApiMethod getEditMessageText(String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return getEditMessageText(callbackQuery.getMessage().getChatId(),
                callbackQuery.getMessage().getMessageId(),
                text,
                inlineKeyboardMarkup);
    }

    private BotApiMethod getEditMessageText(Long chatId, Integer messageId, String text) {

        return this.getEditMessageText(chatId, messageId, text, callbackQuery.getMessage().getReplyMarkup());
    }

    private BotApiMethod getEditMessageText(Long chatId, Integer messageId, String text, InlineKeyboardMarkup ikm ) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(text);
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        editMessageText.setReplyMarkup( ikm );
        editMessageText.setInlineMessageId(callbackQuery.getInlineMessageId());

        return editMessageText;
    }

    private SendMessage getSendMessageDefaultReply() {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId( callbackQuery.getMessage().getChatId() );
        sendMessage.setText( "Що робитимеш далі?" );
        sendMessage.setReplyMarkup( new ReplyKeyboardBuilder().getReplyKeyboard( ResponseBuilder.getValidatedUser( callbackQuery.getFrom() ) ) );

        return sendMessage;
    }

}
