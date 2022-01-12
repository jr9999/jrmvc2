package com.jr.response;

import com.jr.Student;

import java.util.List;

public class StudentSearchAjaxResponse {

    private List<Student> studentList;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
