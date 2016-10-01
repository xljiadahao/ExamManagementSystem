/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "exam_question_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamQuestionAnswer.findAll", query = "SELECT e FROM ExamQuestionAnswer e"),
    @NamedQuery(name = "ExamQuestionAnswer.countUserAnswer", query = "SELECT COUNT(e) FROM ExamQuestionAnswer e WHERE e.fkStudentId = :fkStudentId AND e.fkExamSessionId = :fkExamSessionId"),
    @NamedQuery(name = "ExamQuestionAnswer.findUserAnswered", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.fkStudentId = :fkStudentId AND e.fkExamSessionId = :fkExamSessionId"),
    @NamedQuery(name = "ExamQuestionAnswer.findByPkExamQuestionAnswerId", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.pkExamQuestionAnswerId = :pkExamQuestionAnswerId"),
    @NamedQuery(name = "ExamQuestionAnswer.findByCreatedTime", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.createdTime = :createdTime"),
    @NamedQuery(name = "ExamQuestionAnswer.findByAnswer", query = "SELECT e FROM ExamQuestionAnswer e WHERE e.answer = :answer")})
public class ExamQuestionAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_EXAM_QUESTION_ANSWER_ID")
    private Integer pkExamQuestionAnswerId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 254)
    @Column(name = "ANSWER")
    private String answer;
    @JoinColumn(name = "FK_STUDENT_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false)
    private User fkStudentId;
    @JoinColumn(name = "FK_QUESTION_ID", referencedColumnName = "PK_QUESTION_ID")
    @ManyToOne(optional = false)
    private Question fkQuestionId;
    @JoinColumn(name = "FK_EXAM_SESSION_ID", referencedColumnName = "PK_EXAM_SESSION_ID")
    @ManyToOne(optional = false)
    private ExamSession fkExamSessionId;

    public ExamQuestionAnswer() {
    }

    public ExamQuestionAnswer(Integer pkExamQuestionAnswerId) {
        this.pkExamQuestionAnswerId = pkExamQuestionAnswerId;
    }

    public ExamQuestionAnswer(Integer pkExamQuestionAnswerId, Date createdTime, String answer) {
        this.pkExamQuestionAnswerId = pkExamQuestionAnswerId;
        this.createdTime = createdTime;
        this.answer = answer;
    }

    public Integer getPkExamQuestionAnswerId() {
        return pkExamQuestionAnswerId;
    }

    public void setPkExamQuestionAnswerId(Integer pkExamQuestionAnswerId) {
        this.pkExamQuestionAnswerId = pkExamQuestionAnswerId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public User getFkStudentId() {
        return fkStudentId;
    }

    public void setFkStudentId(User fkStudentId) {
        this.fkStudentId = fkStudentId;
    }

    public Question getFkQuestionId() {
        return fkQuestionId;
    }

    public void setFkQuestionId(Question fkQuestionId) {
        this.fkQuestionId = fkQuestionId;
    }

    public ExamSession getFkExamSessionId() {
        return fkExamSessionId;
    }

    public void setFkExamSessionId(ExamSession fkExamSessionId) {
        this.fkExamSessionId = fkExamSessionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkExamQuestionAnswerId != null ? pkExamQuestionAnswerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamQuestionAnswer)) {
            return false;
        }
        ExamQuestionAnswer other = (ExamQuestionAnswer) object;
        if ((this.pkExamQuestionAnswerId == null && other.pkExamQuestionAnswerId != null) || (this.pkExamQuestionAnswerId != null && !this.pkExamQuestionAnswerId.equals(other.pkExamQuestionAnswerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.ExamQuestionAnswer[ pkExamQuestionAnswerId=" + pkExamQuestionAnswerId + " ]";
    }
    
}
