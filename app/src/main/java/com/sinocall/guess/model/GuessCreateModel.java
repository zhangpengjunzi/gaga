package com.sinocall.guess.model;

import java.util.List;

/**
 * 新建盘口
 * Created by Administrator on 2017/2/27.
 */

public class GuessCreateModel {

    /**
     * requestId : 109679620195060099648814519
     * errorVo : {"code":"1000","msg":"不能够创建"}
     * data : {"list":[{"id":1,"payCoin":1000,"num":10,"expireDay":7},{"id":2,"payCoin":1800,"num":20,"expireDay":7},{"id":3,"payCoin":2500,"num":30,"expireDay":7}],"enable":0}
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
         * msg : 不能够创建
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
         * list : [{"id":1,"payCoin":1000,"num":10,"expireDay":7},{"id":2,"payCoin":1800,"num":20,"expireDay":7},{"id":3,"payCoin":2500,"num":30,"expireDay":7}]
         * enable : 0
         */
        private int currentNum;
        private int enable;
        private List<ListBean> list;

        public int getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(int currentNum) {
            this.currentNum = currentNum;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * payCoin : 1000
             * num : 10
             * expireDay : 7
             */

            private int id;
            private int payCoin;
            private int num;
            private int expireDay;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPayCoin() {
                return payCoin;
            }

            public void setPayCoin(int payCoin) {
                this.payCoin = payCoin;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getExpireDay() {
                return expireDay;
            }

            public void setExpireDay(int expireDay) {
                this.expireDay = expireDay;
            }
        }
    }
}
