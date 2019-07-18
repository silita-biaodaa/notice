package com.silita.notice.dao;


import com.silita.notice.model.ColleCompanyNew;
import com.silita.notice.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ColleCompanyNewMapper extends MyMapper<ColleCompanyNew> {
    /**
     * 获取用户是否关注该公司
     * @param param
     * @return
     */
    List<String> queryYesOrNo(Map<String,Object> param);
}