package gcp;

public enum TextLanguage {
    AFRIKAANS("Afrikaans", "af", ""),
    ALBANIAN("Albanian", "sq", ""),
    AMHARIC("Amharic", "am", ""),
    ARABIC("Arabic", "ar", ""),
    ARMENIAN("Armenian", "hy", ""),
    AZERBAIJANI("Azerbaijani", "az", ""),
    BASQUE("Basque", "eu", ""),
    BELARUSIAN("Belarusian", "be", ""),
    BENGALI("Bengali", "bn", ""),
    BOSNIAN("Bosnian", "bs", ""),
    BULGARIAN("Bulgarian", "bg", ""),
    CATALAN("Catalan", "ca", ""),
    CEBUANO("Cebuano", "ceb", ""),
    CHINESE_SIMPLIFIED("Chinese (Simplified)", "zh", ""),
    CHINESE_TRADITIONAL("Chinese (Traditional)", "zh-TW", ""),
    CORSICAN("Corsican", "co", ""),
    CZECH("Czech", "cs", ""),
    DANISH("Danish", "da", ""),
    DUTCH("Dutch", "nl", ""),
    ENGLISH("English", "en", ""),
    ESPERANTO("Esperanto", "eo", ""),
    ESTONIAN("Estonian", "et", ""),
    FINNISH("Finnish", "fi", ""),
    FRENCH("French", "fr", ""),
    FRISIAN("Frisian", "fy", ""),
    GALICIAN("Galician", "gl", ""),
    GEORGIAN("Georgian", "ka", ""),
    GERMAN("German", "de", ""),
    GREEK("Greek", "el", ""),
    GUJARATI("Gujarati", "gu", ""),
    HAITIAN_GREOLE("Haitian Creole", "ht", ""),
    HAUSA("Hausa", "ha", ""),
    HAWAIIAN("Hawaiian", "haw", ""),
    HEBREW("Hebrew", "he", ""),
    HINDI("Hindi", "hi", ""),
    HMONG("Hmong", "hmn", ""),
    HUNGARIAN("Hungarian", "hu", ""),
    ICELANDIC("Icelandic", "is", ""),
    IGBO("Igbo", "ig", ""),
    INDONESIAN("Indonesian", "id", ""),
    IRISH("Irish", "ga", ""),
    ITALIAN("Italian", "it", ""),
    JAPANESE("Japanese", "ja", ""),
    JAVANESE("Javanese", "jw", ""),
    KANNADA("Kannada", "kn", ""),
    KAZAKH("Kazakh", "kk", ""),
    KHMER("Khmer", "km", ""),
    KOREAN("Korean", "ko", ""),
    KURDISH("Kurdish", "ku", ""),
    KYRGYZ("Kyrgyz", "ky", ""),
    LAO("Lao", "lo", ""),
    LATIN("Latin", "la", ""),
    LATVIAN("Latvian", "lv", ""),
    LITHUANIAN("Lithuanian", "lt", ""),
    LUXEMBOURGISH("Luxembourgish", "lb", ""),
    MACEDONIAN("Macedonian", "mk", ""),
    MALAGASY("Malagasy", "mg", ""),
    MALAY("Malay", "ms", ""),
    MALAYALAM("Malayalam", "ml", ""),
    MALTESE("Maltese", "mt", ""),
    MAORI("Maori", "mi", ""),
    MARATHI("Marathi", "mr", ""),
    MONGOLIAN("Mongolian", "mn", ""),
    MYANMAR_BURMESE("Myanmar (Burmese)", "my", ""),
    NEPALI("Nepali", "ne", ""),
    NORWEGIAN("Norwegian", "no", ""),
    NYANJA_CHICHEWA("Nyanja (Chichewa)", "ny", ""),
    PASHTO("Pashto", "ps", ""),
    PERSIAN("Persian", "fa", ""),
    POLISH("Polish", "pl", ""),
    PORTUGUESE_PORTUGAL_BRAZIL("Portuguese (Portugal, Brazil)", "pt", ""),
    PUNJABI("Punjabi", "pa", ""),
    ROMANIAN("Romanian", "ro", ""),
    RUSSIAN("Russian", "ru", ""),
    SAMOAN("Samoan", "sm", ""),
    SCOTS_GAELIC("Scots Gaelic", "gd", ""),
    SERBIAN("Serbian", "sr", ""),
    SESOTHO("Sesotho", "st", ""),
    SHONA("Shona", "sn", ""),
    SINDHI("Sindhi", "sd", ""),
    SINHALA_SINHALESE("Sinhala (Sinhalese)", "si", ""),
    SLOVAK("Slovak", "sk", ""),
    SLOVENIAN("Slovenian", "sl", ""),
    SOMALI("Somali", "so", ""),
    SPANISH("Spanish", "es", ""),
    SUNDANESE("Sundanese", "su", ""),
    SWAHILI("Swahili", "sw", ""),
    SWEDISH("Swedish", "sv", ""),
    TAGALOG_FILIPINO("Tagalog (Filipino)", "tl", ""),
    TAJIK("Tajik", "tg", ""),
    TAMIL("Tamil", "ta", ""),
    TELUGU("Telugu", "te", ""),
    THAI("Thai", "th", ""),
    TURKISH("Turkish", "tr", ""),
    UKRAINIAN("Ukrainian", "uk", ""),
    URDU("Urdu", "ur", ""),
    UZBEK("Uzbek", "uz", ""),
    VIETNAMESE("Vietnamese", "vi", ""),
    WELSH("Welsh", "cy", ""),
    XHOSA("Xhosa", "xh", ""),
    YIDDISH("Yiddish", "yi", ""),
    YORUBA("Yoruba", "yo", ""),
    ZULU("Zulu", "zu", "");

    private  String name;
    private String code;
    private String flagUTF8;

    TextLanguage(String name, String code, String flagUTF8) {
        this.name = name;
        this.code = code;
        this.flagUTF8 = flagUTF8;
    }

    public static boolean hasLang(String code) {
        for (TextLanguage lang : TextLanguage.values()) {
            if (code.equals( lang.getCode() )) {
                return true;
            }
        }
        return false;
    }

    public static TextLanguage getLanguageByCode(String code) {
        for (TextLanguage lang : TextLanguage.values()) {
            if (code.equals( lang.getCode() )) {
                return lang;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getFlagUTF8() {
        return flagUTF8;
    }

}
