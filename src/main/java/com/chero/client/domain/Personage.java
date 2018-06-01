package com.chero.client.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Personage {

	@Id
	private Integer id;
	private String create_user;
	private Date create_time;
	private String update_user;
	private Date update_time;
	private String extend_1;
	private String extend_2;
	private String extend_3;
	private Integer delete_flag;
	private String remarks;
	private String userface;
	private String name;
	private String realName;
	private String mobile;
	private String gender;
	private Date birthday;
	private String idCard;
	private String subject;
	private String position;
	private String status;
	private String option;
	private String certificate_type;
	private String certificate_number;
	private String id_certificate_photo;
	private String registration_certificate_photo;
	private String title_certificate_photo;
	private String certificate_status;
	private Integer hospital_id;
	private String userId;
	private Integer isRegister;
	private Integer isTemporary;
}
