/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.edu.nus.iss.examsys.team3ft.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lei
 */
@Entity
@Table(name = "group_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupUser.findAll", query = "SELECT g FROM GroupUser g"),
    @NamedQuery(name = "GroupUser.findByGroupId", query = "SELECT g FROM GroupUser g WHERE g.groupUserPK.groupId = :groupId"),
    @NamedQuery(name = "GroupUser.findByUserId", query = "SELECT g FROM GroupUser g WHERE g.groupUserPK.userId = :userId")})
public class GroupUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupUserPK groupUserPK;
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private User user;

    public GroupUser() {
    }

    public GroupUser(GroupUserPK groupUserPK) {
        this.groupUserPK = groupUserPK;
    }

    public GroupUser(String groupId, String userId) {
        this.groupUserPK = new GroupUserPK(groupId, userId);
    }

    public GroupUserPK getGroupUserPK() {
        return groupUserPK;
    }

    public void setGroupUserPK(GroupUserPK groupUserPK) {
        this.groupUserPK = groupUserPK;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupUserPK != null ? groupUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupUser)) {
            return false;
        }
        GroupUser other = (GroupUser) object;
        if ((this.groupUserPK == null && other.groupUserPK != null) || (this.groupUserPK != null && !this.groupUserPK.equals(other.groupUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.examsys.team3ft.model.GroupUser[ groupUserPK=" + groupUserPK + " ]";
    }
    
}
