package com.script;

import java.util.ArrayList;

public class Classroom {
    private int year;
    private String section;
    private String school;
    private ArrayList<Student> students;

    public Classroom(){
        students = new ArrayList<Student>();
    }

    public Classroom(int year, String section, String school){
        this.year = year;
        this.section = section;
        this.school = school;
        students = new ArrayList<Student>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school){
        this.school = school;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section){
        this.section = section;
    }

    public void addStudent(Student s){
        students.add(s);
    }

    @Override
    public String toString(){
        String string = year + "," + section + "," + school + ":";
        for(Student s : students){
            string += s.toString() + "/";
        }
        return string;
    }
}

