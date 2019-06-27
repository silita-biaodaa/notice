package com.silita.notice.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesKeyValue {

    @Value("${redis.max.total}")
    private Integer maxTotal; //最大连接数
    @Value("${redis.max.idle}")
    private Integer maxIdle;//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    @Value("${redis.min.idle}")
    private Integer minIdle;//在jedispool中最小的idle状态(空闲的)的jedis实例的个数
    @Value("${redis.test.borrow}")
    private Boolean testOnBorrow;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    @Value("${redis.test.return}")
    private Boolean testOnReturn;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    @Value("${redis1.ip}")
    private String redis1Ip;
    @Value("${redis1.port}")
    private Integer redis1Port;
    @Value("${spring.redis1.password}")
    private String password1;
    @Value("${redis2.ip}")
    private String redis2Ip;
    @Value("${redis2.port}")
    private Integer redis2Port;
    @Value("${spring.redis2.password}")
    private String password2;


    public  Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public String getRedis1Ip() {
        return redis1Ip;
    }

    public void setRedis1Ip(String redis1Ip) {
        this.redis1Ip = redis1Ip;
    }

    public Integer getRedis1Port() {
        return redis1Port;
    }

    public void setRedis1Port(Integer redis1Port) {
        this.redis1Port = redis1Port;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getRedis2Ip() {
        return redis2Ip;
    }

    public void setRedis2Ip(String redis2Ip) {
        this.redis2Ip = redis2Ip;
    }

    public Integer getRedis2Port() {
        return redis2Port;
    }

    public void setRedis2Port(Integer redis2Port) {
        this.redis2Port = redis2Port;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }


    public static void main(String[] args) {

    }
}
