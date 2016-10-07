package com.hua.library3.domain;

import com.hua.library3.xml.adapter.DateAdapter;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlType(propOrder = { "id", "type", "title", "startdate", "enddate" })
public class LibraryEvent {
    int id;
    String type;
    String title;
    Date startdate;
    Date enddate;

    public LibraryEvent() {
    }

    public int getId() {
        return id;
    }

    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getStartdate() {
        return startdate;
    }

    @XmlElement
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getEnddate() {
        return enddate;
    }

    @XmlElement
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    
    @Override
    public String toString() {
        return "Library Event [id=" + id + ", type=" + type + ", title=" + title +
                ", startdate=" + startdate.toString() + ", enddate=" + enddate.toString() + "]";
    }
    
}