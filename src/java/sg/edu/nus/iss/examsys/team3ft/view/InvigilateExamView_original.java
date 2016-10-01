/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.view;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import sg.edu.nus.iss.examsys.team3ft.business.ExamBean;

/**
 *
 * @author Lei
 */
@ViewScoped
@Named
public class InvigilateExamView_original {
    
    @EJB private ExamBean examBean;
    
    public void setExamSession(Integer examSessionId) {
        
    }
    
}
