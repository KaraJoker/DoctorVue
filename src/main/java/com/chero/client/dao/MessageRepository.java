package com.chero.client.dao;

import com.chero.client.domain.Message;
import com.chero.client.domain.MessageSetting;
import com.chero.client.domain.UserMessage;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MessageRepository {


    Page<UserMessage> findOneAllMessageByCondition(@Param("userId") String userId, @Param("msgType") Integer msgType, @Param("isReading") Boolean isReading, @Param("orgnazationId") String orgnazationId);

    Page<Message> findAnnouncementByCondition(@Param("orgnazationId") String orgnazationId, @Param("title") String title, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<Integer> findUnSendAnnouncement(@Param("nowTime") Date nowTime);
    void sendAnnouncement(@Param("msgId") Integer msgId, @Param("toUserIds") List<String> toUserIds, @Param("orgnazationId") String orgnazationId);

    void addMessage(Message message);

    void setMessageIsSend(@Param("msgIds") List<Integer> msgIds, @Param("peopleNumber") Integer peropleNumber);

    void updateAnnouncement(Message message);

    void messageMarkup(@Param("userId") String userId, @Param("msgIds") Integer[] msgIds, @Param("orgnazationId") String orgnazationId);

    void deleteOneAllMessage(@Param("userId") String userId, @Param("msgIds") Integer[] msgIds, @Param("orgnazationId") String orgnazationId);

    MessageSetting findMessageSettingByUserId(String userId);

    void saveMessageSetting(MessageSetting messageSetting);

    void updateMessageSetting(MessageSetting messageSetting);

    Message findMessageById(Integer id);
}
