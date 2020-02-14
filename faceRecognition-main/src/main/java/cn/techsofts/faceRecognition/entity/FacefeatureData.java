package cn.techsofts.faceRecognition.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (FacefeatureData)实体类
 *
 * @author makejava
 * @since 2020-02-14 19:36:20
 */
public class FacefeatureData implements Serializable {
    private static final long serialVersionUID = -89690402671277574L;
    /**
    * 自增主键
    */
    private Integer id;
    /**
    * 特征数据
    */
    private byte[] data;
    /**
    * 性别  男性0 女性1 未知 -1
    */
    private Integer sex;
    /**
    * 年龄
    */
    private Integer age;
    /**
    * 注册时人脸数据base64编码
    */
    private String picBase64;
    /**
    * 姓名 
    */
    private String name;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPicBase64() {
        return picBase64;
    }

    public void setPicBase64(String picBase64) {
        this.picBase64 = picBase64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * (FacefeatureData)实体类
     *
     * @author Aiots-cao
     * @since 2020-02-14 21:38:03
     */
    public static class FacefeatureData implements Serializable {
        private static final long serialVersionUID = 933077948157133406L;
        /**
        * 自增主键
        */
        private Integer id;
        /**
        * 特征数据
        */
        private byte[] data;
        /**
        * 性别  男性0 女性1 未知 -1
        */
        private Integer sex;
        /**
        * 年龄
        */
        private Integer age;
        /**
        * 注册时人脸数据base64编码
        */
        private String picBase64;
        /**
        * 姓名
        */
        private String name;
        /**
        * 创建时间
        */
        private Date createTime;
        /**
        * 更新时间
        */
        private Date updateTime;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getPicBase64() {
            return picBase64;
        }

        public void setPicBase64(String picBase64) {
            this.picBase64 = picBase64;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

    }
}