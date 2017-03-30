package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/3/15.
 */

public class GuessHandicapDetailsModel {

    /**
     * requestId : 171134145195237732428331534
     * errorVo : {"code":"1000","msg":"获取我参与的开奖盘口详情"}
     * data : {"result":2,"left":"测试1","right":"测试2","leftPersonNum":0,"rightPersonNum":0,"leftCoinNum":700,"rightCoinNum":300,"buyNum":100,"reward":-100,"commission":-100}
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
         * msg : 获取我参与的开奖盘口详情
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
         * result : 2
         * left : 测试1
         * right : 测试2
         * leftPersonNum : 0
         * rightPersonNum : 0
         * leftCoinNum : 700
         * rightCoinNum : 300
         * buyNum : 100
         * reward : -100
         * commission : -100
         */

        private int result;
        private String left;
        private String right;
        private int leftPersonNum;
        private int rightPersonNum;
        private int leftCoinNum;
        private int rightCoinNum;
        private int buyNum;
        private double reward;
        private double commission;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public int getLeftPersonNum() {
            return leftPersonNum;
        }

        public void setLeftPersonNum(int leftPersonNum) {
            this.leftPersonNum = leftPersonNum;
        }

        public int getRightPersonNum() {
            return rightPersonNum;
        }

        public void setRightPersonNum(int rightPersonNum) {
            this.rightPersonNum = rightPersonNum;
        }

        public int getLeftCoinNum() {
            return leftCoinNum;
        }

        public void setLeftCoinNum(int leftCoinNum) {
            this.leftCoinNum = leftCoinNum;
        }

        public int getRightCoinNum() {
            return rightCoinNum;
        }

        public void setRightCoinNum(int rightCoinNum) {
            this.rightCoinNum = rightCoinNum;
        }

        public int getBuyNum() {
            return buyNum;
        }

        public void setBuyNum(int buyNum) {
            this.buyNum = buyNum;
        }

        public double getReward() {
            return reward;
        }

        public void setReward(double reward) {
            this.reward = reward;
        }

        public double getCommission() {
            return commission;
        }

        public void setCommission(double commission) {
            this.commission = commission;
        }
    }
}
