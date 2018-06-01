package com.chero.client.domain;

import lombok.Data;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public class SystemRole {

    private String roleId;	//角色主键id
    private String role;	//角色
    private String status;	//状态（0禁用，1启用）
    private String privilegeManagement;	//权限列表
    @CreatedBy
    private String createUser;		//创建人
    @CreatedDate
    private Date createTime;		//创建时间
    @LastModifiedBy
    private String updateUser;		//更新人
    @LastModifiedDate
    private Date updateTime;		//更新时间
    private Integer deleteFlag;		//删除标记
    private String remarks;			//备注
}
