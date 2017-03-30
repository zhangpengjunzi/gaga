package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class GuessAwardListModel {

    /**
     * requestId : 127823872195081272520816992
     * errorVo : {"code":"1000","msg":"自建盘口可选投注上限列表"}
     * data : {"list":[{"num":30000,"enable":1},{"num":0,"enable":0},{"num":0,"enable":0},{"num":0,"enable":0},{"num":0,"enable":0}]}
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
         * msg : 自建盘口可选投注上限列表
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * num : 30000
             * enable : 1
             */

            private int num;
            private int enable;
            private int select;

            public int getSelect() {
                return select;
            }

            public void setSelect(int select) {
                this.select = select;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getEnable() {
                return enable;
            }

            public void setEnable(int enable) {
                this.enable = enable;
            }
        }
    }
}
