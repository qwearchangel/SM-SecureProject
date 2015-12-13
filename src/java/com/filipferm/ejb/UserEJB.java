/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.ejb;

import com.filipferm.encryption_util.EncryptionModule;
import static com.filipferm.encryption_util.EncryptionModule.toHex;
import com.filipferm.encryption_util.RandomSaltGenerator;
import com.filipferm.models.Users;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Filip
 */
@Stateless
public class UserEJB {

    @PersistenceContext(name = "SM-SecureProjectPU")
    EntityManager em;

    public UserEJB() {
    }

    public Users checkLogin(String username, String password) {
        Users user = em.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", username).getSingleResult();
        
        if (!validatePassword(password, user)) {
            user = null;
        }
        return user;
    }
    
    public String getUsernameById(int id) {
        Users user = em.createNamedQuery("Users.findByUserId", Users.class).setParameter("userId", id).getSingleResult();
        return user.getUsername();
    }

    public boolean validatePassword(String testPass, Users user) {
        return EncryptionModule.validatePassword(testPass, user.getPassword(), user.getIterations(), user.getSalt());
    }

    public void addUser() {
        String password = "password";
        String username = "admin";
        String username2 = "user";
        int iteration = 1000;
        byte[] salt = RandomSaltGenerator.getRandomSalt(32);

        String hashedPass = null;
        try {
            hashedPass = toHex(EncryptionModule.pbkdf2(password.toCharArray(), salt, iteration, 32));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        Users user = new Users();
        Users user2 = new Users();

        user.setEmail("sdf@sdf.se");
        user.setPassword(hashedPass);
        user.setIterations(iteration);
        user.setSalt(toHex(salt));
        user.setUsername(username);
        em.persist(user);

        user2.setEmail("sdf@sdf.se");
        user2.setPassword(hashedPass);
        user2.setIterations(iteration);
        user2.setSalt(toHex(salt));
        user2.setUsername(username2);
        em.persist(user2);

        em.flush();
    }

}
