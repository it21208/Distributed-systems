package com.hua.library3.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "list" }) 
public class ManagerStatusList {
    List<ManagerStatus> list;

    public ManagerStatusList() {
    }

    public List<ManagerStatus> getList() {
        return list;
    }

    @XmlElementWrapper(name = "list") 
    @XmlElement(name = "managerStatus") 
    public void setList(List<ManagerStatus> list) {
        this.list = list;
    }    
}
