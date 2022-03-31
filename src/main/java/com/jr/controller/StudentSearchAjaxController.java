package com.jr.controller;

import com.jr.data.redis.model.Student;
import com.jr.response.StudentSearchAjaxResponse;
import com.jr.spring.ajax.StudentSearch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentSearchAjaxController {

    @RequestMapping(value = "/ajaxstudent/api/search")
    public StudentSearchAjaxResponse getSearchResultViaAjax(@RequestBody StudentSearch studentSearch) {

        //TODO: use student search object to driv results.
        StudentSearchAjaxResponse studentSearchAjaxResponse = new StudentSearchAjaxResponse();
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student(new String("id1"),
                new String("name1"),
                Student.Gender.MALE, 3);
        Student student2 = new Student(new String("id2"),
                new String("name2"),
                Student.Gender.FEMALE, 4);
        studentList.add(student1);
        studentList.add(student2);
        studentSearchAjaxResponse.setStudentList(studentList);

        return studentSearchAjaxResponse;
    }
}
