package ru.rdb.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DbId implements Serializable {

    @Column(name = "grp")
    private String group;

    @Column(name = "id")
    private String id;

    public DbId() {
    }

    public DbId(String group, String id) {
        this.group = group;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
