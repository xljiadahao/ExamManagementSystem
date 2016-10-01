/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.QuestionBean;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.QuestionMultipleChoiceOptions;
import sg.edu.nus.iss.examsys.team3ft.model.SubjectTag;

/**
 *
 * @author Lei
 */
@ViewScoped
@Named
public class ModifyQuestionMCView implements Serializable {

    @EJB private QuestionBean questionBean;

    private String questionType;
    private Integer marks;
    private String questionContent;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    
    private String originalOpt1;
    private String originalOpt2;
    private String originalOpt3;
    private String originalOpt4;

    private Question question;

    @PostConstruct
    public void modify() {
        Integer questionId = (Integer) ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getAttribute("questionId");
        System.out.println("ModifyQuestionWAView modify(questionId): " + questionId);
        setQuestion(getQuestionBean().findByPkQuestionId(questionId));
        setMarks((Integer) getQuestion().getMark());
        setQuestionContent(getQuestion().getQuestionText());
        questionType = question.getQuestionType();
        Collection<QuestionMultipleChoiceOptions> multipleChoices = question.getQuestionMultipleChoiceOptionsCollection();
        List<QuestionMultipleChoiceOptions> multipleChoicesList = new ArrayList<QuestionMultipleChoiceOptions>();
        for (QuestionMultipleChoiceOptions qm : multipleChoices) {
            multipleChoicesList.add(qm);
        }
        opt1 = multipleChoicesList.get(0).getQuestionMultipleChoiceOptionText();
        originalOpt1 = opt1;
        opt2 = multipleChoicesList.get(1).getQuestionMultipleChoiceOptionText();
        originalOpt2 = opt2;
        opt3 = multipleChoicesList.get(2).getQuestionMultipleChoiceOptionText();
        originalOpt3 = opt3;
        opt4 = multipleChoicesList.get(3).getQuestionMultipleChoiceOptionText();
        originalOpt4 = opt4;
//        if("MM".equals(getQuestion().getQuestionType()) || "MS".equals(getQuestion().getQuestionType())) {
//            getQuestion().getQuestionMultipleChoiceOptionsCollection();
//            return null;
//        } else {
//            setMarks((Integer) getQuestion().getMark());
//            setQuestionContent(getQuestion().getQuestionText());
//            return "/lecturer/modifyQuestionWA.xhtml";
//        }
    }

    public String save() {
        System.out.println("ModifyQuestionMCView save: " + "marks--" + marks + ", questionContent--" + questionContent + ", questionType--" + questionType);
        System.out.println("opt1--" + opt1 + ", opt2--" + opt2 + ", opt3--" + opt3 + ", opt4--" + opt4);
        try {
            //1. change the QUESION_STATUS to 'DISABLE'
            question.setQuestionStatus("DISABLE");
            Date createOn = question.getCreatedOn();
            question.setCreatedOn(createOn);
            //2. calculate VERSION_NUMBER and new one question, questionSeq, selectedSubjects, marks, qusetionText, versionNo, type, List<String> opts
            Long qSeqNo = questionBean.enableQuestionVersionCount(question.getQuestionSequence());
            String versionNo = "V" + (qSeqNo + 1);
            Integer questionSeq = question.getQuestionSequence();
            Integer[] selectedSubjects = new Integer[question.getSubjectTagCollection().size()];
            int i = 0;
            for (SubjectTag s : question.getSubjectTagCollection()) {
                if (i < selectedSubjects.length) {
                    selectedSubjects[i] = s.getPkTagId();
                    i++;
                }
            }
            List<String> opts = new ArrayList<String>();
            opts.add(opt1);
            opts.add(opt2);
            opts.add(opt3);
            opts.add(opt4);
            //questionBean.createQuestion(selectedSubjects, marks, questionContent, versionNo, questionSeq);

            //Question q, Integer[] selectedSubjects, Integer marks, String qusetionText, String type, List<String> opts, String versionNo, Integer questionSeq
            questionBean.updateQuestion(question, selectedSubjects, marks, questionContent, questionType, opts, versionNo, questionSeq);

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System User Register", "Question update success");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "/lecturer/modifyQuestion";
        } catch (Exception e) {
            e.printStackTrace();
//            question.setMark(originalMark);
//            question.setQuestionText(originalQuestionContent);
            marks = question.getMark();
            questionContent = question.getQuestionText();
            questionType = question.getQuestionType();
            opt1 = originalOpt1;
            opt2 = originalOpt2;
            opt3 = originalOpt3;
            opt4 = originalOpt4;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System User Register", "Question update fail");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
    }

    /**
     * @return the questionBean
     */
    public QuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public void setQuestionBean(QuestionBean questionBean) {
        this.questionBean = questionBean;
    }

    /**
     * @return the questionType
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * @param questionType the questionType to set
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * @return the marks
     */
    public Integer getMarks() {
        return marks;
    }

    /**
     * @param marks the marks to set
     */
    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    /**
     * @return the questionContent
     */
    public String getQuestionContent() {
        return questionContent;
    }

    /**
     * @param questionContent the questionContent to set
     */
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    /**
     * @return the opt1
     */
    public String getOpt1() {
        return opt1;
    }

    /**
     * @param opt1 the opt1 to set
     */
    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    /**
     * @return the opt2
     */
    public String getOpt2() {
        return opt2;
    }

    /**
     * @param opt2 the opt2 to set
     */
    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    /**
     * @return the opt3
     */
    public String getOpt3() {
        return opt3;
    }

    /**
     * @param opt3 the opt3 to set
     */
    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    /**
     * @return the opt4
     */
    public String getOpt4() {
        return opt4;
    }

    /**
     * @param opt4 the opt4 to set
     */
    public void setOpt4(String opt4) {
        this.opt4 = opt4;
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

}
