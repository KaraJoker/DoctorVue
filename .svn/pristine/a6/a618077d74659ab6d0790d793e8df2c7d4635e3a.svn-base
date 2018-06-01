package com.chero.client.service;

import com.chero.client.domain.Message;
import com.chero.client.domain.MessageSetting;
import com.chero.client.domain.UserMessage;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;

import java.util.Date;

public interface MessageService {

    Page<UserMessage> findOneAllMessage(String userId, Integer msgType, Boolean isReading, CustomPageable customPageable);

    /**
     * 查询公告
     * @param title
     * @param startTime
     * @param endTime
     * @param customPageable
     * @return
     */
    Page<Message> findAllAnnouncementByCondition(String title, Date startTime, Date endTime, CustomPageable customPageable);

    /**
     * 添加公告
     * @param title
     * @param sendTime
     * @param content
     * @return
     */
    void addAnnouncement(String title,Date sendTime,String content);

    /**
     * 编辑公告
     * @param title
     * @param sendTime
     * @param content
     */
    void updateAnnouncement(String title, Date sendTime, String content);
    /**
     * 发送公告
     */
    void sendAnnouncement();

    void messageMarkup(String userId, Integer[] msgIds);

    void deleteOneAllMessage(String userId,  Integer[] ids);

    MessageSetting saveMessageSetting(MessageSetting messageSetting);
    
    MessageSetting findMessageSetting(String userId);
}
