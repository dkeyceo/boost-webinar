package com.dkey.boost.auth;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class PersonBean implements Serializable{
    private String login;
    private String password;
    private boolean authenticated;
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

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
    public void doLogin(){
        authenticated = (authBean.doLogin(login,password)== AuthBean.LoginResult.SUCCESS);
    }
}
