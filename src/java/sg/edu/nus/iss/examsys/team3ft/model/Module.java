/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Module.findAll", query = "SELECT m FROM Module m"),
    @NamedQuery(name = "Module.findByPkModuleCode", query = "SELECT m FROM Module m WHERE m.pkModuleCode = :pkModuleCode"),
    @NamedQuery(name = "Module.findByUkModuleName", query = "SELECT m FROM Module m WHERE m.ukModuleName = :ukModuleName")})
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PK_MODULE_CODE")
    private String pkModuleCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UK_MODULE_NAME")
    private String ukModuleName;
    @ManyToMany(mappedBy = "moduleCollection")
    private Collection<User> userCollection;
    @JoinTable(name = "student_modules_enrolled", joinColumns = {
        @JoinColumn(name = "FK_MODULE_CODE", referencedColumnName = "PK_MODULE_CODE")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_STUDENT_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private Collection<User> userCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkModuleCode")
    private Collection<SubjectTag> subjectTagCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkModuleCode")
    private Collection<ExamPaper> examPaperCollection;

    public Module() {
    }

    public Module(String pkModuleCode) {
        this.pkModuleCode = pkModuleCode;
    }

    public Module(String pkModuleCode, String ukModuleName) {
        this.pkModuleCode = pkModuleCode;
        this.ukModuleName = ukModuleName;
    }

    public String getPkModuleCode() {
        return pkModuleCode;
    }

    public void setPkModuleCode(String pkModuleCode) {
        this.pkModuleCode = pkModuleCode;
    }

    public String getUkModuleName() {
        return ukModuleName;
    }

    public void setUkModuleName(String ukModuleName) {
        this.ukModuleName = ukModuleName;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection1() {
        return userCollection1;
    }

    public void setUserCollection1(Collection<User> userCollection1) {
        this.userCollection1 = userCollection1;
    }

    @XmlTransient
    public Collection<SubjectTag> getSubjectTagCollection() {
        return subjectTagCollection;
    }

    public void setSubjectTagCollection(Collection<SubjectTag> subjectTagCollection) {
        this.subjectTagCollection = subjectTagCollection;
    }

    @XmlTransient
    public Collection<ExamPaper> getExamPaperCollection() {
        return examPaperCollection;
    }

    public void setExamPaperCollection(Collection<ExamPaper> examPaperCollection) {
        this.examPaperCollection = examPaperCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkModuleCode != null ? pkModuleCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.pkModuleCode == null && other.pkModuleCode != null) || (this.pkModuleCode != null && !this.pkModuleCode.equals(other.pkModuleCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.Module[ pkModuleCode=" + pkModuleCode + " ]";
    }
    
}
