package cn.techsofts.faceRecognition.enums;

/**
 * @author Aiots-cao
 * @title: ErrorEnum
 * @projectName faceRecognition
 * @description: TODO
 * @date 2020/2/14 17:27
 */
public enum ErrorEnum {
    BASE64_DEAL_FAIL("base64转换失败", 501),
    FEATURE_GET_FAIL("特征值提取失败", 502);;
    private String msg;
    private Integer status;

    ErrorEnum(String msg, Integer status) {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }
}
