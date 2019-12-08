package database.services;

import database.User;
import database.UserConfigs;
import database.dao.UserConfigsDAO;
import database.dao.UserDAO;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class UserService implements ItemService<User> {

    UserDAO userDAO;
    UserConfigsDAO userConfigsDAO;

    public UserService() {
        userDAO = new UserDAO();
        userConfigsDAO = new UserConfigsDAO();
    }

    @Override
    public void add(User user) {
        userDAO.add(user);
        userConfigsDAO.add(user.getUserConfigs());
    }

    @Override
    public void update(User newUser) {
        User oldUser = this.get(newUser.getId());

        if (oldUser == null) {
            userDAO.add(newUser);
        } else {
            if (!newUser.equals(oldUser)) {
                userDAO.update(newUser);
            }
            if (!newUser.getUserConfigs().equals(oldUser.getUserConfigs())) {
                userConfigsDAO.update(newUser.getUserConfigs());
            }
        }
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public User get(int id) {
        User user = userDAO.get(id);
        if (user == null) {
            return null;
        }
        UserConfigs userConfigs = userConfigsDAO.get(id);

        userConfigs.setUser(user);
        user.setUserConfigs(userConfigs);

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userDAO.getAll();

        UserConfigs userConfigs;
        for (User user : userList) {
            userConfigs = userConfigsDAO.get(user.getId());
            user.setUserConfigs(userConfigs);
        }

        return userList;
    }
}
