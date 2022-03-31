package com.jr.controller;

import com.jr.Student;
import com.jr.response.StudentSearchAjaxResponse;
import com.jr.spring.ajax.StudentSearch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController
public class StudentSearchAjaxController {

    private static final Log logger = LogFactory.getLog(StudentSearchAjaxController.class);

    @RequestMapping(value = "/ajaxstudent/api/search")
    public StudentSearchAjaxResponse getSearchResultViaAjax(@RequestBody StudentSearch studentSearch) {

        logger.info("Performing getSearchResultViaAjax...");

        //TODO: use student search object to driv results.
        StudentSearchAjaxResponse studentSearchAjaxResponse = new StudentSearchAjaxResponse();
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student();
        student1.setName("student1");
        student1.setId(1);
        studentList.add(student1);
        Student student2 = new Student();
        student2.setName("student2");
        student2.setId(2);
        studentList.add(student2);
        studentSearchAjaxResponse.setStudentList(studentList);

        return studentSearchAjaxResponse;
    }
}
