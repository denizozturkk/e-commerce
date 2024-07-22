package com.example.demo.security.config;

import com.example.demo.DTOs.ErrorDto;
import com.example.demo.users.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppException(AppException e)
    {
        return ResponseEntity.status(e.getCode())
                .body(ErrorDto.builder().message(e.getMessage()).build());
    }
}
