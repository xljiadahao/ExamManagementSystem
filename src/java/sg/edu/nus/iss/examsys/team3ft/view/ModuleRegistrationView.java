/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import sg.edu.nus.iss.examsys.team3ft.business.ModuleBean;
import sg.edu.nus.iss.examsys.team3ft.model.Module;

/**
 *
 * @author Lei
 */
@RequestScoped
@Named
public class ModuleRegistrationView implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    //input
    private String moduleCode;
    private String moduleName;

    @EJB private ModuleBean moduleBean;
    
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
    
    public String moduleRegister() {
        System.out.println("moduleRegister input start -----------------");
        System.out.println("moduleCode: " + moduleCode);
        System.out.println("moduleName: " + moduleName);
        System.out.println("moduleRegister input end   -----------------");
        Module module = new Module();
        module.setPkModuleCode(moduleCode);
        module.setUkModuleName(moduleName);
        boolean result = false;
        try {
            moduleBean.createModule(module);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        String registerModuleResult = null;
        if (result == true) {
            registerModuleResult = "Register module " + moduleName + " success";
        } else {
            registerModuleResult = "Register module " + moduleName + " fail";
        }
        clear();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exam Management System Module Register", registerModuleResult);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return null;
    }
    
    private void clear() {
        moduleCode = null;
        moduleName = null;
    }
    
}
