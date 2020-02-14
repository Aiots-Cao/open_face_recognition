package cn.techsofts.faceRecognition.service.impl;

import cn.techsofts.faceRecognition.dao.FacefeatureDataDao;
import cn.techsofts.faceRecognition.entity.FacefeatureData;
import cn.techsofts.faceRecognition.enums.ErrorEnum;
import cn.techsofts.faceRecognition.exception.CustomException;
import cn.techsofts.faceRecognition.service.FaceRecognitionService;
import cn.techsofts.faceRecognition.utils.JSONResult;
import cn.techsofts.faceRecognition.utils.Utils;
import com.arcsoft.face.*;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;
import static org.junit.Assert.assertEquals;

/**
 * @author Aiots-cao
 * @title: FaceRecognitionServiceImpl
 * @projectName facerecognition
 * @description: TODO
 * @date 2019/11/15 16:44
 */
@Service
public class FaceRecognitionServiceImpl implements FaceRecognitionService {
    @Autowired
    private FacefeatureDataDao facefeatureDataDao;
    private FaceEngine faceEngine;

    private String baseTmpPicPath = null;


    @Override
    public JSONResult RegisterFaceRecognitionItem(FacefeatureData signature) {
        if (Utils.isLinux()) {
            baseTmpPicPath = "/ArcSoftTmp/";
        } else {
            baseTmpPicPath = "C:\\Users\\mac\\Desktop\\iv\\";
        }
        String randomRegisterFileName = UUID.randomUUID().toString().replace("-", "") + "-register.jpg";
        boolean result = Utils.Base64ToImage(signature.getPicBase64(), baseTmpPicPath + randomRegisterFileName);
        if (result) {
            if (Utils.initFaceEngine() == null) {
                return JSONResult.errorMsg("引擎初始化失败");
            }
            faceEngine = Utils.initFaceEngine();

            //人脸检测
            ImageInfo imageInfo = getRGBData(new File(baseTmpPicPath + randomRegisterFileName));
            List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
            int detectCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
            System.out.println(faceInfoList);
            System.out.println("detectCode:" + detectCode);
            if (faceInfoList.size() == 0) {
                return JSONResult.errorMsg("未检测到人脸");
            }
            //特征提取
            FaceFeature faceFeature = new FaceFeature();
            int extractCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);
            if (faceFeature.getFeatureData().length == 0) {
                return JSONResult.errorMsg("特征值提取失败");
            }
            System.out.println("特征值大小：" + faceFeature.getFeatureData().length);


            //人脸属性检测
            FunctionConfiguration configuration = new FunctionConfiguration();
            configuration.setSupportAge(true);
            configuration.setSupportFace3dAngle(true);
            configuration.setSupportGender(true);
            configuration.setSupportLiveness(true);
            int processCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList, configuration);


            //性别检测
            List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
            int genderCode = faceEngine.getGender(genderInfoList);
            assertEquals("性别检测失败", genderCode, ErrorInfo.MOK.getValue());
            System.out.println("性别：" + genderInfoList.get(0).getGender());

            //年龄检测
            List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
            int ageCode = faceEngine.getAge(ageInfoList);
            assertEquals("年龄检测失败", ageCode, ErrorInfo.MOK.getValue());
            System.out.println("年龄：" + ageInfoList.get(0).getAge());


            //确保数据库性能  暂不存储 注册时的base64信息
            signature.setPicBase64(null);
            signature.setData(faceFeature.getFeatureData());
            signature.setAge(ageInfoList.get(0).getAge());
            signature.setSex(genderInfoList.get(0).getGender());
            facefeatureDataDao.insert(signature);

