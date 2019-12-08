package database;

public class UserConfigs {
    private String interfaceLang;
    private String fromLang;
    private String toLang;
    private String speechLang;

    private User user;

    public UserConfigs(User user) {
        this("", "", "", "", user);
    }

    public UserConfigs(String interfaceLang, String fromLang, String toLang, String speechLang) {
        this(interfaceLang, fromLang, toLang, speechLang, null);
    }

    public UserConfigs(String interfaceLang, String fromLang, String toLang, String speechLang, User user) {
        this.interfaceLang = interfaceLang;
        this.fromLang = fromLang;
        this.toLang = toLang;
        this.speechLang = speechLang;
        this.user = user;
    }

    public String getInterfaceLang() {
        return interfaceLang;
    }

    public void setInterfaceLang(String interfaceLang) {
        if (interfaceLang == null) {
            this.interfaceLang = "";
        } else {
            this.interfaceLang = interfaceLang;
        }
    }

    public String getFromLang() {
        return fromLang;
    }

    public void setFromLang(String fromLang) {
        if (fromLang == null) {
            this.fromLang = "";
        } else
            this.fromLang = fromLang;
    }

    public String getToLang() {
        return toLang;
    }

    public void setToLang(String toLang) {
        if (toLang == null) {
            this.toLang = "";
        } else
            this.toLang = toLang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpeechLang() {
        return speechLang;
    }

    public void setSpeechLang(String speechLang) {
        if (speechLang == null) {
            this.speechLang = "";
        } else {
            this.speechLang = speechLang;
        }
    }

    @Override
    public String toString() {
        return "UserConfigs{" +
                "interfaceLang='" + interfaceLang + '\'' +
                ", fromLang='" + fromLang + '\'' +
                ", toLang='" + toLang + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserConfigs that = (UserConfigs) o;
        return interfaceLang.equals(that.interfaceLang) &&
                fromLang.equals(that.fromLang) &&
                toLang.equals(that.toLang);
    }
}
