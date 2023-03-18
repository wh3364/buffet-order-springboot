package com.fch.buffetorder.handler;

import com.fch.buffetorder.api.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class MyExceptionHandler {

    @Order(0)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean exceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();
        if (!CollectionUtils.isEmpty(errors)) {
            // 返回所有字段的错误提示
            List<String> messages = errors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
            return ResponseBean.badRequest(messages.get(0));
        }
        return ResponseBean.badRequest("参数不能为空");
    }

    @Order()
    @ExceptionHandler(Exception.class)
    public ResponseBean exceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseBean.badRequest(e.getMessage());
    }
}
