/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamQuestionAnswer;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSection;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.QuestionMultipleChoiceOptions;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@SessionScoped
@Named
public class SittingExamView implements Serializable {

    @EJB private ExamBean examBean;
    //@EJB private QuestionBean questionBean;
    @EJB private UserBean userBean;

    private ExamSession examSession;
    private Map<String, List<Question>> sectionQuestions;
    private List<String> sectionTrace;
    private int currentSection = -1;
    private int currentQuestionInQ = -1;
    private String currentQType;
    private Question question;

    //output
    private String sectionView;
    private String questionView;
    private String moduleName;
    private String startTime;
    private String endTime;
    private String buttonValue;
    private Collection<QuestionMultipleChoiceOptions> multipleChoice;
    private User student;

    //input
    private String answerWA;
    private String[] answerMM;
    private String answerMS;
    
    @PostConstruct
    public void init() {
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        setStudent(userBean.findUserByID(loginID));
    }

    public String setExamSession(Integer examSessionId) {
        //check if in schedule
        if (examBean.isInSchedule(examSessionId) == false) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Exam", "Time is over for the exam");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            examSession = null;
            return "/student/studentMainPage";
        }
        examSession = examBean.findExamSessionById(examSessionId);
        //check if have done the exam
        Collection<ExamQuestionAnswer> answerCollection = examSession.getExamQuestionAnswerCollection();
        if(answerCollection != null && answerCollection.size() > 0) {
            String loginStudent = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
            for(ExamQuestionAnswer ea : answerCollection) {
                if((examSession.getPkExamSessionId() == ea.getFkExamSessionId().getPkExamSessionId()) && loginStudent.equalsIgnoreCase(ea.getFkStudentId().getUserId())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Exam", "You have done the exam " + 
                            examSession.getFkExamId().getFkModuleCode().getPkModuleCode() + "  " + examSession.getFkExamId().getFkModuleCode().getUkModuleName());
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                    examSession = null;
                    return "/student/studentMainPage";
                }
            }
        }
        SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        setStartTime(sdfTime.format(examSession.getExamStartTime()));
        setEndTime(sdfTime.format(examSession.getExamEndTime()));
        sectionQuestions = new HashMap<String, List<Question>>();
        sectionTrace = new ArrayList<String>();
        for (ExamSection e : examSession.getFkExamId().getExamSectionCollection()) {
            List<Question> qs = new ArrayList<Question>();
            for (Question q : e.getQuestionCollection()) {
                qs.add(q);
            }
            sectionQuestions.put(e.getSectionName(), qs);
            sectionTrace.add(e.getSectionName());
        }
        currentSection = 0;
        currentQuestionInQ = 0;
        setQuestion(sectionQuestions.get(sectionTrace.get(currentSection)).get(currentQuestionInQ));
        currentQType = sectionQuestions.get(sectionTrace.get(currentSection)).get(currentQuestionInQ).getQuestionType();
        if("MM".equals(currentQType) || "MS".equals(currentQType)) {
            multipleChoice = question.getQuestionMultipleChoiceOptionsCollection();
        }
        setSectionView(sectionTrace.get(currentSection) + " " + (currentSection + 1) + "/" + sectionTrace.size());
        System.out.println("current section: " + getSectionView());
        setQuestionView("Question " + (currentQuestionInQ + 1) + "/" + sectionQuestions.get(sectionTrace.get(currentSection)).size());
        System.out.println("total quantity of the questions in the section: " + getQuestionView());
        setModuleName(examSession.getFkExamId().getFkModuleCode().getUkModuleName());
        updateButtonValue();
        return navi();
    }

    private void updateButtonValue() {
        if (currentSection == (sectionTrace.size() - 1) && currentQuestionInQ == (sectionQuestions.get(sectionTrace.get(currentSection)).size() - 1)) {
            buttonValue = "Submit";
        } else {
            buttonValue = "Next";
        }
    }

    private String navi() {
        String result = null;
        if ("WA".equals(currentQType)) {
            result = "/student/writtenAnswerQuestionPaper";
        } else if ("MS".equals(currentQType)) {
            result = "/student/singleChoiceQuestionPaper";
        } else if ("MM".equals(currentQType)) {
            result = "/student/multiChoiceQuestionPaper";
        }
        return result;
    }

    public String submit() {
        //check if in schedule
        if (examBean.isInSchedule(examSession.getPkExamSessionId()) == false) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Exam", "Time is over for the exam");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            examSession = null;
            return "/student/studentMainPage";
        }
        //1.save data
        String result = null;
        if ("WA".equals(currentQType)) {
            result = answerWA;
            System.out.println("answerWA: " + result);
        } else if ("MS".equals(currentQType)) {
            result = answerMS;
            System.out.println("answerMS: " + result);
        } else if ("MM".equals(currentQType)) {
            StringBuilder mm = new StringBuilder("");
            for (String s : answerMM) {
                mm.append(s + ",");
            }
            result = mm.toString().substring(0, mm.toString().length() - 1);
            System.out.println("answerMM: " + result);
        }
        ExamQuestionAnswer eqa = new ExamQuestionAnswer();
        eqa.setAnswer(result);
        eqa.setCreatedTime(new Date());
        String loginID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        examBean.saveAnswer(eqa, examSession.getPkExamSessionId(), question.getPkQuestionId(), loginID);
        //2.refresh view data
        if (currentQuestionInQ < sectionQuestions.get(sectionTrace.get(currentSection)).size() - 1) {
            currentQuestionInQ++;
        } else if (currentQuestionInQ == sectionQuestions.get(sectionTrace.get(currentSection)).size() - 1 && currentSection < sectionTrace.size() - 1) {
            currentSection++;
            currentQuestionInQ = 0;
        } else if (currentQuestionInQ == sectionQuestions.get(sectionTrace.get(currentSection)).size() - 1 && currentSection == sectionTrace.size() - 1) {
            currentSection = -1;
            currentQuestionInQ = -1;
            System.out.println("Exam Finished");
        } else {
            System.out.println("Exam Finished Condition Wrong");
        }
        if (currentSection >= 0 && currentQuestionInQ >= 0) {
            setQuestion(sectionQuestions.get(sectionTrace.get(currentSection)).get(currentQuestionInQ));
            currentQType = sectionQuestions.get(sectionTrace.get(currentSection)).get(currentQuestionInQ).getQuestionType();
            if("MM".equals(currentQType) || "MS".equals(currentQType)) {
                multipleChoice = question.getQuestionMultipleChoiceOptionsCollection();
            } else {
                multipleChoice = null;
            }
            setSectionView(sectionTrace.get(currentSection) + " " + (currentSection + 1) + "/" + sectionTrace.size());
            setQuestionView("Question " + (currentQuestionInQ + 1) + "/" + sectionQuestions.get(sectionTrace.get(currentSection)).size());
            setModuleName(examSession.getFkExamId().getFkModuleCode().getUkModuleName());
            updateButtonValue();
            //3.clear input
            answerWA = null;
            answerMM = null;
            answerMS = null;
            return navi();
        } else {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Exam", "You have done the exam " + moduleName);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Exam", "You have done the exam " + 
                            examSession.getFkExamId().getFkModuleCode().getPkModuleCode() + "  " + examSession.getFkExamId().getFkModuleCode().getUkModuleName());
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //3.clear exam session data
            currentQType = null;
            setQuestion(null);
            setSectionView(null);
            setQuestionView(null);
            setModuleName(null);
            buttonValue = null;
            answerWA = null;
            answerMM = null;
            answerMS = null;
            multipleChoice = null;
            examSession = null;
            return "/student/studentMainPage";
        }
        
    }

    /**
     * @return the sectionView
     */
    public String getSectionView() {
        return sectionView;
    }

    /**
     * @param sectionView the sectionView to set
     */
    public void setSectionView(String sectionView) {
        this.sectionView = sectionView;
    }

    /**
     * @return the questionView
     */
    public String getQuestionView() {
        return questionView;
    }

    /**
     * @param questionView the questionView to set
     */
    public void setQuestionView(String questionView) {
        this.questionView = questionView;
    }

    /**
     * @return the question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the buttonValue
     */
    public String getButtonValue() {
        return buttonValue;
    }

    /**
     * @param buttonValue the buttonValue to set
     */
    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    /**
     * @return the answerWA
     */
    public String getAnswerWA() {
        return answerWA;
    }

    /**
     * @param answerWA the answerWA to set
     */
    public void setAnswerWA(String answerWA) {
        this.answerWA = answerWA;
    }

    /**
     * @return the answerMM
     */
    public String[] getAnswerMM() {
        return answerMM;
    }

    /**
     * @param answerMM the answerMM to set
     */
    public void setAnswerMM(String[] answerMM) {
        this.answerMM = answerMM;
    }

    /**
     * @return the answerMS
     */
    public String getAnswerMS() {
        return answerMS;
    }

    /**
     * @param answerMS the answerMS to set
     */
    public void setAnswerMS(String answerMS) {
        this.answerMS = answerMS;
    }

    /**
     * @return the multipleChoice
     */
    public Collection<QuestionMultipleChoiceOptions> getMultipleChoice() {
        return multipleChoice;
    }

    /**
     * @param multipleChoice the multipleChoice to set
     */
    public void setMultipleChoice(Collection<QuestionMultipleChoiceOptions> multipleChoice) {
        this.multipleChoice = multipleChoice;
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

}
