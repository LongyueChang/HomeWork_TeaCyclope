package beens;

import java.util.List;

/**
 * Created by Administrator on 2016/11/13.
 */

public class CycleopediaBeen {

    /**
     * data : [{"id":"8195","title":"饮茶的禁忌（下）","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/29/20151229093519_45699_suolue3.jpg","create_time":"12月29日09:37","nickname":"bubu123"},{"id":"7266","title":"春茶好在那里？","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/08/20140408160100_23936_suolue3.jpg","create_time":"04月08日16:06","nickname":"杯中茗"},{"id":"7265","title":"新茶的存储方法","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/08/20140408155055_73497_suolue3.jpg","create_time":"04月08日15:51","nickname":"杯中茗"},{"id":"7264","title":"新陈茶如何鉴别","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/04/08/20140408153037_75913_suolue3.jpg","create_time":"04月08日15:32","nickname":"杯中茗"},{"id":"7255","title":"2014春茶什么时候上市","source":"买买茶","description":"","wap_thumb":"","create_time":"03月27日16:14","nickname":"杯中茗"},{"id":"7230","title":"陈年铁观音如何辨别  　　","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/24/20140224165452_20358_suolue3.jpg","create_time":"02月24日16:57","nickname":"杯中茗"},{"id":"7201","title":"电脑一族必喝的四杯茶","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/13/20140213182124_51632_suolue3.jpg","create_time":"02月13日18:24","nickname":"茶の物语"},{"id":"7198","title":"春茶饮用应注意","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/12/20140212175934_54316_suolue3.jpg","create_time":"02月12日18:00","nickname":"茗茶起舞"},{"id":"7195","title":"全世界最古老的红茶\u2014正山小种茶叶","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/11/20140211183107_21914_suolue3.jpg","create_time":"02月11日18:35","nickname":"茶茗小屋"},{"id":"7194","title":"碧螺春储存方式以及保质期","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/11/20140211181752_38245_suolue3.jpg","create_time":"02月11日18:18","nickname":"堂吉诃德与茶"},{"id":"7193","title":"西湖龙井新茶鉴别方式","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/11/20140211175544_91278_suolue3.jpg","create_time":"02月11日18:00","nickname":"回忆的沙漏"},{"id":"7184","title":"普洱茶的九种山寨版","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/01/17/20140117153609_68345_suolue3.jpg","create_time":"01月17日15:36","nickname":"茶语人生"},{"id":"7170","title":"金骏眉的保存方法","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/01/06/20140106163817_82787_suolue3.jpg","create_time":"01月06日16:38","nickname":"杯中茗"},{"id":"7168","title":"茶马古道的悠悠历程","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/01/04/20140104141837_31938_suolue3.jpg","create_time":"01月04日14:18","nickname":"杯中茗"},{"id":"7166","title":"红茶基础知识","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/01/03/20140103140750_93127_suolue3.jpg","create_time":"01月03日14:08","nickname":"杯中茗"}]
     * errorMessage : success
     */

    private String errorMessage;
    private List<DataBean> data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8195
         * title : 饮茶的禁忌（下）
         * source : 原创
         * description :
         * wap_thumb : http://s1.sns.maimaicha.com/images/2015/12/29/20151229093519_45699_suolue3.jpg
         * create_time : 12月29日09:37
         * nickname : bubu123
         */

        private String id;
        private String title;
        private String source;
        private String description;
        private String wap_thumb;
        private String create_time;
        private String nickname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWap_thumb() {
            return wap_thumb;
        }

        public void setWap_thumb(String wap_thumb) {
            this.wap_thumb = wap_thumb;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
