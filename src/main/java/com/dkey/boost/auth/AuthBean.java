package com.dkey.boost.auth;

import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AuthBean {
    @PersistenceContext
    private EntityManager entityManager;
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
