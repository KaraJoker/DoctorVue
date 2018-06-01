package com.chero.client.schedule;


import com.chero.client.dao.DoctorInfoRepository;
import com.chero.client.dao.MessageRepository;

import com.chero.client.domain.DoctorInfo;
import com.chero.client.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Slf4j
@Component
public class ScheduledTasks {
	

	@Autowired
	private MessageService messageService;

	@Autowired
//	private RedisService redisService;

	/**
	 * 回收站定时
	 */
//	@Scheduled(cron="22 2 2 * * ?")
//	@LoggerManage(description="回收站定时")
//    public void autoRecovery() {
//		Calendar ca = Calendar.getInstance();
//		ca.setTime(new Date());
//		ca.add(Calendar.DAY_OF_YEAR,-30);
//		Long date = ca.getTime().getTime();
//		List<Long> favoritesId = favoritesRespository.findIdByName("未读列表");
//		List<Collect> collectList = collectRespository.findByCreateTimeLessThanAndIsDeleteAndFavoritesIdIn(date, IsDelete.NO,favoritesId);
//		for(Collect collect : collectList){
//			try {
//				log.info("文章id:" + collect.getId());
//				collectRespository.modifyIsDeleteById(IsDelete.YES, DateUtils.getCurrentTime(), collect.getId());
//				favoritesRespository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
//			} catch (Exception e) {
//				log.error("回收站定时任务异常：",e);
//			}
//		}
//    }

	@Scheduled(cron="0 0/1 8-20 * * ?") // 间隔1分钟,执行公告发送
	public void sendMessage(){
		log.info("执行公告时间 , date={}", new Date());
		messageService.sendAnnouncement();

	}

/*	@Scheduled(cron="11 11 0 * * ?")
	@LoggerManage(description="查询收藏夹放到缓存定时")
	public void putRedisCollector() {
		try {
			IndexCollectorView indexCollectorView = collectorService.getCollectors();
			redisService.setObject("collector", indexCollectorView);
		}catch (Exception e){
			log.error("查询收藏夹放到缓存定时任务异常：",e);
		}
	}*/

}
