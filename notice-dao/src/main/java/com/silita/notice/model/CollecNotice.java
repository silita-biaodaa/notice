package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "collec_notice")
public class CollecNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userId")
    private String userid;

    private String type;

    @Column(name = "noticeId")
    private String noticeid;

    /**
     * 收藏公告的相关公告条数
     */
    @Column(name = "noticeRelationNum")
    private Integer noticerelationnum;

    private String title;

    @Column(name = "collecTime")
    private Date collectime;

    @Column(name = "readOrReaded")
    private Integer readorreaded;

    private String source;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return userId
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return noticeId
     */
    public String getNoticeid() {
        return noticeid;
    }

    /**
     * @param noticeid
     */
    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
    }

    /**
     * 获取收藏公告的相关公告条数
     *
     * @return noticeRelationNum - 收藏公告的相关公告条数
     */
    public Integer getNoticerelationnum() {
        return noticerelationnum;
    }

    /**
     * 设置收藏公告的相关公告条数
     *
     * @param noticerelationnum 收藏公告的相关公告条数
     */
    public void setNoticerelationnum(Integer noticerelationnum) {
        this.noticerelationnum = noticerelationnum;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return collecTime
     */
    public Date getCollectime() {
        return collectime;
    }

    /**
     * @param collectime
     */
    public void setCollectime(Date collectime) {
        this.collectime = collectime;
    }

    /**
     * @return readOrReaded
     */
    public Integer getReadorreaded() {
        return readorreaded;
    }

    /**
     * @param readorreaded
     */
    public void setReadorreaded(Integer readorreaded) {
        this.readorreaded = readorreaded;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }
}