package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.QuestionBean;
import sg.edu.nus.iss.examsys.team3ft.business.SubjectTagBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.Module;

/**
 *
 * @author Chandrakala
 */
@ViewScoped
@Named
public class QuestionMCView implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB private SubjectTagBean subjectTagBean;
    @EJB private QuestionBean questionBean;
    @EJB private UserBean userBean;

    //input
    private String selectedModule;
    private Integer[] selectedSubjects;
    private Integer marks;
    private String qusetionText;
    private String selectedType;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;

    //output
    private Collection<Module> modules;
    private Module selectedModuleObj;
    
    @PostConstruct
    public void init() {
        String loginID = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        if (loginID != null && !"".equals(loginID)) {
            modules = userBean.findUserByID(loginID).getModuleCollection(); //moduleBean.findAllModules();
        }
    }

    public String getSelectedModule() {
        return selectedModule;
    }

    public void setSelectedModule(String selectedModule) {
        this.selectedModule = selectedModule;

    }

    public Integer[] getSelectedSubjects() {
        return selectedSubjects;
    }

    public Collection<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void setSelectedSubjects(Integer[] selectedSubjects) {
        this.selectedSubjects = selectedSubjects;
    }

    public Module getSelectedModuleObj() {
        return selectedModuleObj;
    }

    public void setSelectedModuleObj(Module selectedModuleObj) {
        this.selectedModuleObj = selectedModuleObj;
    }
    
    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }
    
    public String getQusetionText() {
        return qusetionText;
    }

    public void setQusetionText(String qusetionText) {
        this.qusetionText = qusetionText;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String create() {
        boolean result = false;
        String addQuestionResult = null;
        try {
            List<String> opts = new ArrayList<String>();
            opts.add(opt1);
            opts.add(opt2);
            opts.add(opt3);
            opts.add(opt4);
            questionBean.createQuestion(selectedSubjects, marks, qusetionText, selectedType, opts, null, -1);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        if (result == true) {
            addQuestionResult = "Save question success";
        } else {
            addQuestionResult = "Save question fail";
        }
        clear();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Add Question", addQuestionResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        //((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).setAttribute("pkQuestionId", question.getPkQuestionId());
        //((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).setAttribute("selectedPart", selectedPart);
        return null;//("/lecturer/addQuestionParts.xhtml");
    }
    
    public void search() {
        System.out.println("search selectedModule: " + selectedModule);
        //subjectsCollection = subjectTagBean.findSubjectTags(selectedModule);
        selectedModuleObj = subjectTagBean.findModuleByModuleCode(selectedModule);
    }
    
    private void clear() {
        selectedModule = null;
        selectedSubjects = null;
        marks = null;
        qusetionText = null;
        selectedModuleObj = null;
        selectedType = null;
        opt1 = null;
        opt2 = null;
        opt3 = null;
        opt4 = null;
    }

}
