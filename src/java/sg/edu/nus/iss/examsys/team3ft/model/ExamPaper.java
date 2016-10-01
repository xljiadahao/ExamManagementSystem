/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "exam_paper")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamPaper.findAll", query = "SELECT e FROM ExamPaper e"),
    @NamedQuery(name = "ExamPaper.findByPkExamId", query = "SELECT e FROM ExamPaper e WHERE e.pkExamId = :pkExamId"),
    @NamedQuery(name = "ExamPaper.findByCreateDate", query = "SELECT e FROM ExamPaper e WHERE e.createDate = :createDate")})
public class ExamPaper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_EXAM_ID")
    private Integer pkExamId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "FK_MODULE_CODE", referencedColumnName = "PK_MODULE_CODE")
    @ManyToOne(optional = false)
    private Module fkModuleCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkExamId")
    private Collection<ExamSection> examSectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkExamId")
    private Collection<ExamSession> examSessionCollection;

    public ExamPaper() {
    }

    public ExamPaper(Integer pkExamId) {
        this.pkExamId = pkExamId;
    }

    public ExamPaper(Integer pkExamId, Date createDate) {
        this.pkExamId = pkExamId;
        this.createDate = createDate;
    }

    public Integer getPkExamId() {
        return pkExamId;
    }

    public void setPkExamId(Integer pkExamId) {
        this.pkExamId = pkExamId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Module getFkModuleCode() {
        return fkModuleCode;
    }

    public void setFkModuleCode(Module fkModuleCode) {
        this.fkModuleCode = fkModuleCode;
    }

    @XmlTransient
    public Collection<ExamSection> getExamSectionCollection() {
        return examSectionCollection;
    }

    public void setExamSectionCollection(Collection<ExamSection> examSectionCollection) {
        this.examSectionCollection = examSectionCollection;
    }

    @XmlTransient
    public Collection<ExamSession> getExamSessionCollection() {
        return examSessionCollection;
    }

    public void setExamSessionCollection(Collection<ExamSession> examSessionCollection) {
        this.examSessionCollection = examSessionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkExamId != null ? pkExamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamPaper)) {
            return false;
        }
        ExamPaper other = (ExamPaper) object;
        if ((this.pkExamId == null && other.pkExamId != null) || (this.pkExamId != null && !this.pkExamId.equals(other.pkExamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.ExamPaper[ pkExamId=" + pkExamId + " ]";
    }
    
}
