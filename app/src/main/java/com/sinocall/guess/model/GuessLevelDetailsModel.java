package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/27.
 */

public class GuessLevelDetailsModel {

    /**
     * requestId : 157854543195059566569893196
     * errorVo : {"code":"1000","msg":"等级详情"}
     * data : {"discount":0.95,"dailyBuyNum":1600,"repayRate":0.005,"dailyPublishNum":3,"dailyTakePartNum":60,"topicLimit":0}
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
         * msg : 等级详情
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
         * discount : 0.95
         * dailyBuyNum : 1600
         * repayRate : 0.005
         * dailyPublishNum : 3
         * dailyTakePartNum : 60
         * topicLimit : 0
         */

        private String discount;
        private String dailyBuyNum;
        private String repayRate;
        private String dailyPublishNum;
        private String dailyTakePartNum;
        private int topicLimit;

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDailyBuyNum() {
            return dailyBuyNum;
        }

        public void setDailyBuyNum(String dailyBuyNum) {
            this.dailyBuyNum = dailyBuyNum;
        }

        public String getRepayRate() {
            return repayRate;
        }

        public void setRepayRate(String repayRate) {
            this.repayRate = repayRate;
        }

        public String getDailyPublishNum() {
            return dailyPublishNum;
        }

        public void setDailyPublishNum(String dailyPublishNum) {
            this.dailyPublishNum = dailyPublishNum;
        }

        public String getDailyTakePartNum() {
            return dailyTakePartNum;
        }

        public void setDailyTakePartNum(String dailyTakePartNum) {
            this.dailyTakePartNum = dailyTakePartNum;
        }

        public int getTopicLimit() {
            return topicLimit;
        }

        public void setTopicLimit(int topicLimit) {
            this.topicLimit = topicLimit;
        }
    }
}
