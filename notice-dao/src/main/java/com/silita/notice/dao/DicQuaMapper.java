package com.silita.notice.dao;

import com.silita.notice.dao.DicQuaMapper;
import com.silita.notice.model.DicQua;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface DicQuaMapper extends MyMapper<DicQua> {

    /**
     * 查询一级资质
     */
    List<Map<String,Object>> queryQua1(Map<String,Object> param);

    /**
     * 查询子级资质
     * @param param
     * @return
     */
    List<Map<String,Object>> queryQua2(Map<String,Object> param);


    /**
     * 获取一级资质
     */

     List<Map<String,Object>> queryQuaOne(Map<String,Object> param);

    /**
     * 获取二级资质
     */
    List<Map<String,Object>> queryQuaTwo(Map<String,Object> param);

    /**
     * 获取三级资质
     */

    List<Map<String,Object>> queryQuaThree(Map<String,Object> param);

    /**
     * 获取四级资质
     */
    List<Map<String,Object>> queryQuaFour(Map<String,Object> param);

}