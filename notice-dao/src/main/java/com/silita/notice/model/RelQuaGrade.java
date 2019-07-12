package com.silita.notice.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "rel_qua_grade")
public class RelQuaGrade {
    /**
     * 主键
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 资质编码
     */
    @Column(name = "qua_code")
    private String quaCode;

    /**
     * 等级编码
     */
    @Column(name = "grade_type")
    private String gradeType;

    /**
     * 资质等级类型
     */
    @Column(name = "biz_type")
    private String bizType;

    /**
     * 业务类型（0;全部；1：公告；2：企信）
     */
    @Column(name = "grade_code")
    private String gradeCode;

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
     * 获取等级编码
     *
     * @return grade_type - 等级编码
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * 设置等级编码
     *
     * @param gradeType 等级编码
     */
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    /**
     * 获取资质等级类型
     *
     * @return biz_type - 资质等级类型
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 设置资质等级类型
     *
     * @param bizType 资质等级类型
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 获取业务类型（0;全部；1：公告；2：企信）
     *
     * @return grade_code - 业务类型（0;全部；1：公告；2：企信）
     */
    public String getGradeCode() {
        return gradeCode;
    }

    /**
     * 设置业务类型（0;全部；1：公告；2：企信）
     *
     * @param gradeCode 业务类型（0;全部；1：公告；2：企信）
     */
    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }
}