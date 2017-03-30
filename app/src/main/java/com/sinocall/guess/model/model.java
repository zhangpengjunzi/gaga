package com.sinocall.guess.model;

/**
 * Created by Administrator on 2017/2/16.
 */

public class model {

    /**
     * requestId : 155625551194935569952932268
     * errorVo : {"code":"1000","msg":"获取充值中心"}
     * data : {"payType":100,"aliPayData":{"subject":"kindou","_input_charset":"utf-8","sign":"OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D","sendStr":"_input_charset=utf-8&body=kindou&notify_url=http://450530a0.nat123.net/api/userCharge/payZfbNotify&out_trade_no=cck201702161819315065&partner=2088911439658303&payment_type=1&seller_id=sinocall_caiwu@163.com&service=mobile.securitypay.pay&subject=kindou&total_fee=0.1&sign_type=\"RSA\"&sign=\"OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D\"","body":"kindou","notify_url":"http://450530a0.nat123.net/api/userCharge/payZfbNotify","payment_type":"1","out_trade_no":"cck201702161819315065","partner":"2088911439658303","service":"mobile.securitypay.pay","total_fee":"0.1","sign_type":"RSA","seller_id":"sinocall_caiwu@163.com"}}
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
         * aliPayData : {"subject":"kindou","_input_charset":"utf-8","sign":"OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D","sendStr":"_input_charset=utf-8&body=kindou&notify_url=http://450530a0.nat123.net/api/userCharge/payZfbNotify&out_trade_no=cck201702161819315065&partner=2088911439658303&payment_type=1&seller_id=sinocall_caiwu@163.com&service=mobile.securitypay.pay&subject=kindou&total_fee=0.1&sign_type=\"RSA\"&sign=\"OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D\"","body":"kindou","notify_url":"http://450530a0.nat123.net/api/userCharge/payZfbNotify","payment_type":"1","out_trade_no":"cck201702161819315065","partner":"2088911439658303","service":"mobile.securitypay.pay","total_fee":"0.1","sign_type":"RSA","seller_id":"sinocall_caiwu@163.com"}
         */

        private int payType;
        private AliPayDataBean aliPayData;

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public AliPayDataBean getAliPayData() {
            return aliPayData;
        }

        public void setAliPayData(AliPayDataBean aliPayData) {
            this.aliPayData = aliPayData;
        }

        public static class AliPayDataBean {
            /**
             * subject : kindou
             * _input_charset : utf-8
             * sign : OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D
             * sendStr : _input_charset=utf-8&body=kindou&notify_url=http://450530a0.nat123.net/api/userCharge/payZfbNotify&out_trade_no=cck201702161819315065&partner=2088911439658303&payment_type=1&seller_id=sinocall_caiwu@163.com&service=mobile.securitypay.pay&subject=kindou&total_fee=0.1&sign_type="RSA"&sign="OjLgqriezZg8%2Bg5M%2BdJcaaGUUoK6rO%2FiBe0yrf%2FrHx4909jzOFZUJHcXfDl6RHUZEKD%2BvGjsdcxzkv%2Fvf7I8LvE%2BfnIH5px8RhYFnrx0i5xjOoUzBTSlLh2mObckGmzzrcnVMdTAvliA7H%2BcqLtlIKLHW94PqVxypOSaJeeHQVk%3D"
             * body : kindou
             * notify_url : http://450530a0.nat123.net/api/userCharge/payZfbNotify
             * payment_type : 1
             * out_trade_no : cck201702161819315065
             * partner : 2088911439658303
             * service : mobile.securitypay.pay
             * total_fee : 0.1
             * sign_type : RSA
             * seller_id : sinocall_caiwu@163.com
             */

            private String subject;
            private String _input_charset;
            private String sign;
            private String sendStr;
            private String body;
            private String notify_url;
            private String payment_type;
            private String out_trade_no;
            private String partner;
            private String service;
            private String total_fee;
            private String sign_type;
            private String seller_id;

            public String getSubject() {
                return subject;
            }

            public void setSubject(String subject) {
                this.subject = subject;
            }

            public String get_input_charset() {
                return _input_charset;
            }

            public void set_input_charset(String _input_charset) {
                this._input_charset = _input_charset;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getSendStr() {
                return sendStr;
            }

            public void setSendStr(String sendStr) {
                this.sendStr = sendStr;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getPayment_type() {
                return payment_type;
            }

            public void setPayment_type(String payment_type) {
                this.payment_type = payment_type;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getPartner() {
                return partner;
            }

            public void setPartner(String partner) {
                this.partner = partner;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }
        }
    }
}
