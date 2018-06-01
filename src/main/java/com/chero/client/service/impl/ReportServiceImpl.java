package com.chero.client.service.impl;

import com.chero.client.dao.ReportRepository;
import com.chero.client.domain.DateNumber;
import com.chero.client.domain.MonthNumber;
import com.chero.client.domain.ReaderNumber;
import com.chero.client.domain.Report;
import com.chero.client.service.ReportService;
import com.chero.client.utils.CustomPageable;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportRepository reportRepository;

    @Override
    public void addNewReport(Report report) {
        reportRepository.addNewReport(report);
    }
    @Override
    public Page<Report> findReportListByCondition(Date reportStart, Date reportEnd, Date finishStart, Date finishEnd, String patientAccount, Integer timeliness, Integer reportType, CustomPageable customPageable) {
        PageHelper.startPage(customPageable.getPageId(), customPageable.getSize());
        return  reportRepository.findReportListByCondition(reportStart,reportEnd,finishStart,finishEnd,patientAccount,timeliness,reportType);
    }

    @Override
    public Integer findTotalNum(String year) {
        return reportRepository.findTotalNum(year);
    }
    @Override
    public Integer findTotalTimeliness(Integer timeliness, String year) {
        return reportRepository.findTotalTimeliness(timeliness,year);
    }

    @Override
    public List<MonthNumber> findTotalNumPerMonth(String year) {
        return reportRepository.findTotalNumPerMonth(year);
    }
    @Override
    public List<MonthNumber> findTotalTimelinessPerMonth(Integer timeliness, String year) {
        return reportRepository.findTotalTimelinessPerMonth(timeliness,year);
    }

    @Override
    public Integer findTotalNumReportType(Integer reportType, String year) {
        return reportRepository.findTotalNumReportType(reportType,year);
    }
    @Override
    public Integer findTotalTimelinessReportType(Integer reportType, Integer timeliness, String year) {
        return reportRepository.findTotalTimelinessReportType(reportType,timeliness,year);
    }

    @Override
    public List<MonthNumber> findTotalNumPerTypeMonth(String reportType, String year) {
    	String[] repTypes = StringUtils.split(reportType,",");
    	List<Integer> repType=new ArrayList<Integer>();
        if (repTypes != null) {
            for (int i=0;i<repTypes.length;i++) {
            	repType.add(Integer.valueOf(repTypes[i]));
            }
        }
        return reportRepository.findTotalNumPerTypeMonth(repType,year);
    }
    @Override
    public List<MonthNumber> findTotalTimelinessPerTypeMonth(Integer timeliness, String reportType, String year) {
    	String[] repTypes = StringUtils.split(reportType,",");
    	List<Integer> repType=new ArrayList<Integer>();
        if (repTypes != null) {
            for (int i=0;i<repTypes.length;i++) {
            	repType.add(Integer.valueOf(repTypes[i]));
            }
        }
        return reportRepository.findTotalTimelinessPerTypeMonth(timeliness,repType,year);
    }

    @Override
    public List<DateNumber> findTotalNumPerDay() {
        return reportRepository.findTotalNumPerDay();
    }
    @Override
    public List<DateNumber> findTotalTimelinessPerDay(Integer timeliness) {
        return reportRepository.findTotalTimelinessPerDay(timeliness);
    }

    @Override
    public List<ReaderNumber> findTotalNumReader() {
        return reportRepository.findTotalNumReader();
    }
    @Override
    public List<ReaderNumber> findTotalTimelinessReader(Integer timeliness) {
        return reportRepository.findTotalTimelinessReader(timeliness);
    }
}
