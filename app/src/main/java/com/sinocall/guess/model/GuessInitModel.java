package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/3/11.
 */

public class GuessInitModel {

    /**
     * requestId : 115144265195331910850337034
     * errorVo : {"code":"1000","msg":"获取初始化参数"}
     * data : {"qqTopicShare":"http://www.4008365777.com/api/h5/testQQShare","weixinTopiShare":"http://www.4008365777.com","sinaTopicShare":"http://www.4008365777.com","exchangeCenter":"http://192.168.10.200:8088/views/exchangeCenter.html","buyPrivilegeHelp":"http://192.168.10.200:8088/views/buyPrivilegeHelp.html","growUpHelp":"http://192.168.10.200:8088/views/growUpHelp.html","partnerHelp":"http://192.168.10.200:8088/views/partnerHelp.html","publishHelp":"http://192.168.10.200:8088/views/publishHelp.html","userAgreement":"http://www.4008365777.com/views/userAgreement.html","userHelp":"http://192.168.10.200:8088/views/userHelp.html","makeMoneyShare":"http://192.168.10.200:8088/views/makeMoneyShare.html","chargeH5":"http://192.168.10.200:8088/views/chargeH5.html"}
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
         * msg : 获取初始化参数
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
         * qqTopicShare : http://www.4008365777.com/api/h5/testQQShare
         * weixinTopiShare : http://www.4008365777.com
         * sinaTopicShare : http://www.4008365777.com
         * exchangeCenter : http://192.168.10.200:8088/views/exchangeCenter.html
         * buyPrivilegeHelp : http://192.168.10.200:8088/views/buyPrivilegeHelp.html
         * growUpHelp : http://192.168.10.200:8088/views/growUpHelp.html
         * partnerHelp : http://192.168.10.200:8088/views/partnerHelp.html
         * publishHelp : http://192.168.10.200:8088/views/publishHelp.html
         * userAgreement : http://www.4008365777.com/views/userAgreement.html
         * userHelp : http://192.168.10.200:8088/views/userHelp.html
         * makeMoneyShare : http://192.168.10.200:8088/views/makeMoneyShare.html
         * chargeH5 : http://192.168.10.200:8088/views/chargeH5.html
         */

        private String qqTopicShare;
        private String weixinTopiShare;
        private String sinaTopicShare;
        private String exchangeCenter;
        private String buyPrivilegeHelp;
        private String growUpHelp;
        private String partnerHelp;
        private String publishHelp;
        private String userAgreement;
        private String userHelp;
        private String makeMoneyShare;
        private String chargeH5;
        private String topH5;

        public String getTopH5() {
            return topH5;
        }

        public void setTopH5(String topH5) {
            this.topH5 = topH5;
        }

        public String getQqTopicShare() {
            return qqTopicShare;
        }

        public void setQqTopicShare(String qqTopicShare) {
            this.qqTopicShare = qqTopicShare;
        }

        public String getWeixinTopiShare() {
            return weixinTopiShare;
        }

        public void setWeixinTopiShare(String weixinTopiShare) {
            this.weixinTopiShare = weixinTopiShare;
        }

        public String getSinaTopicShare() {
            return sinaTopicShare;
        }

        public void setSinaTopicShare(String sinaTopicShare) {
            this.sinaTopicShare = sinaTopicShare;
        }

        public String getExchangeCenter() {
            return exchangeCenter;
        }

        public void setExchangeCenter(String exchangeCenter) {
            this.exchangeCenter = exchangeCenter;
        }

        public String getBuyPrivilegeHelp() {
            return buyPrivilegeHelp;
        }

        public void setBuyPrivilegeHelp(String buyPrivilegeHelp) {
            this.buyPrivilegeHelp = buyPrivilegeHelp;
        }

        public String getGrowUpHelp() {
            return growUpHelp;
        }

        public void setGrowUpHelp(String growUpHelp) {
            this.growUpHelp = growUpHelp;
        }

        public String getPartnerHelp() {
            return partnerHelp;
        }

        public void setPartnerHelp(String partnerHelp) {
            this.partnerHelp = partnerHelp;
        }

        public String getPublishHelp() {
            return publishHelp;
        }

        public void setPublishHelp(String publishHelp) {
            this.publishHelp = publishHelp;
        }

        public String getUserAgreement() {
            return userAgreement;
        }

        public void setUserAgreement(String userAgreement) {
            this.userAgreement = userAgreement;
        }

        public String getUserHelp() {
            return userHelp;
        }

        public void setUserHelp(String userHelp) {
            this.userHelp = userHelp;
        }

        public String getMakeMoneyShare() {
            return makeMoneyShare;
        }

        public void setMakeMoneyShare(String makeMoneyShare) {
            this.makeMoneyShare = makeMoneyShare;
        }

        public String getChargeH5() {
            return chargeH5;
        }

        public void setChargeH5(String chargeH5) {
            this.chargeH5 = chargeH5;
        }
    }
}
