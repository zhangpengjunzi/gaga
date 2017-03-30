package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/16.
 */

public class GuessPayModel {

    /**
     * requestId : 117724029194933625364286949
     * errorVo : {"code":"1000","msg":"获取充值中心"}
     * data : {"payType":100,"aliPayData":{"payment_type":"1","out_trade_no":"cck201702161412157296","partner":"2088911439658303","service":"mobile.securitypay.pay","subject":"购买金豆","_input_charset":"UTF-8","total_fee":"0.1","sign":"O9nIuIXl9Eg/V3qvtITnYVDR72ZsVOETdNi5iVhh9haZ6X9IIScpkCnskC9ak/nfhLIw63OSk7TTN379JacuvQcsw3FAfMxE5AbyvQ/XpdGegtPvFx3RARTfYtKLGJ+IREKDWqdq6/FdoaE+iJr7sbeKiz0e91JY8sC8LsVcL0k=","body":"购买金豆","notify_url":"http://450530a0.nat123.net/api/userCharge/payZfbNotify","sign_type":"utf-8","seller_id":"sinocall_caiwu@163.com"}}
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
         * msg : 获取充值中心
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
         * payType : 100
         * aliPayData : {"payment_type":"1","out_trade_no":"cck201702161412157296","partner":"2088911439658303","service":"mobile.securitypay.pay","subject":"购买金豆","_input_charset":"UTF-8","total_fee":"0.1","sign":"O9nIuIXl9Eg/V3qvtITnYVDR72ZsVOETdNi5iVhh9haZ6X9IIScpkCnskC9ak/nfhLIw63OSk7TTN379JacuvQcsw3FAfMxE5AbyvQ/XpdGegtPvFx3RARTfYtKLGJ+IREKDWqdq6/FdoaE+iJr7sbeKiz0e91JY8sC8LsVcL0k=","body":"购买金豆","notify_url":"http://450530a0.nat123.net/api/userCharge/payZfbNotify","sign_type":"utf-8","seller_id":"sinocall_caiwu@163.com"}
         */

        private String sendStr;
        private String outTradeNo;
        private WxDataBean wxData;


        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getSendStr() {
            return sendStr;
        }

        public void setSendStr(String sendStr) {
            this.sendStr = sendStr;
        }

        public WxDataBean getWxData() {
            return wxData;
        }

        public void setWxData(WxDataBean wxData) {
            this.wxData = wxData;
        }

        public static class WxDataBean {
            /**
             * appid : wx1f5aafb0417ee63f
             * noncestr : ccc81a97c1535f9a631b9db584a264e4
             * orderId : cck201702161436158276
             * package : Sign=WXPay
             * partnerid : 1254463001
             * prepayid : wx20170216143619920eb95ae50097401827
             * sign : DF913A2668BFBB3BEABB6862C48D767A
             * timestamp : 1487226975
             */

            private String appid;
            private String noncestr;
            private String orderId;
            private String packageValue;
            private String partnerid;
            private String prepayid;
            private String sign;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getPackageValue() {
                return packageValue;
            }

            public void setPackageValue(String packageValue) {
                this.packageValue = packageValue;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
