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
import javax.persistence.JoinTable;
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
@Table(name = "exam_section")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamSection.findAll", query = "SELECT e FROM ExamSection e"),
    @NamedQuery(name = "ExamSection.findByPkExamSectionId", query = "SELECT e FROM ExamSection e WHERE e.pkExamSectionId = :pkExamSectionId"),
    @NamedQuery(name = "ExamSection.findBySectionName", query = "SELECT e FROM ExamSection e WHERE e.sectionName = :sectionName"),
    @NamedQuery(name = "ExamSection.findByTotalMarks", query = "SELECT e FROM ExamSection e WHERE e.totalMarks = :totalMarks"),
    @NamedQuery(name = "ExamSection.findBySectionType", query = "SELECT e FROM ExamSection e WHERE e.sectionType = :sectionType")})
public class ExamSection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_EXAM_SECTION_ID")
    private Integer pkExamSectionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "SECTION_NAME")
    private String sectionName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTAL_MARKS")
    private int totalMarks;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "SECTION_TYPE")
    private String sectionType;
    @JoinTable(name = "exam_section_question", joinColumns = {
        @JoinColumn(name = "FK_EXAM_SECTION_ID", referencedColumnName = "PK_EXAM_SECTION_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_QUESTION_ID", referencedColumnName = "PK_QUESTION_ID")})
    @ManyToMany
    private Collection<Question> questionCollection;
    @JoinColumn(name = "FK_EXAM_ID", referencedColumnName = "PK_EXAM_ID")
    @ManyToOne(optional = false)
    private ExamPaper fkExamId;

    public ExamSection() {
    }

    public ExamSection(Integer pkExamSectionId) {
        this.pkExamSectionId = pkExamSectionId;
    }

    public ExamSection(Integer pkExamSectionId, String sectionName, int totalMarks, String sectionType) {
        this.pkExamSectionId = pkExamSectionId;
        this.sectionName = sectionName;
        this.totalMarks = totalMarks;
        this.sectionType = sectionType;
    }

    public Integer getPkExamSectionId() {
        return pkExamSectionId;
    }

    public void setPkExamSectionId(Integer pkExamSectionId) {
        this.pkExamSectionId = pkExamSectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public ExamPaper getFkExamId() {
        return fkExamId;
    }

    public void setFkExamId(ExamPaper fkExamId) {
        this.fkExamId = fkExamId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkExamSectionId != null ? pkExamSectionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamSection)) {
            return false;
        }
        ExamSection other = (ExamSection) object;
        if ((this.pkExamSectionId == null && other.pkExamSectionId != null) || (this.pkExamSectionId != null && !this.pkExamSectionId.equals(other.pkExamSectionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.ExamSection[ pkExamSectionId=" + pkExamSectionId + " ]";
    }
    
}
