package cn.techsofts.facerecognition.exception;

import cn.techsofts.facerecognition.enums.ErrorEnum;

/**
 * @author Aiots-cao
 * @title: AjaxExceptionHandler
 * @projectName smarthome
 * @description: TODO
 * @date 2019-09-07 14:20
 */
public class CustomException extends RuntimeException{

    private Integer code;

    public CustomException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getStatus();
    }

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
