package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/3/11.
 */

public class GuessAwardReceiveModel {

    /**
     * type : 3
     * data : {"topicId":78,"left":"快来","leftCoinNum":"700","leftPersonNum":"0","right":"条件","rightCoinNum":"300","rightPersonNum":"0","name":"U2017031414464080050","result":1,"commission":0,"userId":8,"buyNum":1000,"reward":-5}
     */

    private int type;
    private DataBean data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * topicId : 78
         * left : 快来
         * leftCoinNum : 700
         * leftPersonNum : 0
         * right : 条件
         * rightCoinNum : 300
         * rightPersonNum : 0
         * name : U2017031414464080050
         * result : 1
         * commission : 0
         * userId : 8
         * buyNum : 1000
         * reward : -5
         */

        private int topicId;
        private String left;
        private String leftCoinNum;
        private String leftPersonNum;
        private String right;
        private String rightCoinNum;
        private String rightPersonNum;
        private String name;
        private int result;
        private double commission;
        private int userId;
        private int buyNum;
        private double reward;

        public int getTopicId() {
            return topicId;
        }

        public void setTopicId(int topicId) {
            this.topicId = topicId;
        }

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getLeftCoinNum() {
            return leftCoinNum;
        }

        public void setLeftCoinNum(String leftCoinNum) {
            this.leftCoinNum = leftCoinNum;
        }

        public String getLeftPersonNum() {
            return leftPersonNum;
        }

        public void setLeftPersonNum(String leftPersonNum) {
            this.leftPersonNum = leftPersonNum;
        }

        public String getRight() {
            return right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getRightCoinNum() {
            return rightCoinNum;
        }

        public void setRightCoinNum(String rightCoinNum) {
            this.rightCoinNum = rightCoinNum;
        }

        public String getRightPersonNum() {
            return rightPersonNum;
        }

        public void setRightPersonNum(String rightPersonNum) {
            this.rightPersonNum = rightPersonNum;
        }

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

        public double getCommission() {
            return commission;
        }

        public void setCommission(double commission) {
            this.commission = commission;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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
    }
}
