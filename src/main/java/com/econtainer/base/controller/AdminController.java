package com.econtainer.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DubininAY
 */
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @GetMapping
    @ResponseBody
    public String adminPage() {
        return "this is admin only page";
    }
}
