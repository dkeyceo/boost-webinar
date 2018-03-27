package com.dkey.boost.auth;

import javax.persistence.*;
import java.util.List;

@Entity
public class Resource {
    @Id
    private String id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "resource")
    private List<Right> rights;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
