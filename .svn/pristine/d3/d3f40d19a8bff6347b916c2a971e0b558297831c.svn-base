package com.chero.client.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by hxh on 2018/5/31.
 */
@Data
public class UserMessage {
    /**
     * 发送人
     */
    private String fromUserId;
    /**
     * 发送人姓名
     */
    private String fromRealName;
    /**
     * 接收人
     */
    private String toUserId;
    /**
     * 消息Id
     */
    @JsonProperty("id")  // 和之前统一
    private Integer msgId;
    /**
     * 消息类型（0通知，1公告）
     */
    @JsonProperty("type")   // 和之前统一
    private Integer msgType;
    /**
     * 机构Id
     */
    private String orgnazationId;
    /**
     * 是否已读
     */
    private Boolean isReading;
    private String title;    //标题
    private String origin;    //来源
    private String content;        //内容
    private String createUser;    //创建人
    private Date createTime;    //创建时间
    private String updateUser;    //更新人
    private Date updateTime;    //更新时间
    private Integer deleteFlag;    //删除标记
}