package com.jr.controller;

import com.jr.data.redis.model.Student;
import com.jr.response.AddStudentResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/jsoncontroller/student")
public class AddStudentJsonController {

    @RequestMapping(value="add", method = RequestMethod.POST)
    public @ResponseBody AddStudentResponse addStudentJson(
            @RequestBody Student student) {

        AddStudentResponse addStudentResponse = new AddStudentResponse();
        addStudentResponse.setName(student.getName());
        addStudentResponse.setId(student.getId());

        return addStudentResponse;
    }

}
