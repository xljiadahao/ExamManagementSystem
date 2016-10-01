package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.ModuleBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUser;
import sg.edu.nus.iss.examsys.team3ft.model.GroupUserPK;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@RequestScoped
@Named
public class UserRegistrationView implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    //input
    private String userId;
    private String userName;
    private String role;
    private String[] selectedModules; 
    
    @EJB private UserBean userBean;
    @EJB private ModuleBean moduleBean;
    
    //output
    private List<Module> modules;
    
    @PostConstruct
    public void init() {
        modules = moduleBean.findAllModules();
    }

    public String[] getSelectedModules() {
        return selectedModules;
    }
    
    public void setSelectedModules(String[] selectedModules) {
//        if (selectedModules != null && selectedModules.length > 0) {
//            System.out.println("selectedModules: " + selectedModules[0]);
//        } else {
//            System.out.println("selectedModules: nothing");
//        }
        this.selectedModules = selectedModules;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    public String userRegister() {
        System.out.println("userRegister input start -----------------");
        System.out.println("userId: " + userId);
        System.out.println("userName: " + userName);
        System.out.println("role: " + role);
        for (int i = 0; i < selectedModules.length; i++) {
            System.out.println("selectedModules: " + selectedModules[i]);
        }
        System.out.println("userRegister input end   -----------------");
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setIsNewUser("Y");
        //user.setPassword("123456");
        GroupUser groupUser = new GroupUser();
        GroupUserPK groupUserPK = new GroupUserPK();
        groupUserPK.setGroupId(role);
        groupUserPK.setUserId(userId);
        groupUser.setGroupUserPK(groupUserPK);
        user.setGroupUser(groupUser);
        //groupUser.setUser(user);
        //userBean.createUserJPA(groupUser);
        boolean result = userBean.createUser(user, selectedModules);
        String registerUserResult = null;
        if (result == true) {
            registerUserResult = "Register " + role + " " + userName + " success";
        } else {
            registerUserResult = "Register " + role + " " + userName + " fail";
        }
        clear();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System User Register", registerUserResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return null;
    }
    
    private void clear() {
        userId = null;
        userName = null;
        role = null;
        selectedModules = null;
    }
  
}
