package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/14.
 */

public class GuessLoginModel {

    /**
     * requestId : 163009879195022534371120068
     * errorVo : {"code":"1000","msg":"登录成功"}
     * data : {"userId":8,"lId":"89DE8BB275934C0F1C3D4BC186F037909","umengToken":"","jpushToken":"","nickName":"张鹏","gender":1,"mobile":"13718682361","hasPayPass":1,"logo":"http://450530a0.nat123.net:80/res/b7420415e07a8b8cdbd2fe9d31c5e4b8.jpeg"}
     */

    private String requestId;
    private ErrorVoBean errorVo;
    private DataBean data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ErrorVoBean getErrorVo() {
        return errorVo;
    }

    public void setErrorVo(ErrorVoBean errorVo) {
        this.errorVo = errorVo;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class ErrorVoBean {
        /**
         * code : 1000
         * msg : 登录成功
         */

        private String code;
        private String msg;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class DataBean {
        /**
         * userId : 8
         * lId : 89DE8BB275934C0F1C3D4BC186F037909
         * umengToken :
         * jpushToken :
         * nickName : 张鹏
         * gender : 1
         * mobile : 13718682361
         * hasPayPass : 1
         * logo : http://450530a0.nat123.net:80/res/b7420415e07a8b8cdbd2fe9d31c5e4b8.jpeg
         */

        private int userId;
        private String lId;
        private String umengToken;
        private String jpushToken;
        private String nickName;
        private int gender;
        private String mobile;
        private int hasPayPass;
        private String logo;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getLId() {
            return lId;
        }

        public void setLId(String lId) {
            this.lId = lId;
        }

        public String getUmengToken() {
            return umengToken;
        }

        public void setUmengToken(String umengToken) {
            this.umengToken = umengToken;
        }

        public String getJpushToken() {
            return jpushToken;
        }

        public void setJpushToken(String jpushToken) {
            this.jpushToken = jpushToken;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getHasPayPass() {
            return hasPayPass;
        }

        public void setHasPayPass(int hasPayPass) {
            this.hasPayPass = hasPayPass;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
