package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_area")
public class SysArea {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String pkid;

    /**
     * 地区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 地区别名（简称）
     */
    @Column(name = "area_short_name")
    private String areaShortName;

    /**
     * 地区编码（可用拼音简写）
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     */
    @Column(name = "area_level")
    private Integer areaLevel;

    /**
     * 地区父id
     */
    @Column(name = "area_parent_id")
    private String areaParentId;

    /**
     * 电话区号
     */
    @Column(name = "phone_code")
    private String phoneCode;

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
     * 获取主键
     *
     * @return pkid - 主键
     */
    public String getPkid() {
        return pkid;
    }

    /**
     * 设置主键
     *
     * @param pkid 主键
     */
    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取地区名称
     *
     * @return area_name - 地区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置地区名称
     *
     * @param areaName 地区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取地区别名（简称）
     *
     * @return area_short_name - 地区别名（简称）
     */
    public String getAreaShortName() {
        return areaShortName;
    }

    /**
     * 设置地区别名（简称）
     *
     * @param areaShortName 地区别名（简称）
     */
    public void setAreaShortName(String areaShortName) {
        this.areaShortName = areaShortName;
    }

    /**
     * 获取地区编码（可用拼音简写）
     *
     * @return area_code - 地区编码（可用拼音简写）
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置地区编码（可用拼音简写）
     *
     * @param areaCode 地区编码（可用拼音简写）
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     *
     * @return area_level - 地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     */
    public Integer getAreaLevel() {
        return areaLevel;
    }

    /**
     * 设置地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     *
     * @param areaLevel 地区级别(1：省、自治区、直辖市；2：地级市、地区、自治州、盟；3-市辖区、县级市、县）
     */
    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    /**
     * 获取地区父id
     *
     * @return area_parent_id - 地区父id
     */
    public String getAreaParentId() {
        return areaParentId;
    }

    /**
     * 设置地区父id
     *
     * @param areaParentId 地区父id
     */
    public void setAreaParentId(String areaParentId) {
        this.areaParentId = areaParentId;
    }

    /**
     * 获取电话区号
     *
     * @return phone_code - 电话区号
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * 设置电话区号
     *
     * @param phoneCode 电话区号
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
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
}