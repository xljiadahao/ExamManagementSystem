/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.utilities;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author Lei
 */
@ApplicationScoped
@Named
public class Utility {
    
    //private List<Module> modules;
    private List<String> themes;
    
    @PostConstruct
    public void init() {
//        setModules(new ArrayList<Module>());
//        getModules().add(new Module("SG5209","Enterprise Java"));
//        getModules().add(new Module("SG5208","OO Design Pattern"));
//        getModules().add(new Module("SG5101","OO Analysis and Design"));
//        getModules().add(new Module("SG5103","Software Quality Management"));
        
        setThemes(new ArrayList<String>());
        getThemes().add("sunny");
        getThemes().add("start");
        getThemes().add("south-street");
        //getThemes().add("rocket");
        getThemes().add("home");
        //getThemes().add("afternoon");
        //getThemes().add("afterwork");
        //getThemes().add("bluesky");
        //getThemes().add("delta");
        //getThemes().add("midnight");
        getThemes().add("overcast");
        getThemes().add("redmond");
        getThemes().add("smoothness");
        
    }

    /**
     * @return the themes
     */
    public List<String> getThemes() {
        return themes;
    }

    /**
     * @param themes the themes to set
     */
    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

//    /**
//     * @return the modules
//     */
//    public List<Module> getModules() {
//        return modules;
//    }
//
//    /**
//     * @param modules the modules to set
//     */
//    public void setModules(List<Module> modules) {
//        this.modules = modules;
//    }
    
}
