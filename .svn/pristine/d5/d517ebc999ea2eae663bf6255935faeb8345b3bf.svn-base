package com.chero.client.domain;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="Report",description="报告")
public class Report implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "报告ID", required = false,hidden=true)
    private String id;	
	@ApiModelProperty(value = "报告名称", required = true)
    private String reportName;	
	@ApiModelProperty(value = "报告类型（0短程心电报告，1长程心电报告，2温度周报告，3温度月报告）", required = true)
    private Integer reportType;	
	@ApiModelProperty(value = "患者账号", required = true)
    private String patientAccount;	
	@ApiModelProperty(value = "姓名", required = true)
    private String name;	
	@ApiModelProperty(value = "性别（0女，1男）", required = true)
    private Integer gender;	
	@ApiModelProperty(value = "年龄", required = true)
    private Integer age;	
	@ApiModelProperty(value = "报告时间", required = true, dataType="java.lang.String")
    private Date  reportTime;
	@ApiModelProperty(value = "申请审核时间", required = true, dataType="java.lang.String")
    private Date  updateTime;
	@ApiModelProperty(value = "完成时间", required = true,hidden=true)
    private Date finishTime;	
	@ApiModelProperty(value = "解读及时性（0超时，1及时）", required = true,hidden=true)
    private Integer timeliness;
	@ApiModelProperty(value = "解读人", required = false,hidden=true)
    private String reader;	
	@ApiModelProperty(value = "医生关联id", required = true,hidden=true)
    private String userId;	
}
