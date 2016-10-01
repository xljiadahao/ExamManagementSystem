package sg.edu.nus.iss.examsys.team3ft.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@RequestScoped
@Named
public class ExamScheduleView {
    
    @EJB private ExamBean examBean;
    @EJB private UserBean userBean;
    
    private String currentDate;
    private Collection<ExamSession> examSessions;
    private String role;
    private SimpleDateFormat sdfTime;
    private SimpleDateFormat sdfDateTime;

    @PostConstruct
    public void init() {
        String loginID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        User user = userBean.findUserByID(loginID);
        role = user.getGroupUser().getGroupUserPK().getGroupId();
        if("student".equalsIgnoreCase(role)) {
            List<ExamSession> examSessionList = new ArrayList<ExamSession>();
            for(ExamSession e : user.getExamSessionCollection()) {
                if (e.getExamEndTime().after(new Date())) {
                    examSessionList.add(e);
                }
            }
            examSessions = examSessionList;
        } else {
            examSessions = examBean.findFollowingExamSessions();
        }
        sdfTime = new SimpleDateFormat("dd-MMM-yyyy", Locale.UK);
        setSdfDateTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK));
        currentDate = sdfTime.format(new Date());
    }
    
    /**
     * @return the currentDate
     */
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * @return the examSessions
     */
    public Collection<ExamSession> getExamSessions() {
        return examSessions;
    }

    /**
     * @param examSessions the examSessions to set
     */
    public void setExamSessions(Collection<ExamSession> examSessions) {
        this.examSessions = examSessions;
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
     * @return the sdfTime
     */
    public SimpleDateFormat getSdfTime() {
        return sdfTime;
    }

    /**
     * @param sdfTime the sdfTime to set
     */
    public void setSdfTime(SimpleDateFormat sdfTime) {
        this.sdfTime = sdfTime;
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
    
}
