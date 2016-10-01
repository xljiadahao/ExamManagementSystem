/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.business;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.QuestionMultipleChoiceOptions;
import sg.edu.nus.iss.examsys.team3ft.model.SubjectTag;

/**
 *
 * @author Lei
 */
@ApplicationException(rollback=true)
@Stateless
public class QuestionBean {
    
    @PersistenceContext EntityManager em;
    
    @EJB private UserBean userBean;
    @EJB private SubjectTagBean subjectTagBean;
    
    public Long enableQuestionsCount() {
        TypedQuery<Long> queryQuestionsCount = em.createNamedQuery("Question.enableQuestionsCount", Long.class);
        return queryQuestionsCount.getSingleResult();
    }
    
    //create WA Quesions
    public void createQuestion(Integer[] selectedSubjects, Integer marks, String qusetionText, String versionNo, Integer questionSeq) {
        Question question = new Question(); 
        if(questionSeq > 0) {
            question.setQuestionSequence(questionSeq);
        } else {
            question.setQuestionSequence((int)(enableQuestionsCount() + 1));
        }
        //question.setVersionNumber("V1");
        if(versionNo == null || "".equalsIgnoreCase(versionNo)) {
            question.setVersionNumber("V1");
        } else {
            question.setVersionNumber(versionNo);
        }
        question.setCreatedOn(new Date());
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        question.setFkCreatedBy(userBean.findUserByID(loginID));
        question.setMark(marks);
        question.setQuestionType("WA");
        question.setQuestionText(qusetionText);
        question.setQuestionStatus("ENABLE");
        System.out.println("QuestionBean WA createQuestion start --------------------------");
        System.out.println("question.getQuestionSequence(): " + question.getQuestionSequence());
        System.out.println("question.getVersionNumber(): " + question.getVersionNumber());
        System.out.println("question.getCreatedOn(): " + question.getCreatedOn());
        System.out.println("question.getFkCreatedBy(): " + question.getFkCreatedBy());
        System.out.println("question.getMark(): " + question.getMark());
        System.out.println("question.getQuestionStatus(): " + question.getQuestionStatus());
        System.out.println("QuestionBean WA createQuestion end   --------------------------");
        em.persist(question);
        em.flush();
        System.out.println("createQuestion WA question.getPkQuestionId(): " + question.getPkQuestionId());   
        System.out.println("selectedSubjects.length: " + selectedSubjects.length);
        for (int i=0; i<selectedSubjects.length; i++) {
            question.getSubjectTagCollection().add(subjectTagBean.findByPkTagId(selectedSubjects[i]));
            //add question in cache
            subjectTagBean.findByPkTagId(selectedSubjects[i]).getQuestionCollection().add(question);
        }
    }
    
    //create MM Quesions
    public void createQuestion(Integer[] selectedSubjects, Integer marks, String qusetionText, String type, List<String> opts, String versionNo, Integer questionSeq) {
        Question question = new Question(); 
        //question.setQuestionSequence((int)(enableQuestionsCount() + 1));
        if(questionSeq > 0) {
            question.setQuestionSequence(questionSeq);
        } else {
            question.setQuestionSequence((int)(enableQuestionsCount() + 1));
        }
        if(versionNo == null || "".equalsIgnoreCase(versionNo)) {
            question.setVersionNumber("V1");
        } else {
            question.setVersionNumber(versionNo);
        }
        question.setCreatedOn(new Date());
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        question.setFkCreatedBy(userBean.findUserByID(loginID));
        question.setMark(marks);
        question.setQuestionType(type);
        question.setQuestionText(qusetionText);
        question.setQuestionStatus("ENABLE");
        System.out.println("QuestionBean MM createQuestion start --------------------------");
        System.out.println("question.getQuestionSequence(): " + question.getQuestionSequence());
        System.out.println("question.getVersionNumber(): " + question.getVersionNumber());
        System.out.println("question.getCreatedOn(): " + question.getCreatedOn());
        System.out.println("question.getFkCreatedBy(): " + question.getFkCreatedBy());
        System.out.println("question.getMark(): " + question.getMark());
        System.out.println("question.getQuestionStatus(): " + question.getQuestionStatus());
        System.out.println("QuestionBean MM createQuestion end   --------------------------");
        em.persist(question);
        em.flush();
        System.out.println("createQuestion MM question.getPkQuestionId(): " + question.getPkQuestionId());
        System.out.println("selectedSubjects.length: " + selectedSubjects.length);
        for (int i=0; i<selectedSubjects.length; i++) {
            question.getSubjectTagCollection().add(subjectTagBean.findByPkTagId(selectedSubjects[i]));
            //add question in cache
            subjectTagBean.findByPkTagId(selectedSubjects[i]).getQuestionCollection().add(question);
        }
        System.out.println("opts.length: " + opts.size());
        for (String opt : opts) {
            QuestionMultipleChoiceOptions qm = new QuestionMultipleChoiceOptions();
            qm.setFkQuestionId(question);
            qm.setQuestionMultipleChoiceOptionText(opt);
            em.persist(qm);
            //two approach to maintain the cache, 1.add, 2.evit() cache for the specific data
            question.getQuestionMultipleChoiceOptionsCollection().add(qm);
            //em.flush();
        }
    }
    
    public Question findByPkQuestionId(Integer questionId) {
        TypedQuery<Question> queryQuestionById = em.createNamedQuery("Question.findByPkQuestionId", Question.class);
        queryQuestionById.setParameter("pkQuestionId", questionId);
        Question question = queryQuestionById.getSingleResult();
        if("MM".equals(question.getQuestionType()) || "MS".equals(question.getQuestionType())) {
            question.getQuestionMultipleChoiceOptionsCollection();
        }
        //question.getSubjectTagCollection();
        Iterator<SubjectTag> iterator = question.getSubjectTagCollection().iterator();
        while (iterator.hasNext()) {
            iterator.next().getFkModuleCode();
        }
        return question;
    }
    
    public Long enableQuestionVersionCount(Integer questionSeq){
        TypedQuery<Long> queryQuestionVersionCount = em.createNamedQuery("Question.findQuestionVersionCount", Long.class);
        queryQuestionVersionCount.setParameter("questionSequence", questionSeq);
        return queryQuestionVersionCount.getSingleResult();
    }
    
    public void mergeQuestion(Question q) {
        em.merge(q);
    }
    
    // to make two step in one transaction WA
    public void updateQuestion(Question q, Integer[] selectedSubjects, Integer marks, String qusetionText, String versionNo, Integer questionSeq) {
        mergeQuestion(q);
        createQuestion(selectedSubjects, marks, qusetionText, versionNo, questionSeq);
    }
    
    // to make two step in one transaction MC
    public void updateQuestion(Question q, Integer[] selectedSubjects, Integer marks, String qusetionText, String type, List<String> opts, String versionNo, Integer questionSeq) {
        mergeQuestion(q);
        createQuestion(selectedSubjects, marks, qusetionText, type, opts, versionNo, questionSeq);
    }
    
}
