package com.chero.client.controller;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.SystemRole;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.service.SysManageService;
import com.chero.client.utils.CheroRequestUtil;
import com.chero.client.utils.ResultUtil;
import com.chero.client.utils.ServerURLConstant;
import com.chero.client.vo.ResultVO;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/users")
@Api("LoginController相关API")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DoctorInfoService doctorInfoService;
    @Autowired
    HttpSession httpSession;
    @Autowired
    SysManageService sysManageService;


    @ApiOperation("注册发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "手机号")
    })
    @GetMapping("/smsSendeRegister")
    public ResultVO<LinkedHashMap<String, Object>> smsSendeRegister(@ApiParam("mobile") String mobile) {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        String restful_api_smsSendeRegister = ServerURLConstant.RESTFUL_API_SMSSENDEREGISTER + "?mobile=" + mobile + "&isRegister=" + false;
        Object data = restTemplate.getForObject(restful_api_smsSendeRegister, Object.class);
//			responseMap数据格式{
//			  "status": 0,
//			  "message": "成功",
//			  "content": {
//			    "code": "919503",
//			    "status": 0,
//			    "username": "18268075654"
//			  }
//			}
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data;
        if (responseMap.get("status").equals(0)) {
            httpSession.setAttribute("mobile", mobile);
            httpSession.setAttribute("smsCode", responseMap.get("code"));
            httpSession.setAttribute("isRegister", false);
            resultVO = ResultUtil.success(responseMap);
            logger.info("【注册发送短信接口返回数据】" + responseMap);
        } else {
            resultVO = ResultUtil.error((Integer) responseMap.get("status"), (String) responseMap.get("message"));
        }
//		}
        return resultVO;
    }

    @ApiOperation("发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "手机号", defaultValue = "18268075654"),
    })
    @GetMapping("/smsSende")
    public ResultVO<LinkedHashMap<String, Object>> smsSender(@ApiParam("mobile") String mobile) {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        String restful_api_smsSend = ServerURLConstant.RESTFUL_API_SMSSENDEREGISTER + "?mobile=" + mobile + "&isRegister=" + true;
        Object data = restTemplate.getForObject(restful_api_smsSend, Object.class);
//			responseMap数据格式{
//			  "status": 0,
//			  "message": "成功",
//			  "content": {
//			    "code": "919503",
//			    "status": 0,
//			    "username": "18268075654"
//			  }
//			}
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data;
        if (responseMap.get("status").equals(0)) {
            httpSession.setAttribute("mobile", mobile);
            httpSession.setAttribute("smsCode", responseMap.get("code"));
            httpSession.setAttribute("isRegister", true);
            resultVO = ResultUtil.success(responseMap);
            logger.info("【注册发送短信接口返回数据】" + responseMap);
        } else {
            resultVO = ResultUtil.error((Integer) responseMap.get("status"), (String) responseMap.get("message"));
        }
        return resultVO;
    }

    @ApiOperation("用户名密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户名"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")
    })
    @PostMapping("/login")
    public Object login(@ApiParam("username") String username, @ApiParam("password") String password) {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        String auth = "cherotest:cherotest"; // 认证的原始信息
        byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        String authHeader = "Basic " + new String(encodeAuth); // 在进行授权的头信息内容配置的时候加密的信息一定要与"Basic"之间有一个空格
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", authHeader);
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", username);
        postParameters.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        String restful_api_login = ServerURLConstant.RESTFUL_API_LOGIN;
        Object data = null;
        try {
            data = restTemplate.postForObject(restful_api_login, httpEntity, Object.class);
        } catch (Exception ex) {
            resultVO = ResultUtil.error(321, "账号密码错误");
            return resultVO;
        }
        Map<String, Object> stringMap = (Map<String, Object>) data;
        if (stringMap.get("status").equals(0)) {
            String token = (String) stringMap.get("access_token");
            httpSession.setAttribute("access_token", token);
            String userId = CheroRequestUtil.getUserId(token);
            DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
            if (doctorInfo != null){
                if (doctorInfo.getStatus() != null && !doctorInfo.getStatus()){
                    return ResultUtil.error(4, "该账号已被禁用，请联系该机构管理员！");
                }
            }
            httpSession.setAttribute("doctorInfo", doctorInfo);
            List<SystemRole> roleList = doctorInfoService.findSystemRoleByUserId(userId);
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("access_token", token);
            contentMap.put("role", roleList);
            contentMap.put("doctorInfo", doctorInfo);
            resultVO = ResultUtil.success(contentMap);
            return resultVO;
        } else {
            return stringMap;
        }
    }

    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")
    })
    @PostMapping("/registry")
    public ResultVO<LinkedHashMap<String, Object>> registry(@ApiParam("password") String password) throws Exception {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("mobile", String.valueOf(httpSession.getAttribute("mobile")));
        postParameters.add("password", password);
        postParameters.add("smsCode", String.valueOf(httpSession.getAttribute("smsCode")));
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters);
        String restful_api_registry = ServerURLConstant.RESTFUL_API_REGISTRY;
        Object data = restTemplate.postForObject(restful_api_registry, httpEntity, Object.class);
