package com.jr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddStudentAjaxViewController {

    @GetMapping("/addStudentAjax")
    public String showForm() {
        return "addStudentAjax";
    }
}