package com.example.nutrident4mom.model;

public class Model
{
    String course,email,name,purl,nameLower;

    public Model() {
    }

    public Model(String course, String email, String name,String nameLower, String purl) {
        this.course = course;
        this.email = email;
        this.name = name;
        this.nameLower = nameLower;

        this.purl = purl;
    }

    public Model(String imageUrl){
        this.purl = imageUrl;
    }

    public String getNameLower() {
        return nameLower;
    }

    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}

