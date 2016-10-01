/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.business;

import java.util.List;
import javax.ejb.ApplicationException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.examsys.team3ft.model.Module;

/**
 *
 * @author Lei
 */
@ApplicationException(rollback=true)
@Stateless
public class ModuleBean {
    
    @PersistenceContext EntityManager em;
    
    public List<Module> findAllModules() {
        TypedQuery<Module> query = em.createNamedQuery("Module.findAll", Module.class);
        return query.getResultList();
    }
    
    public Module findModuleByModuleCode(String moduleCode) {
        TypedQuery<Module> query = em.createNamedQuery("Module.findByPkModuleCode", Module.class);
        query.setParameter("pkModuleCode", moduleCode);
        Module module = query.getSingleResult();
        module.getSubjectTagCollection();
        return module;
    }
    
    public void createModule(Module module) {
        try {
            em.persist(module);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
