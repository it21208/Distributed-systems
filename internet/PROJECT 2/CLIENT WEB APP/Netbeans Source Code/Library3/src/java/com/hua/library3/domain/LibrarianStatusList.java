package com.hua.library3.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "list" })  
public class LibrarianStatusList {
    List<LibrarianStatus> list;

    public LibrarianStatusList() {
    }

    public List<LibrarianStatus> getList() {
        return list;
    }

    @XmlElementWrapper(name = "list") 
    @XmlElement(name = "librarianStatus") 
    public void setList(List<LibrarianStatus> list) {
        this.list = list;
    }    
}
