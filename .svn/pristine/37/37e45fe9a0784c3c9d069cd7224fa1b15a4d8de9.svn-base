package com.chero.client.dao;

import com.chero.client.domain.*;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface SysManageRepository {

    List<DoctorInfo> selectDoctorInfoAll(@Param("realName")String realName, @Param("status")Boolean status, @Param("departmentId")String departmentId);

    Page<DoctorInfo> selectDoctorInfoAllUser(@Param("realName")String realName, @Param("subject")String subject, @Param("status")Boolean status, @Param("userId")String userId);

    DoctorInfo findDoctorInfo(String id);

    List<String> findUserDepartment(@Param("userId")String userId);

    void addDoctorInfo(DoctorInfo DoctorInfo);

    void saveDoctorInfo(DoctorInfo DoctorInfo);

    void deleteDoctorInfo(String id);
    
    void updateDoctorInfoByArray(String idz);

    void addDoctorAndDepartment(@Param("userId") String userId, @Param("departmentId") String departmentId);

    void deleteDoctorAndDepartment(@Param("userId")String userId);

    List<Department> selectDepartment();

    Department findDepartment(String id);
    
    Integer findOrganizationById(String id);
    
    DoctorInfo findPersonMobile(String mobile);

    void addDepartment(Department department);
    
    void addAffiche(Affiche affiche);
    
    Affiche findAffiche(String id);
    
    void saveAffiche(Affiche affiche);
    
    void deleteAffiche(String id);

    void saveDepartment(Department department);

    Page<DoctorRole> selectDoctorRole(@Param("role") String role,@Param("status") String status,@Param("startTime") String startTime,@Param("endTime") String endTime);

    Page<DoctorRole> findDoctorRole();
    DoctorRole findDoctorRoleById(String roleId);
    List<String> findRoleByUserId(@Param("userId") String userId);
    List<String> findDepartmentByUserId(@Param("userId") String userId);
    DoctorInfo findDoctorInfoByUserId(@Param("userId") String userId);

    void addDoctorRole(@Param("role") String role,
                       @Param("status") Integer status,
                       @Param("privilegeListString") String privilegeListString,
                       @Param("createUser") String createUser,
                       @Param("createTime") Date createTime );

    void saveDoctorRole(@Param("id") String id, @Param("role") String role,@Param("status") String status, @Param("privilegeListString") String privilegeListString);

    int findRoleCount(@Param("roleId") String roleId);

    void deleteDoctorRoleAndUser(@Param("roleId") String roleId);

    void deleteDoctorRole(String id);

    void deleteTotalDoctorRolePers(@Param("roleId") String roleId);

    void updateTotalDoctorRolePers(@Param("roleId") String roleId,@Param("userList") List<String> DoctorInfoListString);

    void updateTotalDoctorRolePriv(@Param("roleId") String id,@Param("privilegeListString") String privilegeListString);

    Page<CrLogger> selectOperateLogger(@Param("operator") String operator, @Param("operateAccount") String operateAccount, @Param("startTime") String startTime, @Param("endTime") String endTime);
    
    OperateLogger findOperateLogger(String id);

    Setting findSystemSetting();

    List<DoctorInfo> findDoctorInfoOpetion(String id);

    List<PersonOpetion> findDoctorInfoByArray(String idz);

    List<PersonOpetion> findAllDoctorInfo();

    void saveSystemSetting(Setting setting);

    void saveUserHelpUser(@Param("suggest") String suggest,@Param("userId") String userId);

    void addOperateLogger(@Param("operateTime")Date operateTime,
                          @Param("operateContent")String operateContent,
                          @Param("operateType")String operateType,
                          @Param("operator")String operator,
                          @Param("operateAccount")String operateAccount,
                          @Param("operation")String operation,
                          @Param("operationBefore")String operationBefore,
                          @Param("operationAfter")String operationAfter);

    void deleteDepartment(String id);

    /**
     * 查询部门含有几个下级部门
     * @param departmentId
     * @return
     */
    int findDepartmentSubordinateCount(@Param("departmentId") String departmentId);

    /**
     * 查询部门含有几个员工
     * @param departmentId
     * @return
     */

    int findDepartmentUserCount(@Param("departmentId") String departmentId);
}
