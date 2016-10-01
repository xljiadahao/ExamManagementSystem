/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.ModuleBean;
import sg.edu.nus.iss.examsys.team3ft.business.SubjectTagBean;
import sg.edu.nus.iss.examsys.team3ft.model.Module;

/**
 *
 * @author Lei
 */
@RequestScoped
@Named
public class SubjectRegistrationView implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    //input
    private String moduleCode;
    private String subjectName;
    
    @EJB private SubjectTagBean subjectTagBean;
    @EJB private ModuleBean moduleBean;
    
    //output
    private List<Module> modules;
    
    @PostConstruct
    public void init() {
        setModules(moduleBean.findAllModules());
    }

    /**
     * @return the moduleCode
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * @param moduleCode the moduleCode to set
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * @return the modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * @param modules the modules to set
     */
    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
    
    public String subjectRegister() {
        System.out.println("subjectRegister input start -----------------");
        System.out.println("moduleCode: " + moduleCode);
        System.out.println("subjectName: " + subjectName);
        System.out.println("subjectRegister input end   -----------------");
        //Module module = moduleBean.findModuleByModuleCode(moduleCode);
        //SubjectTag subjectTag = new SubjectTag();
        //subjectTag.setFkModuleCode(module);
        boolean result = false;
        try {
            subjectTagBean.createModule(moduleCode, subjectName);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        String registerUserResult = null;
        if (result == true) {
            registerUserResult = "Register subject " + subjectName + " success";
        } else {
            registerUserResult = "Register subject " + subjectName + " fail";
        }
        clear();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Subject Register", registerUserResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return null;
    }
    
     private void clear() {
        moduleCode = null;
        subjectName = null;
    }
    
}
