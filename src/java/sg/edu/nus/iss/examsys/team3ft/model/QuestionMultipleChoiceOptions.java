/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "question_multiple_choice_options")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionMultipleChoiceOptions.findAll", query = "SELECT q FROM QuestionMultipleChoiceOptions q"),
    @NamedQuery(name = "QuestionMultipleChoiceOptions.findByPkQuestionMultipleChoiceOptions", query = "SELECT q FROM QuestionMultipleChoiceOptions q WHERE q.pkQuestionMultipleChoiceOptions = :pkQuestionMultipleChoiceOptions"),
    @NamedQuery(name = "QuestionMultipleChoiceOptions.findByQuestionMultipleChoiceOptionText", query = "SELECT q FROM QuestionMultipleChoiceOptions q WHERE q.questionMultipleChoiceOptionText = :questionMultipleChoiceOptionText")})
public class QuestionMultipleChoiceOptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_QUESTION_MULTIPLE_CHOICE_OPTIONS")
    private Integer pkQuestionMultipleChoiceOptions;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "QUESTION_MULTIPLE_CHOICE_OPTION_TEXT")
    private String questionMultipleChoiceOptionText;
    @JoinColumn(name = "FK_QUESTION_ID", referencedColumnName = "PK_QUESTION_ID")
    @ManyToOne(optional = false)
    private Question fkQuestionId;

    public QuestionMultipleChoiceOptions() {
    }

    public QuestionMultipleChoiceOptions(Integer pkQuestionMultipleChoiceOptions) {
        this.pkQuestionMultipleChoiceOptions = pkQuestionMultipleChoiceOptions;
    }

    public QuestionMultipleChoiceOptions(Integer pkQuestionMultipleChoiceOptions, String questionMultipleChoiceOptionText) {
        this.pkQuestionMultipleChoiceOptions = pkQuestionMultipleChoiceOptions;
        this.questionMultipleChoiceOptionText = questionMultipleChoiceOptionText;
    }

    public Integer getPkQuestionMultipleChoiceOptions() {
        return pkQuestionMultipleChoiceOptions;
    }

    public void setPkQuestionMultipleChoiceOptions(Integer pkQuestionMultipleChoiceOptions) {
        this.pkQuestionMultipleChoiceOptions = pkQuestionMultipleChoiceOptions;
    }

    public String getQuestionMultipleChoiceOptionText() {
        return questionMultipleChoiceOptionText;
    }

    public void setQuestionMultipleChoiceOptionText(String questionMultipleChoiceOptionText) {
        this.questionMultipleChoiceOptionText = questionMultipleChoiceOptionText;
    }

    public Question getFkQuestionId() {
        return fkQuestionId;
    }

    public void setFkQuestionId(Question fkQuestionId) {
        this.fkQuestionId = fkQuestionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkQuestionMultipleChoiceOptions != null ? pkQuestionMultipleChoiceOptions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionMultipleChoiceOptions)) {
            return false;
        }
        QuestionMultipleChoiceOptions other = (QuestionMultipleChoiceOptions) object;
        if ((this.pkQuestionMultipleChoiceOptions == null && other.pkQuestionMultipleChoiceOptions != null) || (this.pkQuestionMultipleChoiceOptions != null && !this.pkQuestionMultipleChoiceOptions.equals(other.pkQuestionMultipleChoiceOptions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.QuestionMultipleChoiceOptions[ pkQuestionMultipleChoiceOptions=" + pkQuestionMultipleChoiceOptions + " ]";
    }
    
}
