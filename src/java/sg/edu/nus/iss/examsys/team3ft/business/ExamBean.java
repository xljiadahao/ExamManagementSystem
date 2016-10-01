/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.business;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.ejb.ApplicationException;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import sg.edu.nus.iss.examsys.team3ft.instantmessaging.ChatRoom;
import sg.edu.nus.iss.examsys.team3ft.model.ExamPaper;
import sg.edu.nus.iss.examsys.team3ft.model.ExamQuestionAnswer;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSection;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSession;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.User;

/**
 *
 * @author Lei
 */
@ApplicationException(rollback = true)
@Stateless
public class ExamBean {

    @PersistenceContext
    EntityManager em;
    
    @Inject ChatRoom chatRoom;

    public List<ExamSession> findCurrentExams() {
        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findCurrentExam", ExamSession.class);
        List<ExamSession> examSessions = query.getResultList();
        for (ExamSession es : examSessions) {
            es.getUserCollection();
            es.getFkExamId().getFkModuleCode();
        }
        return examSessions;
    }
    
    public List<ExamSession> findFollowingExamSessions() {
        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findFollowingExam", ExamSession.class);
        List<ExamSession> examSessions = query.getResultList();
        for (ExamSession e : examSessions) {
            e.getFkExamId().getFkModuleCode();
        }
        return examSessions;
    }
    
    public ExamSession findExamSessionById(Integer examSessionId) {
        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
        query.setParameter("pkExamSessionId", examSessionId);
        ExamSession examSession = query.getSingleResult();
        for (ExamQuestionAnswer ea : examSession.getExamQuestionAnswerCollection()) {
            ea.getFkExamSessionId();
            ea.getFkStudentId();
        }
        examSession.getFkExamId().getFkModuleCode();
        Collection<ExamSection> examSections = examSession.getFkExamId().getExamSectionCollection();
        for (ExamSection section : examSections) {
            section.getQuestionCollection();
        }
        examSession.getUserCollection();
        return examSession;
    }
    
    public ExamSession refresh(Integer examSessionId){
        ExamSession examSession = findExamSessionById(examSessionId);
        em.refresh(examSession);
        for (ExamQuestionAnswer ea : examSession.getExamQuestionAnswerCollection()) {
            ea.getFkExamSessionId();
            ea.getFkStudentId();
        }
        examSession.getFkExamId().getFkModuleCode();
        Collection<ExamSection> examSections = examSession.getFkExamId().getExamSectionCollection();
        for (ExamSection section : examSections) {
            section.getQuestionCollection();
        }
        examSession.getUserCollection();
        return examSession;
    }
    
    public ExamPaper refreshExamPaper(Integer examPaperId) {
        TypedQuery<ExamPaper> query = em.createNamedQuery("ExamPaper.findByPkExamId", ExamPaper.class);
        query.setParameter("pkExamId", examPaperId);
        ExamPaper examPaper = query.getSingleResult();
        em.refresh(examPaper);
        for(ExamSection esc : examPaper.getExamSectionCollection()) {
            esc.getQuestionCollection();
        }
        return examPaper;
    }

    public boolean isInSchedule(Integer examSessionId) {
        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
        query.setParameter("pkExamSessionId", examSessionId);
        ExamSession examSession = query.getSingleResult();
        Date startTime = examSession.getExamStartTime();
        Date endTime = examSession.getExamEndTime();
        Date currentTime = new Date();
        SimpleDateFormat sdfTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        System.out.println("isInSchedule ** startTime: " + sdfTime.format(startTime) + ", endTime: " + sdfTime.format(endTime) + ", currentTime--" + sdfTime.format(currentTime));
        //System.out.println("currentTime.after(endTime): " + currentTime.after(endTime));
        //System.out.println("currentTime.before(startTime): " + currentTime.before(startTime));
        if(currentTime.after(endTime) || currentTime.before(startTime)) {
            return false;
        } else {
            return true;
        }
    }
    
    public List<ExamQuestionAnswer> findUserAnswered(String userId, Integer examSessionId) {
        TypedQuery<User> queryUser = em.createNamedQuery("User.findByUserId", User.class);
        queryUser.setParameter("userId", userId);
        User student = queryUser.getSingleResult();
        TypedQuery<ExamSession> queryExamSession = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
        queryExamSession.setParameter("pkExamSessionId", examSessionId);
        ExamSession examSession = queryExamSession.getSingleResult();
        TypedQuery<ExamQuestionAnswer> query = em.createNamedQuery("ExamQuestionAnswer.findUserAnswered", ExamQuestionAnswer.class);
        query.setParameter("fkStudentId", student);
        query.setParameter("fkExamSessionId", examSession);
        List<ExamQuestionAnswer> userAnswered =  query.getResultList();
        for(ExamQuestionAnswer eqa : userAnswered) {
            eqa.getFkQuestionId();
        }
        return userAnswered;
    }
    
