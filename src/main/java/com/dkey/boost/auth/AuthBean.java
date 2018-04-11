package com.dkey.boost.auth;

import org.apache.commons.lang3.StringUtils;
import sun.rmi.runtime.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AuthBean {
    public enum LoginResult{
        INCORRECT_LOGIN,
        INCORRECT_PASSWORD,
        SUCCESS
    }
    @PersistenceContext
    private EntityManager entityManager;

    public LoginResult doLogin(String login, String password){
        if(StringUtils.isEmpty(login)){
            return LoginResult.INCORRECT_LOGIN;
        }
        if(StringUtils.isEmpty(password)){
            return LoginResult.INCORRECT_PASSWORD;
        }
        Person person = entityManager.find(Person.class,login);
        if(person == null){
            return LoginResult.INCORRECT_LOGIN;
        }
        if(!password.equals(person.getPassword())){
            return LoginResult.INCORRECT_PASSWORD;
        }
        return LoginResult.SUCCESS;
    }
    public boolean isGranted(String login, String resource){
        if(StringUtils.isEmpty(login)||StringUtils.isEmpty(resource)){
            return false;
        }
        Resource resourceEntity = entityManager.find(Resource.class, resource);
        if(resourceEntity == null){
            return false;
        }
        Person person = entityManager.find(Person.class, login);
        if(person == null){
            return false;
        }
        List<PersonRole> personRoles = person.getPersonRoles();
        if(personRoles == null || personRoles.isEmpty()){
            return false;
        }
        for(PersonRole personRole:personRoles){
            Role role = personRole.getRole();
            if(role == null){
                continue;
            }
            List<Right> rights = role.getRights();
            for(Right right:rights){
                if(right == null){
                    continue;
                }
                Resource resourceRight = right.getResource();
                if(resourceRight.getId().equalsIgnoreCase(resource)){
                    return true;
                }
            }
        }
        return false;
    }
}
