package com.silita.notice.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_company")
public class TbCompany {
    /**
     * 企业id
     */
    @Id
    @Column(name = "com_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String comId;

    /**
     * 本条数据的MD5值
     */
    private String md5;

    /**
     * 企业名称首字母
     */
    @Column(name = "com_name_py")
    private String comNamePy;

    /**
     * 企业名称
     */
    @Column(name = "com_name")
    private String comName;

    /**
     * 统一社会信用代码
     */
    @Column(name = "credit_code")
    private String creditCode;

    /**
     * 组织机构代码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 工商营业执照
     */
    @Column(name = "business_num")
    private String businessNum;

    /**
     * 注册地址
     */
    @Column(name = "regis_address")
    private String regisAddress;

    /**
     * 企业营业地址
     */
    @Column(name = "com_address")
    private String comAddress;

    /**
     * 法人
     */
    @Column(name = "legal_person")
    private String legalPerson;

    /**
     * 经济类型
     */
    @Column(name = "economic_type")
    private String economicType;

    /**
     * 注册资本
     */
    @Column(name = "regis_capital")
    private String regisCapital;

    /**
     * 技术负责人
     */
    @Column(name = "skill_leader")
    private String skillLeader;

    /**
     * url
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 排序
     */
    private Integer px;

    /**
     * 市
     */
    private String city;

    /**
     * 资质范围
     */
    private String range;

    /**
     * 公司logo
     */
    private String logo;

    /**
     * 获取企业id
     *
     * @return com_id - 企业id
     */
    public String getComId() {
        return comId;
    }

    /**
     * 设置企业id
     *
     * @param comId 企业id
     */
    public void setComId(String comId) {
        this.comId = comId;
    }

    /**
     * 获取本条数据的MD5值
     *
     * @return md5 - 本条数据的MD5值
     */
    public String getMd5() {
        return md5;
    }

    /**
     * 设置本条数据的MD5值
     *
     * @param md5 本条数据的MD5值
     */
    public void setMd5(String md5) {
        this.md5 = md5;
    }

    /**
     * 获取企业名称首字母
     *
     * @return com_name_py - 企业名称首字母
     */
    public String getComNamePy() {
        return comNamePy;
    }

    /**
     * 设置企业名称首字母
     *
     * @param comNamePy 企业名称首字母
     */
    public void setComNamePy(String comNamePy) {
        this.comNamePy = comNamePy;
    }

    /**
     * 获取企业名称
     *
     * @return com_name - 企业名称
     */
    public String getComName() {
        return comName;
    }

    /**
     * 设置企业名称
     *
     * @param comName 企业名称
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

    /**
     * 获取统一社会信用代码
     *
     * @return credit_code - 统一社会信用代码
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * 设置统一社会信用代码
     *
     * @param creditCode 统一社会信用代码
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    /**
     * 获取组织机构代码
     *
     * @return org_code - 组织机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置组织机构代码
     *
     * @param orgCode 组织机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取工商营业执照
     *
     * @return business_num - 工商营业执照
     */
    public String getBusinessNum() {
        return businessNum;
    }

    /**
     * 设置工商营业执照
     *
     * @param businessNum 工商营业执照
     */
    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    /**
     * 获取注册地址
     *
     * @return regis_address - 注册地址
     */
    public String getRegisAddress() {
        return regisAddress;
    }

    /**
     * 设置注册地址
     *
     * @param regisAddress 注册地址
     */
    public void setRegisAddress(String regisAddress) {
        this.regisAddress = regisAddress;
    }

    /**
     * 获取企业营业地址
     *
     * @return com_address - 企业营业地址
     */
    public String getComAddress() {
        return comAddress;
    }

    /**
     * 设置企业营业地址
     *
     * @param comAddress 企业营业地址
     */
    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }

    /**
     * 获取法人
     *
     * @return legal_person - 法人
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置法人
     *
     * @param legalPerson 法人
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * 获取经济类型
     *
     * @return economic_type - 经济类型
     */
    public String getEconomicType() {
        return economicType;
    }

    /**
     * 设置经济类型
     *
     * @param economicType 经济类型
     */
    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    /**
     * 获取注册资本
     *
     * @return regis_capital - 注册资本
     */
    public String getRegisCapital() {
        return regisCapital;
    }

    /**
     * 设置注册资本
     *
     * @param regisCapital 注册资本
     */
    public void setRegisCapital(String regisCapital) {
        this.regisCapital = regisCapital;
    }

    /**
     * 获取技术负责人
     *
     * @return skill_leader - 技术负责人
     */
    public String getSkillLeader() {
        return skillLeader;
    }

    /**
     * 设置技术负责人
     *
     * @param skillLeader 技术负责人
     */
    public void setSkillLeader(String skillLeader) {
        this.skillLeader = skillLeader;
    }

    /**
     * 获取url
     *
     * @return url - url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置url
     *
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * 获取排序
     *
     * @return px - 排序
     */
    public Integer getPx() {
        return px;
    }

    /**
     * 设置排序
     *
     * @param px 排序
     */
    public void setPx(Integer px) {
        this.px = px;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取资质范围
     *
     * @return range - 资质范围
     */
    public String getRange() {
        return range;
    }

    /**
     * 设置资质范围
     *
     * @param range 资质范围
     */
    public void setRange(String range) {
        this.range = range;
    }

    /**
     * 获取公司logo
     *
     * @return logo - 公司logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置公司logo
     *
     * @param logo 公司logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
}