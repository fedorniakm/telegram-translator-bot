package bot;

import bot.commands.StartCommand;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class Bot extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN = "900118860:AAHR2NAIRSiDjRg3fmlMjMIpl7g5eM9Hh4k";

    public Bot(String botName) {
        super(botName);
        this.BOT_NAME = botName;
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        System.out.println("update_id:" + update.getUpdateId());

        ResponseBuilder responseBuilder = new ResponseBuilder(update, this);
        responseBuilder.build();
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void execute(List<BotApiMethod> methodsList) {
        try {

            if (methodsList != null) {
                for (BotApiMethod method : methodsList) {
                    this.execute(method);
                }
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeMethod(BotApiMethod botApiMethod) {
        try {
            this.execute(botApiMethod);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
