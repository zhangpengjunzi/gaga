package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class GuessJoinModel {

    /**
     * requestId : 198038180194977129700826604
     * errorVo : {"code":"1000","msg":"我参与的"}
     * data : {"dayWinNum":5,"dayTotalNum":10,"winNum":10,"totalNum":50,"historyTopic":[{"id":1,"name":"CCK001","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","status":0,"progress":"1000/2000","buyNum":"左100","takePartNum":"100","reward":-100,"buyTime":"2017-02-17 15:38:12","shareUrl":"baidu.com"},{"id":2,"name":"CCK002","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","status":1,"progress":"1000/2000","buyNum":"右100","takePartNum":"100","reward":100,"buyTime":"2017-02-17 15:42:12","shareUrl":"youku.com"}]}
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
         * msg : 我参与的
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
         * dayWinNum : 5
         * dayTotalNum : 10
         * winNum : 10
         * totalNum : 50
         * historyTopic : [{"id":1,"name":"CCK001","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","status":0,"progress":"1000/2000","buyNum":"左100","takePartNum":"100","reward":-100,"buyTime":"2017-02-17 15:38:12","shareUrl":"baidu.com"},{"id":2,"name":"CCK002","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","status":1,"progress":"1000/2000","buyNum":"右100","takePartNum":"100","reward":100,"buyTime":"2017-02-17 15:42:12","shareUrl":"youku.com"}]
         */

        private int dayWinNum;
        private int dayTotalNum;
        private int winNum;
        private int totalNum;
        private List<HistoryTopicBean> historyTopic;

        public int getDayWinNum() {
            return dayWinNum;
        }

        public void setDayWinNum(int dayWinNum) {
            this.dayWinNum = dayWinNum;
        }

        public int getDayTotalNum() {
            return dayTotalNum;
        }

        public void setDayTotalNum(int dayTotalNum) {
            this.dayTotalNum = dayTotalNum;
        }

        public int getWinNum() {
            return winNum;
        }

        public void setWinNum(int winNum) {
            this.winNum = winNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<HistoryTopicBean> getHistoryTopic() {
            return historyTopic;
        }

        public void setHistoryTopic(List<HistoryTopicBean> historyTopic) {
            this.historyTopic = historyTopic;
        }

        public static class HistoryTopicBean {
            /**
             * id : 1
             * name : CCK001
             * picUrl : http://pro.iluyin.cn/sinomini/images/banner/init_1.png
             * status : 0
             * progress : 1000/2000
             * buyNum : 左100
             * takePartNum : 100
             * reward : -100
             * buyTime : 2017-02-17 15:38:12
             * shareUrl : baidu.com
             */

            private int id;
            private String name;
            private String picUrl;
            private int status;
            private String progress;
            private String buyNum;
            private String takePartNum;
            private double reward;
            private String buyTime;
            private String shareUrl;
            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getTakePartNum() {
                return takePartNum;
            }

            public void setTakePartNum(String takePartNum) {
                this.takePartNum = takePartNum;
            }

            public double getReward() {
                return reward;
            }

            public void setReward(double reward) {
                this.reward = reward;
            }

            public String getBuyTime() {
                return buyTime;
            }

            public void setBuyTime(String buyTime) {
                this.buyTime = buyTime;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }
        }
    }
}
