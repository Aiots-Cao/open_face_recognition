package cn.techsofts.faceRecognition.controller;

import cn.techsofts.faceRecognition.entity.FacefeatureData;
import cn.techsofts.faceRecognition.service.FaceRecognitionService;
import cn.techsofts.faceRecognition.utils.JSONResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Aiots-cao
 * @title: FaceRecognitionController
 * @projectName facerecognition
 * @description: TODO
 * @date 2019/11/15 16:46
 */
@RestController
public class FaceRecognitionController {
    @Autowired
    private FaceRecognitionService faceRecognitionService;

    @RequestMapping("/facerecognition/register")
    @ApiOperation(value = "人脸注册", notes = "人脸注册的接口")
    private JSONResult registerFaceRecognition(@RequestBody @Valid FacefeatureData signature, BindingResult result) {
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error:list){
                System.out.println(error.getCode()+"-"+error.getDefaultMessage());
            }
            return JSONResult.errorMsg(list.get(0).getDefaultMessage());
        }
        return faceRecognitionService.RegisterFaceRecognitionItem(signature);
    }

    @RequestMapping("/facerecognition/check")
    @ApiOperation(value = "人脸校验", notes = "人脸校验的接口")
    private JSONResult checkFaceRecognition(@RequestBody @Valid FacefeatureData signature, BindingResult result) {
        if (result.hasErrors()){
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error:list){
                System.out.println(error.getCode()+"-"+error.getDefaultMessage());
            }
            return JSONResult.errorMsg(list.get(0).getDefaultMessage());
        }
        return faceRecognitionService.checkFaceRecognition(signature);
    }
}
