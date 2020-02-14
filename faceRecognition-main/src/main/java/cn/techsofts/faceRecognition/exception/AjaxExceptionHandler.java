package cn.techsofts.faceRecognition.exception;

import cn.techsofts.faceRecognition.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Aiots-cao
 * @title: AjaxExceptionHandler
 * @projectName smarthome
 * @description: TODO
 * @date 2019-09-07 14:20
 */
@RestControllerAdvice
public class AjaxExceptionHandler {
    @ExceptionHandler(value=Exception.class)
    public JSONResult defaultErrorHander(Exception e){
        e.printStackTrace();
        return JSONResult.errorException(e.getMessage());
    }
}
