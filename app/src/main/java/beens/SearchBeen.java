package beens;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class SearchBeen {

    /**
     * data : [{"id":"8203","title":"寄语2016","source":"原创","description":"新年寄语","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/31/20151231082937_53817_suolue3.jpg","create_time":"12月31日08:29","nickname":"bubu123"},{"id":"8170","title":"2015年最后十天，开不开心都过来了。愿你的2016只有好运，没有遗憾。","source":"原创","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2015/12/22/20151222141513_37966_suolue3.jpg","create_time":"12月22日14:18","nickname":"bubu123"},{"id":"7255","title":"2014春茶什么时候上市","source":"买买茶","description":"","wap_thumb":"","create_time":"03月27日16:14","nickname":"杯中茗"},{"id":"7206","title":"2013茶界十大事件及2014茶行业十大趋势","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/15/20140215162633_50606_suolue3.jpg","create_time":"02月15日16:30","nickname":"凡人"},{"id":"7191","title":"2014年普洱茶行业的8大趋势","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2014/02/10/20140210153356_79343_suolue3.jpg","create_time":"02月10日15:36","nickname":"阿依玛"},{"id":"7123","title":"2013年末普洱茶投资分析","source":"买买茶","description":"","wap_thumb":"http://s1.sns.maimaicha.com/images/2013/12/07/20131207153652_39682_suolue3.jpg","create_time":"12月07日15:43","nickname":"杯中茗"},{"id":"6240","title":"了解一下茶叶的正确喝法1","source":"买买茶","description":"中国人有喝茶的习惯，除了茶香的美味外，茶是健康的饮料，因为近年来研究发现，茶的某些成分有助于身体保健。虽然喝茶有许多好处","wap_thumb":"http://s1.sns.maimaicha.com/images/2013/07/10/20130710160742_87074_suolue3.jpg","create_time":"07月09日16:58","nickname":"谈天说地清茶馆"},{"id":"6234","title":"早9点花茶醒脑 午1点绿茶降脂:喝茶讲究时间","source":"买买茶","description":"中国人喝茶已有几千年历史，作为生活中的常见饮品，茶味清新淡雅，既能解渴又能养生，是很多人的挚爱。虽然大家都爱喝，但不一定","wap_thumb":"http://s1.sns.maimaicha.com/images/2013/07/09/20130709103918_58593_suolue3.jpg","create_time":"07月09日10:40","nickname":"茶商人"},{"id":"6161","title":"2013世界茶产业北京论坛将举行","source":"中国广播网","description":"\u201c2013年世界茶产业北京论坛暨全国茶产业联展联播联销系列活动\u201d于2013年6月至10月在京举行。活动以全国茶产业联展、","wap_thumb":"http://s1.sns.maimaicha.com/images/2013/07/01/20130701144222_90499_suolue3.jpg","create_time":"07月01日14:43","nickname":"strategist"},{"id":"6068","title":"雅安投10亿元建蒙顶山国际茶叶交易市场","source":"买买茶","description":"近日来，一则消息在茶业界火热传播中\u2014\u2014茶马古城投资10亿元，建中国蒙顶山国际茶叶交易市场，打造中国最大的绿茶交易和藏茶交","wap_thumb":"http://s1.sns.maimaicha.com/images/2013/06/21/20130621144935_64241_suolue3.jpg","create_time":"06月21日14:50","nickname":"tiny"}]
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
         * id : 8203
         * title : 寄语2016
         * source : 原创
         * description : 新年寄语
         * wap_thumb : http://s1.sns.maimaicha.com/images/2015/12/31/20151231082937_53817_suolue3.jpg
         * create_time : 12月31日08:29
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
