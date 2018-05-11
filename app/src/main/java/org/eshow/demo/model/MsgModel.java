package org.eshow.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10270 on 2018/3/8.
 */

public class MsgModel implements Serializable {
    /**
     * content : [{"id":144148,"addTime":"2018-03-29 06:39:58","updateTime":"2018-03-29 06:39:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144149,"addTime":"2018-03-29 06:39:58","updateTime":"2018-03-29 06:39:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144141,"addTime":"2018-03-29 06:39:00","updateTime":"2018-03-29 06:39:00","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144142,"addTime":"2018-03-29 06:39:00","updateTime":"2018-03-29 06:39:00","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144134,"addTime":"2018-03-29 06:37:58","updateTime":"2018-03-29 06:37:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144135,"addTime":"2018-03-29 06:37:58","updateTime":"2018-03-29 06:37:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144128,"addTime":"2018-03-29 06:36:59","updateTime":"2018-03-29 06:36:59","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144127,"addTime":"2018-03-29 06:36:58","updateTime":"2018-03-29 06:36:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144120,"addTime":"2018-03-29 06:35:58","updateTime":"2018-03-29 06:35:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}},{"id":144121,"addTime":"2018-03-29 06:35:58","updateTime":"2018-03-29 06:35:58","category":"报警消息","img":null,"title":"围栏报警","content":"请注意，有设备发出围栏报警了","model":"warn","modelVal":null,"state":0,"username":"13771199261","team":{"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}}]
     * totalElements : 7165
     * totalPages : 717
     * last : false
     * numberOfElements : 10
     * sort : [{"direction":"DESC","property":"addTime","ignoreCase":false,"nullHandling":"NATIVE","descending":true,"ascending":false}]
     * first : true
     * size : 10
     * number : 0
     */

    private int totalElements;
    private int totalPages;
    private boolean last;
    private int numberOfElements;
    private boolean first;
    private int size;
    private int number;
    private List<ContentBean> content;
    private List<SortBean> sort;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public List<SortBean> getSort() {
        return sort;
    }

    public void setSort(List<SortBean> sort) {
        this.sort = sort;
    }

    public static class ContentBean {
        /**
         * id : 144148
         * addTime : 2018-03-29 06:39:58
         * updateTime : 2018-03-29 06:39:58
         * category : 报警消息
         * img : null
         * title : 围栏报警
         * content : 请注意，有设备发出围栏报警了
         * model : warn
         * modelVal : null
         * state : 0
         * username : 13771199261
         * team : {"id":12122,"addTime":"2018-01-19 10:03:10","updateTime":"2018-01-19 10:03:10","name":"汽车追踪","description":"汽车追踪","img":"https://qiniu.easyapi.com/photo/team4.png","industry":"互联网","appSize":0,"userSize":0,"serviceSize":10,"apmSize":0,"orderSize":10,"user":null}
         */

        private int id;
        private String addTime;
        private String updateTime;
        private String category;
        private Object img;
        private String title;
        private String content;
        private String model;
        private Object modelVal;
        private int state;
        private String username;
        private TeamBean team;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Object getModelVal() {
            return modelVal;
        }

        public void setModelVal(Object modelVal) {
            this.modelVal = modelVal;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public TeamBean getTeam() {
            return team;
        }

        public void setTeam(TeamBean team) {
            this.team = team;
        }

        public static class TeamBean {
            /**
             * id : 12122
             * addTime : 2018-01-19 10:03:10
             * updateTime : 2018-01-19 10:03:10
             * name : 汽车追踪
             * description : 汽车追踪
             * img : https://qiniu.easyapi.com/photo/team4.png
             * industry : 互联网
             * appSize : 0
             * userSize : 0
             * serviceSize : 10
             * apmSize : 0
             * orderSize : 10
             * user : null
             */

            private int id;
            private String addTime;
            private String updateTime;
            private String name;
            private String description;
            private String img;
            private String industry;
            private int appSize;
            private int userSize;
            private int serviceSize;
            private int apmSize;
            private int orderSize;
            private Object user;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIndustry() {
                return industry;
            }

            public void setIndustry(String industry) {
                this.industry = industry;
            }

            public int getAppSize() {
                return appSize;
            }

            public void setAppSize(int appSize) {
                this.appSize = appSize;
            }

            public int getUserSize() {
                return userSize;
            }

            public void setUserSize(int userSize) {
                this.userSize = userSize;
            }

            public int getServiceSize() {
                return serviceSize;
            }

            public void setServiceSize(int serviceSize) {
                this.serviceSize = serviceSize;
            }

            public int getApmSize() {
                return apmSize;
            }

            public void setApmSize(int apmSize) {
                this.apmSize = apmSize;
            }

            public int getOrderSize() {
                return orderSize;
            }

            public void setOrderSize(int orderSize) {
                this.orderSize = orderSize;
            }

            public Object getUser() {
                return user;
            }

            public void setUser(Object user) {
                this.user = user;
            }
        }
    }

    public static class SortBean {
        /**
         * direction : DESC
         * property : addTime
         * ignoreCase : false
         * nullHandling : NATIVE
         * descending : true
         * ascending : false
         */

        private String direction;
        private String property;
        private boolean ignoreCase;
        private String nullHandling;
        private boolean descending;
        private boolean ascending;

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public String getNullHandling() {
            return nullHandling;
        }

        public void setNullHandling(String nullHandling) {
            this.nullHandling = nullHandling;
        }

        public boolean isDescending() {
            return descending;
        }

        public void setDescending(boolean descending) {
            this.descending = descending;
        }

        public boolean isAscending() {
            return ascending;
        }

        public void setAscending(boolean ascending) {
            this.ascending = ascending;
        }
    }
}
