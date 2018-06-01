package com.chero.client.service.impl;

import com.chero.client.dao.SysManageRepository;
import com.chero.client.domain.*;
import com.chero.client.service.SysManageService;
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

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class SysManageServiceImpl implements SysManageService {

    @Autowired
    SysManageRepository sysManageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private HttpSession httpSession;


    @Override
    public List<DoctorInfo> selectDoctorInfoAll(String mobile, String name, String departmentId, Boolean status, CustomPageable customPageable) {
        List<DoctorInfo> list = sysManageRepository.selectDoctorInfoAll(name, status, departmentId);
        List<String> userIdList = new ArrayList<>();
        for (DoctorInfo doctorInfo : list){
            userIdList.add(doctorInfo.getUserId());
        }
        if (mobile != null){
            List<String> user = ServerUtil.userIdsToMobile(String.valueOf(httpSession.getAttribute("access_token")), userIdList, mobile);
            Iterator it = list.iterator();
            while(it.hasNext()){
                DoctorInfo d = (DoctorInfo) it.next();
                for (int i = 0; i < user.size(); i++){
                    if (user.get(i).equals(d.getUserId())){
                        break;
                    }
                    if (i == (user.size() - 1)){
                        it.remove();
                    }
                }
            }

        }
        return list;
    }

    @Override
    public Page<DoctorInfo> selectDoctorInfoAllUser(String mobile, String name, String subject, Boolean status, String userId, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        Page<DoctorInfo> page = sysManageRepository.selectDoctorInfoAllUser(name, subject, status, userId);
        List<DoctorInfo> list = page.getResult();
        for (DoctorInfo doctorInfo : list){
            doctorInfo.setDepartmentId(sysManageRepository.findDepartmentByUserId(doctorInfo.getUserId()));
        }
        return page;
    }

    @Override
    public DoctorInfo findDoctorInfo(String id) {
        DoctorInfo doctorInfo = sysManageRepository.findDoctorInfo(id);
        if (String.valueOf(httpSession.getAttribute("access_token")) != null &&
                !String.valueOf(httpSession.getAttribute("access_token")).equals("")) {
            doctorInfo.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorInfo.getUserId(), null));
        }
        return doctorInfo;
    }

    @Override
    public List<String> findUserDepartment(String userId) {
        return sysManageRepository.findUserDepartment(userId);
    }

    @Override
    public void addDoctorInfo(DoctorInfo doctorInfo) {
        sysManageRepository.addDoctorInfo(doctorInfo);
    }

    @Override
    public void saveDoctorInfo(DoctorInfo doctorInfo) {
        sysManageRepository.saveDoctorInfo(doctorInfo);
    }

    @Override
    public void addDoctorAndDepartment(String userId, String departmentId) {
        String[] departments = departmentId.split(",");
        for (String d : departments){
            try {                                      //
                sysManageRepository.addDoctorAndDepartment(userId, d);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public void saveDoctorAndDepartment(String userId, String departmentId) {
        sysManageRepository.deleteDoctorAndDepartment(userId);
        String[] departments = departmentId.split(",");
        for (String d : departments){
            sysManageRepository.addDoctorAndDepartment(userId, d);
        }
    }

    @Override
    public void deleteDoctorInfo(String id) {
        sysManageRepository.deleteDoctorInfo(id);
    }

    @Override
    public List<Department> selectDepartment() {
        return sysManageRepository.selectDepartment();
    }

    @Override
    public Department findDepartment(String id) {
        return sysManageRepository.findDepartment(id);
    }

    @Override
    public void addDepartment(Department department) {
        sysManageRepository.addDepartment(department);
    }

    @Override
    public void saveDepartment(Department department) {
        sysManageRepository.saveDepartment(department);
    }


    @Override
    public Page<DoctorRole> selectDoctorRole(String role, String status, String startTime, String endTime, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        Page<DoctorRole> page = sysManageRepository.selectDoctorRole(role, status, startTime, endTime);
        List<DoctorRole> list = page.getResult();
        for (DoctorRole doctorRole : list){
            int count = sysManageRepository.findRoleCount(doctorRole.getRoleId());
            doctorRole.setRoleNumber(count);
            DoctorInfo doctorInfo = sysManageRepository.findDoctorInfoByUserId(doctorRole.getCreator());
            if (doctorInfo != null){
                doctorRole.setCreator(doctorInfo.getRealName());
            }
        }
        return page;
    }

    @Override
    public Page<DoctorRole> findDoctorRole(CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        return sysManageRepository.findDoctorRole();
    }

    @Override
    public DoctorRole findDoctorRoleById(String roleId) {
        return sysManageRepository.findDoctorRoleById(roleId);
    }

    @Override
    public DoctorInfo findDoctorInfoById(String userId) {
        return sysManageRepository.findDoctorInfoByUserId(userId);
    }

    @Override
    public void addDoctorRole(String role, Integer status, String userId, String... privilegeList) {
        StringBuffer sb = new StringBuffer();
//        sb.append("{");
        for (int i = 0; i < privilegeList.length; i++) {
            sb.append(privilegeList[i] + ",");
        }
        String privilegeListString = sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        sysManageRepository.addDoctorRole(role, status, privilegeListString, userId, new Date());
    }

    @Override
    public void saveDoctorRole(String id, String role, String status, String... privilegeList) {
        StringBuffer sb = new StringBuffer();
//        sb.append("{");
        for (int i = 0; i < privilegeList.length; i++) {
            sb.append(privilegeList[i] + ",");
        }
        String privilegeListString = sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        sysManageRepository.saveDoctorRole(id, role, status, privilegeListString);
    }

    @Override
    public void deleteDoctorRole(String id) {
//       1 删除角色用户关联表 ，2删除角色表
        sysManageRepository.deleteDoctorRole(id);
    }

    @Override
    public void updateTotalDoctorRolePers(String roleId, String userList) {
        StringBuffer sb = new StringBuffer();
//        sb.append("{");
        String[] doctorInfoArray = userList.split(",");
        for (int i = 0; i < doctorInfoArray.length; i++){
             DoctorInfo doctorInfo = sysManageRepository.findDoctorInfo(doctorInfoArray[i]);
            doctorInfoArray[i] = doctorInfo.getUserId();
        }
        //先删除关联表相关关联，再进行重新关联
        sysManageRepository.deleteTotalDoctorRolePers(roleId);
        sysManageRepository.updateTotalDoctorRolePers(roleId, Arrays.asList(doctorInfoArray));
    }

    @Override
    public void updateTotalDoctorRolePriv(String id, String list) {
        StringBuffer sb = new StringBuffer();
//        sb.append("{");
//        for (int i = 0; i < list.length; i++) {
//            sb.append(list[i] + ",");
//        }
//        String[] doctorInfoArray = list.split(",");
//        String doctorPrivilegeList = sb.deleteCharAt(sb.lastIndexOf(",")).toString();
        sysManageRepository.updateTotalDoctorRolePriv(id, list);
    }

    @Override
    public Page<CrLogger> selectOperateLogger(String operator, String operateAccount, String startTime, String endTime, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        return sysManageRepository.selectOperateLogger(operator, operateAccount, startTime, endTime);
    }

    @Override
    public OperateLogger findOperateLogger(String id) {
        return sysManageRepository.findOperateLogger(id);
    }


    @Override
    public Setting findSystemSetting() {
        return sysManageRepository.findSystemSetting();
    }

    @Override
    public List<DoctorInfo> findDoctorInfoOpetion(String id) {
        List<DoctorInfo> list = sysManageRepository.findDoctorInfoOpetion(id);
        for (DoctorInfo doctorInfo : list){
            doctorInfo.setDepartmentId(sysManageRepository.findDepartmentByUserId(doctorInfo.getUserId()));
            doctorInfo.setMobile(ServerUtil.userIdToMobile(String.valueOf(httpSession.getAttribute("access_token")), doctorInfo.getUserId(), null));
        }
        return list;
    }

    @Override
    public List<PersonOpetion> findDoctorInfoByArray(String ids) {
        List<PersonOpetion> personOpetionList=new ArrayList<PersonOpetion>();
        for(String id:ids.split(",")) {
            List<PersonOpetion> list = sysManageRepository.findDoctorInfoByArray(id);
            for (PersonOpetion personOpetion : list){
                DoctorInfo doctorInfo = sysManageRepository.findDoctorInfo(personOpetion.getDoctorinfoId());
//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Content-Type", "application/x-www-form-urlencoded");
//                headers.add("Authorization", "bearer "+httpSession.getAttribute("access_token"));
//                HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(null, headers);
//                String restful_api_findUser= ServerURLConstant.RESTFUL_API_FINDUSER;
//                ResponseEntity<Object> data=restTemplate.exchange(restful_api_findUser, HttpMethod.GET, httpEntity, Object.class);
//                LinkedHashMap<String, Object> responseMap=(LinkedHashMap<String, Object>) data.getBody();
                String mobile = ServerUtil.userIdToMobile((String) httpSession.getAttribute("access_token"), doctorInfo.getUserId(), null);
                personOpetion.setMobile(mobile);
//                personOpetion.setMobile(((Map<String,String>)responseMap.get("content")).get("mobile"));
            }
            Iterator it = list.iterator();
            while(it.hasNext()){
                PersonOpetion p = (PersonOpetion) it.next();
                for (PersonOpetion personOpetion : personOpetionList){
                    if (p.getDoctorinfoId().equals(personOpetion.getDoctorinfoId())){
                        it.remove();
                        break;
                    }
                }
            }
            personOpetionList.addAll(list);
        }
        return personOpetionList;
    }

    @Override
    public List<PersonOpetion> findAllDoctorInfo() {
        return sysManageRepository.findAllDoctorInfo();
    }

    @Override
    public void saveSystemSetting(Setting setting) {
        sysManageRepository.saveSystemSetting(setting);
    }

    @Override
    public void saveUserHelpUser(String suggest, String userId) {
        sysManageRepository.saveUserHelpUser(suggest, userId);
    }

    @Override
    public void addOperateLogger(Date operateTime,
                                 String operateContent,
                                 String operateType,
                                 String operator,
                                 String operateAccount,
                                 String operation,
                                 String operationBefore,
                                 String operationAfter) {
        sysManageRepository.addOperateLogger(operateTime, operateContent, operateType, operator, operateAccount, operation, operationBefore, operationAfter);
    }

    @Override
    public void deleteDepartment(String id) {
        sysManageRepository.deleteDepartment(id);
    }

    @Override
    public int findDepartmentSubordinateCount(String departmentId) {
        return sysManageRepository.findDepartmentSubordinateCount(departmentId);
    }

    @Override
    public int findDepartmentUserCount(String departmentId) {
        return sysManageRepository.findDepartmentUserCount(departmentId);
    }

    @Override
	public DoctorInfo findPersonMobile(String mobile) {
//        List<DoctorInfo> list = sysManageRepository.selectDoctorInfoAll(null, null, null);
//        List<String> userIds = new ArrayList<>();
//        for (DoctorInfo userId : list){
//            userIds.add(userId.getUserId());
//        }
        String userId = ServerUtil.mobileToUserId((String) httpSession.getAttribute("access_token"), mobile);
//        sysManageRepository.findPersonMobile(mobile);
        if (userId != null && !userId.equals("")){
            DoctorInfo doctorInfo = sysManageRepository.findDoctorInfoByUserId(userId);
            doctorInfo.setDepartmentId(sysManageRepository.findDepartmentByUserId(doctorInfo.getUserId()));
            doctorInfo.setMobile(mobile);
            return doctorInfo;
        }else {
            return null;
        }
	}

	@Override
	public void addAffiche(Affiche affiche) {
		sysManageRepository.addAffiche(affiche);
	}

	@Override
	public Affiche findAffiche(String id) {
		return sysManageRepository.findAffiche(id);
	}

	@Override
	public void saveAffiche(Affiche affiche) {
		sysManageRepository.saveAffiche(affiche);
	}

	@Override
	public void deleteAffiche(String id) {
		sysManageRepository.deleteAffiche(id);
	}

	@Override
	public Integer findOrganizationById(String id) {
		// TODO Auto-generated method stub
		return sysManageRepository.findOrganizationById(id);
	}

	@Override
	public void updateDoctorInfoByArray(String ids) {
//        todo 暂时这样，   应该使用<foreach> 在xml里
        for(String id:ids.split(",")) {
//            Integer idz=Integer.parseInt(id);
            sysManageRepository.updateDoctorInfoByArray(id);
        }
	}
}
