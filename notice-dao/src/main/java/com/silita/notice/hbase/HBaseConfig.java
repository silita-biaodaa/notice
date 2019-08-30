package com.silita.notice.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hbase配置类
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2019-02-26 14:57
 */

@Configuration
public class HBaseConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${hbase.zookeeper.quorum}")
    private String hbaseZookeeperQuorum;
    @Value("${hbase.zookeeper.property.clientPort}")
    private String hbaseZookeeperclientPort;
    @Value("${hbase.master}")
    private String hbaseMaster;
    @Value("${hbase.rootdir}")
    private String hbaseRootdir;

    @Bean
    public Connection hBaseConnection() {
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(configuration());
            logger.info("#####连接hbase成功#####");
        } catch (Exception e) {
            logger.error("连接hbase失败!!!", e);
        }
        return connection;
    }

    @Bean
    public org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
        configuration.set("hbase.zookeeper.property.clientPort", hbaseZookeeperclientPort);
        configuration.set("hbase.master", hbaseMaster);
        configuration.set("hbase.rootdir", hbaseRootdir);
        return configuration;
    }
}
