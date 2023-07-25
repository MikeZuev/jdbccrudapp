package ru.mike.crudappjdbc.models;

import java.util.Date;

public class Project {

    private Long id;
    private String name;
    private Date founded;

    public Project() {

    }

    public Project(String name, Date founded) {
        this.name = name;
        this.founded = founded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFounded() {
        return founded;
    }

    public void setFounded(Date founded) {
        this.founded = founded;
    }
}
