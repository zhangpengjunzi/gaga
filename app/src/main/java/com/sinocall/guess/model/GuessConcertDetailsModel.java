package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class GuessConcertDetailsModel {

    /**
     * requestId : 101991035195215720429095420
     * errorVo : {"code":"1000","msg":""}
     * data : {"list":[{"createTime":"2017-03-13 11:34:21","num":-1800,"remind":["自建盘口投注上限"],"remainCoin":1700},{"createTime":"2017-03-13 12:02:19","num":-1000,"remind":["每日单个题目竞猜上限"],"remainCoin":1700}]}
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
         * msg :
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createTime : 2017-03-13 11:34:21
             * num : -1800
             * remind : ["自建盘口投注上限"]
             * remainCoin : 1700
             */

            private String createTime;
            private int num;
            private int remainCoin;
            private List<String> remind;

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

            public int getRemainCoin() {
                return remainCoin;
            }

            public void setRemainCoin(int remainCoin) {
                this.remainCoin = remainCoin;
            }

            public List<String> getRemind() {
                return remind;
            }

            public void setRemind(List<String> remind) {
                this.remind = remind;
            }
        }
    }
}