//		responseMap数据格式{
//		  "status": 0,
//		  "message": "成功",
//		  "content": {
//		    "updateTime": 1527143297674,
//		    "mainRole": {
//		      "userId": "2c906f016390c205016390d5728a0007",
//		      "mainUserId": "2c906f016390c205016390d5728a0007",
//		      "nickname": null,
//		      "avatar": null,
//		      "clientId": null,
//		      "updateTime": 1527143297676,
//		      "gender": null,
//		      "birth": "1969-12-28",
//		      "height": null,
//		      "weight": 45.5,
//		      "mobile": null,
//		      "medicalHistory": null,
//		      "roleId": "2c906f016390c205016390d5728c0008",
//		      "isMainRole": null,
//		      "isMain": null
//		    },
//		    "userId": "2c906f016390c205016390d5728a0007",
//		    "mobile": "18268075656",
//		    "isMain": true
//		  }
//		}
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data;
        if (responseMap.get("status").equals(0)) {
            Map<String, Object> mapObjList = (Map<String, Object>) responseMap.get("content");
            DoctorInfo doctorInfo = new DoctorInfo();
            doctorInfo.setUserId((String) mapObjList.get("userId"));
            doctorInfo.setDeleteFlag(0);
            doctorInfo.setIsRegister(1);
            doctorInfo.setIsTemporary(0);
            doctorInfoService.add(doctorInfo);
            logger.info("【注册接口返回数据】" + responseMap.get("content"));
            resultVO = ResultUtil.success(responseMap.get("content"));
        } else {
            resultVO = ResultUtil.error((Integer) responseMap.get("status"), (String) responseMap.get("message"));
        }
        return resultVO;
    }

    @ApiOperation("短信验证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户名", defaultValue = "18268075654"),
            @ApiImplicitParam(paramType = "query", name = "smsCode", dataType = "String", required = true, value = "验证码", defaultValue = "666")
    })
    @PostMapping("/validateCode")
    public ResultVO<LinkedHashMap<String, Object>> validateCode(@ApiParam("mobile") String mobile, @ApiParam("smsCode") String smsCode) throws Exception {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        if (smsCode.equals(httpSession.getAttribute("smsCode"))) {
            httpSession.setAttribute("mobile", mobile);
            resultVO = ResultUtil.success();
        } else {
            resultVO = ResultUtil.error(321, "验证码不正确");
        }
        logger.info("【短信验证返回数据】" + resultVO);
//		resultVO返回数据类型{
//		  "status": 0,
//		  "message": "成功"
//		}
        return resultVO;
    }

    @ApiOperation("忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码", defaultValue = "666")
    })
    @PostMapping("/findPwd")
    public ResultVO<LinkedHashMap<String, Object>> findPwd(@ApiParam("password") String password) throws Exception {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("mobile", httpSession.getAttribute("mobile").toString());
        postParameters.add("smsCode", httpSession.getAttribute("smsCode").toString());
        postParameters.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, null);
        String restful_api_changePwd = ServerURLConstant.RESTFUL_API_CHANGEPWD;
        ResultVO data = restTemplate.postForObject(restful_api_changePwd, httpEntity, ResultVO.class);
