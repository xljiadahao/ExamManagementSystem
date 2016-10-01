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
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.examsys.team3ft.business.QuestionBean;
import sg.edu.nus.iss.examsys.team3ft.business.SubjectTagBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.Question;

/**
 *
 * @author Lei
 */
@ViewScoped
@Named
public class QuestionModifyView implements Serializable {
    
    @EJB private UserBean userBean;
    @EJB private SubjectTagBean subjectTagBean;
    @EJB private QuestionBean questionBean;
    
    private String selectedModule;
    private Integer selectedSubject;
    
    private Collection<Module> modules;
    private Module selectedModuleObj;
    private List<Question> questions;
    private SimpleDateFormat sdfDateTime;
    
    @PostConstruct
    public void init() {
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        if (loginID != null && !"".equals(loginID)) {
            modules = userBean.findUserByID(loginID).getModuleCollection(); //moduleBean.findAllModules();
        }
        setSdfDateTime(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK));
    }
    
    public void searchSubjectTags() {
        System.out.println("search selectedModule: " + selectedModule);
        //subjectsCollection = subjectTagBean.findSubjectTags(selectedModule);
        setSelectedModuleObj(subjectTagBean.findModuleByModuleCode(selectedModule));
    }
    
    public void searchQuestions() {
        questions = new ArrayList<Question>();
        for (Question q : subjectTagBean.findByPkTagId(selectedSubject).getQuestionCollection()) {
            if ("DISABLE".equalsIgnoreCase(q.getQuestionStatus())) {
                continue;
            }
            questions.add(q);
        }
    }
    
    public String modify(Integer questionId) {
        System.out.println("modify(questionId): " + questionId);
        ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).setAttribute("questionId", questionId);
        if("MS".equalsIgnoreCase(questionBean.findByPkQuestionId(questionId).getQuestionType()) || 
                "MM".equalsIgnoreCase(questionBean.findByPkQuestionId(questionId).getQuestionType())) {
            return "/lecturer/modifyQuestionMC.xhtml";
        } else if ("WA".equalsIgnoreCase(questionBean.findByPkQuestionId(questionId).getQuestionType())) {
            return "/lecturer/modifyQuestionWA.xhtml";
        } else {
            return null;
        }
    }

    /**
     * @return the selectedModule
     */
    public String getSelectedModule() {
        return selectedModule;
    }

    /**
     * @param selectedModule the selectedModule to set
     */
    public void setSelectedModule(String selectedModule) {
        this.selectedModule = selectedModule;
    }

    /**
     * @return the modules
     */
    public Collection<Module> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(Collection<Module> modules) {
        this.modules = modules;
    }

    /**
     * @return the selectedSubject
     */
    public Integer getSelectedSubject() {
        return selectedSubject;
    }

    /**
     * @param selectedSubject the selectedSubject to set
     */
    public void setSelectedSubject(Integer selectedSubject) {
        this.selectedSubject = selectedSubject;
    }

    /**
     * @return the selectedModuleObj
     */
    public Module getSelectedModuleObj() {
        return selectedModuleObj;
    }

    /**
     * @param selectedModuleObj the selectedModuleObj to set
     */
    public void setSelectedModuleObj(Module selectedModuleObj) {
        this.selectedModuleObj = selectedModuleObj;
    }

    /**
     * @return the questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
