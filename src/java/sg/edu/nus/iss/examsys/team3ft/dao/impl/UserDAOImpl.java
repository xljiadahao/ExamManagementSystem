/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sg.edu.nus.iss.examsys.team3ft.dao.UserDAO;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUser;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
public class UserDAOImpl implements UserDAO {

    private static final String SQL_CREATE_USER = "insert into user(USER_ID, USER_NAME, IS_NEW_USER) values(?,?,?)";
    private static final String SQL_CREATE_USER_GROUP_MAPPING = "insert into group_user(GROUP_ID, USER_ID) values(?,?)";
    private static final String SQL_CHANGE_PWD = "update user set PASSWORD=?, IS_NEW_USER=? where USER_ID=?";
    private static final String SQL_LECTURER_MODULE_MAPPING = "insert into lecturer_modules_taught(FK_LECTURER_ID, FK_MODULE_CODE) values(?,?)";
    private static final String SQL_STUDENT_MODULE_MAPPING = "insert into student_modules_enrolled(FK_STUDENT_ID, FK_MODULE_CODE) values(?,?)";

    @Override
    public void create(Connection conn, User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_CREATE_USER);
        ps.setString(1, user.getUserId());
        ps.setString(2, user.getUserName());
        ps.setString(3, "Y");
        ps.executeUpdate();
    }

    @Override
    public void createUserGroupMapping(Connection conn, GroupUser groupUser) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_CREATE_USER_GROUP_MAPPING);
        ps.setString(1, groupUser.getGroupUserPK().getGroupId());
        ps.setString(2, groupUser.getGroupUserPK().getUserId());
        ps.executeUpdate();
    }

    @Override
    public void changePwd(Connection conn, User user, String pwd) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_CHANGE_PWD);
        ps.setString(1, pwd);
        ps.setString(2, "N");
        ps.setString(3, user.getUserId());
        ps.executeUpdate();
    }

    @Override
    public void createLecturerModuleMapping(Connection conn, User user, String[] modulesTaught) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_LECTURER_MODULE_MAPPING);
        for (int i=0; i<modulesTaught.length; i++) {
            ps.setString(1, user.getUserId());
            ps.setString(2, modulesTaught[i]);
            ps.addBatch();
        }
        ps.executeBatch();
    }

    @Override
    public void createStudentModuleMapping(Connection conn, User user, String[] modulesEnrolled) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(SQL_STUDENT_MODULE_MAPPING);
        for (int i=0; i<modulesEnrolled.length; i++) {
            ps.setString(1, user.getUserId());
            ps.setString(2, modulesEnrolled[i]);
            ps.addBatch();
        }
        ps.executeBatch();
    }

}
