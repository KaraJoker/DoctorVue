package com.chero.client.controller;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.Message;
import com.chero.client.domain.MessageSetting;
import com.chero.client.domain.UserMessage;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.service.MessageService;
import com.chero.client.utils.CheroRequestUtil;
import com.chero.client.utils.CustomPageable;
import com.chero.client.utils.PageUtil;
import com.chero.client.utils.ResultUtil;
import com.chero.client.vo.ResultVO;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
@Api("MessageController相关api")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private DoctorInfoService doctorInfoService;


    @ApiOperation("公告消息展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String", required = false, value = "公告标题"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "Long", required = false, value = "起始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "Long", required = false, value = "结束时间")})
    @GetMapping("/announcement/list")
    public Object getAnnouncementList(@ApiParam(value = "type") String title, @ApiParam(value = "startTime") Long startTime, @ApiParam(value = "endTime") Long endTime, CustomPageable customPageable) {
        Date startDate = null;
        Date endDate = null;
        boolean flag = doctorInfoService.isAdminRole();
        if (startTime != null) {
            startDate = new Date(startTime);
        }
        if (endTime != null) {
            endDate = new Date(endTime);
        }
        if (flag) {
            return ResultUtil.success(messageService.findAllAnnouncementByCondition(title, startDate, endDate, customPageable));
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }

    @ApiOperation("添加公告消息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String", required = true, value = "公告标题"),
            @ApiImplicitParam(paramType = "query", name = "sendTime", dataType = "Long", required = true, value = "发送时间"),
            @ApiImplicitParam(paramType = "query", name = "content", dataType = "String", required = true, value = "发送内容")})
    @PostMapping("/announcement/add")
    public Object addAnnouncement(@ApiParam(value = "title", required = true) String title, @ApiParam(value = "sendTime", required = true) Long sendTime, @ApiParam(value = "content", required = true) String content) {
        boolean flag = doctorInfoService.isAdminRole();
        if (flag) {
            if (sendTime < new Date().getTime()) {
                return ResultUtil.error(500, "发送时间不能小于当前时间");
            }
            messageService.addAnnouncement(title, new Date(sendTime), content);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }

    @ApiOperation("编辑公告消息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String", value = "公告标题"),
            @ApiImplicitParam(paramType = "query", name = "sendTime", dataType = "Long", value = "发送时间"),
            @ApiImplicitParam(paramType = "query", name = "content", dataType = "String", value = "发送内容")})
    @PostMapping("/announcement/update")
    public Object updateAnnouncement(@ApiParam(value = "title") String title, @ApiParam(value = "sendTime") Long sendTime, @ApiParam(value = "content") String content) {
        boolean flag = doctorInfoService.isAdminRole();
        if (flag) {
            if (sendTime < new Date().getTime()) {
                return ResultUtil.error(500, "发送时间不能小于当前时间");
            }
            messageService.updateAnnouncement(title, new Date(sendTime), content);
            return ResultUtil.success();
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }


    @ApiOperation("个人消息展示")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "Integer", required = false, value = "类型【0通知，1公告】"),
            @ApiImplicitParam(paramType = "query", name = "isReading", dataType = "Boolean", required = false, value = "是否已读【0已读，1未读】")})
    @PostMapping("/messageDisplay")
    public ResultVO<List<Message>> messageDisplay(HttpServletRequest httpRequest,
                                                  @ApiParam(value = "type", required = false) Integer type,
                                                  @ApiParam(value = "isReading", required = false) Boolean isReading, CustomPageable customPageable) {
        String userId = CheroRequestUtil.getUserId(httpRequest);
        DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
        if (doctorInfo != null) {
            Page<UserMessage> userMessagePage = messageService.findOneAllMessage(userId, type, isReading, customPageable);
            return ResultUtil.success(userMessagePage.getResult(), PageUtil.convert(userMessagePage));
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }

    @ApiOperation("标记已读")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", dataType = "String", required = true, value = "消息id"),})
    @PostMapping("/messageMarkup")
    public ResultVO<String> messageMarkup(HttpServletRequest httpRequest, @ApiParam(value = "ids") Integer[] ids) { // 这里@APiParam里的ids 要和 后面ids名字相同
        String userId = CheroRequestUtil.getUserId(httpRequest);
        messageService.messageMarkup(userId, ids);
        return ResultUtil.success();


    }

    @ApiOperation("删除消息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", dataType = "Integer[]", required = true, value = "消息id"),})
    @PostMapping("/messageDelete")
    public ResultVO<String> messageDelete(HttpServletRequest httpRequest, @ApiParam(value = "ids") Integer[] ids) {
        String userId = CheroRequestUtil.getUserId(httpRequest);
        messageService.deleteOneAllMessage(userId, ids);
        return ResultUtil.success();

    }

    @PostMapping("/setting")
    public ResultVO<MessageSetting> messageSetting(HttpServletRequest httpRequest, Boolean notification,
                                                   Boolean announcement, Integer receiveMethod) {
        String userId = CheroRequestUtil.getUserId(httpRequest);
        DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
        if (doctorInfo != null) {
            MessageSetting messageSetting = new MessageSetting();
            messageSetting.setUserId(userId);
            messageSetting.setNotification(notification);
            messageSetting.setAnnouncement(announcement);
            messageSetting.setReceiveMethod(receiveMethod);
            return ResultUtil.success(messageService.saveMessageSetting(messageSetting));
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }

    @GetMapping("/setting")
    public ResultVO<MessageSetting> getMessageSetting(HttpServletRequest httpRequest) {
        String userId = CheroRequestUtil.getUserId(httpRequest);
        DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
        if (doctorInfo != null) {
            MessageSetting messageSetting = messageService.findMessageSetting(userId);
            return ResultUtil.success(messageSetting);
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }
}
