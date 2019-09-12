package com.silita.notice.service.com.silita.notice.task;

import com.alibaba.fastjson.JSONObject;
import com.silita.notice.dao.TbMessageMapper;
import com.silita.notice.dao.TbUserSubscribeMapper;
import com.silita.notice.model.TbMessage;
import com.silita.notice.service.impl.ElasticsearchService;
import com.silita.notice.utils.DateUtils;
import com.silita.notice.utils.PushMessageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2019/9/10.
 */
public class SendSubscriptionTask implements Runnable {

    private Integer start;
    private Integer pageSize;
    private ElasticsearchService elasticsearchService;
    private TbUserSubscribeMapper tbUserSubscribeMapper;
    private TbMessageMapper tbMessageMapper;
    private long startTime;
    private long endTime;

    public SendSubscriptionTask(Integer start, Integer pageSize, ElasticsearchService elasticsearchService, TbUserSubscribeMapper tbUserSubscribeMapper, TbMessageMapper tbMessageMapper, long startTime, long endTime) {
        this.start = start;
        this.pageSize = pageSize;
        this.elasticsearchService = elasticsearchService;
        this.tbUserSubscribeMapper = tbUserSubscribeMapper;
        this.tbMessageMapper = tbMessageMapper;
        this.startTime = startTime;
        this.endTime = endTime;
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
        //判断是否关注公告号
//        if (false) {
//
//        } else {
        StringBuffer content = new StringBuffer("您所订阅的招标项目已更新" + total + "条，点击查看详情！");
        StringBuffer title = new StringBuffer("招标订阅项目更新通知！");
        //推送APP消息及系统消息
        PushMessageUtils.pushMessage(userId, new HashedMap(1) {{
            put("pkid", pkid);
        }}, title.toString(), content.toString(), "subscribe");
        //发送系统消息
        saveSystemMsg(param, userId, pkid, total, title.toString());
//        }
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
        StringBuffer content = new StringBuffer("");
        if (StringUtils.isNotEmpty(MapUtils.getString(param, "qualName"))) {
            content = new StringBuffer("业务所在地：" + MapUtils.getString(param, "regionName") + "\n")
                    .append("订阅关键词：" + MapUtils.getString(param, "keywords") + "\n")
                    .append("资质：" + MapUtils.getString(param, "qualName") + "\n")
                    .append("更新时间：" + DateUtils.longToStr(startTime, "yyyy-MM-dd HH:mm:ss") + "-" + DateUtils.longToStr(endTime, "yyyy-MM-dd HH:mm:ss") + "\n")
                    .append("您所订阅的招标项目已更新" + total + "条，点击查看详情！");
        } else {
            content = new StringBuffer("业务所在地：" + MapUtils.getString(param, "regionName") + "\n")
                    .append("订阅关键词：" + MapUtils.getString(param, "keywords") + "\n")
                    .append("更新时间：" + DateUtils.longToStr(startTime, "yyyy-MM-dd HH:mm:ss") + "-" + DateUtils.longToStr(endTime, "yyyy-MM-dd HH:mm:ss") + "\n")
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

}
