package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/14.
 */

public class GuessUserModel {

    /**
     * requestId : 113164023195013514095345162
     * errorVo : {"code":"1000","msg":"获取用户信息"}
     * data : {"nickName":"德玛利亚","logo":"","level":"Vip0","takePartNum":0,"buyNum":0,"createNum":0,"gender":1,"mobile":"13718682361","hasPayPass":0}
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
         * msg : 获取用户信息
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
         * nickName : 德玛利亚
         * logo :
         * level : Vip0
         * takePartNum : 0
         * buyNum : 0
         * createNum : 0
         * gender : 1
         * mobile : 13718682361
         * hasPayPass : 0
         */

        private String nickName;
        private String logo;
        private String level;
        private int dayTakePartNum;
        private int dayBuyCoin;
        private int dayCreateNum;
        private int gender;
        private String mobile;
        private int hasPayPass;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getDayTakePartNum() {
            return dayTakePartNum;
        }

        public void setDayTakePartNum(int dayTakePartNum) {
            this.dayTakePartNum = dayTakePartNum;
        }

        public int getDayBuyCoin() {
            return dayBuyCoin;
        }

        public void setDayBuyCoin(int dayBuyCoin) {
            this.dayBuyCoin = dayBuyCoin;
        }

        public int getDayCreateNum() {
            return dayCreateNum;
        }

        public void setDayCreateNum(int dayCreateNum) {
            this.dayCreateNum = dayCreateNum;
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
    }
}
