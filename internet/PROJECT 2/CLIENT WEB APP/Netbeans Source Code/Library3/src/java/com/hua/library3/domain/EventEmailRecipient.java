package com.hua.library3.domain;

import com.hua.library3.xml.adapter.DateAdapter;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "email", "registrationdate" }) 
public class EventEmailRecipient {
    int id;
    String name;
    String email;
    Date registrationdate;

    public EventEmailRecipient() {
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getRegistrationdate() {
        return registrationdate;
    }

    @XmlElement
    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }
    
    @Override
    public String toString() {
        if (registrationdate != null) {
            return "Event Email Recipient[id=" + id + ", name=" + name + ", email=" + email +
                ", registrationdate=" + registrationdate.toString() + "]";
        } else
            return "Event Email Recipient[id=" + id + ", name=" + name + ", email=" + email + "]";       
    }
    
}
