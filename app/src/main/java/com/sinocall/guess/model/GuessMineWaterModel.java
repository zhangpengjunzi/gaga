package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class GuessMineWaterModel {

    /**
     * requestId : 145335623194990781130057056
     * errorVo : {"code":"1000","msg":"获取流水"}
     * data : {"takePartList":[{"createTime":"2017-02-21 09:46:07","num":1,"name":"CCK0","choice":"左"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK1","choice":"左"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK2","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK3","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK4","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK5","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK6","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK7","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK8","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK9","choice":"右"}],"todayNum":1,"totalNum":10}
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
         * msg : 获取流水
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
         * takePartList : [{"createTime":"2017-02-21 09:46:07","num":1,"name":"CCK0","choice":"左"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK1","choice":"左"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK2","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK3","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK4","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK5","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK6","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK7","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK8","choice":"右"},{"createTime":"2017-02-21 09:46:08","num":1,"name":"CCK9","choice":"右"}]
         * todayNum : 1
         * totalNum : 10
         */

        private int todayNum;
        private int totalNum;
        private List<TakePartListBean> takePartList;

        public int getTodayNum() {
            return todayNum;
        }

        public void setTodayNum(int todayNum) {
            this.todayNum = todayNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<TakePartListBean> getTakePartList() {
            return takePartList;
        }

        public void setTakePartList(List<TakePartListBean> takePartList) {
            this.takePartList = takePartList;
        }

        public static class TakePartListBean {
            /**
             * createTime : 2017-02-21 09:46:07
             * num : 1
             * name : CCK0
             * choice : 左
             */

            private String createTime;
            private int num;
            private String remark;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
