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
@Table(name = "exam_session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamSession.findAll", query = "SELECT e FROM ExamSession e"),
    @NamedQuery(name = "ExamSession.findCurrentExam", query = "SELECT e FROM ExamSession e where CURRENT_TIMESTAMP>e.examStartTime AND CURRENT_TIMESTAMP<e.examEndTime"),
    @NamedQuery(name = "ExamSession.findFollowingExam", query = "SELECT e FROM ExamSession e where CURRENT_TIMESTAMP<e.examEndTime"),
    @NamedQuery(name = "ExamSession.findByPkExamSessionId", query = "SELECT e FROM ExamSession e WHERE e.pkExamSessionId = :pkExamSessionId"),
    @NamedQuery(name = "ExamSession.findByExamStartTime", query = "SELECT e FROM ExamSession e WHERE e.examStartTime = :examStartTime"),
    @NamedQuery(name = "ExamSession.findByExamEndTime", query = "SELECT e FROM ExamSession e WHERE e.examEndTime = :examEndTime"),
    @NamedQuery(name = "ExamSession.findByLocation", query = "SELECT e FROM ExamSession e WHERE e.location = :location")})
public class ExamSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_EXAM_SESSION_ID")
    private Integer pkExamSessionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXAM_START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date examStartTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXAM_END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date examEndTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LOCATION")
    private String location;
    @JoinTable(name = "exam_session_user", joinColumns = {
        @JoinColumn(name = "FK_EXAM_SESSION_ID", referencedColumnName = "PK_EXAM_SESSION_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_USER_ID", referencedColumnName = "USER_ID")})
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkExamSessionId")
    private Collection<ExamQuestionAnswer> examQuestionAnswerCollection;
    @JoinColumn(name = "FK_EXAM_ID", referencedColumnName = "PK_EXAM_ID")
    @ManyToOne(optional = false)
    private ExamPaper fkExamId;

    public ExamSession() {
    }

    public ExamSession(Integer pkExamSessionId) {
        this.pkExamSessionId = pkExamSessionId;
    }

    public ExamSession(Integer pkExamSessionId, Date examStartTime, Date examEndTime, String location) {
        this.pkExamSessionId = pkExamSessionId;
        this.examStartTime = examStartTime;
        this.examEndTime = examEndTime;
        this.location = location;
    }

    public Integer getPkExamSessionId() {
        return pkExamSessionId;
    }

    public void setPkExamSessionId(Integer pkExamSessionId) {
        this.pkExamSessionId = pkExamSessionId;
    }

    public Date getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }

    public Date getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<ExamQuestionAnswer> getExamQuestionAnswerCollection() {
        return examQuestionAnswerCollection;
    }

    public void setExamQuestionAnswerCollection(Collection<ExamQuestionAnswer> examQuestionAnswerCollection) {
        this.examQuestionAnswerCollection = examQuestionAnswerCollection;
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
        hash += (pkExamSessionId != null ? pkExamSessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamSession)) {
            return false;
        }
        ExamSession other = (ExamSession) object;
        if ((this.pkExamSessionId == null && other.pkExamSessionId != null) || (this.pkExamSessionId != null && !this.pkExamSessionId.equals(other.pkExamSessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.ExamSession[ pkExamSessionId=" + pkExamSessionId + " ]";
    }
    
}
