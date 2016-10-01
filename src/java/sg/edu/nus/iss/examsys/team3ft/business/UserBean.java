/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import sg.edu.nus.iss.examsys.team3ft.dao.DAOFactory;
import sg.edu.nus.iss.examsys.team3ft.model.ExamQuestionAnswer;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUser;
import sg.edu.nus.iss.examsys.team3ft.model.User;
import sg.edu.nus.iss.examsys.team3ft.utilities.DBUtility;

/**
 *
 * @author Lei
 */
@ApplicationException(rollback=true)
@Stateless
public class UserBean {
    
    @PersistenceContext EntityManager em;
    @PersistenceUnit EntityManagerFactory emf;
    
    public GroupUser findGroupByUserID(String userID) {
        TypedQuery<GroupUser> query = em.createNamedQuery("GroupUser.findByUserId", GroupUser.class);
        query.setParameter("userId", userID);
        GroupUser groupUser = query.getSingleResult();
        // avoid lazy-loading
        groupUser.getUser();
        return groupUser;
    }
    
    public User findUserByID(String userID) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUserId", User.class);
        query.setParameter("userId", userID);
        User user = query.getSingleResult();
        user.getGroupUser();
        user.getModuleCollection();
        user.getModuleCollection1();
        user.getExamSessionCollection();
        //Collection<ExamQuestionAnswer> examQuestionAnswers = user.getExamQuestionAnswerCollection();
        return user;
    }
    
    public User refreshUserExamQuestionAnswers(String userID) {
        TypedQuery<User> query = em.createNamedQuery("User.findByUserId", User.class);
        query.setParameter("userId", userID);
        User user = query.getSingleResult();
        em.refresh(user);
        for(ExamQuestionAnswer eqa : user.getExamQuestionAnswerCollection()) {
            eqa.getFkQuestionId();
        }
        return user;
    }
    
    public User findUserByIDUsingNativeQuery(String userID) {
        String sqlString ="select USER_ID, PASSWORD, USER_NAME, IS_NEW_USER from USER where USER_ID=?";
        Query query = em.createNativeQuery(sqlString, User.class);
        query.setParameter(1, userID);
        User user = (User)query.getSingleResult();
        user.getGroupUser();
        return user;
    }
    
    //clean the cache for the specific user
    public void refreshCache(String userID) {
        emf.getCache().evict(User.class, userID); 
    }
    
    public boolean changePwd(User user, String pwd) {
//        user.setPassword(pwd);
//        user.setIsNewUser("N");
//        em.merge(user);
        Connection conn = DBUtility.getConnection();
        try {
            conn.setAutoCommit(false);
            DAOFactory.getUserDAO().changePwd(conn, user, pwd);
            conn.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public boolean createUser(User user, String[] relatedModules) {
        Connection conn = DBUtility.getConnection();
        try {
            conn.setAutoCommit(false);
            DAOFactory.getUserDAO().create(conn, user);
            DAOFactory.getUserDAO().createUserGroupMapping(conn, user.getGroupUser());
            if ("lecturer".equalsIgnoreCase(user.getGroupUser().getGroupUserPK().getGroupId().trim())) {
                DAOFactory.getUserDAO().createLecturerModuleMapping(conn, user, relatedModules);
            } else if ("student".equalsIgnoreCase(user.getGroupUser().getGroupUserPK().getGroupId().trim())) {
                DAOFactory.getUserDAO().createStudentModuleMapping(conn, user, relatedModules);
            }
            conn.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public void createUserJPA(GroupUser g) {
        try {
            em.persist(g);
        } catch (ConstraintViolationException ex) {
            System.out.println("UserRegistrationView ConstraintViolation Start: ----------------------------");
            ex.printStackTrace();
            for (ConstraintViolation cv: ex.getConstraintViolations()) {
                System.out.println(cv.getMessage());
            }
            System.out.println("UserRegistrationView ConstraintViolation End:   ----------------------------");
        }
    }
    
}
