package bot.keyboard;

import gcp.TextLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class TextLanguagesInlineKeyboard {
    private String marker;
    private List<List<TextLanguage>> pages;
    private boolean auto_button;

    private final int PAGE_ELEMENTS = 21;

    public TextLanguagesInlineKeyboard(String marker) {
        this.marker = marker;
        this.auto_button = true;

        List<TextLanguage> languageList = Arrays.asList(TextLanguage.values());

        pages = new ArrayList<>();
        List<TextLanguage> page = new ArrayList<>();
        for (int i = 0, k = 0; i < languageList.size(); i++, k++) {
            if (k == PAGE_ELEMENTS) {
                pages.add(page);
                page = new ArrayList<>();
                k = 0;
            }
            page.add(languageList.get(i));
        }
        pages.add(page);
    }

    private InlineKeyboardMarkup buildPage(int page) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        if (!(page == 0)) {
            row.add(new InlineKeyboardButton().setText("<<<").setCallbackData(marker +
                    LanguagesInlineKeyboardControls.SEPARATOR +
                    LanguagesInlineKeyboardControls.START));
            row.add(new InlineKeyboardButton().setText("<").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE + "_" + (page - 1)));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        row.add(new InlineKeyboardButton().setText("Назад").setCallbackData(marker
                + LanguagesInlineKeyboardControls.SEPARATOR
                + LanguagesInlineKeyboardControls.BACK));
        if (! (page == pages.size() - 1)) {
            row.add(new InlineKeyboardButton().setText(">").setCallbackData(
                    marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + (page + 1)
            ));
            row.add(new InlineKeyboardButton().setText(">>>").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.END));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        rows.add(row);


        if (auto_button) {
            row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText("Автовизначення мови").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.AUTO));
            rows.add(row);
        }

        List<TextLanguage> pageLangs = pages.get(page);

        row = new ArrayList<>();
        int k = 0;
        for (TextLanguage lang : pageLangs) {
            row.add(new InlineKeyboardButton()
                    .setText(lang.getName())
                    .setCallbackData(marker
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + LanguagesInlineKeyboardControls.LANG
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + lang.getCode()));
            k++;
            if (k == 3) {
                rows.add(row);
                row = new ArrayList<>();
                k = 0;
            }
        }
        rows.add(row);

        row = new ArrayList<>();

        if (!(page == 0)) {
            row.add(new InlineKeyboardButton().setText("<<<").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.START));
            row.add(new InlineKeyboardButton().setText("<").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + (page - 1)));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        row.add(new InlineKeyboardButton().setText("Назад").setCallbackData(marker
                + LanguagesInlineKeyboardControls.SEPARATOR
                + LanguagesInlineKeyboardControls.BACK));
        if (! (page == pages.size() - 1)) {
            row.add(new InlineKeyboardButton().setText(">").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + (page + 1)));
            row.add(new InlineKeyboardButton().setText(">>>").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.END));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }

        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getFirst() {
        return buildPage(0);
    }

    public InlineKeyboardMarkup getLast() {
        return buildPage(pages.size()  - 1);
    }

    public InlineKeyboardMarkup getPage(int page) {
        return buildPage(page);
    }

    public InlineKeyboardMarkup getState(LanguagesInlineKeyboardControls control, String value) {
        if (control == LanguagesInlineKeyboardControls.START) {
            return getFirst();
        } else if (control == LanguagesInlineKeyboardControls.END) {
            return getLast();
        } else if (control == LanguagesInlineKeyboardControls.PAGE) {
            return getPage(Integer.parseInt( value ));
        } else {
            return getFirst();
        }
    }

    public void setAuto(boolean b) {
        this.auto_button = b;
    }

}
