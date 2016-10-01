/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "subject_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectTag.findAll", query = "SELECT s FROM SubjectTag s"),
    @NamedQuery(name = "SubjectTag.findByPkTagId", query = "SELECT s FROM SubjectTag s WHERE s.pkTagId = :pkTagId"),
    @NamedQuery(name = "SubjectTag.findByUkTagName", query = "SELECT s FROM SubjectTag s WHERE s.ukTagName = :ukTagName")})
public class SubjectTag implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_TAG_ID")
    private Integer pkTagId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UK_TAG_NAME")
    private String ukTagName;
    @ManyToMany(mappedBy = "subjectTagCollection")
    private Collection<Question> questionCollection;
    @JoinColumn(name = "FK_MODULE_CODE", referencedColumnName = "PK_MODULE_CODE")
    @ManyToOne(optional = false)
    private Module fkModuleCode;

    public SubjectTag() {
    }

    public SubjectTag(Integer pkTagId) {
        this.pkTagId = pkTagId;
    }

    public SubjectTag(Integer pkTagId, String ukTagName) {
        this.pkTagId = pkTagId;
        this.ukTagName = ukTagName;
    }

    public Integer getPkTagId() {
        return pkTagId;
    }

    public void setPkTagId(Integer pkTagId) {
        this.pkTagId = pkTagId;
    }

    public String getUkTagName() {
        return ukTagName;
    }

    public void setUkTagName(String ukTagName) {
        this.ukTagName = ukTagName;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public Module getFkModuleCode() {
        return fkModuleCode;
    }

    public void setFkModuleCode(Module fkModuleCode) {
        this.fkModuleCode = fkModuleCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkTagId != null ? pkTagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubjectTag)) {
            return false;
        }
        SubjectTag other = (SubjectTag) object;
        if ((this.pkTagId == null && other.pkTagId != null) || (this.pkTagId != null && !this.pkTagId.equals(other.pkTagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.SubjectTag[ pkTagId=" + pkTagId + " ]";
    }
    
}
