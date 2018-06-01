package com.chero.client.controller;

import com.chero.client.convert.DoctorInfo2DoctorInfoVOConverter;
import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.DoctorReview;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.service.SysManageService;
import com.chero.client.utils.*;
import com.chero.client.vo.ResultVO;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/doctorInfo")
@Api("DoctorInfoController相关api")
public class DoctorInfoController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorInfoController.class);
    @Autowired
    HttpSession httpSession;
    @Autowired
    DoctorInfoService doctorInfoService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SysManageService sysManageService;
    @Autowired
    DoctorInfo2DoctorInfoVOConverter converter;

    @ApiOperation("个人信息展示")
    @PostMapping("/show")
    public ResultVO<?> show(HttpServletRequest httpRequest) {
    	ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
        String userId=CheroRequestUtil.getUserId(httpRequest);
        DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		logger.info("【个人信息展示返回数据】" + doctorInfo);
        resultVO=ResultUtil.success(converter.convert(doctorInfo));
        return resultVO;
    }
    @ApiOperation("他人信息展示")
    @PostMapping("/showOther")
    public ResultVO<DoctorInfo> show(HttpServletRequest httpRequest, @RequestParam String userId) {
        ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
    	DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
    	logger.info("【他人信息展示返回数据】" + doctorInfo);
        resultVO=ResultUtil.success(converter.convert(doctorInfo));
        return resultVO;
    }
    @Value("${custom.server.userface.profile}")
    private String userfaceProfile;
    @Value("${custom.server.upload.profile}")
    private String uploadProfile;
    @Value("${custom.server.registration.profile}")
    private String registrationProfile;
    @Value("${custom.server.certificate.profile}")
    private String certificateProfile;
    @Value("${cdn.image.baseurl}")
    private String cdnPath;
    @Value("${custom.server.image.baseurl}")
    private String localPath;
    @ApiOperation("个人信息保存")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "file", dataType = "MultipartFile", required = true, value = "头像"),
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "String", required = true, value = "性别"),
            @ApiImplicitParam(paramType = "query", name = "departmentId", dataType = "String", required = true, value = "所在部门id"),
            @ApiImplicitParam(paramType = "query", name = "certificateNumber", dataType = "String", required = true, value = "身份证号")
    })
    @PostMapping("/save")
    public ResultVO<DoctorInfo> save(HttpServletRequest httpRequest,
                                     @ApiParam(value = "file", required = false) MultipartFile file,
                                     @ApiParam(value = "name") String name,
                                     @ApiParam(value = "subject", required = false) String subject,
                                     @ApiParam(value = "gender", required = true) Integer gender,
                                     @ApiParam(value = "certificateNumber", required = true) String certificateNumber,
                                     @ApiParam(value = "departmentId", required = false) String departmentId,
                                     @ApiParam(value = "position", required = false) String position) {
    	ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
        DoctorInfo doctorInfo = new DoctorInfo();
        if (!file.isEmpty()) {
            try {
                String fileName = ImageUtil.upload(file, localPath, cdnPath, userfaceProfile).getFileName();
                doctorInfo.setUserface(fileName);
            } catch (IOException e) {
                logger.error("【个人信息头像保存错误】: localPath={}, cdnPath={}, userfaceFile={}", localPath, cdnPath, file);
            }
        }
        String userId=CheroRequestUtil.getUserId(httpRequest);
        doctorInfo.setRealName(name);
        doctorInfo.setGender(gender);
        doctorInfo.setCertificateNumber(certificateNumber);
        doctorInfo.setSubject(subject);
        doctorInfo.setPosition(position);
        doctorInfo.setUserId(userId);
        doctorInfo.setUserId(userId);
        doctorInfoService.updateInfo(doctorInfo);
        //            为人员添加部门
        sysManageService.saveDoctorAndDepartment(doctorInfo.getUserId(), departmentId);
        resultVO=ResultUtil.success();
        return resultVO;
    }
    
    @ApiOperation("医生查看角色权限")
    @ApiImplicitParam(paramType = "query", name = "doctorId", dataType = "Integer", required = true, value = "医生id")
    @PostMapping("/doctorLookRole")
    public ResultVO<List<String>> doctorLookRole(HttpServletRequest httpRequest, @ApiParam(value = "doctorId", required = true)String doctorId) {
        ResultVO<List<String>> resultVO = new ResultVO<List<String>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            String role = String.valueOf(httpSession.getAttribute("role"));
            if ("ROLE_admin".equals(role)) {
                String[] roleIds = doctorInfoService.doctorLookRole(doctorId);
                List<String> privilegeString=new ArrayList<String>();
                for(String roleId:roleIds) {
                	privilegeString.add(sysManageService.findDoctorRoleById(roleId).getPrivilegeListString());
                }
                resultVO=ResultUtil.success(privilegeString);
            } else {
                resultVO.setStatus(310);
                resultVO.setMessage("返回失败");
            }
        }else{
            resultVO.setStatus(310);
            resultVO.setMessage("返回失败");
        }
