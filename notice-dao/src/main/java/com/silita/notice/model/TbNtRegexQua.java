package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_nt_regex_qua")
public class TbNtRegexQua {
    /**
     * pkid
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pkid;

    /**
     * 公告编辑信息ID
     */
    @Column(name = "nt_edit_id")
    private String ntEditId;

    /**
     * 公告id
     */
    @Column(name = "nt_id")
    private String ntId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 资质关系表达式(程序算法生成）
     */
    @Column(name = "qua_regex")
    private String quaRegex;

    /**
     * 获取pkid
     *
     * @return pkid - pkid
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * 设置pkid
     *
     * @param pkid pkid
     */
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取公告编辑信息ID
     *
     * @return nt_edit_id - 公告编辑信息ID
     */
    public String getNtEditId() {
        return ntEditId;
    }

    /**
     * 设置公告编辑信息ID
     *
     * @param ntEditId 公告编辑信息ID
     */
    public void setNtEditId(String ntEditId) {
        this.ntEditId = ntEditId;
    }

    /**
     * 获取公告id
     *
     * @return nt_id - 公告id
     */
    public String getNtId() {
        return ntId;
    }

    /**
     * 设置公告id
     *
     * @param ntId 公告id
     */
    public void setNtId(String ntId) {
        this.ntId = ntId;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取更新时间
     *
     * @return updated - 更新时间
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * 设置更新时间
     *
     * @param updated 更新时间
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * 获取更新人
     *
     * @return update_by - 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取资质关系表达式(程序算法生成）
     *
     * @return qua_regex - 资质关系表达式(程序算法生成）
     */
    public String getQuaRegex() {
        return quaRegex;
    }

    /**
     * 设置资质关系表达式(程序算法生成）
     *
     * @param quaRegex 资质关系表达式(程序算法生成）
     */
    public void setQuaRegex(String quaRegex) {
        this.quaRegex = quaRegex;
    }
}