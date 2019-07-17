package com.silita.notice.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Snatchurl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据库按年份分区用
     */
    @Id
    private Integer range;

    /**
     * 抓取的地址
     */
    private String url;

    /**
     * 当时所属的页码
     */
    @Column(name = "urlPage")
    private Integer urlpage;

    /**
     * 抓取时间
     */
    @Column(name = "snatchDateTime")
    private Date snatchdatetime;

    /**
     * 所属的抓取计划ID
     */
    @Column(name = "snatchPlanId")
    private Integer snatchplanid;

    /**
     * ??:0??????????????1?????2
     */
    private Integer type;

    /**
     * 类型:默认0;1补充公告,2答疑公告
     */
    @Column(name = "otherType")
    private Integer othertype;

    /**
     * 类型:0施工;1设计,2监理,3采购
     */
    @Column(name = "biddingType")
    private Integer biddingtype;

    /**
     * 同批次标志
     */
    private String uuid;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否被抓取:0没,1已抓
     */
    private Integer status;

    /**
     * 发布时间
     */
    @Column(name = "openDate")
    private Date opendate;

    @Column(name = "keysWords")
    private String keyswords;

    /**
     * 公告人工编辑0未1已
     */
    private Integer edit;

    /**
     * 标段
     */
    private String block;

    /**
     * 表名
     */
    @Column(name = "tableName")
    private String tablename;

    /**
     * 随机数
     */
    @Column(name = "randomNum")
    private Integer randomnum;

    /**
     * uuid
     */
    private String suuid;

    /**
     * 内容变更次数
     */
    @Column(name = "changeNum")
    private Integer changenum;

    /**
     * 逻辑删除：0显示，1不显示
     */
    @Column(name = "isShow")
    private Integer isshow;

    /**
     * 省code
     */
    private String province;

    /**
     * 市code
     */
    private String city;

    /**
     * 县code
     */
    private String county;

    /**
     * 公告所属地区等级
     */
    private Integer rank;

    /**
     * ridisId
     */
    @Column(name = "redisId")
    private Integer redisid;

    /**
     * 公告所有网站id
     */
    @Column(name = "websitePlanId")
    private Integer websiteplanid;

    /**
     * 项目类型  // 0：采购 ， 1：工程 
     */
    @Column(name = "businessType")
    private String businesstype;

    /**
     * 公告来源省份
     */
    private String source;

    /**
     * 按倒序排列：根据维度值情况
     */
    private Integer px;

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
     * 获取数据库按年份分区用
     *
     * @return range - 数据库按年份分区用
     */
    public Integer getRange() {
        return range;
    }

    /**
     * 设置数据库按年份分区用
     *
     * @param range 数据库按年份分区用
     */
    public void setRange(Integer range) {
        this.range = range;
    }

    /**
     * 获取抓取的地址
     *
     * @return url - 抓取的地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置抓取的地址
     *
     * @param url 抓取的地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取当时所属的页码
     *
     * @return urlPage - 当时所属的页码
     */
    public Integer getUrlpage() {
        return urlpage;
    }

    /**
     * 设置当时所属的页码
     *
     * @param urlpage 当时所属的页码
     */
    public void setUrlpage(Integer urlpage) {
        this.urlpage = urlpage;
    }

    /**
     * 获取抓取时间
     *
     * @return snatchDateTime - 抓取时间
     */
    public Date getSnatchdatetime() {
        return snatchdatetime;
    }

    /**
     * 设置抓取时间
     *
     * @param snatchdatetime 抓取时间
     */
    public void setSnatchdatetime(Date snatchdatetime) {
        this.snatchdatetime = snatchdatetime;
    }

    /**
     * 获取所属的抓取计划ID
     *
     * @return snatchPlanId - 所属的抓取计划ID
     */
    public Integer getSnatchplanid() {
        return snatchplanid;
    }

    /**
     * 设置所属的抓取计划ID
     *
     * @param snatchplanid 所属的抓取计划ID
     */
    public void setSnatchplanid(Integer snatchplanid) {
        this.snatchplanid = snatchplanid;
    }

    /**
     * 获取??:0??????????????1?????2
     *
     * @return type - ??:0??????????????1?????2
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置??:0??????????????1?????2
     *
     * @param type ??:0??????????????1?????2
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取类型:默认0;1补充公告,2答疑公告
     *
     * @return otherType - 类型:默认0;1补充公告,2答疑公告
     */
    public Integer getOthertype() {
        return othertype;
    }

    /**
     * 设置类型:默认0;1补充公告,2答疑公告
     *
     * @param othertype 类型:默认0;1补充公告,2答疑公告
     */
    public void setOthertype(Integer othertype) {
        this.othertype = othertype;
    }

    /**
     * 获取类型:0施工;1设计,2监理,3采购
     *
     * @return biddingType - 类型:0施工;1设计,2监理,3采购
     */
    public Integer getBiddingtype() {
        return biddingtype;
    }

    /**
     * 设置类型:0施工;1设计,2监理,3采购
     *
     * @param biddingtype 类型:0施工;1设计,2监理,3采购
     */
    public void setBiddingtype(Integer biddingtype) {
        this.biddingtype = biddingtype;
    }

    /**
     * 获取同批次标志
     *
     * @return uuid - 同批次标志
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置同批次标志
     *
     * @param uuid 同批次标志
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取是否被抓取:0没,1已抓
     *
     * @return status - 是否被抓取:0没,1已抓
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否被抓取:0没,1已抓
     *
     * @param status 是否被抓取:0没,1已抓
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取发布时间
     *
     * @return openDate - 发布时间
     */
    public Date getOpendate() {
        return opendate;
    }

    /**
     * 设置发布时间
     *
     * @param opendate 发布时间
     */
    public void setOpendate(Date opendate) {
        this.opendate = opendate;
    }

    /**
     * @return keysWords
     */
    public String getKeyswords() {
        return keyswords;
    }

    /**
     * @param keyswords
     */
    public void setKeyswords(String keyswords) {
        this.keyswords = keyswords;
    }

    /**
     * 获取公告人工编辑0未1已
     *
     * @return edit - 公告人工编辑0未1已
     */
    public Integer getEdit() {
        return edit;
    }

    /**
     * 设置公告人工编辑0未1已
     *
     * @param edit 公告人工编辑0未1已
     */
    public void setEdit(Integer edit) {
        this.edit = edit;
    }

    /**
     * 获取标段
     *
     * @return block - 标段
     */
    public String getBlock() {
        return block;
    }

    /**
     * 设置标段
     *
     * @param block 标段
     */
    public void setBlock(String block) {
        this.block = block;
    }

    /**
     * 获取表名
     *
     * @return tableName - 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 设置表名
     *
     * @param tablename 表名
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * 获取随机数
     *
     * @return randomNum - 随机数
     */
    public Integer getRandomnum() {
        return randomnum;
    }

    /**
     * 设置随机数
     *
     * @param randomnum 随机数
     */
    public void setRandomnum(Integer randomnum) {
        this.randomnum = randomnum;
    }

    /**
     * 获取uuid
     *
     * @return suuid - uuid
     */
    public String getSuuid() {
        return suuid;
    }

    /**
     * 设置uuid
     *
     * @param suuid uuid
     */
    public void setSuuid(String suuid) {
        this.suuid = suuid;
    }

    /**
     * 获取内容变更次数
     *
     * @return changeNum - 内容变更次数
     */
    public Integer getChangenum() {
        return changenum;
    }

    /**
     * 设置内容变更次数
     *
     * @param changenum 内容变更次数
     */
    public void setChangenum(Integer changenum) {
        this.changenum = changenum;
    }

    /**
     * 获取逻辑删除：0显示，1不显示
     *
     * @return isShow - 逻辑删除：0显示，1不显示
     */
    public Integer getIsshow() {
        return isshow;
    }

    /**
     * 设置逻辑删除：0显示，1不显示
     *
     * @param isshow 逻辑删除：0显示，1不显示
     */
    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    /**
     * 获取省code
     *
     * @return province - 省code
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省code
     *
     * @param province 省code
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市code
     *
     * @return city - 市code
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市code
     *
     * @param city 市code
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取县code
     *
     * @return county - 县code
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置县code
     *
     * @param county 县code
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * 获取公告所属地区等级
     *
     * @return rank - 公告所属地区等级
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 设置公告所属地区等级
     *
     * @param rank 公告所属地区等级
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 获取ridisId
     *
     * @return redisId - ridisId
     */
    public Integer getRedisid() {
        return redisid;
    }

    /**
     * 设置ridisId
     *
     * @param redisid ridisId
     */
    public void setRedisid(Integer redisid) {
        this.redisid = redisid;
    }

    /**
     * 获取公告所有网站id
     *
     * @return websitePlanId - 公告所有网站id
     */
    public Integer getWebsiteplanid() {
        return websiteplanid;
    }

    /**
     * 设置公告所有网站id
     *
     * @param websiteplanid 公告所有网站id
     */
    public void setWebsiteplanid(Integer websiteplanid) {
        this.websiteplanid = websiteplanid;
    }

    /**
     * 获取项目类型  // 0：采购 ， 1：工程 
     *
     * @return businessType - 项目类型  // 0：采购 ， 1：工程 
     */
    public String getBusinesstype() {
        return businesstype;
    }

    /**
     * 设置项目类型  // 0：采购 ， 1：工程 
     *
     * @param businesstype 项目类型  // 0：采购 ， 1：工程 
     */
    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    /**
     * 获取公告来源省份
     *
     * @return source - 公告来源省份
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置公告来源省份
     *
     * @param source 公告来源省份
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取按倒序排列：根据维度值情况
     *
     * @return px - 按倒序排列：根据维度值情况
     */
    public Integer getPx() {
        return px;
    }

    /**
     * 设置按倒序排列：根据维度值情况
     *
     * @param px 按倒序排列：根据维度值情况
     */
    public void setPx(Integer px) {
        this.px = px;
    }
}