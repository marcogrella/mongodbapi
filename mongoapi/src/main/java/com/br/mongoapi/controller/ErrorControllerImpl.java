package com.br.mongoapi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;

public interface ErrorControllerImpl extends ErrorController {
    String getErrorPath();
}
