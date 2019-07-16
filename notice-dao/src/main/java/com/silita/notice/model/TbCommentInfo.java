package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_comment_info")
public class TbCommentInfo {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkid;

    /**
     * 评论者昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 公司
     */
    @Column(name = "in_company")
    private String inCompany;

    /**
     * 职位
     */
    private String post;

    /**
     * 头像地址
     */
    private String image;

    /**
     * 评论者id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 关联id（中标，招标，公司id）
     */
    @Column(name = "related_id")
    private String relatedId;

    /**
     * 关联类型(zhongbiao,zhaobiao,company)
     */
    @Column(name = "related_type")
    private String relatedType;

    /**
     * 是否发布(1:是,0:否)
     */
    @Column(name = "is_pub")
    private Integer isPub;

    /**
     * 审核状态（0：审核中；1：审核通过；2：审核未通过；3：已屏蔽）
     */
    private Integer state;

    /**
     * 发布时间
     */
    private Date updated;

    /**
     * 公告来源
     */
    private String source;

    /**
     * 评论内容
     */
    @Column(name = "comm_content")
    private String commContent;

    /**
     * 获取主键id
     *
     * @return pkid - 主键id
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * 设置主键id
     *
     * @param pkid 主键id
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取评论者昵称
     *
     * @return nick_name - 评论者昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置评论者昵称
     *
     * @param nickName 评论者昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取公司
     *
     * @return in_company - 公司
     */
    public String getInCompany() {
        return inCompany;
    }

    /**
     * 设置公司
     *
     * @param inCompany 公司
     */
    public void setInCompany(String inCompany) {
        this.inCompany = inCompany;
    }

    /**
     * 获取职位
     *
     * @return post - 职位
     */
    public String getPost() {
        return post;
    }

    /**
     * 设置职位
     *
     * @param post 职位
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * 获取头像地址
     *
     * @return image - 头像地址
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置头像地址
     *
     * @param image 头像地址
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 获取评论者id
     *
     * @return user_id - 评论者id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置评论者id
     *
     * @param userId 评论者id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取关联id（中标，招标，公司id）
     *
     * @return related_id - 关联id（中标，招标，公司id）
     */
    public String getRelatedId() {
        return relatedId;
    }

    /**
     * 设置关联id（中标，招标，公司id）
     *
     * @param relatedId 关联id（中标，招标，公司id）
     */
    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    /**
     * 获取关联类型(zhongbiao,zhaobiao,company)
     *
     * @return related_type - 关联类型(zhongbiao,zhaobiao,company)
     */
    public String getRelatedType() {
        return relatedType;
    }

    /**
     * 设置关联类型(zhongbiao,zhaobiao,company)
     *
     * @param relatedType 关联类型(zhongbiao,zhaobiao,company)
     */
    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    /**
     * 获取是否发布(1:是,0:否)
     *
     * @return is_pub - 是否发布(1:是,0:否)
     */
    public Integer getIsPub() {
        return isPub;
    }

    /**
     * 设置是否发布(1:是,0:否)
     *
     * @param isPub 是否发布(1:是,0:否)
     */
    public void setIsPub(Integer isPub) {
        this.isPub = isPub;
    }

    /**
     * 获取审核状态（0：审核中；1：审核通过；2：审核未通过；3：已屏蔽）
     *
     * @return state - 审核状态（0：审核中；1：审核通过；2：审核未通过；3：已屏蔽）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置审核状态（0：审核中；1：审核通过；2：审核未通过；3：已屏蔽）
     *
     * @param state 审核状态（0：审核中；1：审核通过；2：审核未通过；3：已屏蔽）
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取发布时间
     *
     * @return updated - 发布时间
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * 设置发布时间
     *
     * @param updated 发布时间
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * 获取公告来源
     *
     * @return source - 公告来源
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置公告来源
     *
     * @param source 公告来源
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取评论内容
     *
     * @return comm_content - 评论内容
     */
    public String getCommContent() {
        return commContent;
    }

    /**
     * 设置评论内容
     *
     * @param commContent 评论内容
     */
    public void setCommContent(String commContent) {
        this.commContent = commContent;
    }
}