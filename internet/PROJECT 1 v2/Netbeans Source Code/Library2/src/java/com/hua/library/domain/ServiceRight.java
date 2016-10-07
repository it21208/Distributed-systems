package com.hua.library.domain;

public class ServiceRight {
    private int id;
    private String name;

    public ServiceRight() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    @Override
    public String toString() {
        return "Service [id=" + id + ", name=" + name + "]";
    }
}
