package bot.keyboard;

import gcp.SpeechLanguage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.lang.reflect.Array;
import java.util.*;

public class VoiceLanguagesInlineKeyboard {

    private String marker;
    private List<List<List<SpeechLanguage>>> pages;

    final int PAGE_ROWS = 7;

    class Row {
        private List<SpeechLanguage> row;

        public Row() {
            row = new ArrayList<>();
        }

        public int length() {
            if (row.size() == 0) {
                return 0;
            }

            int len = 0;
            for (SpeechLanguage sl : row) {
                len += sl.getName().length();
            }
            return len;
        }

        public int itemsCount() {
            return row.size();
        }

        public void add(SpeechLanguage sl) {
            row.add(sl);
        }

        public List<SpeechLanguage> getAsList() {
            return row;
        }

    }

    public VoiceLanguagesInlineKeyboard(String marker) {
        this.marker = marker;
        pages = new ArrayList<>();

        ArrayList<SpeechLanguage> languages = new ArrayList<>(Arrays.asList(SpeechLanguage.values()));

        Collections.sort(languages, (o1, o2) -> {
            String o1Name = o1.getName();
            String o2Name = o2.getName();
            int res = String.CASE_INSENSITIVE_ORDER.compare(o1Name, o2Name);
            if (res == 0) {
                res = o1Name.compareTo(o2Name);
            }
            return res;
        });

        List<List<SpeechLanguage>> rows = new ArrayList<>();
        final int ROW_CHAR_LENGTH = 40;
        Row row = new Row();
        for (SpeechLanguage currentLang : languages) {
            if ((row.length() + currentLang.getName().length()) <= ROW_CHAR_LENGTH && row.itemsCount() < 3) {
                row.add(currentLang);
            } else {
                rows.add(row.getAsList());
                row = new Row();
                row.add(currentLang);

                if (rows.size() == PAGE_ROWS) {
                    pages.add(rows);
                    rows = new ArrayList<>();
                }
            }
        }
        pages.add(rows);
    }

    public InlineKeyboardMarkup getFirst() {
        return buildPage(0);
    }

    public InlineKeyboardMarkup getLast() {
        return buildPage( pages.size() - 1 );
    }

    public InlineKeyboardMarkup getPage(int page) {
        return buildPage(page);
    }

    private InlineKeyboardMarkup buildPage(int pageNum) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        if (!(pageNum == 0)) {
            row.add(new InlineKeyboardButton().setText("<<<").setCallbackData(marker +
                    LanguagesInlineKeyboardControls.SEPARATOR +
                    LanguagesInlineKeyboardControls.START));
            row.add(new InlineKeyboardButton().setText("<").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE + "_" + (pageNum - 1)));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        row.add(new InlineKeyboardButton().setText("Назад").setCallbackData(marker
                + LanguagesInlineKeyboardControls.SEPARATOR
                + LanguagesInlineKeyboardControls.BACK));
        if (! (pageNum == pages.size() - 1)) {
            row.add(new InlineKeyboardButton().setText(">").setCallbackData(
                    marker
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + LanguagesInlineKeyboardControls.PAGE
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + (pageNum + 1)
            ));
            row.add(new InlineKeyboardButton().setText(">>>").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.END));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        rows.add(row);

        row = new ArrayList<>();
        List<List<SpeechLanguage>> page = pages.get(pageNum);
        for (List<SpeechLanguage> rowLine : page) {
            for (SpeechLanguage item : rowLine) {
                row.add(new InlineKeyboardButton()
                        .setText( item.getName() )
                        .setCallbackData(marker
                                + LanguagesInlineKeyboardControls.SEPARATOR
                                + LanguagesInlineKeyboardControls.LANG
                                + LanguagesInlineKeyboardControls.SEPARATOR
                                + item.getCode()));
            }
            rows.add(row);
            row = new ArrayList<>();
        }

        if (!(pageNum == 0)) {
            row.add(new InlineKeyboardButton().setText("<<<").setCallbackData(marker +
                    LanguagesInlineKeyboardControls.SEPARATOR +
                    LanguagesInlineKeyboardControls.START));
            row.add(new InlineKeyboardButton().setText("<").setCallbackData(marker
                    + LanguagesInlineKeyboardControls.SEPARATOR
                    + LanguagesInlineKeyboardControls.PAGE + "_" + (pageNum - 1)));
        } else {
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
            row.add(new InlineKeyboardButton().setText(" ").setCallbackData(" "));
        }
        row.add(new InlineKeyboardButton().setText("Назад").setCallbackData(marker
                + LanguagesInlineKeyboardControls.SEPARATOR
                + LanguagesInlineKeyboardControls.BACK));
        if (! (pageNum == pages.size() - 1)) {
            row.add(new InlineKeyboardButton().setText(">").setCallbackData(
                    marker
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + LanguagesInlineKeyboardControls.PAGE
                            + LanguagesInlineKeyboardControls.SEPARATOR
                            + (pageNum + 1)
            ));
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
}
