package com.chero.client.controller;

import com.chero.client.domain.*;
import com.chero.client.service.DoctorInfoService;
import com.chero.client.service.ReportService;
import com.chero.client.utils.CheroRequestUtil;
import com.chero.client.utils.CustomPageable;
import com.chero.client.utils.PageUtil;
import com.chero.client.utils.ResultUtil;
import com.chero.client.vo.ResultVO;
import com.github.pagehelper.Page;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@Api("ReportController相关api")
public class ReportController {

	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	DoctorInfoService doctorInfoService;

	@Autowired
	ReportService reportService;

	@Autowired
	HttpSession httpSession;

	@ApiOperation("按年统计及时性")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "year", dataType = "String", required = true, value = "年份") })
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/countTimelinessByYear")
	public ResultVO<Map<String, Integer>> countTimelinessByYear(HttpServletRequest httpRequest,
			@ApiParam("year") String year) {
		ResultVO<Map<String, Integer>> resultVO = new ResultVO<Map<String, Integer>>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			Integer totalNum;
			Integer totalTimeliness;
			totalNum = reportService.findTotalNum(year);
			totalTimeliness = reportService.findTotalTimeliness(0, year);
			Map<String, Integer> staticsMap = new HashMap<>();
			staticsMap.put("totalNum", totalNum);
			staticsMap.put("totalTimeleness", totalTimeliness);
			logger.info("【按年统计及时性接口返回数据】" + staticsMap);
			resultVO = ResultUtil.success(staticsMap);
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}

	@ApiOperation("按月统计及时性")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "year", dataType = "String", required = true, value = "年份") })
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/countTimelinessByMonth")
	public ResultVO<Map<String, List<MonthNumber>>> countTimelinessByMonth(HttpServletRequest httpRequest,
			@ApiParam("year") String year) {
		ResultVO<Map<String, List<MonthNumber>>> resultVO = new ResultVO<Map<String, List<MonthNumber>>>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			List<MonthNumber> totalNumPerMonthList = null;
			List<MonthNumber> totalTimelinessPerMonthList = null;
			totalNumPerMonthList = reportService.findTotalNumPerMonth(year);
			totalTimelinessPerMonthList = reportService.findTotalTimelinessPerMonth(0, year);
			Map<String, List<MonthNumber>> staticsMap = new HashMap<>();
			staticsMap.put("totalNumPerMonthList", totalNumPerMonthList);
			staticsMap.put("totalTimelinessPerMonthList", totalTimelinessPerMonthList);
			logger.info("【按月统计及时性接口返回数据】" + staticsMap);
			resultVO = ResultUtil.success(staticsMap);
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}

	@ApiOperation("按报告类型统计及时性")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "year", dataType = "String", required = true, value = "年份") })
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/countTimelinessByType")
	public ResultVO<Map<String, Integer[]>> countTimelinessByType(HttpServletRequest httpRequest,
			@ApiParam("year") String year) {
		ResultVO<Map<String, Integer[]>> resultVO = new ResultVO<Map<String, Integer[]>>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			Map<Integer, Integer> totalNumPerTypeMap = new HashMap<Integer, Integer>();
			Map<Integer, Integer> totalTimelinessPerTypeMap = new HashMap<Integer, Integer>();
			totalNumPerTypeMap.put(0, reportService.findTotalNumReportType(0, year));
			totalNumPerTypeMap.put(1, reportService.findTotalNumReportType(1, year));
			totalNumPerTypeMap.put(2, reportService.findTotalNumReportType(2, year));
			totalNumPerTypeMap.put(3, reportService.findTotalNumReportType(3, year));
			totalTimelinessPerTypeMap.put(0, reportService.findTotalTimelinessReportType(0, 0, year));
			totalTimelinessPerTypeMap.put(1, reportService.findTotalTimelinessReportType(1, 0, year));
			totalTimelinessPerTypeMap.put(2, reportService.findTotalTimelinessReportType(2, 0, year));
			totalTimelinessPerTypeMap.put(3, reportService.findTotalTimelinessReportType(3, 0, year));
			Map<String, Map<Integer, Integer>> staticsMap = new HashMap<>();
			staticsMap.put("totalNumPerTypeMap", totalNumPerTypeMap);
			staticsMap.put("totalTimelinessPerTypeMap", totalTimelinessPerTypeMap);
			logger.info("【按报告类型统计及时性接口返回数据】" + staticsMap);
			resultVO = ResultUtil.success(staticsMap);
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}

	@ApiOperation("按类型和月份统计及时性")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "reportType", dataType = "String", required = true, value = "报告类型（0短程心电报告，1长程心电报告，2温度周报告，3温度月报告）"),
			@ApiImplicitParam(paramType = "query", name = "year", dataType = "String", required = true, value = "年份") })
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/countTimelinessByTypeAndMonth")
	public ResultVO<Map<String, List<MonthNumber>>> countTimelinessByTypeAndMonth(HttpServletRequest httpRequest,@ApiParam("reportType") String reportType, @ApiParam("year") String year) {
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			List<MonthNumber> totalNumPerMonthList = new ArrayList<MonthNumber>();
			List<MonthNumber> totalTimelinessPerMonthList = new ArrayList<MonthNumber>();
			totalNumPerMonthList=reportService.findTotalNumPerTypeMonth(reportType, year);
			totalTimelinessPerMonthList=reportService.findTotalTimelinessPerTypeMonth(0, reportType, year);
			Map<String, List<MonthNumber>> staticsMap = new HashMap<>();
			staticsMap.put("totalNumPerMonthList", totalNumPerMonthList);
			staticsMap.put("totalTimelinessPerMonthList", totalTimelinessPerMonthList);
			return ResultUtil.success(staticsMap);
		} else {
			return ResultUtil.error(401, "该账号没有权限");
		}
	}

	@ApiOperation("按解读人统计及时性")
	@PostMapping("/countTimelinessByPerson")
	public ResultVO<Map<String, List<ReaderNumber>>> countTimelinessByPerson(HttpServletRequest httpRequest) {
		ResultVO<Map<String, List<ReaderNumber>>> resultVO = new ResultVO<Map<String, List<ReaderNumber>>>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			List<ReaderNumber> totalNumList;
			List<ReaderNumber> totalTimelinessList;
			totalNumList = reportService.findTotalNumReader();
			totalTimelinessList = reportService.findTotalTimelinessReader(0);
			Map<String, List<ReaderNumber>> staticsMap = new HashMap<>();
			staticsMap.put("totalNumList", totalNumList);
			staticsMap.put("totalTimelinessList", totalTimelinessList);
			logger.info("【按解读人统计及时性接口返回数据】" + staticsMap);
			resultVO = ResultUtil.success(staticsMap);
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}

	@ApiOperation("近一月统计及时性")
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/countTimelinessByDay")
	public ResultVO<Map<String, List<DateNumber>>> countTimelinessByDay(HttpServletRequest httpRequest) {
		ResultVO<Map<String, List<DateNumber>>> resultVO = new ResultVO<Map<String, List<DateNumber>>>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			List<DateNumber> totalNumPerDayList = null;
			List<DateNumber> totalTimelinessPerDayList = null;
			totalNumPerDayList = reportService.findTotalNumPerDay();
			totalTimelinessPerDayList = reportService.findTotalTimelinessPerDay(0);
			Map<String, List<DateNumber>> staticsMap = new HashMap<>();
			staticsMap.put("totalNumPerDayList", totalNumPerDayList);
			staticsMap.put("totalTimelinessPerDayList", totalTimelinessPerDayList);
			logger.info("【近一月统计及时性接口返回数据】" + staticsMap);
			resultVO = ResultUtil.success(staticsMap);
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}

	@ApiOperation("解读报告条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "reportStart", dataType = "Long", required = false, value = "报告时间开始"),
            @ApiImplicitParam(paramType = "query", name = "reportEnd", dataType = "Long", required = false, value = "报告时间结束"),
            @ApiImplicitParam(paramType = "query", name = "finishStart", dataType = "Long", required = false, value = "完成时间开始"),
            @ApiImplicitParam(paramType = "query", name = "finishEnd", dataType = "Long", required = false, value = "完成时间结束"),
            @ApiImplicitParam(paramType = "query", name = "patientAccount", dataType = "String", required = false, value = "患者账号"),
            @ApiImplicitParam(paramType = "query", name = "timeliness", dataType = "Integer", required = false, value = "解读及时性（0超时，1及时）"),
            @ApiImplicitParam(paramType = "query", name = "reportType", dataType = "String", required = false, value = "报告类型（【0,1】心电报告，【2,3】温度月报告）")})
    @ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
    @PostMapping("/selectByCondition")
    public ResultVO<List<Report>> selectByCondition(HttpServletRequest httpRequest,
                                                    @ApiParam(value = "reportStart", required = false) Long reportStart,
                                                    @ApiParam(value = "reportEnd", required = false) Long reportEnd,
                                                    @ApiParam(value = "finishStart", required = false) Long finishStart,
                                                    @ApiParam(value = "finishEnd", required = false) Long finishEnd,
                                                    @ApiParam(value = "patientAccount", required = false) String patientAccount,
                                                    @ApiParam(value = "timeliness", required = false) Integer timeliness,
                                                    @ApiParam(value = "reportType", required = false) String reportType,
                                                    @ApiParam(value = "customPageable", required = false) CustomPageable customPageable){
        String userId = CheroRequestUtil.getUserId(httpRequest);
        DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
        Long[] conditionLong = { reportStart, reportEnd, finishStart, finishEnd };
        Date[] conditionTime = new Date[conditionLong.length];
        for (int i = 0; i < conditionLong.length; i++) {
            if (conditionLong[i] != null){
                conditionTime[i] = new Date(conditionLong[i]);
            }
        }
        if (doctorInfo != null) {
            String[] repTypes = StringUtils.split(reportType,",");
            if (repTypes != null) {
                for (String repType : repTypes) {
                    if (repType != null && repType != "") {
                        Page<Report> reportList =reportService.findReportListByCondition(conditionTime[0], conditionTime[1],
                                conditionTime[2], conditionTime[3], patientAccount, timeliness,
                                Integer.valueOf(repType), customPageable);
                        return ResultUtil.success(reportList.getResult(), PageUtil.convert(reportList));
                    }
                }
            }
            Page<Report> reportList =reportService.findReportListByCondition(conditionTime[0], conditionTime[1], conditionTime[2], conditionTime[3], patientAccount, timeliness, null, customPageable);
            return ResultUtil.success(reportList.getResult(), PageUtil.convert(reportList));
        } else {
            return ResultUtil.error(401, "该账号没有权限");
        }
    }

	@ApiOperation("新增报告")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "reportName", dataType = "String", required = true, value = "报告名称"),
			@ApiImplicitParam(paramType = "query", name = "reportType", dataType = "Integer", required = true, value = "报告类型"),
			@ApiImplicitParam(paramType = "query", name = "patientAccount", dataType = "String", required = true, value = "患者账号"),
			@ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
			@ApiImplicitParam(paramType = "query", name = "gender", dataType = "Integer", required = true, value = "性别"),
			@ApiImplicitParam(paramType = "query", name = "age", dataType = "Integer", required = true, value = "年龄"),
			@ApiImplicitParam(paramType = "query", name = "reportTime", dataType = "Long", required = true, value = "报告类型（0短程心电报告，1长程心电报告，2温度周报告，3温度月报告）"),
			@ApiImplicitParam(paramType = "query", name = "updateTime", dataType = "Long", required = true, value = "更新时间") })
	@ApiResponses({ @ApiResponse(code = 200, message = "返回成功"), @ApiResponse(code = 321, message = "返回失败"), })
	@PostMapping("/addReport")
	public ResultVO<String> addReport(HttpServletRequest httpRequest, @ApiParam("reportName") String reportName,
			@ApiParam("reportType") Integer reportType, @ApiParam("patientAccount") String patientAccount,
			@ApiParam("name") String name, @ApiParam("gender") Integer gender, @ApiParam("age") Integer age,
			@ApiParam("reportTime") Long reportTime, @ApiParam("updateTime") Long updateTime) {
		ResultVO<String> resultVO = new ResultVO<String>();
		String userId = CheroRequestUtil.getUserId(httpRequest);
		DoctorInfo doctorInfo = doctorInfoService.findDoctorInfoByUserId(userId);
		if (doctorInfo != null) {
			Report report = new Report();
			report.setReportType(reportType);
			report.setReportName(reportName);
			report.setPatientAccount(patientAccount);
			report.setName(name);
			report.setGender(gender);
			report.setAge(age);
			report.setReader(doctorInfo.getRealName());
			report.setReportTime(new Date(reportTime));
			report.setUpdateTime(new Date(updateTime));
			report.setFinishTime(new Date());
			if ((report.getFinishTime().getTime() - report.getUpdateTime().getTime()) <= 24 * 1000 * 60 * 60) {
				report.setTimeliness(1);
			} else {
				report.setTimeliness(0);
			}
			report.setUserId(doctorInfo.getId());
			reportService.addNewReport(report);
			resultVO = ResultUtil.success();
		} else {
			resultVO = ResultUtil.error(401, "该账号没有权限");
		}
		return resultVO;
	}
}
