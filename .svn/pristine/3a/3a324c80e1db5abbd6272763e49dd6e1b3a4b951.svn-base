package com.chero.client.service;

import com.chero.client.domain.DateNumber;
import com.chero.client.domain.MonthNumber;
import com.chero.client.domain.ReaderNumber;
import com.chero.client.domain.Report;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;

import java.util.Date;
import java.util.List;

public interface ReportService {

    void addNewReport(Report report);
    Page<Report> findReportListByCondition(Date reportStart, Date reportEnd, Date finishStart, Date finishEnd, String patientAccount, Integer timeliness, Integer reportType, CustomPageable customPageable);
    //按年统计及时性
    Integer findTotalNum(String year);
    Integer findTotalTimeliness(Integer timeliness,String year);
    //按月统计及时性
    List<MonthNumber> findTotalNumPerMonth(String year);
    List<MonthNumber> findTotalTimelinessPerMonth(Integer timeliness,String year);
    //按报告类型统计及时性
    Integer findTotalNumReportType(Integer reportType,String year);
    Integer findTotalTimelinessReportType(Integer reportType,Integer timeliness,String year);
    //按类型和月份统计及时性
    List<MonthNumber> findTotalNumPerTypeMonth(String reportType,String year);
    List<MonthNumber> findTotalTimelinessPerTypeMonth(Integer timeliness,String reportType,String year);
    //按解读人统计及时性
    List<ReaderNumber> findTotalNumReader();
    List<ReaderNumber> findTotalTimelinessReader(Integer timeliness);
    //近一月统计及时性
    List<DateNumber> findTotalNumPerDay();
    List<DateNumber> findTotalTimelinessPerDay(Integer timeliness);
}
