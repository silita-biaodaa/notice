package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "dic_common")
public class DicCommon {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 父结点id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 词典类型
     */
    private String type;

    /**
     * 排序编号
     */
    @Column(name = "order_no")
    private Short orderNo;

    /**
     * 是否叶子
     */
    @Column(name = "is_leaf")
    private Boolean isLeaf;

    /**
     * 详细描述
     */
    private String desc;

    /**
     * 备用字段
     */
    private String remark;

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
     * 获取父结点id
     *
     * @return parent_id - 父结点id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父结点id
     *
     * @param parentId 父结点id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取词典类型
     *
     * @return type - 词典类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置词典类型
     *
     * @param type 词典类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取详细描述
     *
     * @return desc - 详细描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置详细描述
     *
     * @param desc 详细描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
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
}