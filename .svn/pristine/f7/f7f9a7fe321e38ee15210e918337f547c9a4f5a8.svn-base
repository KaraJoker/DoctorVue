package com.chero.client.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public class Message {
    private Integer id;		//主键id
	private String title;	//标题
    private String origin;	//来源
    private String content;		//内容
    private Integer type;		//类型（0通知，1公告）
    private Date sendTime;		//发送时间
    private Integer peopleNumber;	//是否已读（0已读，1未读）
    private String orgnazationId;	// 机构id
    private Boolean isSend;		//是否发布
    @CreatedBy
	private String createUser;	//创建人
	private String fromUserId;	//创建人id
	@CreatedDate
	private Date createTime;	//创建时间
	@LastModifiedBy
	private String updateUser;	//更新人
	@LastModifiedDate
	private Date updateTime;	//更新时间
	private String extend1;		//预留扩展字段1	
	private String extend2;		//预留扩展字段2
	private String extend3;		//预留扩展字段3
	private Integer deleteFlag = 0;	//删除标记
	private String remarks;		//备注
}
