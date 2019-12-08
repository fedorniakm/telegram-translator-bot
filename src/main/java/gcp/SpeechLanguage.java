package gcp;

public enum SpeechLanguage {
    AFRIKAANS("", "af-ZA", "Afrikaans"),
    AMHARIC("", "am-ET", "Amharic"),
    ARMENIAN("", "hy-AM", "Armenian"),
    AZERBAIJANI("", "az-AZ", "Azerbaijani"),
    INDONESIAN("", "df-ID", "Indonesian"),
    MALAY("", "ms-MY", "Malay"),
    BENGALI_BANGLADESH("", "bn-BD", "Bengali (Bangladesh)"),
    BENGALI_INDIA("", "bn-IN", "Bengali (India)"),
    CATALAN("", "ca-ES", "Catalan"),
    CZECH("", "cs-CZ", "Czech"),
    DANISH("", "da-DK", "Danish"),
    GERMAN("", "de-DE", "German"),
    ENG_AUSTRALIA("", "en-AU", "English (Australia)"),
    ENG_CANADA("", "en-CA", "English (Canada)"),
    ENG_GHANA("", "en-GH", "English (Ghana)"),
    ENG_UK("", "en-GB", "English (United Kingdom)"),
    ENG_INDIA("", "en-IN", "English (India)"),
    ENG_IRELAND("", "en-IE", "English (Ireland)"),
    ENG_KENYA("", "en-KE", "English (Kenya)"),
    ENG_NEW_ZEALAND("", "en-NZ", "English (New Zealand)"),
    ENG_NIGERIA("", "en-NG", "English (Nigeria)"),
    ENG_PHILIPPINES("", "en-PH", "English (Philippines)"),
    ENG_SINGAPORE("", "en-SG", "English (Singapore)"),
    ENG_SOUTH_AFRICA("", "en-ZA", "English (South Africa)"),
    ENG_TANZANIA("", "en-TZ", "English (Tanzania)"),
    ENG_US("", "en-US", "English (United States)"),
    SPANISH_ARGENTINA("", "es-AR", "Spanish (Argentina)"),
    SPANISH_BOLIVIA("", "es-BO", "Spanish (Bolivia)"),
    SPANISH_CHILE("", "es-CL", "Spanish (Chile)"),
    SPANISH_COLOMBIA("", "es-CO", "Spanish (Colombia)"),
    SPANISH_COSTA_RICA("", "es-CR", "Spanish (Costa Rica)"),
    SPANISH_ECUADOR("", "es-EC", "Spanish (Ecuador)"),
    SPANISH_EL_SALVADOR("", "es-SV", "Spanish (El Salvador)"),
    SPANISH_SPAIN("", "es-ES", "Spanish (Spain)"),
    SPANISH_US("", "es-US", "Spanish (United States)"),
    SPANISH_GUATEMALA("", "es-GT", "Spanish (Guatemala)"),
    SPANISH_HONDURAS("", "es-HN", "Spanish (Honduras)"),
    SPANISH_MEXICO("", "es-MX", "Spanish (Mexico)"),
    SPANISH_NICARAGUA("", "es-NI", "Spanish (Nicaragua)"),
    SPANISH_PANAMA("", "es-PA", "Spanish (Panama)"),
    SPANISH_PARAGUAY("", "es-PY", "Spanish (Paraguay)"),
    SPANISH_PERU("", "es-PE", "Spanish (Peru)"),
    SPANISH_PUERTO_RICO("", "es-PR", "Spanish (Puerto Rico)"),
    SPANISH_DOMINICAN_REPUBLIC("", "es-DO", "Spanish (Dominican Republic)"),
    SPANISH_URUGUAY("", "es-UY", "Spanish (Uruguay)"),
    SPANISH_VENEZUELA("", "es-VE", "Spanish (Venezuela)"),
    BASQUE("", "eu-ES", "Basque"),
    FELIPINO("", "fil-PH", "Filipino"),
    FRENCH_CANADA("", "fr-CA", "French (Canada)"),
    FRENCH_FRANCE("", "fr-FR", "French (France)"),
    GALICIAN("", "gl-ES", "Galician"),
    GEORGIAN("", "ka-GE", "Georgian"),
    GUJARATI("", "gu-IN", "Gujarati"),
    CROATIAN("", "hr-HR", "Croatian"),
    ZULU("", "zu-ZA", "Zulu"),
    ICELANDIC("", "is-IS", "Icelandic"),
    ITALIAN("", "it-IT", "Italian"),
    JAVANESE("", "jv-ID", "Javanese"),
    KANNADA("", "kn-IN", "Kannada"),
    KHMER("", "km-KH", "Khmer"),
    LAO("", "lo-LA", "Lao"),
    LATVIAN("", "lv-LV", "Latvian"),
    LITHUANIAN("", "lt-LT", "Lithuanian"),
    HUNGARIAN("", "hu-HU", "Hungarian"),
    MALAYALAM("", "ml-IN", "Malayalam"),
    MARATHI("", "mr-IN", "Marathi"),
    DUTCH("", "nl-NL", "Dutch"),
    NEPALI("", "ne-NP", "Nepali"),
    NORWEGIAN_BOKMAL("", "nb-NO", "Norwegian Bokmål"),
    POLISH("", "pl-PL", "Polish"),
    PORTUGUESE_BRAZIL("", "pt-BR", "Portuguese (Brazil)"),
    PORTUGUESE_PORTUGAL("", "pt-PT", "Portuguese (Portugal)"),
    ROMANIAN("", "ro-RO", "Romanian"),
    SINHALA("", "si-LK", "Sinhala"),
    SLOVAK("", "sk-SK", "Slovak"),
    SLOVENIAN("", "sl-SL", "Slovenian"),
    SUNDANESE("", "su-ID", "Sundanese"),
    SWAHILI_TANZANIA("", "sw-TZ", "Swahili (Tanzania)"),
    SWAHILI_KENYA("", "sw-KE", "Swahili (Kenya)"),
    FINNISH("", "fi-FI", "Finnish"),
    SWEDISH("", "sv-SE", "Swedish"),
    TAMIL_INDIA("", "ta-IN", "Tamil (India)"),
    TAMIL_SINGAPORE("", "ta-SG", "Tamil (Singapore)"),
    TAMIL_SRI_LANKA("", "ta-LK", "Tamil (Sri Lanka)"),
    TANIL_MALAYSIA("", "ta-MY", "Tamil (Malaysia)"),
    TEUGU("", "te-IN", "Telugu"),
    VIETNAMESE("", "vi-VN", "Vietnamese"),
    TURKISH("", "tr-TR", "Turkish"),
    URDU_PAKISTAN("", "ur-PK", "Urdu (Pakistan)"),
    URDU_INDIA("", "ur-IN", "Urdu (India)"),
    GREEK("", "el-GR", "Greek"),
    BULGARIAN("", "bg-BG", "Bulgarian"),
    RUSSIAN("", "ru-RU", "Russian"),
    SERBIAN("", "sr-RS", "Serbian"),
    UKRAINIAN("", "uk-UA", "Ukrainian"),
    HEBREW("", "he-IL", "Hebrew"),
    ARABIC_ISRAEL("", "ar-IL", "Arabic (Israel)"),
    ARABIC_JORDAN("", "ar-JO", "Arabic (Jordan)"),
    ARABIC_UAE("", "ar-AE", "Arabic (United Arab Emirates)"),
    ARABIC_BAHRAIN("", "ar-BH", "Arabic (Bahrain)"),
    ARABIC_ALGERIA("", "ar-DZ", "Arabic (Algeria)"),
    ARABIC_SAUDI_ARABIA("", "ar-SA", "Arabic (Saudi Arabia)"),
    ARABIC_IRAQ("", "ar-IQ", "Arabic (Iraq)"),
    ARABIC_KUWAIT("", "ar-KW", "Arabic (Kuwait)"),
    ARABIC_MOROCCO("", "ar-MA", "Arabic (Morocco)"),
    ARABIC_TUNISIA("", "ar-TN", "Arabic (Tunisia)"),
    ARABIC_OMAN("", "ar-OM", "Arabic (Oman)"),
    ARABIC_PALESTINE("", "ar-PS", "Arabic (State of Palestine)"),
    ARABIC_QATAR("", "ar-QA", "Arabic (Qatar)"),
    ARABIC_LEBANON("", "ar-LB", "Arabic (Lebanon)"),
    ARABIC_EGYPT("", "ar-EG", "Arabic (Egypt)"),
    PERSIAN("", "fa-IR", "Persian"),
    HINDI("", "hi-IN", "Hindi"),
    THAI("", "th-TH", "Thai"),
    KOREAN("", "ko-KR", "Korean"),
    CHINESE_MANDARIN_TRADITIONAL_TAIWAN("", "zh-TW", "Chinese, Mandarin (Traditional, Taiwan)"),
    CHINESE_CANTONESE_TRADITIONAL_HONG_KONG("", "yue-Hant-HK", "Chinese, Cantonese (Traditional, Hong Kong)"),
    JAPANESE("", "ja-JP", "Japanese"),
    CHINESE_MANDARIN_SIMPLIFIED_HONG_KONG("", "zh-HK", "Chinese, Mandarin (Simplified, Hong Kong)"),
    CHINESE_MANDARIN_SIMPLIFIED_CHINA("", "zh", "Chinese, Mandarin (Simplified, China)");

    private String name;
    private String code;
    private String originalName;

    SpeechLanguage(String originalName, String code, String name) {
        this.name = name;
        this.code = code;
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getOriginalName() {
        return name;
    }

    public static SpeechLanguage getLanguageByCode(String code) {
        for (SpeechLanguage lang : SpeechLanguage.values()) {
            if (code.equals( lang.getCode() )) {
                return lang;
            }
        }
        return null;
    }
}
