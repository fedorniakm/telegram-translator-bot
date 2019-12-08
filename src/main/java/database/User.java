package database;

public class User {

    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private UserConfigs userConfigs;

    public User(int id) {
        this(id, "", "", "", null);
    }

    public User(int id, String userName, String firstName, String lastName) {
        this(id, userName, firstName, lastName, null);
    }

    public User(int id, String userName, String firstName,
                String lastName, UserConfigs userConfigs) {
        this.id = id;

        this.userName = validator(userName);
        this.firstName = validator(firstName);
        this.lastName = validator(lastName);

        if (userConfigs != null) {
            this.userConfigs = userConfigs;
            this.userConfigs.setUser(this);
        } else {
            this.userConfigs = new UserConfigs(this);
        }

    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName == null) {
            this.userName = "";
        } else {
            this.userName = userName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            this.firstName = "";
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            this.lastName = "";
        } else {
            this.lastName = lastName;
        }
    }

    public UserConfigs getUserConfigs() {
        return userConfigs;
    }

    public void setUserConfigs(UserConfigs userConfigs) {
        if (userConfigs == null) {
            this.userConfigs = new UserConfigs(this);
        } else {
            this.userConfigs = userConfigs;
        }

    }

    private String validator(String s) {
        if (s == null) return "";
        else return s;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userConfigs=" + userConfigs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                userName.equals(user.userName) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName);
    }

    public boolean equals(org.telegram.telegrambots.meta.api.objects.User user) {
        return id == user.getId() &&
                userName.equals(user.getUserName()) &&
                firstName.equals(user.getFirstName()) &&
                lastName.equals(user.getLastName());
    }

}
