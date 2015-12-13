/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.utils;

import com.filipferm.ejb.GroupEJB;
import com.filipferm.ejb.UserEJB;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Filip
 */
@ManagedBean
@SessionScoped
public class SessionFilter {

    @EJB
    UserEJB userEJB;
    @EJB
    GroupEJB groupEJB;

    public boolean checkUser(String pageRoleLimit) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext exCon = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) exCon.getRequest();

        String currentUri = request.getRequestURI();

        Map<String, Object> session = exCon.getSessionMap();
        int userId;
        if (!session.isEmpty() && session.containsKey("login")) {
            userId = (int) session.get("login");
        } else {
            return false;
        }
        
        String userRole = checkUserRole(userId);
        if (userRole.equals("admin") || pageRoleLimit.equals(userRole)) {
            return true;
        }

        return false;

//        if (!session.isEmpty()) {
//
//            if (session.containsKey("login")
//                    && checkUserRole("admin", (int) session.get("login"))) {
//                return true;
//            } else if (session.containsKey("login")
//                    && currentUri.contains("user")
//                    && checkUserRole("user", (int) session.get("login"))) {
//                return true;
//            }
//            return false;
//        }
//        return false;
    }

    private String checkUserRole(int userId) {
        return groupEJB.getGroupnameByUserName(userEJB.getUsernameById(userId));
    }

    public boolean loggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("login");
    }

}
