package com.silita.notice.model;

import javax.persistence.*;

@Table(name = "colle_company_new")
public class ColleCompanyNew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公司Id
     */
    @Column(name = "companyId")
    private String companyid;

    /**
     * 公司名称
     */
    @Column(name = "companyName")
    private String companyname;

    /**
     * 收藏用户Id
     */
    @Column(name = "userId")
    private String userid;

    @Column(name = "tableName")
    private String tablename;

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
     * 获取公司Id
     *
     * @return companyId - 公司Id
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * 设置公司Id
     *
     * @param companyid 公司Id
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    /**
     * 获取公司名称
     *
     * @return companyName - 公司名称
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * 设置公司名称
     *
     * @param companyname 公司名称
     */
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    /**
     * 获取收藏用户Id
     *
     * @return userId - 收藏用户Id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置收藏用户Id
     *
     * @param userid 收藏用户Id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return tableName
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}