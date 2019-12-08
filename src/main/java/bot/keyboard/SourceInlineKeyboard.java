package bot.keyboard;

import database.User;
import gcp.TextLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SourceInlineKeyboard {

    private String marker;

    public SourceInlineKeyboard(String marker) {
        this.marker = marker;
    }

    public InlineKeyboardMarkup getKeyboard(User user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        row.add(new InlineKeyboardButton()
                .setText("Перекласти на " + TextLanguage.getLanguageByCode( user.getUserConfigs().getToLang() ).getName())
                .setCallbackData(marker + "_lang_" + user.getUserConfigs().getToLang()));

        row.add(new InlineKeyboardButton().setText("Перекласти на...").setCallbackData(marker + "_langs"));

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
