package com.silita.notice.dao;

import com.silita.notice.model.TbMessage;

/**
 * tb_message Mapper
 */
public interface TbMessageMapper {

    /**
     * 添加消息
     *
     * @return
     */
    int insert(TbMessage message);
}