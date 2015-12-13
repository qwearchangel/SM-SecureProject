/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.ejb;

import com.filipferm.models.Groups;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Filip
 */
@Stateless
public class GroupEJB {
    
    @PersistenceContext(name = "SM-SecureProjectPU")
    EntityManager em;

    public GroupEJB() {
    }
    
    public String getGroupnameByUserName(String username) {
        Groups group = em.createNamedQuery("Groups.findByGroupUser", Groups.class).setParameter("groupUser", username).getSingleResult();
        return group.getGroupName();
    }
    
}
