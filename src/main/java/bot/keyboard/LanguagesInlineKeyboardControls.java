package bot.keyboard;

public enum LanguagesInlineKeyboardControls {
    AUTO("auto"),

    LANG("lang"),

    PAGE("page"),
    START("start"),
    END("end"),
    SEPARATOR("_"),
    LOOPBACK(" "),

    BACK("back"),
    LANGS("langs");

    private String marker;

    LanguagesInlineKeyboardControls(String marker) {
        this.marker = marker;
    }

    public static LanguagesInlineKeyboardControls getControl(String marker) {
        for (LanguagesInlineKeyboardControls c : values()) {
            if (c.marker.equals(marker)) {
                return c;
            }
        }
        return null;
    }

    public String value() {
        return marker;
    }

    @Override
    public String toString() {
        return marker;
    }
}
