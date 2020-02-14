package cn.techsofts.faceRecognition.service;

import cn.techsofts.faceRecognition.entity.FacefeatureData;
import cn.techsofts.faceRecognition.utils.JSONResult;

/**
 * @author Aiots-cao
 * @title: FaceRecognition
 * @projectName facerecognition
 * @description: TODO
 * @date 2019/11/15 16:43
 */
public interface FaceRecognitionService {
    JSONResult RegisterFaceRecognitionItem(FacefeatureData signature);
    JSONResult checkFaceRecognition(FacefeatureData signature);
}
