package cn.techsofts.facerecognition.controller;

import cn.techsofts.facerecognition.entity.FacefeatureData;
import cn.techsofts.facerecognition.service.FaceRecognitionService;
import cn.techsofts.facerecognition.utils.JSONResult;
import cn.techsofts.facerecognition.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    private JSONResult registerFaceRecognition(@RequestBody @Valid FacefeatureData signature, BindingResult result) {
        JSONResult jsonResult = Utils.checkError(result);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        return faceRecognitionService.RegisterFaceRecognitionItem(signature);
    }

    @RequestMapping("/facerecognition/check")
    private JSONResult checkFaceRecognition(@RequestBody @Valid FacefeatureData signature, BindingResult result) {
        JSONResult jsonResult = Utils.checkError(result);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        return faceRecognitionService.checkFaceRecognition(signature);
    }
}
