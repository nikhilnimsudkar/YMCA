package com.ymca.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class PortalErrorHandler /*implements ErrorController*/ {

    private static final Logger log = LoggerFactory.getLogger(PortalErrorHandler.class);

    //@RequestMapping(value = "/error")
    public String error(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return "portal_error";
    }


    //@Override
    public String getErrorPath() {
        return "/error";
    }
}