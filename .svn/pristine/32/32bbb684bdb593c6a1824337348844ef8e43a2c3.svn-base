package com.chero.client.utils;

import com.github.pagehelper.Page;

/**
 * Created by hxh on 2018/5/16.
 */
public class PageUtil {
    /**
     * 获取分页起始条数
     *
     * @param page 传递的查询参数
     * @return
     */

    public static PageVO convert(Page page) {

         PageVO pageVO = new PageVO();
        pageVO.setSize(page.getPageSize());
        pageVO.setTotalElements(page.getTotal());
        pageVO.setTotalPages(page.getPages());
        pageVO.setNumber(page.getPageNum());
         return pageVO;
    }

}
