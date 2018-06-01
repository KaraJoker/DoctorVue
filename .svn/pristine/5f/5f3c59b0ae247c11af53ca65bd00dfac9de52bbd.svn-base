package com.chero.client.service.impl;

import com.chero.client.dao.DoctorInfoRepository;
import com.chero.client.dao.DoctorReviewRepository;
import com.chero.client.dao.SysManageRepository;
import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.DoctorReview;
import com.chero.client.domain.SystemRole;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.utils.CheroRequestUtil;
import com.chero.client.utils.CustomPageable;
import com.chero.client.utils.ServerURLConstant;
import com.chero.client.utils.ServerUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Service
public class DoctorInfoServiceImpl implements DoctorInfoService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DoctorInfoRepository doctorInfoRepository;

    @Autowired
    DoctorReviewRepository doctorReviewRepository;

    @Autowired
    SysManageRepository sysManageRepository;

    @Override
    public void updateInfo(DoctorInfo doctorInfo) {
        doctorInfoRepository.updateInfo(doctorInfo);
    }

    @Override
    public void saveInfo(DoctorInfo doctorInfo) {
        doctorInfo.setCreateTime(new Date());
        doctorInfoRepository.saveInfo(doctorInfo);
    }

    @Override
    public void addDoctorReview(DoctorReview doctorReview) {
        doctorReviewRepository.addDoctorReview(doctorReview);
    }

    @Override
    public Page<DoctorReview> findDoctorReviewList(String userAccount, Date startTime, Date endTime, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        Page<DoctorReview> page = doctorReviewRepository.findDoctorReviewList(userAccount, startTime, endTime);
        List<DoctorReview> list = page.getResult();
        for (DoctorReview doctorReview : list) {
            doctorReview.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorReview.getUserId(), null));

        }
        return page;
    }

    @Override
    public Page<DoctorReview> findDoctorReviewFinishList(String userAccount, Date startTime, Date endTime, Integer reviewState, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        Page<DoctorReview> page = doctorReviewRepository.findDoctorReviewFinishList(userAccount, startTime, endTime, reviewState);
        List<DoctorReview> list = page.getResult();
        for (DoctorReview doctorReview : list) {
            doctorReview.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorReview.getUserId(), null));
        }
        return page;
//            return null;
    }

    @Override
    public Page<DoctorReview> findDoctorReviewUserList(String userId, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        Page<DoctorReview> page = doctorReviewRepository.findDoctorReviewUserList(userId);
        List<DoctorReview> list = page.getResult();
        for (DoctorReview doctorReview : list) {
            doctorReview.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorReview.getUserId(), null));

        }
        return page;
    }

    @Override
    public DoctorReview findDoctorReview(String Id) {
        DoctorReview doctorReview = doctorReviewRepository.findOne(Id);
        if (doctorReview != null) {
            doctorReview.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorReview.getUserId(), null));
        }
        return doctorReview;
    }

    @Override
    public void updataDoctorReview(DoctorReview doctorReview) {
        doctorReviewRepository.updataDoctorReview(doctorReview);
    }

    @Override
    public DoctorReview findDoctorReviewState(String userId) {
        DoctorReview doctorReview = doctorReviewRepository.findDoctorReviewState(userId);
        if (doctorReview != null) {
            doctorReview.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorReview.getUserId(), null));
        }
        return doctorReview;
    }

    @Override
    public String[] doctorLookRole(String id) {
        return doctorInfoRepository.doctorLookRole(id);
    }

    @Override
    public List<DoctorInfo> findAllDoctorInfo() {
        List<DoctorInfo> list = doctorInfoRepository.findAll();
        for (DoctorInfo doctorInfo : list) {
            doctorInfo.setDepartmentId(sysManageRepository.findDepartmentByUserId(doctorInfo.getUserId()));
        }
        return list;
    }

    @Override
    public DoctorInfo findDoctorInfoByUserId(String userId) {
        DoctorInfo doctorInfo = doctorInfoRepository.findDoctorInfoByUserId(userId);
        if (doctorInfo == null) {
            return doctorInfo;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Authorization", "bearer " + httpSession.getAttribute("access_token"));
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
        String restful_api_findUser = ServerURLConstant.RESTFUL_API_FINDUSER;
        ResponseEntity<Object> data = restTemplate.exchange(restful_api_findUser, HttpMethod.GET, httpEntity, Object.class);
        LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) data.getBody();
        doctorInfo.setUserId(userId);
        doctorInfo.setMobile(((Map<String, String>) responseMap.get("content")).get("mobile"));
        doctorInfo.setDepartmentId(sysManageRepository.findDepartmentByUserId(doctorInfo.getUserId()));
        return doctorInfo;
    }

    @Override
    public DoctorInfo findByMobile(String mobile) {
        return doctorInfoRepository.findByMobile(mobile);
    }

    @Override
    public void update(DoctorInfo doctorInfo) {
        doctorInfoRepository.update(doctorInfo);
    }

    @Override
    public void add(DoctorInfo doctorInfo) {
        doctorInfoRepository.add(doctorInfo);
    }

    @Override
    public List<SystemRole> findSystemRoleByUserId(String userId) {
        return doctorInfoRepository.findSystemRoleByUserId(userId);
    }

    @Override
    public boolean isAdminRole() {
        String accessToken = String.valueOf(httpSession.getAttribute("access_token"));
        String userId = CheroRequestUtil.getUserId(accessToken);
        String roleId = doctorInfoRepository.findAdminRoleIdByUserId(userId);
        if (roleId != null && !"".equals(roleId)) {
            return true;
        } else {
            return false;
        }
    }
}
