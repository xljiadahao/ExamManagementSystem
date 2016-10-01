/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.view;


import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import sg.edu.nus.iss.examsys.team3ft.business.QuestionBean;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.Question;
import sg.edu.nus.iss.examsys.team3ft.model.SubjectTag;

/**
 *
 * @author Chandrakala
 */
@ViewScoped
@Named
public class AddingQuestionPartsView_original implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @EJB private QuestionBean questionBean;
    
    //input
    private Integer pkQuestionId;
    private Integer selectedPart;
    private String selectedType;
    
    //output
    private String display ="display:none;";
    private Question question;
    private Module module;
    private String subjectTags;
    private Integer currentPart = 1;

    @PostConstruct
    public void init() {
        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        pkQuestionId = (Integer)req.getAttribute("pkQuestionId");
        selectedPart = (Integer)req.getAttribute("selectedPart");
        System.out.println("AddingQuestionPartsView  pkQuestionId--" + pkQuestionId + ", selectedPart--" + selectedPart);
        question = questionBean.findByPkQuestionId(pkQuestionId);
        if (question != null) {
            Iterator<SubjectTag> iterator = question.getSubjectTagCollection().iterator();
            StringBuilder subjectTagsTmp = new StringBuilder("");
            while (iterator.hasNext()) {
                SubjectTag sTag = iterator.next();
                subjectTagsTmp.append(sTag.getUkTagName() + ", ");
                setModule(sTag.getFkModuleCode());
            }
            subjectTags = subjectTagsTmp.toString().substring(0, subjectTagsTmp.toString().length()-2);
        }
    }
    
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }
    
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    public void onChange(){
        System.out.println("selected " + selectedType);
        if(selectedType.equalsIgnoreCase("WA")){
            display="display:none";
        }else{
            display="display:block";
        }
    }

    /**
     * @return the subjectTags
     */
    public String getSubjectTags() {
        return subjectTags;
    }

    /**
     * @param subjectTags the subjectTags to set
     */
    public void setSubjectTags(String subjectTags) {
        this.subjectTags = subjectTags;
    }

    /**
     * @return the currentPart
     */
    public Integer getCurrentPart() {
        return currentPart;
    }

    /**
     * @param currentPart the currentPart to set
     */
    public void setCurrentPart(Integer currentPart) {
        this.currentPart = currentPart;
    }
    
}
