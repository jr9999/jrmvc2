package com.jr.controller;

import com.jr.Student;
import com.jr.response.AddStudentAjaxResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddStudentAjaxController {

    //List<Student> users;

    // @ResponseBody, not necessary, since class is annotated with @RestController
    // @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
    // @JsonView(Views.Public.class) - Optional, filters json data to display.
    //@JsonView(Views.Public.class)
    @RequestMapping(value = "/ajaxstudent/api/add")
    public AddStudentAjaxResponse addStudentViaAjax(@RequestBody Student student) {

        AddStudentAjaxResponse result = new AddStudentAjaxResponse();
        result.setName(student.getName());
        result.setId(student.getId());

        /*
        if (isValidSearchCriteria(search)) {
            List<User> users = findByUserNameOrEmail(search.getUsername(), search.getEmail());

            if (users.size() > 0) {
                result.setCode("200");
                result.setMsg("");
                result.setResult(users);
            } else {
                result.setCode("204");
                result.setMsg("No user!");
            }

        } else {
            result.setCode("400");
            result.setMsg("Search criteria is empty!");
        }
         */

        //AddStudentAjaxResponseBody will be converted into json format and send back to the request.
        return result;
    }

}
