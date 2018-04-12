package com.dkey.boost.auth;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class PersonBean implements Serializable{
    private String login;
    private String password;
    private boolean authenticated;
    private String initialRequestURI;
    @EJB
    private AuthBean authBean;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getInitialRequestURI() {
        return initialRequestURI;
    }

    public void setInitialRequestURI(String initialRequestURI) {
        this.initialRequestURI = initialRequestURI;
    }

    public AuthBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
    public void doLogin(){
        if(authenticated){

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(initialRequestURI);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        authenticated = (authBean.doLogin(login,password)== AuthBean.LoginResult.SUCCESS);
    }
}
