package com.chero.client.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PersonOpetion {

    private String doctorinfoId;
    private String realName;
    private String mobile;
    private String departmentId;
    private String parentId;
    private String option="false";
    private String departmentName;
}
