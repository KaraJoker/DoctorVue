package com.chero.client.service;

import com.chero.client.domain.*;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface SysManageService {

    List<DoctorInfo> selectDoctorInfoAll(String mobile, String realName,String departmentId,Boolean status, CustomPageable customPageable);

    Page<DoctorInfo> selectDoctorInfoAllUser(String mobile, String name, String subject, Boolean status, String userId, CustomPageable customPageable);

    DoctorInfo findDoctorInfo(String id);

    List<String> findUserDepartment(String userId);

    void addDoctorInfo(DoctorInfo DoctorInfo);

    void saveDoctorInfo(DoctorInfo DoctorInfo);

    void addDoctorAndDepartment(String userId, String departmentId);

    void saveDoctorAndDepartment(String userId, String departmentId);

    void deleteDoctorInfo(String id);

    List<Department> selectDepartment();

    Department findDepartment(String id);

    void addDepartment(Department department);
    
    void addAffiche(Affiche affiche);
    
    Affiche findAffiche(String id);
    
    void saveAffiche(Affiche affiche);
    
    void deleteAffiche(String id);
    
    Integer findOrganizationById(String id);
    
    DoctorInfo findPersonMobile(String mobile);

    void saveDepartment(Department department);
    
    void updateDoctorInfoByArray(String ids);

    Page<DoctorRole> selectDoctorRole(String role, String status, String startTime, String endTime, CustomPageable customPageable);

    Page<DoctorRole> findDoctorRole(CustomPageable customPageable);
    DoctorRole findDoctorRoleById(String id);
    DoctorInfo findDoctorInfoById(String id);
    void addDoctorRole(String role, Integer status,  String userId, String... privilegeList);

    void saveDoctorRole(String id,String role, String status, String...privilegeList);

    void deleteDoctorRole(String id);

    void updateTotalDoctorRolePers(String roleId, String DoctorInfoArray);

    void updateTotalDoctorRolePriv(String id, String privilegeArray);

    Page<CrLogger> selectOperateLogger(String operator,String operateAccount,String startTime,String endTime, CustomPageable customPageable);
    OperateLogger findOperateLogger(String id);

    Setting findSystemSetting();

    List<DoctorInfo> findDoctorInfoOpetion(String id);

    List<PersonOpetion> findDoctorInfoByArray(String ids);

    List<PersonOpetion> findAllDoctorInfo();

    void saveSystemSetting(Setting setting);

    void saveUserHelpUser(String suggest,String userId);

    void addOperateLogger(Date operateTime,
                          String operateContent,
                          String operateType,
                          String operator,
                          String operateAccount,
                          String operation,
                          String operationBefore,
                          String operationAfter);

    void deleteDepartment(String id);

    int findDepartmentSubordinateCount(String departmentId);

    int findDepartmentUserCount(String departmentId);

}
