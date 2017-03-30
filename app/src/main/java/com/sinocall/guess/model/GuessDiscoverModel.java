package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/3/6.
 */

public class GuessDiscoverModel {

    /**
     * requestId : 179825924195138041456815784
     * errorVo : {"code":"1000","msg":"下注成功"}
     * data : {"recordCoin":1,"support":130,"remain":946799}
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
         * msg : 下注成功
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
         * recordCoin : 1
         * support : 130
         * remain : 946799
         */

        private int recordCoin;
        private int support;
        private double remain;

        public int getRecordCoin() {
            return recordCoin;
        }

        public void setRecordCoin(int recordCoin) {
            this.recordCoin = recordCoin;
        }

        public int getSupport() {
            return support;
        }

        public void setSupport(int support) {
            this.support = support;
        }

        public double getRemain() {
            return remain;
        }

        public void setRemain(double remain) {
            this.remain = remain;
        }
    }
}
