package com.chero.client.service.impl;

import com.chero.client.constant.OrgnazationConstant;
import com.chero.client.dao.DoctorInfoRepository;
import com.chero.client.dao.MessageRepository;
import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.Message;
import com.chero.client.domain.MessageSetting;
import com.chero.client.domain.UserMessage;
import com.chero.client.enums.MessageTypeEnum;
import com.chero.client.service.MessageService;
import com.chero.client.utils.CheroBeanUtils;
import com.chero.client.utils.CheroRequestUtil;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DoctorInfoRepository doctorInfoRepository;
    @Autowired
    private HttpSession httpSession;

    @Override
    public Page<UserMessage> findOneAllMessage(String userId, Integer msgType, Boolean isReading, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        return messageRepository.findOneAllMessageByCondition(userId, msgType, isReading, OrgnazationConstant.GUO_REN);
    }

    @Override
    public Page<Message> findAllAnnouncementByCondition(String title, Date startTime, Date endTime, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        return messageRepository.findAnnouncementByCondition(OrgnazationConstant.GUO_REN, title, startTime, endTime);
    }

    @Override
    public void addAnnouncement(String title, Date sendTime, String content) {
        Message message = new Message();
        message.setTitle(title);
        message.setSendTime(sendTime);
        message.setContent(content);
        String accessToken = String.valueOf(httpSession.getAttribute("access_token"));
        String userId = CheroRequestUtil.getUserId(accessToken);
        DoctorInfo doctorInfo = doctorInfoRepository.findDoctorInfoByUserId(userId);
        message.setCreateUser(doctorInfo.getRealName());
        message.setFromUserId(userId);
        message.setOrgnazationId(OrgnazationConstant.GUO_REN);
        // 发送人数
//        message.setPeopleNumber(doctorInfoRepository.countAll());
        message.setUpdateUser(message.getCreateUser());
        message.setType(MessageTypeEnum.ANNOUNCEMENT.getCode());
        message.setUpdateTime(new Date());
        message.setIsSend(false);
        messageRepository.addMessage(message);
    }

    @Override
    public void updateAnnouncement(String title, Date sendTime, String content) {
        Message message = new Message();
        message.setTitle(title);
        message.setSendTime(sendTime);
        message.setContent(content);
        String accessToken = String.valueOf(httpSession.getAttribute("access_token"));
        String userId = CheroRequestUtil.getUserId(accessToken);
        DoctorInfo doctorInfo = doctorInfoRepository.findDoctorInfoByUserId(userId);
        // 发送人数
//        message.setPeopleNumber(doctorInfoRepository.countAll());
        message.setUpdateUser(doctorInfo.getRealName());
        message.setUpdateTime(new Date());
        messageRepository.updateAnnouncement(message);
    }

    @Override
    @Transactional
    public void sendAnnouncement() {
        List<Integer> msgIds = messageRepository.findUnSendAnnouncement(new Date());
        if (msgIds != null && msgIds.size() > 0) {
            List<String> toUserIdList = doctorInfoRepository.findAllUsedUserId();
            if (toUserIdList != null && toUserIdList.size() > 0) {
                messageRepository.setMessageIsSend(msgIds, toUserIdList.size());
                for (Integer msgId : msgIds) {
                    messageRepository.sendAnnouncement(msgId, toUserIdList, OrgnazationConstant.GUO_REN);
                }
            }
        }
    }

    @Override
    public void messageMarkup(String userId, Integer[] msgIds) {
        if (msgIds != null) {
            messageRepository.messageMarkup(userId, msgIds, OrgnazationConstant.GUO_REN);
        }
    }

    @Override
    public void deleteOneAllMessage(String userId, Integer[] msgIds) {
        if (msgIds != null) {
            messageRepository.deleteOneAllMessage(userId, msgIds, OrgnazationConstant.GUO_REN);
        }
    }

    @Override
    @Transactional
    public MessageSetting saveMessageSetting(MessageSetting messageSetting) {
        MessageSetting oldSetting = messageRepository.findMessageSettingByUserId(messageSetting.getUserId());
        MessageSetting currentSetting;
        if (oldSetting == null) {
            messageRepository.saveMessageSetting(messageSetting);
            currentSetting = messageSetting;
        } else {
            messageRepository.updateMessageSetting(messageSetting);
            CheroBeanUtils.copyProperties(messageSetting, oldSetting);
            currentSetting = oldSetting;
        }
        return currentSetting;
    }

    @Override
    public MessageSetting findMessageSetting(String userId) {
        MessageSetting currentSetting = messageRepository.findMessageSettingByUserId(userId);
        if (currentSetting == null) {
            currentSetting = new MessageSetting();
            currentSetting.setUserId(userId);
            currentSetting.setAnnouncement(true);
            currentSetting.setNotification(true);
            currentSetting.setReceiveMethod(0);
            messageRepository.saveMessageSetting(currentSetting);
        }
        return currentSetting;

    }
}
