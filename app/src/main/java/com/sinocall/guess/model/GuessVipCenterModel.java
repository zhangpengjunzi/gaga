package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class GuessVipCenterModel {

    /**
     * requestId : 107775540195183974974374929
     * errorVo : {"code":"1000","msg":"获取用户权益"}
     * data : {"lastMonthPoint":0,"currentMonthPoint":0,"myLevelId":1,"discount":"1.0折","dailyBuyNum":"5次","repayRate":"30.0%","dailyPublishNum":"0个","dailyTakePartNum":"3个","topicLimit":"[1000,2000]","levelList":[{"id":1,"num":0},{"id":2,"num":199},{"id":3,"num":599},{"id":4,"num":1999},{"id":5,"num":2999}]}
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
         * msg : 获取用户权益
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
         * lastMonthPoint : 0
         * currentMonthPoint : 0
         * myLevelId : 1
         * discount : 1.0折
         * dailyBuyNum : 5次
         * repayRate : 30.0%
         * dailyPublishNum : 0个
         * dailyTakePartNum : 3个
         * topicLimit : [1000,2000]
         * levelList : [{"id":1,"num":0},{"id":2,"num":199},{"id":3,"num":599},{"id":4,"num":1999},{"id":5,"num":2999}]
         */

        private int lastMonthPoint;
        private int currentMonthPoint;
        private int myLevelId;
        private String discount;
        private String dailyBuyNum;
        private String repayRate;
        private String dailyPublishNum;
        private String dailyTakePartNum;
        private String topicLimit;
        private List<LevelListBean> levelList;

        public int getLastMonthPoint() {
            return lastMonthPoint;
        }

        public void setLastMonthPoint(int lastMonthPoint) {
            this.lastMonthPoint = lastMonthPoint;
        }

        public int getCurrentMonthPoint() {
            return currentMonthPoint;
        }

        public void setCurrentMonthPoint(int currentMonthPoint) {
            this.currentMonthPoint = currentMonthPoint;
        }

        public int getMyLevelId() {
            return myLevelId;
        }

        public void setMyLevelId(int myLevelId) {
            this.myLevelId = myLevelId;
        }

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

        public String getTopicLimit() {
            return topicLimit;
        }

        public void setTopicLimit(String topicLimit) {
            this.topicLimit = topicLimit;
        }

        public List<LevelListBean> getLevelList() {
            return levelList;
        }

        public void setLevelList(List<LevelListBean> levelList) {
            this.levelList = levelList;
        }

        public static class LevelListBean {
            /**
             * id : 1
             * num : 0
             */

            private int id;
            private int num;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }
        }
    }
}
