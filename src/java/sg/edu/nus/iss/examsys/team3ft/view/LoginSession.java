package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUser;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@SessionScoped
@Named
public class LoginSession implements Serializable {

    @EJB private UserBean userBean;
    @EJB private ExamBean examBean;
    
    private String loginID;
    private String password;
    
    private String userName;
    
    private String newPassword;
    private List<ExamSession> enrolledLectures;

    /**
     * @return the loginID
     */
    public String getLoginID() {
        return loginID;
    }

    /**
     * @param loginID the loginID to set
     */
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String login() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
        try {
            System.out.println("loginID: " + loginID);
            req.login(loginID, password);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loginID", loginID);
        } catch (ServletException ex) {
            return ("/error");
        }
        userBean.refreshCache(loginID);
        GroupUser groupUser = userBean.findGroupByUserID(loginID);
        String group = groupUser.getGroupUserPK().getGroupId();
        userName = groupUser.getUser().getUserName();
        //userName = userBean.findUserByID(loginID).getUserName(); 
        String isNewUser = groupUser.getUser().getIsNewUser();
        List<ExamSession> exams = examBean.findCurrentExams();
        if ("student".equalsIgnoreCase(groupUser.getGroupUserPK().getGroupId())) {
            enrolledLectures = new ArrayList<ExamSession>();
            for(ExamSession e : exams) {
                for(User u : e.getUserCollection()) {
                    if(loginID.equals(u.getUserId())) {
                        enrolledLectures.add(e);
                        break;
                    }
                }
            }
        } else {
            enrolledLectures = exams;
        }
//        User user = userBean.findUserByIDUsingNativeQuery(loginID);
//        String group = user.getGroupUser().getGroupUserPK().getGroupId();
//        userName = user.getUserName();
//        String isNewUser = user.getIsNewUser();
        System.out.println("isNewUser: " + isNewUser);
        System.out.println("enrolledLectures.size(): " + enrolledLectures.size());
        if("Y".equalsIgnoreCase(isNewUser)) {
            return ("/common/changePwd");
        } else {
            return ("/" + group + "/" + group + "MainPage?faces-redirect=true");
        }
    }

    public String logout() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
        req.getSession().invalidate();
        return ("/login?faces-redirect=true");
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     * @return the enrolledLectures
     */
    public List<ExamSession> getEnrolledLectures() {
        return enrolledLectures;
    }

    /**
     * @param enrolledLectures the enrolledLectures to set
     */
    public void setEnrolledLectures(List<ExamSession> enrolledLectures) {
        this.enrolledLectures = enrolledLectures;
    }
    
    public String changePwd() {
        //GroupUser groupUser = userBean.findGroupByUserID(loginID);
        System.out.println("changePwd for loginID " + loginID);
        User user = userBean.findUserByID(loginID);
        boolean changePwdResult = userBean.changePwd(user, newPassword);
        if (changePwdResult == true) {
            System.out.println("changePwd for loginID " + loginID + " success");
        } else {
            System.out.println("changePwd for loginID " + loginID + " fail");
        }
        return ("/" + user.getGroupUser().getGroupUserPK().getGroupId() + "/" + user.getGroupUser().getGroupUserPK().getGroupId() + "MainPage?faces-redirect=true");
    }

}