    public Long countUserAnswer(String userId, Integer examSessionId) {
        TypedQuery<User> queryUser = em.createNamedQuery("User.findByUserId", User.class);
        queryUser.setParameter("userId", userId);
        User student = queryUser.getSingleResult();
        TypedQuery<ExamSession> queryExamSession = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
        queryExamSession.setParameter("pkExamSessionId", examSessionId);
        ExamSession examSession = queryExamSession.getSingleResult();
        TypedQuery<Long> query = em.createNamedQuery("ExamQuestionAnswer.countUserAnswer", Long.class);
        query.setParameter("fkStudentId", student);
        query.setParameter("fkExamSessionId", examSession);
        return query.getSingleResult();
    }

//    public Collection<ExamQuestionAnswer> findExamQuestionAnswerCollection(Integer examSessionId) {
//        TypedQuery<ExamSession> query = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
//        query.setParameter("pkExamSessionId", examSessionId);
//        ExamSession examSession = query.getSingleResult();
//        Collection<ExamQuestionAnswer> examQuestionAnswerCollection = examSession.getExamQuestionAnswerCollection();
//        for (ExamQuestionAnswer ea : examSession.getExamQuestionAnswerCollection()){
//            ea.getFkExamSessionId();
//            ea.getFkStudentId();
//        }
//        return examQuestionAnswerCollection;
//    }
    //clean the cache for the specific user
//    public void refreshCache(String userID) {
//        emf.getCache().evict(User.class, userID); 
//    }
    public void saveAnswer(ExamQuestionAnswer eqa, Integer examSessionId, Integer questionId, String studentId) {
        TypedQuery<ExamSession> queryExamSession = em.createNamedQuery("ExamSession.findByPkExamSessionId", ExamSession.class);
        queryExamSession.setParameter("pkExamSessionId", examSessionId);
        ExamSession examSession = queryExamSession.getSingleResult();
        TypedQuery<Question> queryQuestion = em.createNamedQuery("Question.findByPkQuestionId", Question.class);
        queryQuestion.setParameter("pkQuestionId", questionId);
        Question question = queryQuestion.getSingleResult();
        TypedQuery<User> queryUser = em.createNamedQuery("User.findByUserId", User.class);
        queryUser.setParameter("userId", studentId);
        User user = queryUser.getSingleResult();
        eqa.setFkExamSessionId(examSession);
        eqa.setFkQuestionId(question);
        eqa.setFkStudentId(user);
        em.persist(eqa);
        //em.flush();
        //add new data into the JPA cache
        examSession.getExamQuestionAnswerCollection().add(eqa);
    }
    
    public Integer saveExamPaper(String moduleCode) {
        TypedQuery<Module> queryModule = em.createNamedQuery("Module.findByPkModuleCode", Module.class);
        queryModule.setParameter("pkModuleCode", moduleCode);
        ExamPaper examPaper = new ExamPaper();
        examPaper.setFkModuleCode(queryModule.getSingleResult());
        examPaper.setCreateDate(new Date());
        em.persist(examPaper);
        em.flush();
        System.out.println("examPaper.getPkExamId(): " + examPaper.getPkExamId());
        return examPaper.getPkExamId();
    }
    
    public void saveExamSection(Integer examPaperId, List<Question> questionForCurrentection, ExamSection examSection) {
        TypedQuery<ExamPaper> queryExamPaper = em.createNamedQuery("ExamPaper.findByPkExamId", ExamPaper.class);
        queryExamPaper.setParameter("pkExamId", examPaperId);
        examSection.setFkExamId(queryExamPaper.getSingleResult());
        try {
            em.persist(examSection);
            em.flush();
        }  catch (ConstraintViolationException ex) {
            System.out.println("saveExamSection ConstraintViolation Start: ----------------------------");
            ex.printStackTrace();
            for (ConstraintViolation cv: ex.getConstraintViolations()) {
                System.out.println(cv.getMessage());
            }
            System.out.println("saveExamSection ConstraintViolation End:   ----------------------------");
        }
        for(Question q : questionForCurrentection) {
            TypedQuery<Question> queryQuestion = em.createNamedQuery("Question.findByPkQuestionId", Question.class);
            queryQuestion.setParameter("pkQuestionId", q.getPkQuestionId());
            examSection.getQuestionCollection().add(queryQuestion.getSingleResult());
        }
    }
    
    @Schedule(minute="*/30")
    public void refreshExamChatting() {
        if (findCurrentExams().size() > 0) {
            System.out.println("findCurrentExams().size() > 0, No Need RefreshExamChatting Timer Task");
        } else {
            System.out.println("RefreshExamChatting Timer Task");
            chatRoom.setChatMap(new HashMap<String, StringBuilder>());
        }  
    }

}
