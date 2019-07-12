package com.silita.notice.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "dic_qua")
public class DicQua {
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 父节点id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 资质名称（标准名）
     */
    @Column(name = "qua_name")
    private String quaName;

    /**
     * 资质等级对应的词典类型
     */
    @Column(name = "grade_type")
    private String gradeType;

    /**
     * 资质编码
     */
    @Column(name = "qua_code")
    private String quaCode;

    /**
     * 排序编号
     */
    @Column(name = "order_no")
    private Short orderNo;

    /**
     * 资质层级
     */
    private String level;

    /**
     * 业务类型（0:全部；1:公告；2:企信）
     */
    @Column(name = "biz_type")
    private String bizType;

    /**
     * 过期时间
     */
    private String expired;

    /**
     * 是否叶子
     */
    @Column(name = "is_leaf")
    private Boolean isLeaf;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "bench_name")
    private String benchName;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取父节点id
     *
     * @return parent_id - 父节点id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父节点id
     *
     * @param parentId 父节点id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取资质名称（标准名）
     *
     * @return qua_name - 资质名称（标准名）
     */
    public String getQuaName() {
        return quaName;
    }

    /**
     * 设置资质名称（标准名）
     *
     * @param quaName 资质名称（标准名）
     */
    public void setQuaName(String quaName) {
        this.quaName = quaName;
    }

    /**
     * 获取资质等级对应的词典类型
     *
     * @return grade_type - 资质等级对应的词典类型
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * 设置资质等级对应的词典类型
     *
     * @param gradeType 资质等级对应的词典类型
     */
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    /**
     * 获取资质编码
     *
     * @return qua_code - 资质编码
     */
    public String getQuaCode() {
        return quaCode;
    }

    /**
     * 设置资质编码
     *
     * @param quaCode 资质编码
     */
    public void setQuaCode(String quaCode) {
        this.quaCode = quaCode;
    }

    /**
     * 获取排序编号
     *
     * @return order_no - 排序编号
     */
    public Short getOrderNo() {
        return orderNo;
    }

    /**
     * 设置排序编号
     *
     * @param orderNo 排序编号
     */
    public void setOrderNo(Short orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取资质层级
     *
     * @return level - 资质层级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置资质层级
     *
     * @param level 资质层级
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取业务类型（0:全部；1:公告；2:企信）
     *
     * @return biz_type - 业务类型（0:全部；1:公告；2:企信）
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 设置业务类型（0:全部；1:公告；2:企信）
     *
     * @param bizType 业务类型（0:全部；1:公告；2:企信）
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 获取过期时间
     *
     * @return expired - 过期时间
     */
    public String getExpired() {
        return expired;
    }

    /**
     * 设置过期时间
     *
     * @param expired 过期时间
     */
    public void setExpired(String expired) {
        this.expired = expired;
    }

    /**
     * 获取是否叶子
     *
     * @return is_leaf - 是否叶子
     */
    public Boolean getIsLeaf() {
        return isLeaf;
    }

    /**
     * 设置是否叶子
     *
     * @param isLeaf 是否叶子
     */
    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 获取备用字段
     *
     * @return remark - 备用字段
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备用字段
     *
     * @param remark 备用字段
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取描述
     *
     * @return desc - 描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置描述
     *
     * @param desc 描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * @return bench_name
     */
    public String getBenchName() {
        return benchName;
    }

    /**
     * @param benchName
     */
    public void setBenchName(String benchName) {
        this.benchName = benchName;
    }
}