/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.beans;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Filip
 */
@ManagedBean
public class LogoutBean {
    
     public void logout() throws IOException {
        ExternalContext exCon = FacesContext.getCurrentInstance().getExternalContext();
        exCon.invalidateSession();
        exCon.redirect(exCon.getRequestContextPath() + "/faces/login.xhtml");
    }
}
