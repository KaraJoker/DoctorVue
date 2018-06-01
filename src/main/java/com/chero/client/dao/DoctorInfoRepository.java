package com.chero.client.dao;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.SystemRole;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DoctorInfoRepository extends BaseRepository<DoctorInfo>{

	Integer countAll();
	List<String> findAllUsedUserId();
	DoctorInfo findByMobile(@Param("mobile")String mobile);
	DoctorInfo findDoctorInfoByUserId(@Param("userId")String userId);
	List<SystemRole> findSystemRoleByUserId(@Param("userId")String userId);
	String findAdminRoleIdByUserId(@Param("userId")String userId);

    void updateInfo(DoctorInfo doctorInfo);
    void saveInfo(DoctorInfo doctorInfo);
    String[] doctorLookRole(@Param("id")String id);
}
