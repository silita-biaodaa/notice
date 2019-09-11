package com.silita.notice.service.com.silita.notice.task;

import com.silita.notice.dao.TbMessageMapper;
import com.silita.notice.dao.TbUserSubscribeMapper;
import com.silita.notice.service.impl.ElasticsearchService;
import com.silita.notice.utils.DateUtils;
import com.silita.notice.utils.ExecutorProcessPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    //    @Scheduled(cron = "")
    private void moringScheduld() {
        scheduld();
    }

    //    @Scheduled(cron = "")
    private void afternoonScheduld() {
        scheduld();
    }

    private void scheduld() {
        logger.info("定时任务执行开始,时间:" + DateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        int total = tbUserSubscribeMapper.queryUserSubscibe();
        if (total <= 0) {
            return;
        }
        ExecutorProcessPool pool = ExecutorProcessPool.getInstance();
        int threadCount = 10;
        int pageSize = this.getPage(total, threadCount);
        try {
            for (int i = 1; i <= threadCount; i++) {
                int page = (i - 1) * pageSize;
                pool.execute(new SendSubscriptionTask(page, pageSize, elasticsearchService, tbUserSubscribeMapper, tbMessageMapper, new Date(), new Date()));
            }
            pool.shutdown();
        } catch (Exception e) {
            logger.error("定时任务执行失败！", e);
            pool.shutdown();
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

}
