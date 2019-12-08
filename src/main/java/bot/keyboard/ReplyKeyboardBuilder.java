package bot.keyboard;

import database.User;
import gcp.TextLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardBuilder {

    public static final String FROM_SYMBOL = "\uD83D\uDCE5";
    public static final String TO_SYMBOL = "\uD83D\uDCE4";
    public static final String CHANGE_SYMBOL = "\uD83D\uDD1B";


    public ReplyKeyboardBuilder() { }

    public ReplyKeyboardMarkup getReplyKeyboard(User user) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        if (user.getUserConfigs().getFromLang().equals("") || user.getUserConfigs().getFromLang().equals("auto")) {
            row.add(FROM_SYMBOL + " Авто");
        } else {
            row.add(FROM_SYMBOL + " " + TextLanguage.getLanguageByCode( user.getUserConfigs().getFromLang() ).getName());
        }

        row.add(CHANGE_SYMBOL);
        row.add(TO_SYMBOL + " " + TextLanguage.getLanguageByCode( user.getUserConfigs().getToLang() ).getName());

        rows.add(row);
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;
    }
//
//    public ReplyKeyboardMarkup getMainPage() {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//        row.add(SETTINGS_TEXT);
//        rows.add(row);
//
//        replyKeyboardMarkup.setKeyboard(rows);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//
//        return replyKeyboardMarkup;
//    }
//
//    public ReplyKeyboardMarkup getSettings() {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//
//
//        row.add(INTERFACE_LANG_TEXT);
//        rows.add(row);
//
//        row = new KeyboardRow();
//        row.add(FROM_LANG_TEXT);
//        rows.add(row);
//
//        row = new KeyboardRow();
//        row.add(TO_LANG_TEXT);
//        rows.add(row);
//
//        row = new KeyboardRow();
//        row.add(BACK_TEXT);
//        rows.add(row);
//
//        replyKeyboardMarkup.setKeyboard(rows);
//
//
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        return replyKeyboardMarkup;
//    }
//
//    public ReplyKeyboardMarkup getKeyboard(String from, String to, String i) {
//        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//        List<KeyboardRow> rows = new ArrayList<>();
//        KeyboardRow row = new KeyboardRow();
//
//
//        row.add("\uD83D\uDCE5 " + from);
//        row.add("\uD83D\uDCE4 " + to);
//        rows.add(row);
//
//        row = new KeyboardRow();
//        row.add("\uD83C\uDF10 " + i);
//        rows.add(row);
//
//        replyKeyboardMarkup.setKeyboard(rows);
//
//
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        return replyKeyboardMarkup;
//    }

}
