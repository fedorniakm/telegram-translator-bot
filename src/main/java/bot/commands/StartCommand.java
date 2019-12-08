package bot.commands;

import bot.keyboard.ReplyKeyboardBuilder;
import bot.ResponseBuilder;
import database.User;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "With this command you can start the Bot");
    }

    @Override
    public void execute(AbsSender absSender, org.telegram.telegrambots.meta.api.objects.User telegramUser, Chat chat, String[] strings) {
        StringBuilder responseBuilder = new StringBuilder();
        User user = ResponseBuilder.getValidatedUser(telegramUser);

        responseBuilder.append("\uD83D\uDC4B Вітаю тебе, ").append(user.getFirstName())
                .append(" ").append(user.getLastName()).append("!\n\n")
                .append("Я допоможу тобі перекласти текст на іншу мову.\n\n" +
                        "Також у мене є декілька функцій, що\n" +
                        "допоможуть тобі працювати зі мною:\n" +
                        "- Розпізнаю текст на фото\n" +
                        "- Розпізнаю текст з голосового повідомлення\n"
//                        "- [У процесі] Озвучую текст\n" +
//                        "- [У процесі] Працюю з документами"
        );

        ReplyKeyboardBuilder replyKeyboard = new ReplyKeyboardBuilder();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(responseBuilder.toString());
        sendMessage.setReplyMarkup(replyKeyboard.getReplyKeyboard(user));

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
