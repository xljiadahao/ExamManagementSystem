/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.examsys.team3ft.business;

import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.examsys.team3ft.model.Module;
import sg.edu.nus.iss.examsys.team3ft.model.SubjectTag;

/**
 *
 * @author Lei
 */
@ApplicationException(rollback = true)
@Stateless
public class SubjectTagBean {

    @PersistenceContext EntityManager em;
    @EJB private ModuleBean moduleBean;

    public void createModule(String moduleCode, String subjectName) {
        try {
            Module module = moduleBean.findModuleByModuleCode(moduleCode);
            SubjectTag subjectTag = new SubjectTag();
            subjectTag.setUkTagName(subjectName);
            subjectTag.setFkModuleCode(module);
            em.persist(subjectTag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Module findModuleByModuleCode(String moduleCode) { //Collection<SubjectTag> findSubjectTags
        TypedQuery<Module> queryModule = em.createNamedQuery("Module.findByPkModuleCode", Module.class);
        queryModule.setParameter("pkModuleCode", moduleCode);
        Module module = queryModule.getSingleResult();
        module.getSubjectTagCollection();
        return module;
    }
    
    public SubjectTag findByPkTagId(Integer pkTagId) {
        TypedQuery<SubjectTag> querySubjectTag = em.createNamedQuery("SubjectTag.findByPkTagId", SubjectTag.class);
        querySubjectTag.setParameter("pkTagId", pkTagId);
        SubjectTag subjectTag = querySubjectTag.getSingleResult();
        subjectTag.getQuestionCollection();
        return subjectTag;
    }

}
