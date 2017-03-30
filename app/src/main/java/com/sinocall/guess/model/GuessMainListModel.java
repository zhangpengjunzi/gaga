package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class GuessMainListModel {

    /**
     * requestId : 144581797195216772028903443
     * errorVo : {"code":"1000","msg":"获取首页盘口列表"}
     * data : {"list":[{"createTime":"","id":38,"description":"盘口38","coinSwitch":10000,"recordCoin":6601,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":2900,"supportright":1100},{"createTime":"","id":37,"description":"盘口37","coinSwitch":10000,"recordCoin":1601,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":300,"supportright":600},{"createTime":"","id":35,"description":"盘口35","coinSwitch":10000,"recordCoin":1501,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0},{"createTime":"","id":34,"description":"盘口34","coinSwitch":10000,"recordCoin":1901,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0},{"createTime":"","id":33,"description":"盘口33","coinSwitch":10000,"recordCoin":301,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0}],"remain":1700}
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
         * msg : 获取首页盘口列表
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
         * list : [{"createTime":"","id":38,"description":"盘口38","coinSwitch":10000,"recordCoin":6601,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":2900,"supportright":1100},{"createTime":"","id":37,"description":"盘口37","coinSwitch":10000,"recordCoin":1601,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":300,"supportright":600},{"createTime":"","id":35,"description":"盘口35","coinSwitch":10000,"recordCoin":1501,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0},{"createTime":"","id":34,"description":"盘口34","coinSwitch":10000,"recordCoin":1901,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0},{"createTime":"","id":33,"description":"盘口33","coinSwitch":10000,"recordCoin":301,"picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_1.png","left":"左选项","right":"右选项","supportLeft":0,"supportright":0}]
         * remain : 1700
         */

        private double remain;
        private List<ListBean> list;

        public double getRemain() {
            return remain;
        }

        public void setRemain(double remain) {
            this.remain = remain;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * createTime :
             * id : 38
             * description : 盘口38
             * coinSwitch : 10000
             * recordCoin : 6601
             * picUrl : http://pro.iluyin.cn/sinomini/images/banner/init_1.png
             * left : 左选项
             * right : 右选项
             * supportLeft : 2900
             * supportright : 1100
             */

            private String createTime;
            private int id;
            private String description;
            private int coinSwitch;
            private int recordCoin;
            private String picUrl;
            private String left;
            private String right;
            private int supportLeft;
            private int supportright;
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getCoinSwitch() {
                return coinSwitch;
            }

            public void setCoinSwitch(int coinSwitch) {
                this.coinSwitch = coinSwitch;
            }

            public int getRecordCoin() {
                return recordCoin;
            }

            public void setRecordCoin(int recordCoin) {
                this.recordCoin = recordCoin;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getLeft() {
                return left;
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public String getRight() {
                return right;
            }

            public void setRight(String right) {
                this.right = right;
            }

            public int getSupportLeft() {
                return supportLeft;
            }

            public void setSupportLeft(int supportLeft) {
                this.supportLeft = supportLeft;
            }

            public int getSupportright() {
                return supportright;
            }

            public void setSupportright(int supportright) {
                this.supportright = supportright;
            }
        }
    }
}
