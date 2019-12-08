package gcp;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslateText {
    private String inputText;
    private TextLanguage inputLanguage;
    private TextLanguage outputLanguage;

    public TranslateText(String inputText, TextLanguage outputLanguage) {
        this(inputText, null, outputLanguage);
    }

    public TranslateText(String inputText, String outputLanguageCode) {
        this(inputText, null, TextLanguage.getLanguageByCode(outputLanguageCode));
    }

    public TranslateText(String inputText, TextLanguage inputLanguage, TextLanguage outputLanguage) {
        this.inputText = inputText;
        this.inputLanguage = inputLanguage;
        this.outputLanguage = outputLanguage;
    }

    public String getTranslate() {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation;

        if (inputLanguage == null) {
            translation = translate.translate(inputText, TranslateOption.targetLanguage(outputLanguage.getCode()));
        } else {
            translation = translate.translate(inputText, TranslateOption.sourceLanguage(inputLanguage.getCode()), TranslateOption.targetLanguage(outputLanguage.getCode()));
        }

        return translation.getTranslatedText();
    }

    public String getInputText() {
        return inputText;
    }

    public TextLanguage getInputLanguage() {
        if (inputLanguage == null) {
            inputLanguage = TranslateText.getLanguage(inputText);
        }

        return inputLanguage;
    }

    public static TextLanguage getLanguage(String text) {
        String lang = TranslateOptions.getDefaultInstance().getService().detect(text).getLanguage();
        return TextLanguage.getLanguageByCode(lang);
    }

    public TextLanguage getOutputLanguage() {
        return outputLanguage;
    }
}
