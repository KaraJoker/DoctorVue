package com.chero.client.service;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.domain.DoctorReview;
import com.chero.client.domain.SystemRole;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;
import java.util.Date;
import java.util.List;

public interface DoctorInfoService {
	DoctorInfo findDoctorInfoByUserId(String userId);
	DoctorInfo findByMobile(String mobile);
	List<SystemRole> findSystemRoleByUserId(String userId);

    /**
     * 当前角色是否是管理员
     * @return
     */
	boolean isAdminRole();
	void update(DoctorInfo doctorInfo);
	void add(DoctorInfo doctorInfo);
    void updateInfo(DoctorInfo doctorInfo);
    void saveInfo(DoctorInfo doctorInfo);
    String[] doctorLookRole(String id);

    /**
     * 查询所有人员列表
     */
    List<DoctorInfo> findAllDoctorInfo();

    /**
     * 增加医生认证记录
     * @param doctorReview
     */
     void addDoctorReview(DoctorReview doctorReview);
    /**
     * 查询待审核医生列表
     */
    Page<DoctorReview> findDoctorReviewList(String userAccount, Date startTime, Date endTime, CustomPageable customPageable);
    /**
     * 查询已审核医生列表
     */
    Page<DoctorReview> findDoctorReviewFinishList(String userAccount, Date startTime, Date endTime, Integer reviewState, CustomPageable customPageable);
    /**
     * 查询某人审核列表
     */
    Page<DoctorReview> findDoctorReviewUserList(String userId, CustomPageable customPageable);
    /**
     * 获取某人数据
     */
    public DoctorReview findDoctorReview(String Id);
    /**
     * 获取某人数据
     */
    void updataDoctorReview(DoctorReview doctorReview);
    /**
     * 获取某人当前认证状态
     */
    DoctorReview findDoctorReviewState(String userId);
}
