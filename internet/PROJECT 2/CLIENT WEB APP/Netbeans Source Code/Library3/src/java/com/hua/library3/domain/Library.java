package com.hua.library3.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "name", "address", "telephone", "email", "openhours", "libraryEventsList"})  
public class Library {
    String name;
    String address;
    String telephone;
    String email;
    String openhours;    
    List<LibraryEvent> libraryEventsList;

    public Library() {
    }

    public String getName() {
        return name;
    }

    @XmlElement 
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    @XmlElement
    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    @XmlElement
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenhours() {
        return openhours;
    }

    @XmlElement
    public void setOpenhours(String openhours) {
        this.openhours = openhours;
    }

    public List<LibraryEvent> getLibraryEventsList() {
        return libraryEventsList;
    }

    @XmlElementWrapper(name = "libraryEventsList") 
    @XmlElement(name = "libraryEvent") 
    public void setLibraryEventsList(List<LibraryEvent> libraryEventsList) {
        this.libraryEventsList = libraryEventsList;
    }
    
    @Override
    public String toString() {
        return "Library [name=" + name + ", address=" + address + "]";
    }    
}
