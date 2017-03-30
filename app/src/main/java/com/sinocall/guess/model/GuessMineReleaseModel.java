package com.sinocall.guess.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class GuessMineReleaseModel {

    /**
     * requestId : 117112343194991864221529154
     * errorVo : {"code":"1000","msg":"获取我发布的"}
     * data : {"createList":[{"createTime":"2017-02-21 09:41:37","id":1,"name":"CCK0","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":2,"name":"CCK0","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":3,"name":"CCK1","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":4,"name":"CCK2","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":5,"name":"CCK3","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":6,"name":"CCK4","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":7,"name":"CCK5","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":8,"name":"CCK6","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/6","status":0},{"createTime":"2017-02-21 09:41:38","id":9,"name":"CCK7","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/7","status":0},{"createTime":"2017-02-21 09:41:38","id":10,"name":"CCK8","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/8","status":0}],"dayCreate":0,"totalCreate":0}
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
         * msg : 获取我发布的
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
         * createList : [{"createTime":"2017-02-21 09:41:37","id":1,"name":"CCK0","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":2,"name":"CCK0","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":3,"name":"CCK1","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":4,"name":"CCK2","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":5,"name":"CCK3","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":6,"name":"CCK4","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":7,"name":"CCK5","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","rnum":30,"commission":0,"status":1},{"createTime":"2017-02-21 09:41:38","id":8,"name":"CCK6","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/6","status":0},{"createTime":"2017-02-21 09:41:38","id":9,"name":"CCK7","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/7","status":0},{"createTime":"2017-02-21 09:41:38","id":10,"name":"CCK8","picUrl":"http://pro.iluyin.cn/sinomini/images/banner/init_2.png","shareUrl":"baidu.com","progress":"1/8","status":0}]
         * dayCreate : 0
         * totalCreate : 0
         */

        private int dayCreate;
        private int totalCreate;
        private List<CreateListBean> createList;

        public int getDayCreate() {
            return dayCreate;
        }

        public void setDayCreate(int dayCreate) {
            this.dayCreate = dayCreate;
        }

        public int getTotalCreate() {
            return totalCreate;
        }

        public void setTotalCreate(int totalCreate) {
            this.totalCreate = totalCreate;
        }

        public List<CreateListBean> getCreateList() {
            return createList;
        }

        public void setCreateList(List<CreateListBean> createList) {
            this.createList = createList;
        }

        public static class CreateListBean {
            /**
             * createTime : 2017-02-21 09:41:37
             * id : 1
             * name : CCK0
             * picUrl : http://pro.iluyin.cn/sinomini/images/banner/init_2.png
             * rnum : 30
             * commission : 0
             * status : 1
             * shareUrl : baidu.com
             * progress : 1/6
             */

            private String createTime;
            private int id;
            private String name;
            private String picUrl;
            private int rnum;
            private double commission;
            private int status;
            private String shareUrl;
            private String progress;
            private String description;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public int getRnum() {
                return rnum;
            }

            public void setRnum(int rnum) {
                this.rnum = rnum;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getProgress() {
                return progress;
            }

            public void setProgress(String progress) {
                this.progress = progress;
            }
        }
    }
}
