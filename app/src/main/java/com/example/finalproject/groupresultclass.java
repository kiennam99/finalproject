package com.example.finalproject;

public class groupresultclass {
    String School;
    String course;
    String NStudent;
    String marks;

    public  groupresultclass(){

    }
    public groupresultclass(String school, String course, String NStudent, String marks, String website, String weight) {
        School = school;
        this.course = course;
        this.NStudent = NStudent;
        this.marks = marks;
        this.website = website;
        this.weight = weight;
    }

    String website;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    String weight;

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getNStudent() {
        return NStudent;
    }

    public void setNStudent(String nstudent) {
        NStudent = nstudent;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
