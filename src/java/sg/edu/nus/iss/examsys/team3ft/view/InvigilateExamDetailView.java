package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamQuestionAnswer;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSection;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Chandrakala
 */
@SessionScoped
@Named
public class InvigilateExamDetailView implements Serializable {
    
    @EJB private UserBean userBean;
    @EJB private ExamBean examBean;
    
    private PieChartModel pieChartModelQuestionNo;
    private PieChartModel pieChartModelQuestionScore;
    private User student;
    private String invigilaterRole;
    private ExamSession examSession;
    
    private Integer totalQuestionNo;
    private Long examQuestionNoAnswered;
    
    private Integer totalScore;
    private Integer answeredScores;
    
    public String forwordExamDetail(String userId, Integer examSessionId) {
        setStudent(getUserBean().findUserByID(userId));
        //invigilaterRole = student.getGroupUser().getGroupUserPK().getGroupId();
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        setInvigilaterRole(getUserBean().findUserByID(loginID).getGroupUser().getGroupUserPK().getGroupId());
        setExamSession(getExamBean().refresh(examSessionId));
        //pieChartModelQuestionNo Model
        setExamQuestionNoAnswered(getExamBean().countUserAnswer(userId, examSessionId));
        setTotalQuestionNo((Integer) 0);
        for(ExamSection e : getExamSession().getFkExamId().getExamSectionCollection()) {
            setTotalQuestionNo((Integer) (getTotalQuestionNo() + e.getQuestionCollection().size()));
        }
        setPieChartModelQuestionNo(new PieChartModel());
        getPieChartModelQuestionNo().set("Answered", getExamQuestionNoAnswered());
        getPieChartModelQuestionNo().set("Not Answered", getTotalQuestionNo() - getExamQuestionNoAnswered());
        getPieChartModelQuestionNo().setShowDataLabels(true);
        getPieChartModelQuestionNo().setLegendPosition("w");
        //pieChartModelQuestionNo.setTitle("Question Number Statistics");
        pieChartModelQuestionNo.setShadow(true);
        //pieChartModelQuestionScore Model
        setAnsweredScores((Integer) 0);
        List<ExamQuestionAnswer> userAnswered = getExamBean().findUserAnswered(userId, examSessionId);
        for(ExamQuestionAnswer eqa : userAnswered) {
            setAnsweredScores((Integer) (getAnsweredScores() + eqa.getFkQuestionId().getMark()));
        }
        setTotalScore((Integer) 0);
        for(ExamSection es : getExamSession().getFkExamId().getExamSectionCollection()) {
            for(Question q : es.getQuestionCollection()) {
                setTotalScore((Integer) (getTotalScore() + q.getMark()));
            }
        }
        setPieChartModelQuestionScore(new PieChartModel());
        getPieChartModelQuestionScore().set("Answered", getAnsweredScores());
        getPieChartModelQuestionScore().set("Not Answered", getTotalScore() - getAnsweredScores());
        getPieChartModelQuestionScore().setShowDataLabels(true);
        getPieChartModelQuestionScore().setLegendPosition("w");
        //pieChartModelQuestionScore.setTitle("Question Score Statistics");
        pieChartModelQuestionScore.setShadow(true);
//        User userForExamQuestionAnswersStatis = userBean.refreshUserExamQuestionAnswers(userId);
//        for(ExamQuestionAnswer eqa : userForExamQuestionAnswersStatis.getExamQuestionAnswerCollection()) {
//            
//        }
        return "/" + getInvigilaterRole() + "/studentExamDetail";
    }

    /**
     * @return the student
     */
    public User getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(User student) {
        this.student = student;
    }

    /**
     * @return the invigilaterRole
     */
    public String getInvigilaterRole() {
        return invigilaterRole;
    }

    /**
     * @param invigilaterRole the invigilaterRole to set
     */
    public void setInvigilaterRole(String invigilaterRole) {
        this.invigilaterRole = invigilaterRole;
    }

    /**
     * @return the pieChartModelQuestionNo
     */
    public PieChartModel getPieChartModelQuestionNo() {
        return pieChartModelQuestionNo;
    }

    /**
     * @param pieChartModelQuestionNo the pieChartModelQuestionNo to set
     */
    public void setPieChartModelQuestionNo(PieChartModel pieChartModelQuestionNo) {
        this.pieChartModelQuestionNo = pieChartModelQuestionNo;
    }

    /**
     * @return the pieChartModelQuestionScore
     */
    public PieChartModel getPieChartModelQuestionScore() {
        return pieChartModelQuestionScore;
    }

    /**
     * @param pieChartModelQuestionScore the pieChartModelQuestionScore to set
     */
    public void setPieChartModelQuestionScore(PieChartModel pieChartModelQuestionScore) {
        this.pieChartModelQuestionScore = pieChartModelQuestionScore;
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
     * @return the userBean
     */
    public UserBean getUserBean() {
        return userBean;
    }

    /**
     * @param userBean the userBean to set
     */
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    /**
     * @return the examBean
     */
    public ExamBean getExamBean() {
        return examBean;
    }

    /**
     * @param examBean the examBean to set
     */
    public void setExamBean(ExamBean examBean) {
        this.examBean = examBean;
    }

    /**
     * @return the totalQuestionNo
     */
    public Integer getTotalQuestionNo() {
        return totalQuestionNo;
    }

    /**
     * @param totalQuestionNo the totalQuestionNo to set
     */
    public void setTotalQuestionNo(Integer totalQuestionNo) {
        this.totalQuestionNo = totalQuestionNo;
    }

    /**
     * @return the examQuestionNoAnswered
     */
    public Long getExamQuestionNoAnswered() {
        return examQuestionNoAnswered;
    }

    /**
     * @param examQuestionNoAnswered the examQuestionNoAnswered to set
     */
    public void setExamQuestionNoAnswered(Long examQuestionNoAnswered) {
        this.examQuestionNoAnswered = examQuestionNoAnswered;
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
     * @return the answeredScores
     */
    public Integer getAnsweredScores() {
        return answeredScores;
    }

    /**
     * @param answeredScores the answeredScores to set
     */
    public void setAnsweredScores(Integer answeredScores) {
        this.answeredScores = answeredScores;
    }
    
}
