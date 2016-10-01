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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.enableQuestionsCount", query = "SELECT COUNT(q) FROM Question q WHERE q.questionStatus='ENABLE'"),
    @NamedQuery(name = "Question.findByPkQuestionId", query = "SELECT q FROM Question q WHERE q.pkQuestionId = :pkQuestionId"),
    @NamedQuery(name = "Question.findByQuestionSequence", query = "SELECT q FROM Question q WHERE q.questionSequence = :questionSequence"),
    @NamedQuery(name = "Question.findQuestionVersionCount", query = "SELECT COUNT(q) FROM Question q WHERE q.questionSequence = :questionSequence"),
    @NamedQuery(name = "Question.findByVersionNumber", query = "SELECT q FROM Question q WHERE q.versionNumber = :versionNumber"),
    @NamedQuery(name = "Question.findByCreatedOn", query = "SELECT q FROM Question q WHERE q.createdOn = :createdOn"),
    @NamedQuery(name = "Question.findByMark", query = "SELECT q FROM Question q WHERE q.mark = :mark"),
    @NamedQuery(name = "Question.findByQuestionType", query = "SELECT q FROM Question q WHERE q.questionType = :questionType"),
    @NamedQuery(name = "Question.findByQuestionText", query = "SELECT q FROM Question q WHERE q.questionText = :questionText"),
    @NamedQuery(name = "Question.findByQuestionStatus", query = "SELECT q FROM Question q WHERE q.questionStatus = :questionStatus")})
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_QUESTION_ID")
    private Integer pkQuestionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTION_SEQUENCE")
    private int questionSequence;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "VERSION_NUMBER")
    private String versionNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARK")
    private int mark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "QUESTION_TYPE")
    private String questionType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "QUESTION_TEXT")
    private String questionText;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "QUESTION_STATUS")
    private String questionStatus;
    @JoinTable(name = "subject_tag_question", joinColumns = {
        @JoinColumn(name = "FK_QUESTION_ID", referencedColumnName = "PK_QUESTION_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_TAG_ID", referencedColumnName = "PK_TAG_ID")})
    @ManyToMany
    private Collection<SubjectTag> subjectTagCollection;
    @ManyToMany(mappedBy = "questionCollection")
    private Collection<ExamSection> examSectionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkQuestionId")
    private Collection<QuestionMultipleChoiceOptions> questionMultipleChoiceOptionsCollection;
    @JoinColumn(name = "FK_CREATED_BY", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private User fkCreatedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkQuestionId")
    private Collection<ExamQuestionAnswer> examQuestionAnswerCollection;

    public Question() {
    }

    public Question(Integer pkQuestionId) {
        this.pkQuestionId = pkQuestionId;
    }

    public Question(Integer pkQuestionId, int questionSequence, String versionNumber, Date createdOn, int mark, String questionType, String questionText, String questionStatus) {
        this.pkQuestionId = pkQuestionId;
        this.questionSequence = questionSequence;
        this.versionNumber = versionNumber;
        this.createdOn = createdOn;
        this.mark = mark;
        this.questionType = questionType;
        this.questionText = questionText;
        this.questionStatus = questionStatus;
    }

    public Integer getPkQuestionId() {
        return pkQuestionId;
    }

    public void setPkQuestionId(Integer pkQuestionId) {
        this.pkQuestionId = pkQuestionId;
    }

    public int getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(int questionSequence) {
        this.questionSequence = questionSequence;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    @XmlTransient
    public Collection<SubjectTag> getSubjectTagCollection() {
        return subjectTagCollection;
    }

    public void setSubjectTagCollection(Collection<SubjectTag> subjectTagCollection) {
        this.subjectTagCollection = subjectTagCollection;
    }

    @XmlTransient
    public Collection<ExamSection> getExamSectionCollection() {
        return examSectionCollection;
    }

    public void setExamSectionCollection(Collection<ExamSection> examSectionCollection) {
        this.examSectionCollection = examSectionCollection;
    }

    @XmlTransient
    public Collection<QuestionMultipleChoiceOptions> getQuestionMultipleChoiceOptionsCollection() {
        return questionMultipleChoiceOptionsCollection;
    }

    public void setQuestionMultipleChoiceOptionsCollection(Collection<QuestionMultipleChoiceOptions> questionMultipleChoiceOptionsCollection) {
        this.questionMultipleChoiceOptionsCollection = questionMultipleChoiceOptionsCollection;
    }

    public User getFkCreatedBy() {
        return fkCreatedBy;
    }

    public void setFkCreatedBy(User fkCreatedBy) {
        this.fkCreatedBy = fkCreatedBy;
    }

    @XmlTransient
    public Collection<ExamQuestionAnswer> getExamQuestionAnswerCollection() {
        return examQuestionAnswerCollection;
    }

    public void setExamQuestionAnswerCollection(Collection<ExamQuestionAnswer> examQuestionAnswerCollection) {
        this.examQuestionAnswerCollection = examQuestionAnswerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkQuestionId != null ? pkQuestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.pkQuestionId == null && other.pkQuestionId != null) || (this.pkQuestionId != null && !this.pkQuestionId.equals(other.pkQuestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.Question[ pkQuestionId=" + pkQuestionId + " ]";
    }
    
}
