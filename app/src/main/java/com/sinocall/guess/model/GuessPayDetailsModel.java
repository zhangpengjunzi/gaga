package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */

public class GuessPayDetailsModel {

    /**
     * requestId : 168711350195135454933131913
     * errorVo : {"code":"1000","msg":"获取充值明细"}
     * data : {"exchangeList":[{"createTime":"2017-02-20 13:42:40","num":11,"reason":"手机充值: ¥11","remainCoin":11}]}
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
         * msg : 获取充值明细
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
        private List<ExchangeListBean> exchangeList;

        public List<ExchangeListBean> getExchangeList() {
            return exchangeList;
        }

        public void setExchangeList(List<ExchangeListBean> exchangeList) {
            this.exchangeList = exchangeList;
        }

        public static class ExchangeListBean {
            /**
             * createTime : 2017-02-20 13:42:40
             * num : 11
             * reason : 手机充值: ¥11
             * remainCoin : 11
             */

            private String createTime;
            private int num;
            private String reason;
            private int remainCoin;

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

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getRemainCoin() {
                return remainCoin;
            }

            public void setRemainCoin(int remainCoin) {
                this.remainCoin = remainCoin;
            }
        }
    }
}
