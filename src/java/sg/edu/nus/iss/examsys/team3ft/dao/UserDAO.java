/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.dao;

import java.sql.Connection;
import java.sql.SQLException;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUser;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
public interface UserDAO {
    
    public void create(Connection conn, User user) throws SQLException;
    public void createUserGroupMapping(Connection conn, GroupUser groupUser) throws SQLException;
    public void changePwd(Connection conn, User user, String pwd) throws SQLException;
    public void createLecturerModuleMapping(Connection conn, User user, String[] modulesTaught) throws SQLException;
    public void createStudentModuleMapping(Connection conn, User user, String[] modulesEnrolled) throws SQLException;
    
}
