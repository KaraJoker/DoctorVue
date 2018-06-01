package com.chero.client.dao;

import com.chero.client.domain.DoctorReview;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface DoctorReviewRepository extends BaseRepository<DoctorReview>{

    /**
     * 新增
     * @param doctorReview
     */
    void addDoctorReview(DoctorReview doctorReview);

    /**
     *
     * @param userAccount
     * @param startTime
     * @param endTime
     * @return
     */
    Page<DoctorReview> findDoctorReviewList(
        @Param("userAccount")String userAccount,
        @Param("startTime")Date startTime,
        @Param("endTime")Date endTime
    );
    Page<DoctorReview> findDoctorReviewFinishList(
            @Param("userAccount")String userAccount,
            @Param("startTime")Date startTime,
            @Param("endTime")Date endTime,
            @Param("reviewState")Integer reviewState
    );
    Page<DoctorReview> findDoctorReviewUserList(
            @Param("userId")String userId
    );
    DoctorReview findOne(
            @Param("Id")String Id
    );
    void updataDoctorReview(DoctorReview doctorReview);

    DoctorReview findDoctorReviewState(@Param("userId")String userId);
}
