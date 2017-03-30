package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/23.
 */

public class GuessUploadModel {

    /**
     * requestId : 198285341195013262534271954
     * errorVo : {"code":"1000","msg":"更新用户头像"}
     * data : {"logo":"http://450530a0.nat123.net:80/res/345ccfe0a29e9067ac964fd52ca755ed.jpeg"}
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
         * msg : 更新用户头像
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
         * logo : http://450530a0.nat123.net:80/res/345ccfe0a29e9067ac964fd52ca755ed.jpeg
         */

        private String logo;

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
