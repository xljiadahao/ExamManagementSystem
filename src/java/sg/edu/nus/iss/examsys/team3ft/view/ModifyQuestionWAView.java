/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.Date;
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
import sg.edu.nus.iss.examsys.team3ft.model.SubjectTag;

/**
 *
 * @author Lei
 */
@ViewScoped
@Named
public class ModifyQuestionWAView implements Serializable {
    
    @EJB private QuestionBean questionBean; 
    
    private Integer marks;
    private String questionContent;

    private Question question;
    
    @PostConstruct
    public void modify() {
        Integer questionId = (Integer)((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getAttribute("questionId");
        System.out.println("ModifyQuestionWAView modify(questionId): " + questionId);
        setQuestion(getQuestionBean().findByPkQuestionId(questionId));
        marks = question.getMark();
        questionContent = question.getQuestionText();
//        if("MM".equals(getQuestion().getQuestionType()) || "MS".equals(getQuestion().getQuestionType())) {
//            getQuestion().getQuestionMultipleChoiceOptionsCollection();
//            return null;
//        } else {
//            marks = question.getMark();
//            questionContent = question.getQuestionText();
//            return "/lecturer/modifyQuestionWA.xhtml";
//        }
    }
    
    public String save() {
        System.out.println("ModifyQuestionWAView save: " + "marks--" + marks + ", questionContent--" + questionContent);
//        Integer originalMark = question.getMark();
//        String originalQuestionContent = question.getQuestionText();
//        question.setMark(marks);
//        question.setQuestionText(questionContent);
        try {
            //1. change the QUESION_STATUS to 'DISABLE'
            question.setQuestionStatus("DISABLE");
            Date createOn = question.getCreatedOn();
            question.setCreatedOn(createOn);
            //2. calculate VERSION_NUMBER and new one question, questionSeq, selectedSubjects, marks, qusetionText, versionNo
            Long qSeqNo = questionBean.enableQuestionVersionCount(question.getQuestionSequence());
            String versionNo = "V" + (qSeqNo+1);
            Integer questionSeq = question.getQuestionSequence();
            Integer[] selectedSubjects = new Integer[question.getSubjectTagCollection().size()];
            int i=0;
            for(SubjectTag s : question.getSubjectTagCollection()) {
                if (i<selectedSubjects.length) {
                    selectedSubjects[i] = s.getPkTagId();
                    i++;
                }
            }
            //questionBean.createQuestion(selectedSubjects, marks, questionContent, versionNo, questionSeq);
            
            questionBean.updateQuestion(question, selectedSubjects, marks, questionContent, versionNo, questionSeq);
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System User Register", "Question update success");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return "/lecturer/modifyQuestion";
        } catch (Exception e) {
            e.printStackTrace();
//            question.setMark(originalMark);
//            question.setQuestionText(originalQuestionContent);
            marks = question.getMark();
            questionContent = question.getQuestionText();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System User Register", "Question update fail");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            return null;
        }
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

}
