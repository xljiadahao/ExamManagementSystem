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
import javax.persistence.OneToOne;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByIsNewUser", query = "SELECT u FROM User u WHERE u.isNewUser = :isNewUser")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "USER_ID")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "IS_NEW_USER")
    private String isNewUser;
    @JoinTable(name = "lecturer_modules_taught", joinColumns = {
        @JoinColumn(name = "FK_LECTURER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_MODULE_CODE", referencedColumnName = "PK_MODULE_CODE")})
    @ManyToMany
    private Collection<Module> moduleCollection;
    @ManyToMany(mappedBy = "userCollection1")
    private Collection<Module> moduleCollection1;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<ExamSession> examSessionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCreatedBy")
    private Collection<Question> questionCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private GroupUser groupUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkStudentId")
    private Collection<ExamQuestionAnswer> examQuestionAnswerCollection;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String password, String userName, String isNewUser) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.isNewUser = isNewUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(String isNewUser) {
        this.isNewUser = isNewUser;
    }

    @XmlTransient
    public Collection<Module> getModuleCollection() {
        return moduleCollection;
    }

    public void setModuleCollection(Collection<Module> moduleCollection) {
        this.moduleCollection = moduleCollection;
    }

    @XmlTransient
    public Collection<Module> getModuleCollection1() {
        return moduleCollection1;
    }

    public void setModuleCollection1(Collection<Module> moduleCollection1) {
        this.moduleCollection1 = moduleCollection1;
    }

    @XmlTransient
    public Collection<ExamSession> getExamSessionCollection() {
        return examSessionCollection;
    }

    public void setExamSessionCollection(Collection<ExamSession> examSessionCollection) {
        this.examSessionCollection = examSessionCollection;
    }

    @XmlTransient
    public Collection<Question> getQuestionCollection() {
        return questionCollection;
    }

    public void setQuestionCollection(Collection<Question> questionCollection) {
        this.questionCollection = questionCollection;
    }

    public GroupUser getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(GroupUser groupUser) {
        this.groupUser = groupUser;
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
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.User[ userId=" + userId + " ]";
    }
    
}
