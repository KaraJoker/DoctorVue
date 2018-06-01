package com.chero.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.chero.client.domain.*;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.service.SysManageService;
import com.chero.client.utils.*;
import com.chero.client.vo.ResultVO;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sysManage")
@SuppressWarnings("unchecked")
@Api("SysManageController相关api")
public class SysManageController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private static final String SAVEWITHOUTSMS = "http://47.98.154.107:8003/users/saveWithoutSms";

    @Autowired
    DoctorInfoService doctorInfoService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    SysManageService sysManageService;
    @Autowired
    RestTemplate restTemplate;


    @ApiOperation("人员列表条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = false, value = "手机号"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = false, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "departmentId", dataType = "String", required = false, value = "所在部门"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Boolean", required = false, value = "人员状态"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/selectDoctorInfo")
    public ResultVO<List<DoctorInfo>> selectDoctorInfo(HttpServletRequest httpRequest,
                                                       @ApiParam(value = "mobile", required = false) String mobile,
                                                       @ApiParam(value = "name", required = false) String name,
                                                       @ApiParam(value = "departmentId", required = false) String departmentId,
                                                       @ApiParam(value = "status", required = false) Boolean status,
                                                       CustomPageable customPageable) {
        ResultVO<List<DoctorInfo>> resultVO = new ResultVO<List<DoctorInfo>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        String role = String.valueOf(httpSession.getAttribute("role"));
        if (token.equals(httpSession.getAttribute("access_token"))) {
//            todo 手机号的userId 从虚怀那里获取
            List<DoctorInfo> doctorInfoList = sysManageService.selectDoctorInfoAll(mobile, name, departmentId, status, customPageable);
            List<DoctorInfo> returnList = new ArrayList<>();
            int pageId = customPageable.getPageId();
//            if (pageId * 10 <= doctorInfoList.size() / 10){
//                returnList = doctorInfoList.subList((pageId-1) * 10, pageId * 10);
//            }else if (pageId * 10 )
            for (DoctorInfo doctorInfo : doctorInfoList){
                doctorInfo.setMobile(ServerUtil.userIdToMobile(token, doctorInfo.getUserId(), null));
            }
            PageVO pageVO = new PageVO();
            pageVO.setSize(doctorInfoList.size());
            pageVO.setNumber(pageId);
            if (doctorInfoList.size() > 0){
                if ((doctorInfoList.size() / 10) >= pageId){
                    for (int i = (pageId -1) * 10; i < pageId * 10; i++){
                        returnList.add(doctorInfoList.get(i));
                    }
                }else if ((doctorInfoList.size() / 10) == (pageId - 1)){
                    int len = doctorInfoList.size() % 10;
                    for (int i = (pageId -1) * 10; i < len; i++){
                        returnList.add(doctorInfoList.get(i));
                    }
                }
            }
            DoctorInfo currentdoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(new Date(),
//                    "系统管理-人员管理",
//                    "查看",
//                    currentdoctorInfo.getRealName(),
//                    currentDoctorInfo.getRealName(),
//                    "查看详情",
//                    null,
//                    null);
            logger.info("【人员管理条件查询接口返回数据】" + doctorInfoList);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");

            if (doctorInfoList != null) {
                resultVO.setContent(doctorInfoList);
                resultVO.setPage(pageVO);
            }
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("人员查看")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "doctorInfoId", dataType = "String", required = true, value = "人员doctorInfoId"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findDoctorInfo")
    public ResultVO<DoctorInfo> findDoctorInfo(HttpServletRequest httpRequest, @ApiParam("doctorInfoId") String doctorInfoId) {
        ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        DoctorInfo doctorInfo = new DoctorInfo();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            doctorInfo = sysManageService.findDoctorInfo(doctorInfoId);
            doctorInfo.setDepartmentId(sysManageService.findUserDepartment(doctorInfo.getUserId()));
//            Gson gson = new Gson();
//            sysManageService.addOperateLogger(new Date(),
//                    "系统管理-人员管理",
//                    "查看",
//                    doctorInfo.getRealName(),
//                    currentDoctorInfo.getRealName(),
//                    "查看详情",
//                    gson.toJson(doctorInfo),
//                    gson.toJson(doctorInfo));
            logger.info("【人员管理条件查询接口返回数据】" + doctorInfo);
            logger.info("添加日志——>" + "系统管理-人员管理");
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(doctorInfo);
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @SuppressWarnings("unused")
    @ApiOperation("查询手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "手机号"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findMobile")
    public ResultVO<DoctorInfo> findMobile(HttpServletRequest httpRequest, @ApiParam("mobile") String mobile) {
        ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo = sysManageService.findPersonMobile(mobile);
//        sysManageService.addOperateLogger(new Date(),"系统管理-查询手机号","查询",doctorInfo.getRealName(),currentDoctorInfo.getRealName(),"查看详情", null, null);
        logger.info("【查询手机号接口返回数据】" + doctorInfo);
        logger.info("添加日志——>" + "系统管理-查询手机号");
        if (doctorInfo != null) {
            resultVO = ResultUtil.success(doctorInfo);
        } else {
            resultVO = ResultUtil.error(321, "手机号未添加");
        }
        return resultVO;
    }

    @ApiOperation("人员添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "手机号"),
            @ApiImplicitParam(paramType = "query", name = "realName", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "String", required = true, value = "性别"),
            @ApiImplicitParam(paramType = "query", name = "certificateNumber", dataType = "String", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "birthday", dataType = "String", required = true, value = "出生日期"),
            @ApiImplicitParam(paramType = "query", name = "subject", dataType = "String", required = true, value = "所在部门"),
            @ApiImplicitParam(paramType = "query", name = "departmentId", dataType = "String", required = true, value = "所在部门id"),
            @ApiImplicitParam(paramType = "query", name = "position", dataType = "String", required = true, value = "职位"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Boolean", required = true, value = "人员状态")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/addDoctorInfo")
    public ResultVO<?> addDoctorInfo(
            HttpServletRequest httpRequest,
            @ApiParam("mobile") String mobile,
            @ApiParam("realName") String realName,
            @ApiParam("gender") Integer gender,
            @ApiParam("certificateNumber") String certificateNumber,
            @ApiParam("birthday") Long birthday,
            @ApiParam("subject") String subject,
            @ApiParam("departmentId") String departmentId,
            @ApiParam("position") String position,
            @ApiParam("status") Boolean status) throws ParseException {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
        DoctorInfo doctorInfo = new DoctorInfo();
//      请求虚怀服务器增加用户并返回userId
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token").toString());
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("mobile", mobile);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        Object data = restTemplate.postForObject(SAVEWITHOUTSMS, httpEntity, Object.class);
        if (data instanceof LinkedHashMap){
            LinkedHashMap map = (LinkedHashMap) data;
            LinkedHashMap content = (LinkedHashMap) map.get("content");
            doctorInfo.setUserId(content.get("userId").toString());
            DoctorInfo doctorInfo1 = doctorInfoService.findDoctorInfoByUserId(content.get("userId").toString());
            if (doctorInfo1 != null){
                return ResultUtil.error(321, "该用户已经注册过医生信息");
            }
        }else{
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
            return resultVO;
        }
        doctorInfo.setMobile(mobile);
        doctorInfo.setRealName(realName);
        doctorInfo.setGender(gender);
        doctorInfo.setCertificateNumber(certificateNumber);
        doctorInfo.setCertificateType(0);
        if (birthday != null) {
            doctorInfo.setBirthday(new Date(birthday));
        }
        doctorInfo.setSubject(subject);
        doctorInfo.setPosition(position);
        doctorInfo.setStatus(status);
        doctorInfo.setCreateTime(new Date());
        doctorInfo.setCreateUser(CheroRequestUtil.getUserId(httpRequest));
        if (token.equals(httpSession.getAttribute("access_token"))) {
            doctorInfo.setIsTemporary(0);
            doctorInfoService.update(doctorInfo);
            sysManageService.addDoctorInfo(doctorInfo);
//            为人员添加部门
            sysManageService.addDoctorAndDepartment(doctorInfo.getUserId(), departmentId);
            Gson gson = new Gson();
//                List<doctorInfo> currentdoctorInfo = (List<doctorInfo>) httpSession.getAttribute("doctorInfoList");
            sysManageService.addOperateLogger(new Date(),
                    "系统管理-人员管理",
                    "添加",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    gson.toJson(doctorInfo));
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【人员添加接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("人员修改")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "人员id"),
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "手机号"),
            @ApiImplicitParam(paramType = "query", name = "realName", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "Integer", required = true, value = "性别"),
            @ApiImplicitParam(paramType = "query", name = "certificateNumber", dataType = "String", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "birthday", dataType = "Date", required = true, value = "出生日期"),
            @ApiImplicitParam(paramType = "query", name = "subject", dataType = "String", required = true, value = "所在部门"),
            @ApiImplicitParam(paramType = "query", name = "departmentId", dataType = "String", required = true, value = "所在部门id"),
            @ApiImplicitParam(paramType = "query", name = "position", dataType = "String", required = true, value = "职位"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Boolean", required = true, value = "人员状态")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveDoctorInfo")
    public ResultVO<?> saveDoctorInfo(HttpServletRequest httpRequest,
                                      @ApiParam("id") String id,
                                      @ApiParam("mobile") String mobile,
                                      @ApiParam("realName") String realName,
                                      @ApiParam("gender") Integer gender,
                                      @ApiParam("certificateNumber") String certificateNumber,
                                      @ApiParam("birthday") Long birthday,
                                      @ApiParam("subject") String subject,
                                      @ApiParam("departmentId") String departmentId,
                                      @ApiParam("position") String position,
                                      @ApiParam("status") Boolean status) throws ParseException {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        DoctorInfo doctorInfo = sysManageService.findDoctorInfo(id);
        doctorInfo.setRealName(realName);
        doctorInfo.setGender(gender);
        doctorInfo.setCertificateNumber(certificateNumber);
//        doctorInfo.setcertificateNumber(certificateNumber);
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        doctorInfo.setBirthday(new Date(birthday));
        doctorInfo.setSubject(subject);
        doctorInfo.setPosition(position);
        doctorInfo.setStatus(status);
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            DoctorInfo doctorInfo1Before = sysManageService.findDoctorInfo(doctorInfo.getId());
            sysManageService.saveDoctorAndDepartment(doctorInfo.getUserId(), departmentId);
            sysManageService.saveDoctorInfo(doctorInfo);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            if(currentDoctorInfo != null){
                sysManageService.addOperateLogger(
                        new Date(),
                        "系统管理-人员管理",
                        "修改",
                        currentDoctorInfo.getRealName(),
                        currentDoctorInfo.getMobile(),
                        "查看详情",
                        gson.toJson(doctorInfo1Before),
                        gson.toJson(doctorInfo)
                );
            }
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【人员管理保存接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("人员删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "人员id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/deleteDoctorInfo")
    public ResultVO<?> deleteDoctorInfo(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            DoctorInfo doctorInfo1Before = sysManageService.findDoctorInfo(id);
//            todo 删除该人员所有的角色和部门
            sysManageService.deleteDoctorInfo(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-人员管理",
                    "删除",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    gson.toJson(doctorInfo1Before),
                    null
            );
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【人员删除接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("机构部门列表查询")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/selectDepartment")
    public ResultVO<List<Department>> selectDepartment(HttpServletRequest httpRequest) {
        ResultVO<List<Department>> resultVO = new ResultVO<List<Department>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        List<Department> departmentList = new ArrayList<>();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            departmentList = sysManageService.selectDepartment();
            logger.info("【机构部门查询接口返回数据】" + departmentList);
            resultVO=ResultUtil.success(departmentList);
        } else {
        	resultVO=ResultUtil.error(401,"该账号没有权限");
        }
        return resultVO;
    }

    @ApiOperation("部门基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "param", name = "id", dataType = "String", required = true, value = "部门id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findDepartment")
    public ResultVO<Department> findDepartment(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<Department> resultVO = new ResultVO<Department>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Department department = new Department();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            department = sysManageService.findDepartment(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(new Date(), "系统管理-机构部门基本信息", "查看", currentDoctorInfo.getRealName(), currentDoctorInfo.getRealName(), "查看详情", null, null);
            logger.info("【部门基本信息接口返回数据】" + department);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(department);
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("部门删除")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "部门id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/deleteDepartment")
    public ResultVO<?> deleteDepartment(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Department department = new Department();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            Department departmentBefore = sysManageService.findDepartment(id);
            int departmentSubordinateCount = sysManageService.findDepartmentSubordinateCount(id);
            int departmentUserCount = sysManageService.findDepartmentUserCount(id);
//            判断是否含有下级部门
            if (departmentSubordinateCount != 0){
                resultVO.setStatus(321);
                resultVO.setMessage("不部门还存有下级部门，请先删除下级部门再删除本部门");
                return ResultUtil.error(321, "本部门还有" + departmentSubordinateCount + "个下级部门，请先删除这些部门再删除本部门");
            }
//            判断该部门是否含有医生
            if (departmentUserCount != 0){
                resultVO.setStatus(321);
                resultVO.setMessage("不部门还存有下级部门，请先删除下级部门再删除本部门");
                return ResultUtil.error(321, "本部门还有" + departmentUserCount + "位医生，请先移除这些医生再删除本部门");
            }
            sysManageService.deleteDepartment(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            if (currentDoctorInfo != null){
                sysManageService.addOperateLogger(
                        new Date(),
                        "系统管理-机构部门删除",
                        "查看",
                        currentDoctorInfo.getRealName(),
                        currentDoctorInfo.getMobile(),
                        "查看详情", gson.toJson(departmentBefore), null);
            }
            logger.info("【部门删除接口返回数据】" + department);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("部门添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "departmentName", dataType = "String", required = true, value = "部门名"),
            @ApiImplicitParam(paramType = "query", name = "parentId", dataType = "Integer", required = true, value = "父id"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String", required = true, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "principle", dataType = "String", required = true, value = "部门负责人"),
            @ApiImplicitParam(paramType = "query", name = "departmentPhone", dataType = "String", required = true, value = "部门电话"),
            @ApiImplicitParam(paramType = "query", name = "address", dataType = "String", required = true, value = "地址"),
            @ApiImplicitParam(paramType = "query", name = "remarks", dataType = "String", required = true, value = "备注")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/addDepartment")
    public ResultVO<?> addDepartment(HttpServletRequest httpRequest,
                                     @ApiParam("departmentName") String departmentName,
                                     @ApiParam("parentId") String parentId,
                                     @ApiParam("status") String status,
                                     @ApiParam("principle") String principle,
                                     @ApiParam("departmentPhone") String departmentPhone,
                                     @ApiParam("address") String address,
                                     @ApiParam("remarks") String remarks) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Department department = new Department();
        department.setDepartmentName(departmentName);
        department.setParentId(parentId);
        department.setStatus(status);
        department.setPrinciple(principle);
        department.setDepartmentPhone(departmentPhone);
        department.setAddress(address);
        department.setRemarks(remarks);
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            sysManageService.addDepartment(department);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-机构部门",
                    "添加",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    gson.toJson(department));
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【部门添加接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("部门编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "部门id"),
            @ApiImplicitParam(paramType = "query", name = "departmentName", dataType = "String", required = true, value = "部门名"),
            @ApiImplicitParam(paramType = "query", name = "parentId", dataType = "Integer", required = true, value = "父id"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String", required = true, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "principle", dataType = "String", required = true, value = "部门负责人"),
            @ApiImplicitParam(paramType = "query", name = "departmentPhone", dataType = "String", required = true, value = "部门电话"),
            @ApiImplicitParam(paramType = "query", name = "address", dataType = "String", required = true, value = "地址"),
            @ApiImplicitParam(paramType = "query", name = "remarks", dataType = "String", required = true, value = "备注")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveDepartment")
    public ResultVO<?> saveDepartment(HttpServletRequest httpRequest, @ApiParam("id") String id, @ApiParam("departmentName") String departmentName, @ApiParam("parentId") String parentId, @ApiParam("status") String status, @ApiParam("principle") String principle, @ApiParam("departmentPhone") String departmentPhone, @ApiParam("address") String address, @ApiParam("remarks") String remarks) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Department department = new Department();
        department.setId(id);
        department.setDepartmentName(departmentName);
        department.setParentId(parentId);
        department.setStatus(status);
        department.setPrinciple(principle);
        department.setDepartmentPhone(departmentPhone);
        department.setAddress(address);
        department.setRemarks(remarks);
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            Department departmentBefore = sysManageService.findDepartment(department.getId());
            sysManageService.saveDepartment(department);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-机构部门",
                    "更新",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    gson.toJson(departmentBefore),
                    gson.toJson(department));
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【部门保存接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("角色权限条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "role", dataType = "String", required = false, value = "角色"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String", required = false, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "String", required = false, value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "String", required = false, value = "结束时间")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/selectDoctorRole")
    public ResultVO<List<DoctorRole>> selectDoctorRole(
            HttpServletRequest httpRequest,
            @ApiParam(value = "role", required = false) String role,
            @ApiParam(value = "status", required = false) String status,
            @ApiParam(value = "startTime", required = false) String startTime,
            @ApiParam(value = "endTime", required = false) String endTime,
            CustomPageable customPageable) throws ParseException {
        ResultVO<List<DoctorRole>> resultVO = new ResultVO<List<DoctorRole>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Page<DoctorRole> doctorRoleList = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (startTime != null && endTime != null) {
                doctorRoleList = sysManageService.selectDoctorRole(role, status, sdf.format(new Date(Long.parseLong(startTime))), sdf.format(new Date(Long.parseLong(endTime))), customPageable);
            } else if (startTime == null && endTime != null) {
                doctorRoleList = sysManageService.selectDoctorRole(role, status, null, sdf.format(new Date(Long.parseLong(endTime))), customPageable);
            } else if (startTime != null && endTime == null) {
                doctorRoleList = sysManageService.selectDoctorRole(role, status, sdf.format(new Date(Long.parseLong(startTime))), null, customPageable);
            } else {
                doctorRoleList = sysManageService.selectDoctorRole(role, status, null, null, customPageable);
            }
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(new Date(), "系统管理-角色权限列表查询", "查看", currentDoctorInfo.getRealName(), currentDoctorInfo.getMobile(), "查看详情", null, null);
            logger.info("【角色权限条件查询接口返回数据】" + doctorRoleList);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(doctorRoleList.getResult());
            resultVO.setPage(PageUtil.convert(doctorRoleList));
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("角色权限信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = true, value = "部门id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findDoctorRole")
    public ResultVO<List<DoctorRole>> findDoctorRole(HttpServletRequest httpRequest, @ApiParam("id") String id, CustomPageable customPageable) {
        ResultVO<List<DoctorRole>> resultVO = new ResultVO<List<DoctorRole>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Page<DoctorRole> doctorRoleList;
        if (token.equals(httpSession.getAttribute("access_token"))) {
            doctorRoleList = sysManageService.findDoctorRole(customPageable);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-角色权限基本信息",
//                    "查看",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情", null, null);
            logger.info("【角色权限信息接口返回数据】" + doctorRoleList);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(doctorRoleList.getResult());
            resultVO.setPage(PageUtil.convert(doctorRoleList));
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("角色权限添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "role", dataType = "String", required = true, value = "角色"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Integer", required = true, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "privilegeArray", dataType = "String[]", required = true, value = "权限列表")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/addDoctorRole")
    public ResultVO<?> addDoctorRole(HttpServletRequest httpRequest,
                                     @ApiParam("role") String role,
                                     @ApiParam("status") Integer status,
                                     @ApiParam("privilegeArray") String... privilegeArray) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            JSONObject object = new JSONObject();
            object.put("role", role);
            object.put("status", status);
            object.put("privilegeArray", privilegeArray);
            sysManageService.addDoctorRole(role, status, CheroRequestUtil.getUserId(httpRequest), privilegeArray);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-角色权限",
                    "添加", currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    object.toString());
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【角色权限添加接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("角色权限更新")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "角色权限id"),
            @ApiImplicitParam(paramType = "query", name = "role", dataType = "String", required = true, value = "角色"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String", required = true, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "privilegeArray", dataType = "String[]", required = true, value = "权限列表")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveDoctorRole")
    public ResultVO<?> saveDoctorRole(HttpServletRequest httpRequest, @ApiParam("id") String id, @ApiParam("role") String role, @ApiParam("status") String status, @ApiParam("privilegeArray") String... privilegeArray) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            JSONObject jsonObjectBefore = new JSONObject();
            JSONObject jsonObjectAfter = new JSONObject();
            sysManageService.saveDoctorRole(id, role, status, privilegeArray);
            jsonObjectAfter.put("role", role);
            jsonObjectAfter.put("status", status);
            jsonObjectAfter.put("privilegeArray", privilegeArray);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-角色权限",
                    "更新",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    jsonObjectBefore.toString(),
                    jsonObjectAfter.toString()
            );
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【角色权限更新接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("角色权限删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "角色权限id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/deleteDoctorRole")
    public ResultVO<?> deleteDoctorRole(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.deleteDoctorRole(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-角色权限",
                    "删除",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    null
            );
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【角色权限删除接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("查看角色人员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = true, value = "角色id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/ViewDoctorInfo")
    public ResultVO<Map<String, List<?>>> ViewDoctorInfo(HttpServletRequest httpRequest, String id) {
        ResultVO<Map<String, List<?>>> resultVO = new ResultVO<Map<String, List<?>>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            List<DoctorInfo> doctorInfoList = sysManageService.findDoctorInfoOpetion(id);
            List<DoctorInfo> alldoctorInfo = doctorInfoService.findAllDoctorInfo();
            for (DoctorInfo doctorInfo : doctorInfoList){
                alldoctorInfo.remove(doctorInfo);
            }
            Map<String, List<?>> resultMap = new HashMap<String, List<?>>();
            resultMap.put("alldoctorInfo", alldoctorInfo);
            resultMap.put("doctorInfoList", doctorInfoList);
//            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-角色权限",
//                    "查看",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(resultMap);
            logger.info("【查看选择人员接口返回数据】" + resultVO.getContent());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("部门及子部门选择人员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", dataType = "String", required = true, value = "部门id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/departMentPickDoctorInfo")
    public ResultVO<List<PersonOpetion>> departMentPickDoctorInfo(HttpServletRequest httpRequest, @ApiParam("id") String ids) {
        ResultVO<List<PersonOpetion>> resultVO = new ResultVO<>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            List<PersonOpetion> personOpetionList = sysManageService.findDoctorInfoByArray(ids);
//            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-部门及子部门选择人员",
//                    "部门及子部门选择人员",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(personOpetionList);
            logger.info("【部门及子部门选择人员接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("禁用部门及子部门")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", dataType = "String", required = true, value = "部门id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/disableDepartMent")
    public ResultVO<List<PersonOpetion>> disableDepartMent(HttpServletRequest httpRequest, @ApiParam("id") String ids) {
        ResultVO<List<PersonOpetion>> resultVO = new ResultVO<>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.updateDoctorInfoByArray(ids);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-禁用部门及子部门",
                    "禁用部门及子部门",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    new Gson().toJson(ids),
                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【禁用部门及子部门接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("选择人员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", dataType = "String", required = true, value = "角色权限id"),
            @ApiImplicitParam(paramType = "query", name = "userList", dataType = "String", required = true, value = "人员列表")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/PickDoctorInfo")
    public ResultVO<?> PickDoctorInfo(HttpServletRequest httpRequest, @ApiParam("id") String roleId, @ApiParam("doctorInfoArrayString") String userList) {
        ResultVO<?> resultVO = new ResultVO<>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.updateTotalDoctorRolePers(roleId, userList);
//            DoctorInfo doctorInfo = new DoctorInfo();
//            String[] doctorInfoArray = userList.split(",");
//            for (int i = 0; i < doctorInfoArray.length; i++) {
//                doctorInfo.setId(doctorInfoArray[i]);
//                sysManageService.saveDoctorInfo(doctorInfo);
//            }
//            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-角色权限",
//                    "选择人员",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【选择人员接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("取消选择人员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "角色权限id"),
            @ApiImplicitParam(paramType = "query", name = "doctorInfoArrayString", dataType = "String", required = true, value = "人员列表")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/UnpickDoctorInfo")
    public ResultVO<?> UnpickDoctorInfo(HttpServletRequest httpRequest, @ApiParam("id") String id, @ApiParam("doctorInfoArrayString") String doctorInfoArrayString) {
        ResultVO<?> resultVO = new ResultVO<>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.updateTotalDoctorRolePers(id, doctorInfoArrayString);
            DoctorInfo doctorInfo = new DoctorInfo();
            String[] doctorInfoArray = doctorInfoArrayString.split(",");
            for (int i = 0; i < doctorInfoArray.length; i++) {
                doctorInfo.setUserId(doctorInfoArray[i]);
                sysManageService.saveDoctorInfo(doctorInfo);
            }
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-角色权限",
                    "选择人员",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【选择人员接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("查看设置权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", dataType = "String", required = true, value = "角色权限id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/ViewPrivilege")
    public ResultVO<Object> ViewPrivilege(HttpServletRequest httpRequest, @ApiParam("roleId") String roleId) {
        ResultVO<Object> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            DoctorRole doctorRole = sysManageService.findDoctorRoleById(roleId);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-角色权限",
//                    "查看",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            if (doctorRole != null) {
                resultVO.setContent(doctorRole);
            }
            logger.info("【查看设置权限接口返回数据】" + resultVO.getContent());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("设置权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", dataType = "String", required = true, value = "角色权限id"),
            @ApiImplicitParam(paramType = "query", name = "privilegeArray", dataType = "String[]", required = true, value = "权限列表")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/PickPrivilege")
    public ResultVO<?> PickPrivilege(HttpServletRequest httpRequest, @ApiParam("roleId") String roleId, @ApiParam("privilegeArray") String privilegeArray) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.updateTotalDoctorRolePriv(roleId, privilegeArray);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-角色权限",
//                    "设置权限",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【设置权限接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("操作日志条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "operator", dataType = "String", required = false, value = "操作人"),
            @ApiImplicitParam(paramType = "query", name = "operateAccount", dataType = "String", required = false, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "String", required = false, value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "String", required = false, value = "结束时间")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/selectOperateLogger")
    public ResultVO<List<CrLogger>> selectOperateLogger(HttpServletRequest httpRequest,
                                                        @ApiParam(value = "operator", required = false) String operator,
                                                        @ApiParam(value = "operateAccount", required = false) String operateAccount,
                                                        @ApiParam(value = "startTime", required = false) String startTime,
                                                        @ApiParam(value = "endTime", required = false) String endTime,
                                                        CustomPageable customPageable) throws ParseException {
        ResultVO<List<CrLogger>> resultVO = new ResultVO<List<CrLogger>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Page<CrLogger> loggerList = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (startTime != null && !"".equals(startTime) && endTime != null && !"".equals(endTime)) {
                loggerList = sysManageService.selectOperateLogger(
                        operator,
                        operateAccount,
                        sdf.format(new Date(Long.parseLong(startTime))),
                        sdf.format(new Date(Long.parseLong(endTime))),
                        customPageable);
            } else if ((startTime == null || "".equals(startTime)) && endTime != null && !"".equals(endTime)) {
                loggerList = sysManageService.selectOperateLogger(
                        operator,
                        operateAccount,
                        null,
                        sdf.format(new Date(Long.parseLong(endTime))),
                        customPageable);
            } else if (startTime != null && !"".equals(endTime) && (endTime == null || "".equals(endTime))) {
                loggerList = sysManageService.selectOperateLogger(
                        operator,
                        operateAccount,
                        sdf.format(new Date(Long.parseLong(startTime))),
                        null,
                        customPageable);
            } else {
                loggerList = sysManageService.selectOperateLogger(
                        operator,
                        operateAccount,
                        null,
                        null,
                        customPageable);
            }
//            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-操作日志",
//                    "查看",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            logger.info("【操作日志条件查询接口返回数据】" + loggerList);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(loggerList.getResult());
            resultVO.setPage(PageUtil.convert(loggerList));
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("操作日志单条查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = false, value = "结束时间")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findOperateLogger")
    public ResultVO<OperateLogger> selectOperateLogger(HttpServletRequest httpRequest,
                                                       @ApiParam(value = "id", required = false) String id) throws ParseException {
        ResultVO<OperateLogger> resultVO = new ResultVO<OperateLogger>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            OperateLogger crLogger = sysManageService.findOperateLogger(id);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(crLogger);
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("操作日志添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "operateTime", dataType = "String", required = true, value = "操作时间"),
            @ApiImplicitParam(paramType = "query", name = "operateContent", dataType = "String", required = true, value = "操作内容"),
            @ApiImplicitParam(paramType = "query", name = "operateType", dataType = "String", required = true, value = "操作类型"),
            @ApiImplicitParam(paramType = "query", name = "operator", dataType = "String", required = true, value = "操作人"),
            @ApiImplicitParam(paramType = "query", name = "operateAccount", dataType = "String", required = true, value = "操作人账号"),
            @ApiImplicitParam(paramType = "query", name = "operation", dataType = "String", required = true, value = "操作"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/addOperateLogger")
    public ResultVO<?> addOperateLogger(HttpServletRequest httpRequest,
                                        @ApiParam("operateTime") String operateTime,
                                        @ApiParam("operateContent") String operateContent,
                                        @ApiParam("operateType") String operateType,
                                        @ApiParam("operator") String operator,
                                        @ApiParam("operateAccount") String operateAccount,
                                        @ApiParam("operation") String operation) throws ParseException {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sysManageService.addOperateLogger(sdf.parse(operateTime), operateContent, operateType, operator, operateAccount, operation, null, null);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(new Date(), "系统管理-操作日志", "添加", currentDoctorInfo.getRealName(), currentDoctorInfo.getRealName(), "查看详情", null, null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【操作日志添加接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    /**
     * 系统设置
     */

    @Value("${custom.server.image.baseurl}")
    private String baseUrl;
    @Value("${cdn.image.baseurl}")
    private String cdnUrl;
    @Value("${custom.server.systemlogo.profile}")
    private String logoProfile;
    @Value("${custom.server.propaganda.profile}")
    private String propagandaProfile;

    @ApiOperation("系统设置基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "用户id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findSystemSetting")
    public ResultVO<Setting> findSystemSetting(HttpServletRequest httpRequest) {
        ResultVO<Setting> resultVO = new ResultVO<Setting>();
//        String token = httpRequest.getHeader("Authorization").split(" ")[1];
//        if (token.equals(httpSession.getAttribute("access_token"))) {
        Setting setting = sysManageService.findSystemSetting();
        if (setting == null) {
            setting = new Setting();
        }
        DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//        sysManageService.addOperateLogger(
//                new Date(),
//                "系统管理-基本设置",
//                "查看",
//                currentDoctorInfo.getRealName(),
//                currentDoctorInfo.getMobile(),
//                "查看详情",
//                null,
//                null);
        setting.setLogo(cdnUrl + setting.getLogo());
        String[] propagandaArray = StringUtils.split(setting.getPublishPicture(), ",");
        if (propagandaArray == null) {
            propagandaArray = new String[0];
        }
        for (int i = 0; i < propagandaArray.length; i++) {
            if (propagandaArray[i] != null && !"".equals(propagandaArray[i])) {
                propagandaArray[i] = cdnUrl + propagandaArray[i];
            }
        }
        setting.setPublishPicture(StringUtils.join(propagandaArray, ","));
        logger.info("【系统设置基本信息接口返回数据】" + setting);
        resultVO.setStatus(200);
        resultVO.setMessage("成功");
        resultVO.setContent(setting);
        return resultVO;
    }

    @ApiOperation("系统设置保存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "logo", dataType = "MultipartFile", required = true, value = "系统logo"),
            @ApiImplicitParam(paramType = "query", name = "sysName", dataType = "Boolean", required = true, value = "系统名称"),
            @ApiImplicitParam(paramType = "query", name = "copyright", dataType = "String", required = true, value = "版本信息"),
            @ApiImplicitParam(paramType = "query", name = "publishPicture", dataType = "MultipartFile", required = true, value = "宣传图片"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveSystemSetting")
    public ResultVO<?> saveSystemSetting(HttpServletRequest httpRequest,
                                         @ApiParam(value = "logo", required = false) MultipartFile logo,
                                         @ApiParam("sysName") String sysName,
                                         @ApiParam("copyright") Boolean copyright,
                                         @ApiParam("publishPicture") MultipartFile... publishPicture) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];

        Setting setting = new Setting();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            setting.setId("1");
            // logo上传
            if (logo != null && !logo.isEmpty()) {
                try {
                    String filename = ImageUtil.upload(logo, baseUrl, cdnUrl, logoProfile).getFileName();
                    setting.setLogo(filename);
                } catch (IOException e) {
                    logger.info("【logo上传错误】 \n filename = {} \n localfile = {}, \n cdnfile = {}", logoProfile, baseUrl, cdnUrl);
                }
            }
            // 获取老的propaganda
            String oldPropagandaStr = sysManageService.findSystemSetting().getPublishPicture();
            String[] oldPropagandaArray = StringUtils.split(oldPropagandaStr, ",");
            // propaganda上传
            if (publishPicture != null) {
                int len = publishPicture.length;
                // 无聊写的功能
                int oldLen;
                if (oldPropagandaArray != null) {
                    oldLen = oldPropagandaArray.length;
                    if (oldPropagandaArray.length < len) {
                        String[] strArray = new String[len];
                        for (int i = 0; i < oldLen; i++) {
                            strArray[i] = oldPropagandaArray[i];
                        }
                        oldPropagandaArray = strArray;
                        oldLen = len;
                    }
                } else {
                    oldPropagandaArray = new String[len];
                    oldLen = len;
                }

                // 新旧替换
                for (int i = 0; i < oldLen; i++) {
                    if (!publishPicture[i].isEmpty()) {
                        try {
                            String filename = ImageUtil.upload(publishPicture[i], baseUrl, cdnUrl, propagandaProfile).getFileName();
                            oldPropagandaArray[i] = filename;
//                        sb.append(filename);
                        } catch (IOException e) {
                            logger.info("【propaganda上传错误】 \n filename = {} \n localfile = {}, \n cdnfile = {}", propagandaProfile, baseUrl, cdnUrl);
                        }
                    }
                }
                logger.info("【现在宣传图】,图1={}, 长度={}", oldPropagandaArray[1], oldPropagandaArray.length);
                setting.setPublishPicture(StringUtils.join(oldPropagandaArray, ","));
            }

            setting.setSysName(sysName);
            setting.setCopyright(copyright);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.saveSystemSetting(setting);
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-设置信息",
//                    "保存",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情",
//                    null,
//                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【系统设置保存接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("用户建议保存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "suggest", dataType = "String", required = true, value = "用户建议"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveUserHelp")
    public ResultVO<?> saveUserHelp(HttpServletRequest httpRequest, @ApiParam("suggest") String suggest) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        DoctorInfo doctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
        if (token.equals(httpSession.getAttribute("access_token"))) {
            sysManageService.saveUserHelpUser(suggest, doctorInfo.getId());
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-用户建议",
                    "新增",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getRealName(),
                    "查看详情",
                    null,
                    suggest);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【用户建议保存接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("系统公告添加")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "afficheName", dataType = "String", required = true, value = "公告名称"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Integer", required = true, value = "状态(0未发送，1已发送)"),
            @ApiImplicitParam(paramType = "query", name = "sendNumber", dataType = "String", required = true, value = "发送人数"),
            @ApiImplicitParam(paramType = "query", name = "sendMan", dataType = "String", required = true, value = "发送人"),
            @ApiImplicitParam(paramType = "query", name = "sendTime", dataType = "Date", required = true, value = "发送时间"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/addAffiche")
    public ResultVO<?> addAffiche(HttpServletRequest httpRequest,
                                  @ApiParam(value = "afficheName", required = false) String afficheName,
                                  @ApiParam(value = "status", required = false) Integer status,
                                  @ApiParam(value = "sendNumber", required = false) String sendNumber,
                                  @ApiParam(value = "sendMan", required = false) String sendMan,
                                  @ApiParam(value = "sendTime", required = false) Long sendTime) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Affiche affiche = new Affiche();
        affiche.setAfficheName(afficheName);
        affiche.setStatus(status);
        affiche.setSendNumber(sendNumber);
        affiche.setSendMan(sendMan);
        affiche.setSendTime(new Date(sendTime));
//        try {
//            affiche.setSendTime(sdf.parse(sendTime));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            sysManageService.addAffiche(affiche);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-系统公告",
                    "添加",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    null,
                    gson.toJson(affiche));
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【系统公告添加接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("系统公告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = true, value = "系统公告id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/findAffiche")
    public ResultVO<Affiche> findAffiche(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<Affiche> resultVO = new ResultVO<Affiche>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Affiche affiche = new Affiche();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            affiche = sysManageService.findAffiche(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            sysManageService.addOperateLogger(
//                    new Date(),
//                    "系统管理-系统公告信息",
//                    "查看",
//                    currentDoctorInfo.getRealName(),
//                    currentDoctorInfo.getMobile(),
//                    "查看详情", null, null);
            logger.info("【系统公告信息接口返回数据】" + affiche);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            resultVO.setContent(affiche);
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("系统公告保存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = true, value = "主键id"),
            @ApiImplicitParam(paramType = "query", name = "afficheName", dataType = "String", required = true, value = "公告名称"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "Integer", required = true, value = "状态(0未发送，1已发送)"),
            @ApiImplicitParam(paramType = "query", name = "sendNumber", dataType = "String", required = true, value = "发送人数"),
            @ApiImplicitParam(paramType = "query", name = "sendMan", dataType = "String", required = true, value = "发送人"),
            @ApiImplicitParam(paramType = "query", name = "sendTime", dataType = "String", required = true, value = "发送时间"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/saveAffiche")
    public ResultVO<?> saveAffiche(HttpServletRequest httpRequest,
                                   @ApiParam("id") String id,
                                   @ApiParam("afficheName") String afficheName,
                                   @ApiParam("status") Integer status,
                                   @ApiParam("sendNumber") String sendNumber,
                                   @ApiParam("sendMan") String sendMan,
                                   @ApiParam("sendTime") Long sendTime) {
        ResultVO<?> resultVO = new ResultVO<Object>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Affiche affiche = new Affiche();
        affiche.setId(id);
        affiche.setAfficheName(afficheName);
        affiche.setStatus(status);
        affiche.setSendNumber(sendNumber);
        affiche.setSendMan(sendMan);
        affiche.setSendTime(new Date(sendTime));
//        try {
//            affiche.setSendTime(sdf.parse(sendTime));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Gson gson = new Gson();
            Affiche afficheBefore = sysManageService.findAffiche(affiche.getId());
            sysManageService.saveAffiche(affiche);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-系统公告",
                    "保存",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    gson.toJson(afficheBefore),
                    gson.toJson(affiche));
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【系统公告保存接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }

    @ApiOperation("系统公告信息删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = true, value = "系统公告id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功"),
            @ApiResponse(code = 321, message = "返回失败"),
    })
    @PostMapping("/deleteAffiche")
    public ResultVO<Affiche> deleteAffiche(HttpServletRequest httpRequest, @ApiParam("id") String id) {
        ResultVO<Affiche> resultVO = new ResultVO<Affiche>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        Gson gson = new Gson();
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Affiche afficheBefore = sysManageService.findAffiche(id);
            sysManageService.deleteAffiche(id);
            DoctorInfo currentDoctorInfo = (DoctorInfo) httpSession.getAttribute("doctorInfo");
            sysManageService.addOperateLogger(
                    new Date(),
                    "系统管理-系统公告信息",
                    "删除",
                    currentDoctorInfo.getRealName(),
                    currentDoctorInfo.getMobile(),
                    "查看详情",
                    gson.toJson(afficheBefore),
                    null);
            resultVO.setStatus(200);
            resultVO.setMessage("成功");
            logger.info("【系统公告信息删除接口返回数据】" + resultVO.getMessage());
        } else {
            resultVO.setStatus(321);
            resultVO.setMessage("失败");
        }
        return resultVO;
    }
}