//		responseMap数据格式格式"status": 0,
//	    "message": "成功",
//	    "content": {
//	      "updateTime": 1527150531783,
//	      "userId": "2c906f016391199e01639140dd0c001c",
//	      "mobile": "18268071111",
//	      "isMain": true
//	    }
        if (data.getStatus() == 0) {
            data.setStatus(200);
        }
        return data;
    }

    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "oldPassword", dataType = "String", required = true, value = "旧密码"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "新密码"),
    })
    @PostMapping("/changePwd")
    public ResultVO<LinkedHashMap<String, Object>> changePwd(HttpServletRequest httpRequest, @ApiParam("oldPassword") String oldPassword, @ApiParam("password") String password) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token"));
        MultiValueMap<String, Object> getParameters = new LinkedMultiValueMap<>();
        getParameters.add("oldPassword", oldPassword);
        getParameters.add("password", password);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(getParameters, headers);
        String restful_api_findPwd = ServerURLConstant.RESTFUL_API_FINDPWD;
        ResultVO data = restTemplate.postForObject(restful_api_findPwd, httpEntity, ResultVO.class);
        logger.info("【修改密码接口返回数据】" + data);
        if (data.getStatus() == 0) {
            data.setStatus(200);
        }
        return data;
    }

    @ApiOperation("查看该用户信息")
    @GetMapping("/findUser")
    public ResultVO<LinkedHashMap<String, Object>> findUser() throws Exception {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        if (httpSession.getAttribute("access_token") != null) {
            headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token"));
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
            String restful_api_findUser = ServerURLConstant.RESTFUL_API_FINDUSER;
            ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.GET, httpEntity, Object.class);
            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
            logger.info("【查看该用户信息接口返回数据】" + responseMap.get("content"));
            resultVO = ResultUtil.success(responseMap.get("content"));
        } else {
            resultVO = ResultUtil.error(321, "请重新登录!");
        }
        return resultVO;
    }

    @ApiOperation("查看该用户详细信息（医生信息）")
    @GetMapping("/findUserDetail")
    public ResultVO<LinkedHashMap<String, Object>> findUserDetail() throws Exception {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        if (httpSession.getAttribute("access_token") != null) {
            headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token"));
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
            String restful_api_findUserDetail = ServerURLConstant.RESTFUL_API_FINDUSERDETAIL;
            ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUserDetail, HttpMethod.GET, httpEntity, Object.class);
            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
            logger.info("【查看该用户详细信息（医生信息）接口返回数据】" + responseMap.get("content"));
            resultVO = ResultUtil.success(responseMap.get("content"));
        } else {
            resultVO = ResultUtil.error(321, "请重新登录!");
        }
        return resultVO;
    }

    @ApiOperation("更换手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户名"),
            @ApiImplicitParam(paramType = "query", name = "smsCode", dataType = "String", required = true, value = "验证码")
    })
    @PostMapping("/updateMobile")
    public ResultVO<LinkedHashMap<String, Object>> validatepdateMobile(HttpServletRequest httpRequest,
                                                                       @ApiParam(value = "mobile") String mobile,
                                                                       @ApiParam("smsCode") String smsCode) {
        ResultVO<LinkedHashMap<String, Object>> resultVO = new ResultVO<LinkedHashMap<String, Object>>();
        if (smsCode.equals(httpSession.getAttribute("smsCode"))) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token").toString());
            MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
            postParameters.add("mobile", mobile);
            postParameters.add("smsCode", smsCode);
            String restful_api_validatepdateMobile = ServerURLConstant.RESTFUL_API_VALIDATEPDATEMOBILE;
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
            ResultVO data = restTemplate.postForObject(restful_api_validatepdateMobile, httpEntity, ResultVO.class);
            if (data.getStatus().equals(0)) {
                httpSession.invalidate();
                logger.info("【更换手机号接口返回数据】" + data);
                resultVO = ResultUtil.success(data);
            } else {
                logger.info("【更换手机号接口返回数据】" + data);
                resultVO = ResultUtil.error(data.getStatus(), data.getMessage());
            }
        } else {
            resultVO = ResultUtil.error(321, "验证失败");
        }
        return resultVO;
    }

    @ApiOperation("退出")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 404, message = "请求路径或页面跳转错误")
    })
    @GetMapping("/logout")
    public ResultVO<?> logout() {
        ResultVO<?> resultVO = new ResultVO<>();
        logger.info("【用户退出】" + httpSession.getAttribute("d"));
        httpSession.invalidate();
        resultVO.setStatus(200);
        resultVO.setMessage("退出成功");
        return resultVO;
    }

    @ApiOperation("app服务器获取医生姓名")
    @ApiResponses({
            @ApiResponse(code = 400, message = "参数错误"),
            @ApiResponse(code = 404, message = "请求路径或页面跳转错误")
    })
    @GetMapping("/getDoctorName")
    public String getDoctorName(@ApiParam(value = "doctorId") String doctorId) {
        DoctorInfo doctorInfo = sysManageService.findDoctorInfo(doctorId);
        if (doctorInfo == null) {
            return "";
        } else if (doctorInfo.getRealName() == null || doctorInfo.getRealName().equals("")) {
            return "";
        }
        return doctorInfo.getRealName();
    }
}
