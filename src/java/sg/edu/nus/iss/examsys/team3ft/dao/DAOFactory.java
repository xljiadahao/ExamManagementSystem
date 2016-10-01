package sg.edu.nus.iss.examsys.team3ft.dao;

import sg.edu.nus.iss.examsys.team3ft.dao.impl.UserDAOImpl;


public class DAOFactory {

    private static UserDAO userDAO = null;

    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

}
