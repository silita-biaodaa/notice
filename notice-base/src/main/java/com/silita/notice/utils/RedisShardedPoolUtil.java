package com.silita.notice.utils;

import com.silita.notice.common.ObjectTranscoder;
import com.silita.notice.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * Sharded Redis工具类
 * Created by ckl on 2018/8/31.
 */
@Slf4j
public class RedisShardedPoolUtil {

    /**
     * 获取
     * @param key
     * @return
     */
    public static Object get(String key){
        ShardedJedis jedis = null;
        Object result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            byte[] in = jedis.get(key.getBytes());
             result = ObjectTranscoder.deserialize(in);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static Boolean keyExist(String key){
        ShardedJedis jedis = null;
        Object result = null;
        try {
            jedis = RedisShardedPool.getJedis();
           return jedis.exists(key.getBytes());
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            RedisShardedPool.returnBrokenResource(jedis);
            return false;
        }finally {
            RedisShardedPool.returnResource(jedis);
        }
    }
}
