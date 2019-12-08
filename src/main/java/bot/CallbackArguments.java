package bot;

import bot.keyboard.LanguagesInlineKeyboardControls;

public class CallbackArguments {
    private String marker;
    private LanguagesInlineKeyboardControls control;
    private String value;

    public CallbackArguments(String data) {
        String[] args = this.getArgs(data);

        System.out.println(data);

        this.marker = args[0];
        this.control = null;
        this.value = null;

        if (args.length >= 2) {
            this.control = LanguagesInlineKeyboardControls.getControl(args[1]);
        }

        if (args.length >= 3) {
            this.value = args[2];
        }

    }

    public String getMarker() {
        return marker;
    }

    public LanguagesInlineKeyboardControls getControl() {
        return control;
    }

    public String getValue() {
        return value;
    }

    private String[] getArgs(String data) {
        return data.split(LanguagesInlineKeyboardControls.SEPARATOR.value());
    }
}
