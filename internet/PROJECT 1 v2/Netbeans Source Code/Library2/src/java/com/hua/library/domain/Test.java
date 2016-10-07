package com.hua.library.domain;

public class Test {
    int id;
    byte[] pdf;

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
    
    @Override
    public String toString() {
        return "Test []";    
    }
}
