package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SimPleModel {

    /**
     * requestId : 137270335195271347409603691
     * errorVo : {"code":"1000","msg":"购买特权成功"}
     * data : {"remain":997000}
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
         * msg : 购买特权成功
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
         * remain : 997000.0
         */

        private double remain;

        public double getRemain() {
            return remain;
        }

        public void setRemain(double remain) {
            this.remain = remain;
        }
    }
}