            System.out.println("姓名：" + signature.getName());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Utils.fileRename(baseTmpPicPath + randomRegisterFileName, baseTmpPicPath + dateFormat.format(new Date()) + "-" + signature.getName() + ".jpg");
            return JSONResult.ok();
        } else {
            return JSONResult.errorMsg("IO系统异常");
        }
    }

    @Override
    public JSONResult checkFaceRecognition(FacefeatureData signature) {

        System.out.println("开始计时：");
        if (Utils.isLinux()) {
            baseTmpPicPath = "/ArcSoftTmp/";
        } else {
            baseTmpPicPath = "C:\\Users\\mac\\Desktop\\iv\\";
        }
        long startTime = System.currentTimeMillis();


        String randomCheckPicPath = baseTmpPicPath + UUID.randomUUID().toString().replace("-", "") + "-check.jpg";
        boolean result = Utils.Base64ToImage(signature.getPicBase64(), randomCheckPicPath);
        System.out.println("图片转换完成,转换结果:" + result + " 此时总用时：" + (System.currentTimeMillis() - startTime) + "ms");
        if (result) {
            List<FacefeatureData> faceRecognitionSignatureList = new ArrayList<>();
            faceRecognitionSignatureList = facefeatureDataDao.queryAll(new FacefeatureData());
            System.out.println("数据库人脸库查询完成 此时总用时：" + (System.currentTimeMillis() - startTime) + "ms");

            if (Utils.initFaceEngine() == null) {
                return JSONResult.errorMsg("引擎初始化失败");
            }
            faceEngine = Utils.initFaceEngine();

            System.out.println("人脸监测引擎激活成功 此时总用时：" + (System.currentTimeMillis() - startTime) + "ms");

            try {
                //人脸检测
                ImageInfo imageInfo = getRGBData(new File(randomCheckPicPath));
                List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
                int detectCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
                System.out.println(faceInfoList);
                System.out.println("detectCode:" + detectCode);
                if (faceInfoList.size() == 0) {
                    return JSONResult.errorMsg("未检测到人脸");
                }

                //特征提取
                FaceFeature faceFeature = new FaceFeature();
                int extractCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);
                if (faceFeature.getFeatureData().length == 0) {
                    Utils.delFile(new File(randomCheckPicPath));
                    throw new CustomException(ErrorEnum.FEATURE_GET_FAIL);
                }
                System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

                System.out.println("照片特征提取完后 此时总用时：" + (System.currentTimeMillis() - startTime) + "ms");

                //加入一段人脸信息监测

                //人脸属性检测
                FunctionConfiguration configuration = new FunctionConfiguration();
                configuration.setSupportLiveness(true);
                int processCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList, configuration);

                //活体检测
                List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
                int livenessCode = faceEngine.getLiveness(livenessInfoList);
                System.out.println("活体：" + livenessInfoList.get(0).getLiveness());
                if (livenessInfoList.get(0).getLiveness() != 1) {
                    Utils.delFile(new File(randomCheckPicPath));
                    return JSONResult.errorMsg("活体监测失败");
                }

                for (FacefeatureData faceRecognitionSignature : faceRecognitionSignatureList) {
                    //特征比对
                    FaceFeature targetFaceFeature = new FaceFeature();
                    targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
                    FaceFeature sourceFaceFeature = new FaceFeature();
                    sourceFaceFeature.setFeatureData(faceRecognitionSignature.getData());
                    FaceSimilar faceSimilar = new FaceSimilar();
                    int compareCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                    System.out.println("相似度:" + faceSimilar.getScore());
                    if (faceSimilar.getScore() >= 0.8) {
                        System.out.println("特征对比完成 此时总用时：" + (System.currentTimeMillis() - startTime) + "ms");
                        //把Base64数据清除 不再返回给前端 防止造成太大压力
                        faceRecognitionSignature.setPicBase64(null);
                        faceRecognitionSignature.setData(null);
                        Utils.delFile(new File(randomCheckPicPath));
                        return JSONResult.ok(faceRecognitionSignature);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.delFile(new File(randomCheckPicPath));
            }
        } else {
            Utils.delFile(new File(randomCheckPicPath));
            return JSONResult.errorMsg("IO系统异常");
        }
        Utils.delFile(new File(randomCheckPicPath));
        return JSONResult.errorMsg("无匹配人脸数据");
    }
}
