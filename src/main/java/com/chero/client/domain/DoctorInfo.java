package com.chero.client.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public class DoctorInfo {

	@Id
	private String id;			//主键id
	@CreatedBy
	private String createUser;	//创建人
	@CreatedDate
	private Date createTime;	//创建时间
	@LastModifiedBy
	private String updateUser;	//更新人
	@LastModifiedDate
	private Date updateTime;	//更新时间
	private String extend1;		//预留扩展字段1	
	private String extend2;		//预留扩展字段2
	private String extend3;		//预留扩展字段3
	private Integer deleteFlag = 0;	//删除标记(0使用，1删除，2彻底删除)
	private String remarks;		//备注
	private String userface;	//头像
	private String realName;	//真实姓名
	private String mobile;		//手机号
	private Integer gender;		//性别
	private Date birthday;	//出生日期（yyyy-MM-dd）
	private String subject;		//科室（所在部门）
	private List<String> departmentId;//所在部门id
	private String position;	//岗位
	private Boolean status;		//状态（0禁用，1启用）
	private Integer certificateType;	//证件类型
	private String certificateNumber;	//证件号码
	private String idCertificatePhoto;	//证件照片
	private String registrationCertificatePhoto;	//医师执业注册证
	private String titleCertificatePhoto;			//医生职称证
	private String certificateStatus;				//认证状态
	private String userId;			//用户关联uuid
	private Integer isRegister;		//是否注册（0未注册，1已注册）
	private Integer isTemporary;	//是否临时（0临时，1正式
}
