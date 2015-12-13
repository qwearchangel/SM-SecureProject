/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.beans;

import com.filipferm.ejb.UserEJB;
import com.filipferm.models.Users;
import com.filipferm.utils.SessionFilter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Init;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author Filip
 */
@ManagedBean
@SessionScoped
public class LoginBean {

    private String username;
    private String password;
    private String originalUrl;
    private int attempts;

    public LoginBean() {
    }

    @Init
    public void Init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        originalUrl = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);

        if (originalUrl == null) {
            originalUrl = externalContext.getRequestContextPath() + "login.xhtml";
        } else {
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);

            if (originalQuery != null) {
                originalUrl += "?" + originalQuery;
            }
        }
    }

    @EJB
    private UserEJB userEbj;

    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext exCon = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) exCon.getRequest();
        Map<String, Object> session = exCon.getSessionMap();
//        userEbj.addUser();
        String groupPage = exCon.getRequestContextPath() + "/faces/user/index.xhtml";
        try {
            username = Jsoup.clean(username, Whitelist.basic());
            password = Jsoup.clean(password, Whitelist.basic());
//            request.login(username, password);
            if (!session.containsKey("loginattempts")) {
                attempts = 0;
                session.put("loginattempts", attempts);
            }
            int logAttempts = (int) session.get("loginattempts");
            if (logAttempts > 2) {
                /**
                 * Loginattempts should be stored in a database instead of a
                 * session. And attempts should have its own class for checking
                 * ip and how long the account is locked out. If i had done what
                 * i wrote above, it should have been a safe from brute force
                 * and ddos on the login page. 
                 * There should allso be a cooldown between every login attemtps
                 * to make it more secure.
                 */
                context.addMessage(null, new FacesMessage("Maximum number of login attemtps exeeded, Please try again later"));
            } else {
                Users user = userEbj.checkLogin(username, password);
                if (user == null) {
                    attempts++;
                    exCon.getSessionMap().put("loginattempts", attempts);
                    context.addMessage(null, new FacesMessage("No username or password found please try again. Attempts: " + attempts));
                    throw new Exception();
                }
                exCon.getSessionMap().put("login", user.getUserId());
                exCon.redirect(groupPage);
                System.out.println("Logged in");
            }
        } catch (ServletException ex) {
            ex.printStackTrace();
            context.addMessage(null, new FacesMessage("Username or Password is wrong!"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//----------------------------GETTER AND SETTERS---------------------------
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
