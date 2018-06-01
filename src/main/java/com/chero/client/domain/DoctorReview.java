package com.chero.client.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
public class DoctorReview {
    private String id;
    private String userId;//申请人id
//    private String userAccount;//申请人账号
    private String userRealName;//申请人真实姓名
    private String departmentId;//部门id
    private String subject;//科室（所在部门）
    private String position;//职位
    private Integer certificateType;//证件类型
    private String certificateNumber;//证件号
    private String idCertificatePhoto;//证件照片地址
    private String registrationCertificatePhoto;//医师执业注册证
    private String titleCertificatePhoto;//医生职称证
    private String reviewId;//评审人id
    private String reviewName;//审核人姓名
    private Integer reviewState;//评审状态：0未评审，1评审成功，2评审失败,3正在填写
    private String reviewRemark;//评审反馈
    private String createUser;//创建人
    @CreatedDate
    private Date createTime;//创建时间
    private String updateUser;//更新者
    @LastModifiedDate
    private Date updateTime;//更新时间
    private String extend1;//扩展 1
    private String extend2;//扩展 2
    private String extend3;//扩展 3
    private String deleteFlag = "0";//删除标记
    private String remarks;//备注
    /**
     * 其他表字段
     */
//    private String idCard;//证件号
    private String mobile;


}
