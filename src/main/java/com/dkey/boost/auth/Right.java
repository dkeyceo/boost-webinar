package com.dkey.boost.auth;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resource_rights")
public class Right {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Resource resource;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
