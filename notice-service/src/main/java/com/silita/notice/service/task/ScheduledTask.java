package com.silita.notice.service.task;

import com.alibaba.fastjson.JSONObject;
import com.silita.notice.dao.TbMessageMapper;
import com.silita.notice.dao.TbUserSubscribeMapper;
import com.silita.notice.service.impl.ElasticsearchService;
import com.silita.notice.utils.DateUtils;
import com.silita.notice.utils.ExecutorProcessPool;
import com.silita.notice.utils.HttpUtils;
import com.silita.notice.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhushuai on 2019/9/11.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTask {

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private ElasticsearchService elasticsearchService;
    @Autowired
    private TbUserSubscribeMapper tbUserSubscribeMapper;
    @Autowired
    private TbMessageMapper tbMessageMapper;

    //    @Scheduled(cron = "0 0/5 * * * ?")
    @Scheduled(cron = "0 0 9 * * ?")
    private void moringScheduld() {
        Date end = new Date();
        StringBuffer last = new StringBuffer(DateUtils.beforeDate(DateUtils.dateToStr(end, "yyyy-MM-dd"), 1));
        last.append(" 15:00:00");
        Date start = DateUtils.strToDate(last.toString(), "yyyy-MM-dd HH:mm:ss");
        scheduld(start, end);
    }

    //    @Scheduled(cron = "0 0 15 * * ?")
    private void afternoonScheduld() {
        Date end = new Date();
        StringBuffer today = new StringBuffer(DateUtils.dateToStr(end, "yyyy-MM-dd"));
        today.append(" 09:00:00");
        Date start = DateUtils.strToDate(today.toString(), "yyyy-MM-dd HH:mm:ss");
        scheduld(start, end);
    }

    private void scheduld(Date start, Date end) {
        logger.info("定时任务执行开始,时间:" + DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        int total = tbUserSubscribeMapper.queryUserSubscibe();
        if (total <= 0) {
            return;
        }
        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();
        int threadCount = 10;
        int pageSize = this.getPage(total, threadCount);
        String token = fetchAccessToken();
        try {
            for (int i = 1; i <= threadCount; i++) {
                int page = (i - 1) * pageSize;
                pool.execute(new SendSubscriptionTask(page, pageSize, elasticsearchService, tbUserSubscribeMapper, tbMessageMapper, start.getTime(), end.getTime(), token));
            }
        } catch (Exception e) {
            logger.error("定时任务执行失败！", e);
        }
    }

    private Integer getPage(Integer total, Integer pageSize) {
        Integer pages = 0;
        if (total % pageSize == 0) {
            pages = total / pageSize;
        } else {
            pages = (total / pageSize) + 1;
        }
        return pages;
    }

    private String fetchAccessToken() {
        String token = null;
        String appid = PropertyUtil.getProperty("app_id");
        String requestUrl = "http://wx.biaodaa.com/weixin/fetchAccessToken";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("appid", appid);
        String parameterJson = JSONObject.toJSONString(parameter);
        logger.info("==调用fetchAccessToken入参[" + parameterJson + "]");
        String result = HttpUtils.connectURL(requestUrl, parameterJson, "POST");
        logger.info("==调用fetchAccessToken返回[" + result + "]");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(result);
        Integer status = jsonObject.getInteger("status");
        if (status != null && status.intValue() == 1) {
            JSONObject accessToken = (JSONObject) jsonObject.get("accessToken");
            if (accessToken != null && accessToken.getString("accessToken") != null) {
                token = accessToken.getString("accessToken");
            }
        }
        return token;
    }
}
