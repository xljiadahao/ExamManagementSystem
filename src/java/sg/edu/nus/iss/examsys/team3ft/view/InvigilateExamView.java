package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSection;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@SessionScoped
@Named
public class InvigilateExamView implements Serializable {
    
    @EJB private UserBean userBean;
    @EJB private ExamBean examBean;
    
    private String role;
    private SimpleDateFormat sdfDateTime;
    private ExamSession examSession;
    private Integer totalScore;
    private User loginUser;
    
    public String invigilateCurrentExam(Integer examSessionId) {
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        setLoginUser(userBean.findUserByID(loginID));
        role = userBean.findUserByID(loginID).getGroupUser().getGroupUserPK().getGroupId();
        sdfDateTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        //Integer examSessionId = Integer.parseInt((String)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("examSessionId"));
        examSession = examBean.findExamSessionById(examSessionId);
        totalScore = 0;
        for(ExamSection es : examSession.getFkExamId().getExamSectionCollection()) {
            for(Question q : es.getQuestionCollection()) {
                totalScore += q.getMark();
            }
        }
        return "/" + role + "/invigilateExam";
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

    /**
     * @return the examSession
     */
    public ExamSession getExamSession() {
        return examSession;
    }

    /**
     * @param examSession the examSession to set
     */
    public void setExamSession(ExamSession examSession) {
        this.examSession = examSession;
    }

    /**
     * @return the sdfDateTime
     */
    public SimpleDateFormat getSdfDateTime() {
        return sdfDateTime;
    }

    /**
     * @param sdfDateTime the sdfDateTime to set
     */
    public void setSdfDateTime(SimpleDateFormat sdfDateTime) {
        this.sdfDateTime = sdfDateTime;
    }

    /**
     * @return the totalScore
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore the totalScore to set
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * @return the loginUser
     */
    public User getLoginUser() {
        return loginUser;
    }

    /**
     * @param loginUser the loginUser to set
     */
    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }
    
}
