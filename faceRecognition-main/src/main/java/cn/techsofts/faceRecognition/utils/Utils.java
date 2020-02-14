package cn.techsofts.faceRecognition.utils;

import cn.techsofts.faceRecognition.enums.ErrorEnum;
import cn.techsofts.faceRecognition.exception.CustomException;
import com.alibaba.druid.util.StringUtils;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

/**
 * @author Aiots-cao
 * @title: Utils
 * @projectName faceRecognition
 * @description: TODO
 * @date 2019/11/15 17:25
 */
public class Utils {
    /**
     * <pre>
     * @return 是否Linux操作系统
     * </pre>
     */
    public static boolean isLinux() {
        return !System.getProperty("os.name").toLowerCase()
                .startsWith("windows");
    }


    /**
     * 　　* @description: 将Base64编码转换成 图片文件
     * 　　* @param [imgStr, imgFilePath]
     * 　　* @return boolean
     * 　　* @throws
     * 　　* @author Aiots-cao
     * 　　* @date 2019/11/16 17:42
     */
    public static boolean Base64ToImage(String imgStr, String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
        boolean result = false;
        if (StringUtils.isEmpty(imgStr)) { // 图像数据为空
            return result;
        }
        Decoder decoder = Base64.getDecoder();
        try {
            // Base64解码
            byte[] b = decoder.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            result = true;
            return result;
        } catch (Exception e) {
            throw new CustomException(ErrorEnum.BASE64_DEAL_FAIL);
        }

        //Base64编码
        //Encoder encoder = Base64.getEncoder();
        //String result = encoder.encodeToString(byteArray);

    }

    private static boolean FaceEngineisActived = false;
    private static FaceEngine faceEngine = null;

    /**
     * 　　* @description: 初始化虹软人脸识别引擎 如果已经初始化 则返回初始化后的实例
     * 　　* @param []
     * 　　* @return com.arcsoft.face.FaceEngine
     * 　　* @throws
     * 　　* @author Aiots-cao
     * 　　* @date 2019/11/16 17:43
     */
    public static FaceEngine initFaceEngine() {
        String appId = "9rjmcC2haitEs6wRUbMDLpkkQwKRE4PfWLy4hueVvCW3";
        String sdkKey = "";
        if (Utils.isLinux()) {
            sdkKey = "GGxCx87JCZBQ3p894j86AksppfbCPmBous1WEiwgjwAQ";
        } else {
            sdkKey = "GGxCx87JCZBQ3p894j86AkspxnAPPEvAEfySu3amQxbj";
        }
        if (!FaceEngineisActived) {
            if (Utils.isLinux()) {
                faceEngine = new FaceEngine("/ArcSoftSo");
            } else {
                faceEngine = new FaceEngine("C:\\IdeProjects\\faceRecognition\\faceRecognition-main\\dll");
            }
            //激活引擎
            int activeCode = faceEngine.activeOnline(appId, sdkKey);

            if (activeCode != ErrorInfo.MOK.getValue() && activeCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                System.out.println("引擎激活失败");
                return null;
            }

            //引擎配置
            EngineConfiguration engineConfiguration = new EngineConfiguration();
            engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
            engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);

            //功能配置
            FunctionConfiguration functionConfiguration = new FunctionConfiguration();
            functionConfiguration.setSupportAge(true);
            functionConfiguration.setSupportFace3dAngle(true);
            functionConfiguration.setSupportFaceDetect(true);
            functionConfiguration.setSupportFaceRecognition(true);
            functionConfiguration.setSupportGender(true);
            functionConfiguration.setSupportLiveness(true);
            functionConfiguration.setSupportIRLiveness(true);
            engineConfiguration.setFunctionConfiguration(functionConfiguration);


            //初始化引擎
            int initCode = faceEngine.init(engineConfiguration);

            if (initCode != ErrorInfo.MOK.getValue()) {
                System.out.println("初始化引擎失败");
                return null;
            }
            FaceEngineisActived = true;
        }
        return faceEngine;
    }

    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    public static boolean fileRename(String oldname, String newname) {
        /*旧文件名*/
        File file1 = new File(oldname);
        /*新文件名*/
        File file2 = new File(newname);
        /*重命名*/
        return file1.renameTo(file2);

    }

    public static void main(String[] args) {
        fileRename("C:\\Users\\mac\\Desktop\\iv\\2f1bef4f06994d51a73ae40bd529dbd8-register.jpg"
        ,"C:\\Users\\mac\\Desktop\\iv\\"+new Date().toString()+"曹宝红.jpg");
        System.out.println(new Date().toString());
    }
}
