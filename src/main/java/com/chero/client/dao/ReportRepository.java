package com.chero.client.dao;

import com.chero.client.domain.DateNumber;
import com.chero.client.domain.MonthNumber;
import com.chero.client.domain.ReaderNumber;
import com.chero.client.domain.Report;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportRepository {

    void addNewReport(Report report);
    Page<Report> findReportListByCondition(@Param("reportStart")Date reportStart,
                                           @Param("reportEnd")Date reportEnd,
                                           @Param("finishStart")Date finishStart,
                                           @Param("finishEnd")Date finishEnd,
                                           @Param("patientAccount")String patientAccount,
                                           @Param("timeliness")Integer timeliness,
                                           @Param("reportType")Integer reportType
                                           );
    //按年统计及时性
    Integer findTotalNum(@Param("year") String year);
    Integer findTotalTimeliness(@Param("timeliness") Integer timeliness, @Param("year") String year);
    //按月统计及时性
    List<MonthNumber> findTotalNumPerMonth(@Param("year")String year);
    List<MonthNumber> findTotalTimelinessPerMonth(@Param("timeliness")Integer timeliness,@Param("year")String year);
    //按报告类型统计及时性
    Integer findTotalNumReportType(@Param("reportType")Integer reportType,@Param("year")String year);
    Integer findTotalTimelinessReportType(@Param("reportType")Integer reportType,@Param("timeliness")Integer timeliness,@Param("year")String year);
    //按类型和月份统计及时性
    List<MonthNumber> findTotalNumPerTypeMonth(@Param("reportType")List<Integer> reportType,@Param("year")String year);
    List<MonthNumber> findTotalTimelinessPerTypeMonth(@Param("timeliness")Integer timeliness,@Param("reportType")List<Integer> reportType,@Param("year")String year);
    //按解读人统计及时性
    List<ReaderNumber> findTotalNumReader();
    List<ReaderNumber> findTotalTimelinessReader(@Param("timeliness") Integer timeliness);
    //近一月统计及时性
    List<DateNumber> findTotalNumPerDay();
    List<DateNumber> findTotalTimelinessPerDay(@Param("timeliness")Integer timeliness);
}
