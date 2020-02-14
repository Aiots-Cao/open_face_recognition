package cn.techsofts.facerecognition.service;

import cn.techsofts.facerecognition.entity.FacefeatureData;
import cn.techsofts.facerecognition.utils.JSONResult;

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
