package bot;


import java.util.ArrayList;
import java.util.List;

public class Messages {
    private String text;

    public Messages(String text) {
        this.text = text;
    }

    public List<String> getMessages() {
        List<String> messagesList = new ArrayList<>();
        final int messageCharactersLimit = 4096;

        if (text.length() < messageCharactersLimit) {
            messagesList.add(text);
        } else {
            StringBuilder message = new StringBuilder(text);
            while (message.length() != 0) {
                if (message.length() <= messageCharactersLimit) {
                    messagesList.add(message.toString());
                    break;
                } else {
                    messagesList.add( message.substring(0, messageCharactersLimit) );
                    message = new StringBuilder(message.substring(messageCharactersLimit, message.length()));
                }
            }
        }

        return messagesList;
    }

}
