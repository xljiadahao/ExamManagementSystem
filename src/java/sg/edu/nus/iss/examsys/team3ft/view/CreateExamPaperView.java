package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;
import sg.edu.nus.iss.examsys.team3ft.business.ModuleBean;
import sg.edu.nus.iss.examsys.team3ft.business.SubjectTagBean;
import sg.edu.nus.iss.examsys.team3ft.business.UserBean;
import sg.edu.nus.iss.examsys.team3ft.model.ExamSection;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.Question;

/**
 *
 * @author Lei
 */
@SessionScoped
@Named
public class CreateExamPaperView implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ModuleBean moduleBean;
    @EJB
    private UserBean userBean;
    @EJB
    private ExamBean examBean;
    @EJB
    private SubjectTagBean subjectTagBean;

    private String selectedModule;
    private Collection<Module> modules;
    //private String display = "display:none;";
    private Module currentModule;

    private String selectedSectionType;
    private String manuDisplay;
    private String autoDisplay;

    private String sectionName;
    //for mannually create exam paper
    private Integer selectedSubject;
    private List<Question> questions;
    private List<Question> selectQuestions;
    private List<Question> questionsForCurrentSection;
    private SimpleDateFormat sdfDateTime;
    private String buttonValue;
    private String noticeMsg;
    //for dynamic create exam papaer
    private Integer markForDynamicCreate;
    private Integer[] selectedSubTagsForAuto;
    private Integer generateMark;
    //for section controll
    private Integer sectionNumber;
    private Integer currentSection;
    private Integer examPaperId;
    

    public void changeSectionType() {
        if (getSelectedSectionType().equalsIgnoreCase("M")) {
            setManuDisplay("display:block;");
            setAutoDisplay("display:none;");
        } else {
            setManuDisplay("display:none;");
            setAutoDisplay("display:block;");
        }
    }

    @PostConstruct
    public void init() {
        String loginID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginID");
        if (loginID != null && !"".equals(loginID)) {
            modules = userBean.findUserByID(loginID).getModuleCollection(); //moduleBean.findAllModules();
        }
    }

    public String generateExamPaper() {
        currentSection = 1;
        defaultSetting();
        currentModule = moduleBean.findModuleByModuleCode(selectedModule);
        examPaperId = examBean.saveExamPaper(selectedModule);
        return "/lecturer/createExamPaperDetail";
    }

    //avoid duplicated questions
    public void searchQuestionsBySubjectTag() {
        setQuestions(new ArrayList<Question>());
        for (Question q : subjectTagBean.findByPkTagId(selectedSubject).getQuestionCollection()) {
            if ("DISABLE".equalsIgnoreCase(q.getQuestionStatus())) {
                continue;
            }
            boolean isExistInThisPaper = false;
            for (ExamSection esc : examBean.refreshExamPaper(examPaperId).getExamSectionCollection()) {
                for (Question qu : esc.getQuestionCollection()) {
                    if (q.getPkQuestionId() == qu.getPkQuestionId()) {
                        isExistInThisPaper = true;
                        break;
                    }
                }
                if (isExistInThisPaper == true) {
                    break;
                }
            }
            boolean isExistInCurrent = false;
            for (Question cur : getQuestionsForCurrentSection()) {
                if (cur.getPkQuestionId() == q.getPkQuestionId()) {
                    isExistInCurrent = true;
                    break;
                }
            }
            if (isExistInThisPaper == false && isExistInCurrent == false) {
                getQuestions().add(q);
            }
        }
    }

    //avoid duplicated questions, for auto generate
