package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class GuessPayCenterModel {

    /**
     * requestId : 150491493194923038580274955
     * errorVo : {"code":"1000","msg":"获取充值中心"}
     * data : {"remain":1109,"exchangeCoin":1000,"chargeItems":[{"name":"10000金豆，需100元","price":100,"payPrice":100,"remark":"","giftCoinNum":0},{}],"payTypes":[{"payType":"102","payDec":"苹果"},{},{}],"jump":0,"url":""}
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
         * remain : 1109
         * exchangeCoin : 1000
         * chargeItems : [{"name":"10000金豆，需100元","price":100,"payPrice":100,"remark":"","giftCoinNum":0},{}]
         * payTypes : [{"payType":"102","payDec":"苹果"},{},{}]
         * jump : 0
         * url :
         */

        private double remain;
        private double exchangeCoin;
        private int jump;
        private String url;
        private List<ChargeItemsBean> chargeItems;
        private List<PayTypesBean> payTypes;

        public double getRemain() {
            return remain;
        }

        public void setRemain(double remain) {
            this.remain = remain;
        }

        public double getExchangeCoin() {
            return exchangeCoin;
        }

        public void setExchangeCoin(double exchangeCoin) {
            this.exchangeCoin = exchangeCoin;
        }

        public int getJump() {
            return jump;
        }

        public void setJump(int jump) {
            this.jump = jump;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<ChargeItemsBean> getChargeItems() {
            return chargeItems;
        }

        public void setChargeItems(List<ChargeItemsBean> chargeItems) {
            this.chargeItems = chargeItems;
        }

        public List<PayTypesBean> getPayTypes() {
            return payTypes;
        }

        public void setPayTypes(List<PayTypesBean> payTypes) {
            this.payTypes = payTypes;
        }

        public static class ChargeItemsBean {
            /**
             * name : 10000金豆，需100元
             * price : 100
             * payPrice : 100
             * remark :
             * giftCoinNum : 0
             */

            private String name;
            private double price;
            private double payPrice;
            private String remark;
            private int giftCoinNum;
            private int select;//0为未选中，1为选中

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPayPrice() {
                return payPrice;
            }

            public void setPayPrice(double payPrice) {
                this.payPrice = payPrice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getGiftCoinNum() {
                return giftCoinNum;
            }

            public void setGiftCoinNum(int giftCoinNum) {
                this.giftCoinNum = giftCoinNum;
            }
        }

        public static class PayTypesBean {
            /**
             * payType : 102
             * payDec : 苹果
             */

            private String payType;
            private String payDec;
            private int paySelect;//0代表未选中，1代表选中

            public int getPaySelect() {
                return paySelect;
            }

            public void setPaySelect(int paySelect) {
                this.paySelect = paySelect;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getPayDec() {
                return payDec;
            }

            public void setPayDec(String payDec) {
                this.payDec = payDec;
            }
        }
    }
}
