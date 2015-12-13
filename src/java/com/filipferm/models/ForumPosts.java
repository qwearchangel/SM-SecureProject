/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.models;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Filip
 */
@Entity
@Table(name = "forum_posts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ForumPosts.findAll", query = "SELECT f FROM ForumPosts f"),
    @NamedQuery(name = "ForumPosts.findByIdForumPosts", query = "SELECT f FROM ForumPosts f WHERE f.idForumPosts = :idForumPosts"),
    @NamedQuery(name = "ForumPosts.findByMessage", query = "SELECT f FROM ForumPosts f WHERE f.message = :message")})
public class ForumPosts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idForum_Posts")
    private Integer idForumPosts;
    @Size(max = 255)
    @Column(name = "message")
    private String message;
    @JoinColumn(name = "users_user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    public ForumPosts() {
    }

    public ForumPosts(Integer idForumPosts) {
        this.idForumPosts = idForumPosts;
    }

    public Integer getIdForumPosts() {
        return idForumPosts;
    }

    public void setIdForumPosts(Integer idForumPosts) {
        this.idForumPosts = idForumPosts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idForumPosts != null ? idForumPosts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ForumPosts)) {
            return false;
        }
        ForumPosts other = (ForumPosts) object;
        if ((this.idForumPosts == null && other.idForumPosts != null) || (this.idForumPosts != null && !this.idForumPosts.equals(other.idForumPosts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.filipferm.models.ForumPosts[ idForumPosts=" + idForumPosts + " ]";
    }
    
}
