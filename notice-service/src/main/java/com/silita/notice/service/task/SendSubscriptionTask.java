package com.silita.notice.service.task;

import com.alibaba.fastjson.JSONObject;
import com.silita.notice.dao.TbMessageMapper;
import com.silita.notice.dao.TbUserSubscribeMapper;
import com.silita.notice.model.TbMessage;
import com.silita.notice.service.impl.ElasticsearchService;
import com.silita.notice.utils.DateUtils;
import com.silita.notice.utils.HttpUtils;
import com.silita.notice.utils.PropertyUtil;
import com.silita.notice.utils.PushMessageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2019/9/10.
 */
public class SendSubscriptionTask implements Runnable {

    private Logger logger = Logger.getLogger(SendSubscriptionTask.class);

    private Integer start;
    private Integer pageSize;
    private ElasticsearchService elasticsearchService;
    private TbUserSubscribeMapper tbUserSubscribeMapper;
    private TbMessageMapper tbMessageMapper;
    private long startTime;
    private long endTime;
    private String token;

    public SendSubscriptionTask(Integer start, Integer pageSize, ElasticsearchService elasticsearchService, TbUserSubscribeMapper tbUserSubscribeMapper, TbMessageMapper tbMessageMapper, long startTime, long endTime, String token) {
        this.start = start;
        this.pageSize = pageSize;
        this.elasticsearchService = elasticsearchService;
        this.tbUserSubscribeMapper = tbUserSubscribeMapper;
        this.tbMessageMapper = tbMessageMapper;
        this.startTime = startTime;
        this.endTime = endTime;
        this.token = token;
    }

    @Override
    public void run() {
        Map<String, Object> valMap = new HashedMap(2) {{
            put("start", start);
            put("end", pageSize);
        }};
        List<Map<String, Object>> list = tbUserSubscribeMapper.queryListUserSubscibe(valMap);
        if (null == list || list.size() <= 0) {
            return;
        }
        int size = list.size();
        Map<String, Object> param;
        for (int i = 0; i < size; i++) {
            param = JSONObject.parseObject(list.get(i).get("condition").toString());
            param.put("start", startTime);
            param.put("end", endTime);
            param.put("pageNo", 1);
            param.put("pageSize", 6);
            this.sendMessage(param, MapUtils.getString(list.get(i), "userId"), MapUtils.getInteger(list.get(i), "pkid"));
        }
    }

    /**
     * 查询结果后发送消息
     *
     * @param param
     * @param userId
     * @param pkid
     */
    private void sendMessage(Map<String, Object> param, String userId, Integer pkid) {
        Map<String, Object> result = elasticsearchService.getSubscribe(param, "subscribe");
        int total = MapUtils.getInteger(result, "total");
        if (total <= 0) {
            return;
        }
        Map<String, Object> weChatUser = tbUserSubscribeMapper.queryRelUserInfo(userId);
        //判断是否关注公告号
        if (MapUtils.isNotEmpty(weChatUser)) {
            sendUserPushMsg(token, MapUtils.getString(weChatUser, "open_id"), DateUtils.dateToStr(new Date(startTime), "MM-dd HH:mm"),
                    DateUtils.dateToStr(new Date(endTime), "MM-dd HH:mm"), total, MapUtils.getString(param, "keywords"), pkid);
        } else {
            StringBuffer content = new StringBuffer("您所订阅的招标项目已更新" + total + "条，点击查看详情！");
            StringBuffer title = new StringBuffer("招标订阅项目更新通知！");
            //推送APP消息及系统消息
            PushMessageUtils.pushMessage(userId, new HashedMap(1) {{
                put("pkid", pkid);
            }}, title.toString(), content.toString(), "subscribe");
            //发送系统消息
            saveSystemMsg(param, userId, pkid, total, title.toString());
        }
    }

    /**
     * 发送系统消息
     *
     * @param param
     * @param userId
     * @param pkid
     * @param total
     */
    private void saveSystemMsg(Map<String, Object> param, String userId, Integer pkid, Integer total, String title) {
        StringBuffer content;
        if (StringUtils.isNotEmpty(MapUtils.getString(param, "qualName"))) {
            content = new StringBuffer("业务所在地：" + MapUtils.getString(param, "regionName") + "\n")
                    .append("订阅关键词：" + MapUtils.getString(param, "keywords") + "\n")
                    .append("资质：" + MapUtils.getString(param, "qualName") + "\n")
                    .append("更新时间：" + DateUtils.longToStr(startTime, "MM-dd HH:mm") + "至" + DateUtils.longToStr(endTime, "MM-dd HH:mm") + "\n")
                    .append("您所订阅的招标项目已更新" + total + "条，点击查看详情！");
        } else {
            content = new StringBuffer("业务所在地：" + MapUtils.getString(param, "regionName") + "\n")
                    .append("订阅关键词：" + MapUtils.getString(param, "keywords") + "\n")
                    .append("更新时间：" + DateUtils.longToStr(startTime, "MM-dd HH:mm") + "-" + DateUtils.longToStr(endTime, "MM-dd HH:mm") + "\n")
                    .append("您所订阅的招标项目已更新" + total + "条，点击查看详情！");
        }
        //系统消息发送
        TbMessage message = new TbMessage();
        message.setUserId(userId);
        message.setIsRead(0);
        message.setMsgTitle(title);
        message.setMsgContent(content.toString());
        message.setMsgType("subscribe");
        message.setReplyId(pkid.toString());
        message.setPushd(new Date());
        tbMessageMapper.insert(message);
    }

    /**
     * 发送用户未打卡消息
     *
     * @param accessToken
     */
    public void sendUserPushMsg(String accessToken, String openId, String start, String end, int total, String keyWords, Integer pkid) {
        String url = PropertyUtil.getProperty("send_template_message");
        String redirectUrl = PropertyUtil.getProperty("redirect_url");
        redirectUrl = redirectUrl.replace("SUBSCRIBE_ID", pkid.toString());
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("touser", openId);
        sendMap.put("template_id", "w0m4dLwV4NLN6VxzO7Bun5duOVuX2IkFbqhMmMYd-5U");
        sendMap.put("url", redirectUrl);
        Map<String, Object> data = new HashMap<>();
        Map<String, String> firstMap = new HashMap<String, String>(1) {{
            put("value", "您所订阅的招标项目已更新!");
        }};
        data.put("first", firstMap);
        Map<String, String> keywordMap1 = new HashMap<String, String>(1) {{
            put("value", start + "至" + end);
        }};
        data.put("keyword1", keywordMap1);
        Map<String, String> keywordMap2 = new HashMap<String, String>(1) {{
            put("value", "招标公告");
        }};
        data.put("keyword2", keywordMap2);
        Map<String, String> keywordMap3 = new HashMap<String, String>(1) {{
            put("value", keyWords);
        }};
        data.put("keyword3", keywordMap3);
        Map<String, String> keywordMap4 = new HashMap<String, String>(1) {{
            put("value", "您所订阅的招标项目已更新" + total + "条！");
        }};
        data.put("keyword4", keywordMap4);
        sendMap.put("data", data);
        String requstUrl = url.replace("ACCESS_TOKEN", accessToken);
        logger.info("http请求参数:" + JSONObject.toJSONString(sendMap));
        HttpUtils.connectURL(requstUrl, JSONObject.toJSONString(sendMap), "POST");
    }

}
