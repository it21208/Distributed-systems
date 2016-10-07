/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hua.library3.xml.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author tioa
 */
public class DateAdapter extends XmlAdapter<String, java.util.Date> {
    public java.util.Date unmarshal(String v) throws Exception {
        if ((v == null) || (v.equals("")))
            return null;
        else {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(v);
        }
    }

    public String marshal(java.util.Date v) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(v);
    }
}