//    public void searchQuestionsBySubjectTags() {
//         setQuestions(new ArrayList<Question>());
//        for (Question q : subjectTagBean.findByPkTagId(selectedSubject).getQuestionCollection()) {
//            if ("DISABLE".equalsIgnoreCase(q.getQuestionStatus())) {
//                continue;
//            }
//            boolean isExistInThisPaper = false;
//            for (ExamSection esc : examBean.refreshExamPaper(examPaperId).getExamSectionCollection()) {
//                for (Question qu : esc.getQuestionCollection()) {
//                    if (q.getPkQuestionId() == qu.getPkQuestionId()) {
//                        isExistInThisPaper = true;
//                        break;
//                    }
//                }
//                if (isExistInThisPaper == true) {
//                    break;
//                }
//            }
//            boolean isExistInCurrent = false;
//            for (Question cur : getQuestionsForCurrentSection()) {
//                if (cur.getPkQuestionId() == q.getPkQuestionId()) {
//                    isExistInCurrent = true;
//                    break;
//                }
//            }
//            if (isExistInThisPaper == false && isExistInCurrent == false) {
//                getQuestions().add(q);
//            }
//        }
//    }
    public void addToCurrentSection() {
        System.out.println("addToCurrentSection start ------------------------");
        for (Question q : selectQuestions) {
            System.out.println(q.getPkQuestionId() + ", " + q.getQuestionText());
            getQuestionsForCurrentSection().add(q);
        }

        System.out.println("addToCurrentSection end   ------------------------");
        //refresh view
        setQuestions(new ArrayList<Question>());
        selectedSubject = null;
    }
    
    public String saveAutoGenerateSection() {
        if (sectionName == null || "".equalsIgnoreCase(sectionName)) {
            sectionName = "Section " + currentSection;
        }
        System.out.println("dynamicallySaveForCurrentSection start ----------------------");
        System.out.println("sectionName: " + sectionName);
        System.out.println("examPaperId: " + examPaperId);
        System.out.println("generateMark: " + generateMark);
        System.out.println("selectedSectionType: " + selectedSectionType);
        System.out.println("dynamicallySaveForCurrentSection end   ----------------------");
        ExamSection examSection = new ExamSection();
        examSection.setSectionName(sectionName);
        examSection.setSectionType(selectedSectionType);
        examSection.setTotalMarks(generateMark);
        String registerUserResult = null;
        try {
            examBean.saveExamSection(examPaperId, getQuestionsForCurrentSection(), examSection);
            registerUserResult = "Create Exam Section Success";
        } catch (Exception e) {
            registerUserResult = "Create Exam Section Fail";
        }
        String url = refreshExamSectionView();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Create Exam Paper", registerUserResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        //refresh view, selectedSectionType sectionName selectedSubject
        return url;    
    }

    public void dynamicallySaveForCurrentSection() {
        List<Question> questionResource = new ArrayList<Question>();
        //auto generate algorithm
        for (Integer subjectTagId : selectedSubTagsForAuto) {
            selectedSubject = subjectTagId;
            searchQuestionsBySubjectTag();
            for(Question q : getQuestions()) {
                boolean isExist = false;
                for(Question qr : questionResource) {
                    if(q.getPkQuestionId() == qr.getPkQuestionId()) {
                        isExist = true;
                        break;
                    }
                }
                if(isExist) {
                    continue;
                } else {
                    questionResource.add(q);
                }
            }
        }
        questionsForCurrentSection = autoGenerateQuestionAlgorithm(questionResource, markForDynamicCreate);
        generateMark = 0;
        for(Question q : questionsForCurrentSection) {
            generateMark += q.getMark();
        }
    }

    private List<Question> autoGenerateQuestionAlgorithm(List<Question> questions, Integer totalMark) {
        int currentMark = 0;
        List<Question> selectedQuestions = new ArrayList<Question>();
        for(int i=0; i<questions.size(); i++) {
            Question q = questions.get(i);
            currentMark += q.getMark();
            if(currentMark < totalMark) {
                selectedQuestions.add(q);
                continue;
            } else if (currentMark == totalMark){
                selectedQuestions.add(q);
                break;
            } else {
                currentMark -= q.getMark();
                int diff = totalMark - currentMark;
                boolean isMatch = false;
                while(diff > 0) {
                    for(int tmpIndex=i; tmpIndex<questions.size(); tmpIndex++){
                        if(diff == questions.get(tmpIndex).getMark()) {
                            selectedQuestions.add(questions.get(tmpIndex));
                            isMatch = true;
                            break;
                        }
                    }
                    if(isMatch == true) {
                        break;
                    } else {
                        diff--;
                    }
                }
                break;
            }
        }
        return selectedQuestions;
    }

    public String mannuallySaveForCurrentSection() {
        Integer totalMarks = 0;
        for (Question q : getQuestionsForCurrentSection()) {
            totalMarks += q.getMark();
        }
        if (sectionName == null || "".equalsIgnoreCase(sectionName)) {
            sectionName = "Section " + currentSection;
        }
        System.out.println("saveForCurrentSection start ----------------------");
        System.out.println("sectionName: " + sectionName);
        System.out.println("examPaperId: " + examPaperId);
        System.out.println("totalMarks: " + totalMarks);
        System.out.println("selectedSectionType: " + selectedSectionType);
        System.out.println("saveForCurrentSection end   ----------------------");
        ExamSection examSection = new ExamSection();
        examSection.setSectionName(sectionName);
        examSection.setSectionType(selectedSectionType);
        examSection.setTotalMarks(totalMarks);
        String registerUserResult = null;
        try {
            examBean.saveExamSection(examPaperId, getQuestionsForCurrentSection(), examSection);
            registerUserResult = "Create Exam Section Success";
        } catch (Exception e) {
            registerUserResult = "Create Exam Section Fail";
        }
        String url = refreshExamSectionView();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Create Exam Paper", registerUserResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        //refresh view, selectedSectionType sectionName selectedSubject
        return url;
    }

    private String refreshExamSectionView() {
        if (sectionNumber == currentSection) {
            generateMark = null;
            selectedSubTagsForAuto = null;
            selectedModule = null;
            currentModule = null;
            selectedSectionType = null;
            manuDisplay = null;
            autoDisplay = null;
            sectionName = null;
            //for mannually create exam paper
            selectedSubject = null;
            questions = null;
            selectQuestions = null;
            setQuestionsForCurrentSection(null);
            buttonValue = null;
            //for dynamic create exam papaer
            markForDynamicCreate = null;
            //for section controll
            sectionNumber = null;
            currentSection = null;
            examPaperId = null;
            return "/lecturer/createExamPaper";
        } else {
            currentSection++;
            defaultSetting();
            return "/lecturer/createExamPaperDetail";
        }
    }

    private void defaultSetting() {
        //default setting
        selectedSubTagsForAuto= null;
        generateMark = null;
        manuDisplay = "display:block;";
        autoDisplay = "display:none;";
        selectedSectionType = "M";
        sdfDateTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.UK);
        questions = null;
        selectedSubject = null;
        selectQuestions = null;
        sectionName = null;
        markForDynamicCreate = null;
        setQuestionsForCurrentSection(new ArrayList<Question>());
        if (sectionNumber > currentSection) {
            buttonValue = "Save for Section";
            noticeMsg = "Go To Next Section";
        } else if (sectionNumber == currentSection) {
            buttonValue = "Submit";
            noticeMsg = "Submit";
        }
    }

    public void setSectionNumber(Integer sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public String getSelectedModule() {
        return selectedModule;
    }

    public Collection<Module> getModules() {
        return modules;
    }

    public void setSelectedModule(String selectedModule) {
        this.selectedModule = selectedModule;
    }

    public void setModules(Collection<Module> modules) {
        this.modules = modules;
    }

//    public void onChange() {
//        display = "display:block";
//    }
    /**
     * @return the manuDisplay
     */
    public String getManuDisplay() {
        return manuDisplay;
    }

    /**
     * @param manuDisplay the manuDisplay to set
     */
    public void setManuDisplay(String manuDisplay) {
        this.manuDisplay = manuDisplay;
    }

    /**
     * @return the autoDisplay
     */
    public String getAutoDisplay() {
        return autoDisplay;
    }

    /**
     * @param autoDisplay the autoDisplay to set
     */
    public void setAutoDisplay(String autoDisplay) {
        this.autoDisplay = autoDisplay;
    }

    /**
     * @return the selectedSectionType
     */
    public String getSelectedSectionType() {
        return selectedSectionType;
    }

    /**
     * @param selectedSectionType the selectedSectionType to set
     */
    public void setSelectedSectionType(String selectedSectionType) {
        this.selectedSectionType = selectedSectionType;
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
     * @return the markForDynamicCreate
     */
    public Integer getMarkForDynamicCreate() {
        return markForDynamicCreate;
    }

    /**
     * @param markForDynamicCreate the markForDynamicCreate to set
     */
    public void setMarkForDynamicCreate(Integer markForDynamicCreate) {
        this.markForDynamicCreate = markForDynamicCreate;
    }

    /**
     * @return the currentSection
     */
    public Integer getCurrentSection() {
        return currentSection;
    }

    /**
     * @param currentSection the currentSection to set
     */
//    public void setCurrentSection(Integer currentSection) {
//        this.currentSection = currentSection;
//    }
    /**
     * @return the currentModule
     */
    public Module getCurrentModule() {
        return currentModule;
    }

    /**
     * @param currentModule the currentModule to set
     */
    public void setCurrentModule(Module currentModule) {
        this.currentModule = currentModule;
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
     * @return the selectQuestions
     */
    public List<Question> getSelectQuestions() {
        return selectQuestions;
    }

    /**
     * @param selectQuestions the selectQuestions to set
     */
    public void setSelectQuestions(List<Question> selectQuestions) {
        this.selectQuestions = selectQuestions;
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
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the selectedSubTagsForAuto
     */
    public Integer[] getSelectedSubTagsForAuto() {
        return selectedSubTagsForAuto;
    }

    /**
     * @param selectedSubTagsForAuto the selectedSubTagsForAuto to set
     */
    public void setSelectedSubTagsForAuto(Integer[] selectedSubTagsForAuto) {
        this.selectedSubTagsForAuto = selectedSubTagsForAuto;
    }

    /**
     * @return the noticeMsg
     */
    public String getNoticeMsg() {
        return noticeMsg;
    }

    /**
     * @param noticeMsg the noticeMsg to set
     */
    public void setNoticeMsg(String noticeMsg) {
        this.noticeMsg = noticeMsg;
    }

    /**
     * @return the questionsForCurrentSection
     */
    public List<Question> getQuestionsForCurrentSection() {
        return questionsForCurrentSection;
    }

    /**
     * @param questionsForCurrentSection the questionsForCurrentSection to set
     */
    public void setQuestionsForCurrentSection(List<Question> questionsForCurrentSection) {
        this.questionsForCurrentSection = questionsForCurrentSection;
    }

    /**
     * @return the generateMark
     */
    public Integer getGenerateMark() {
        return generateMark;
    }

}