//        resultVO=ResultUtil.success(privilegeString);
        return resultVO;
    }

    @ApiOperation("删除头像")
    @ApiResponses({
            @ApiResponse(code = 310, message = "失败"),
    })
    @PostMapping("/deleteFace")
    public ResultVO<List<String>> deleteFace(HttpServletRequest httpRequest) {
        ResultVO<List<String>> resultVO = new ResultVO<List<String>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
        	String userId=CheroRequestUtil.getUserId(httpRequest);
        	DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
        	doctorInfo.setUserface(null);
            doctorInfoService.updateInfo(doctorInfo);
            resultVO.setStatus(200);
            resultVO.setMessage("删除成功");
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("删除失败");
        }
        logger.info("【删除头像】" + resultVO.getMessage());
        return resultVO;
    }

    @ApiOperation("个人认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "realName", dataType = "MultipartFile", required = true, value = "真实姓名"),
            @ApiImplicitParam(paramType = "query", name = "certificateType", dataType = "Integer", required = true, value = "证件类型(0身份证...)"),
            @ApiImplicitParam(paramType = "query", name = "certificateNumber", dataType = "String", required = true, value = "证件号"),
            @ApiImplicitParam(paramType = "query", name = "file", dataType = "MultipartFile", required = true, value = "身份证照片")
    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/basicAuth")
    public ResultVO<?> basicAuth(HttpServletRequest httpRequst,
                                 @ApiParam(value = "realName") String realName,
                                 @ApiParam(value = "certificateType") Integer certificateType,
                                 @ApiParam(value = "certificateNumber") String certificateNumber,
                                 @ApiParam(value = "file") MultipartFile file) {
        ResultVO<DoctorReview> resultVO = new ResultVO<DoctorReview>();
        String token = httpRequst.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            String userId = CheroRequestUtil.getUserId(httpRequst);
//            DoctorInfo doctorInfo =(DoctorInfo)httpSession.getAttribute("doctorInfo");
//            Personage personage = doctorInfoService.findDoctorByUserId(sysUser.getId());
//            if (personage == null){
//                personage = new Personage();
//                personage.setUserId(sysUser.getId());
//                personage.setMobile(sysUser.getUsername());
//                doctorInfoService.saveInfo(personage);
//                personage = doctorInfoService.findDoctorByUserId(sysUser.getId());
//
//            } else {
//                personage = new Personage();
//            }
//            personage.setUserId(sysUser.getId());
//            personage.setRealName(realName);
//            personage.setCertificate_type(certificate_type);
//            personage.setcertificateNumber(certificateNumber);
            DoctorReview doctorReview = new DoctorReview();
            doctorReview.setUserRealName(realName);
            doctorReview.setCertificateType(certificateType);
            doctorReview.setCertificateNumber(certificateNumber);
            doctorReview.setUserId(userId);
//            doctorReview.setUserAccount(doctorInfo.getMobile());
            doctorReview.setCreateUser(userId);
            doctorReview.setCreateTime(new Date());
            doctorReview.setUpdateUser(userId);
            doctorReview.setUpdateTime(new Date());
            doctorReview.setReviewState(3);
            if(file!=null){
                if (!file.isEmpty()) {
                    try {
                        String fileName = ImageUtil.upload(file, localPath, cdnPath, uploadProfile).getFileName();
                        doctorReview.setIdCertificatePhoto(fileName);
                    } catch (IOException e) {
                        logger.error("【个人认证头像保存错误】: localPath={}, cdnPath={}, Id_certificate_photo={}", localPath, cdnPath, file);
                    }
                }
            }
            doctorInfoService.addDoctorReview(doctorReview);
//            doctorInfoService.updateInfo(personage);
            resultVO.setStatus(200);
            resultVO.setMessage("保存成功");
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("保存失败");
        }
        return resultVO;
    }

    @ApiOperation("医师认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "registrationFile", dataType = "MultipartFile", required = true, value = "医师执业注册证"),
            @ApiImplicitParam(paramType = "query", name = "certificateFile", dataType = "MultipartFile", required = true, value = "医生职称证"),
            @ApiImplicitParam(paramType = "query", name = "subject", dataType = "String", required = true, value = "部门"),
            @ApiImplicitParam(paramType = "query", name = "position", dataType = "String", required = true, value = "职位"),
    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/doctorAuth")
    public ResultVO<?> doctorAuth(HttpServletRequest httpRequest,
                                  @ApiParam(value = "registrationFile") MultipartFile registrationFile,
                                  @ApiParam(value = "certificateFile") MultipartFile certificateFile,
                                  @ApiParam(value = "departmentId") String departmentId,
                                  @ApiParam(value = "subject") String subject,
                                  @ApiParam(value = "position") String position) {
    	ResultVO<DoctorInfo> resultVO = new ResultVO<DoctorInfo>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
//            DoctorInfo doctorInfo =(DoctorInfo)httpSession.getAttribute("username");
//            Personage personage = doctorInfoService.findDoctorByUserId(sysUser.getId());
//            Personage personage = new Personage();
//            personage.setUserId(sysUser.getId());
            String userId = CheroRequestUtil.getUserId(httpRequest);
            DoctorReview doctorReview = doctorInfoService.findDoctorReviewState(userId);
            doctorReview.setDepartmentId(departmentId);
            doctorReview.setSubject(subject);
            doctorReview.setPosition(position);
            doctorReview.setReviewState(0);
            if (registrationFile != null && certificateFile != null && !registrationFile.isEmpty() && !certificateFile.isEmpty()) {
                try {
                    String fileName = ImageUtil.upload(registrationFile, localPath, cdnPath, registrationProfile).getFileName();
//                    personage.setRegistration_certificate_photo(fileName);
                    doctorReview.setRegistrationCertificatePhoto(fileName);

                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("【师执业注册证错误】: localPath={}, cdnPath={}, registrationFile={}", localPath, cdnPath, registrationFile);
                }
                try {
                    String fileName = ImageUtil.upload(certificateFile, localPath, cdnPath, certificateProfile).getFileName();
//                    personage.setTitle_certificate_photo(fileName);
                    doctorReview.setTitleCertificatePhoto(fileName);
                } catch (IOException e) {
                    logger.error("【医生职称证错误】: localPath={}, cdnPath={}, file={}", localPath, cdnPath, certificateFile);
                }
            }
//            personage.setSubject(subject);
//            personage.setPosition(position);
//            doctorInfoService.updateInfo(personage);
//            personage = doctorInfoService.findDoctorByUserId(sysUser.getId());
//            doctorReview.setUserId(sysUser.getId() + "");
//            doctorReview.setUserAccount(personage.getMobile());
//            doctorReview.setReviewState(0);
//            doctorReview.setCreateUser(sysUser.getId() + "");
//            doctorReview.setCreateTime(new Date());
//            doctorReview.setUpdateUser(sysUser.getId() + "");
//            doctorReview.setUpdateTime(new Date());
            doctorInfoService.updataDoctorReview(doctorReview);
            resultVO.setStatus(200);
            resultVO.setMessage("保存成功");
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("保存失败");
        }
        return resultVO;
    }

    @ApiOperation("获取请求认证列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userMobile", dataType = "String", required = false, value = "账号"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "Long", required = false, value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "Long", required = false, value = "结束时间"),

    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/doctorReviewList")
    public ResultVO<List<DoctorReview>> doctorReviewList(HttpServletRequest httpRequest,
                                    @ApiParam(value = "userAccount", required = false) String userAccount,
                                    @ApiParam(value = "startTime", required = false) Long startTime,
                                    @ApiParam(value = "endTime", required = false) Long endTime,
                                    CustomPageable customPageable) {
    	ResultVO<List<DoctorReview>> resultVO = new ResultVO<List<DoctorReview>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Page<DoctorReview> page;
            if (startTime != null && endTime != null){
                page = doctorInfoService.findDoctorReviewList(
                        userAccount,
                        new Date(startTime),
                        new Date(endTime),
                        customPageable);
            }else {
                page = doctorInfoService.findDoctorReviewList(
                        userAccount,
                        null,
                        null,
                        customPageable);
            }
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(page.getResult());
            resultVO.setPage(PageUtil.convert(page));
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }

    @ApiOperation("获取某人认证记录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "String", required = false, value = "账号"),

    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/doctorReviewUser")
    public ResultVO<List<DoctorReview>> doctorReviewUser(HttpServletRequest httpRequest,
                                        @ApiParam(value = "userId", required = true) String userId,
                                        CustomPageable customPageable) {
    	ResultVO<List<DoctorReview>> resultVO = new ResultVO<List<DoctorReview>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Page<DoctorReview> page = doctorInfoService.findDoctorReviewUserList(
                    userId,
                    customPageable);
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(page.getResult());
            resultVO.setPage(PageUtil.convert(page));
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }
    @ApiOperation("获取已认证列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userMobile", dataType = "String", required = false, value = "账号"),
            @ApiImplicitParam(paramType = "query", name = "startTime", dataType = "Long", required = false, value = "开始时间"),
            @ApiImplicitParam(paramType = "query", name = "endTime", dataType = "Long", required = false, value = "结束时间"),
            @ApiImplicitParam(paramType = "query", name = "reviewState", dataType = "Integer", required = false, value = "诊断状态（0未诊断，1诊断完成，2诊断失败，不传的时候查询全部）"),
    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/doctorReviewFinishList")
    public ResultVO<?> doctorReviewFinishList(HttpServletRequest httpRequest,
                                              @ApiParam(value = "userAccount", required = false) String userAccount,
                                              @ApiParam(value = "startTime", required = false) Long startTime,
                                              @ApiParam(value = "endTime", required = false) Long endTime,
                                              @ApiParam(value = "reviewState", required = false) Integer reviewState,
                                              CustomPageable customPageable) {
    	ResultVO<List<DoctorReview>> resultVO = new ResultVO<List<DoctorReview>>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            Page<DoctorReview> page;
            if (startTime != null && endTime != null){
                page = doctorInfoService.findDoctorReviewFinishList(
                        userAccount,
                        new Date(startTime),
                        new Date(endTime),
                        reviewState,
                        customPageable);
            }else {
                page = doctorInfoService.findDoctorReviewFinishList(
                        userAccount,
                        null,
                        null,
                        reviewState,
                        customPageable);
            }
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(page.getResult());
            resultVO.setPage(PageUtil.convert(page));
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }

    @ApiOperation("获取用户当前认证状态")
    @ApiImplicitParams({

    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/findDoctorReviewState")
    public ResultVO<DoctorReview> doctorReviewFinish(HttpServletRequest httpRequest) {
    	ResultVO<DoctorReview> resultVO = new ResultVO<DoctorReview>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
//            DoctorInfo currentSysUser = (DoctorInfo) httpSession.getAttribute("doctorInfo");
//            String userId = ServerUtil.userIdToMobile(token, CheroRequestUtil.getUserId(httpRequest), null);
            DoctorReview doctorReview = doctorInfoService.findDoctorReviewState(CheroRequestUtil.getUserId(httpRequest));
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(doctorReview);
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }
    @ApiOperation("管理员提交审核结果")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = false, value = "id"),
            @ApiImplicitParam(paramType = "query", name = "reviewRemark", dataType = "String", required = false, value = "描述"),
            @ApiImplicitParam(paramType = "query", name = "reviewState", dataType = "Integer", required = false, value = "诊断状态（0未诊断，1诊断完成，2诊断失败）")

    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "保存失败"),
    })
    @PostMapping("/doctorReviewFinish")
    public ResultVO<DoctorReview> findDoctorReviewState(HttpServletRequest httpRequest,
                                          @ApiParam(value = "id", required = false) String id,
                                          @ApiParam(value = "reviewRemark", required = false) String reviewRemark,
                                          @ApiParam(value = "reviewState", required = false) Integer reviewState) {
    	ResultVO<DoctorReview> resultVO = new ResultVO<DoctorReview>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        String userId=CheroRequestUtil.getUserId(token);
        if (token.equals(httpSession.getAttribute("access_token"))) {
            DoctorReview doctorReview = doctorInfoService.findDoctorReview(id);
            if (reviewState == 1){
                DoctorInfo doctorInfo = new DoctorInfo();
                doctorInfo.setUserId(doctorReview.getUserId() + "");
                doctorInfo.setCertificateType(doctorReview.getCertificateType());
                doctorInfo.setRealName(doctorReview.getUserRealName());
                doctorInfo.setCertificateNumber(doctorReview.getCertificateNumber());
                doctorInfo.setCertificateNumber(doctorReview.getCertificateNumber());
                doctorInfo.setSubject(doctorReview.getSubject());
                doctorInfo.setPosition(doctorReview.getPosition());
                doctorInfo.setCreateTime(new Date());
                doctorInfo.setStatus(true);
                doctorInfoService.saveInfo(doctorInfo);
                sysManageService.saveDoctorAndDepartment(doctorReview.getUserId(), doctorReview.getDepartmentId());
            }
            DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
            doctorReview.setUpdateTime(new Date());
            doctorReview.setUpdateUser(userId);
            doctorReview.setReviewId(userId);
            doctorReview.setReviewName(doctorInfo.getRealName());
            doctorReview.setReviewState(reviewState);
            doctorReview.setReviewRemark(reviewRemark);
            doctorInfoService.updataDoctorReview(doctorReview);
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(doctorReview);
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }
    @ApiOperation("获取某条认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", required = false, value = "id"),
    })
    @ApiResponses({
            @ApiResponse(code = 310, message = "获取失败"),
    })
    @PostMapping("/getDoctorReview")
    public ResultVO<?> getDoctorReview(HttpServletRequest httpRequest, @ApiParam(value = "id", required = false) String id) {
    	ResultVO<DoctorReview> resultVO = new ResultVO<DoctorReview>();
        String token = httpRequest.getHeader("Authorization").split(" ")[1];
        if (token.equals(httpSession.getAttribute("access_token"))) {
            DoctorReview doctorReview = doctorInfoService.findDoctorReview(id);
            resultVO.setStatus(200);
            resultVO.setMessage("获取成功");
            resultVO.setContent(doctorReview);
        } else {
            resultVO.setStatus(310);
            resultVO.setMessage("获取失败");
        }
        return resultVO;
    }
}
