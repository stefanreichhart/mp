package com.mp.api.config;

import com.mp.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MeasurementPointsControllerAdvice {

    // TODO fix this
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ModelAndView badRequestException(HttpServletRequest req, BadRequestException exception) {
        ModelAndView mv = new ModelAndView();
        return mv;
    }

}
