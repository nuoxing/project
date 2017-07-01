/*
 * 文件名：EnrolService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月23日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.jyzx.otherDBSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyzx.service.EnrolService;


/**
 * 〈一句话功能简述〉 〈功能详细描述〉操作金信执法库的service
 * 
 * @author Administrator
 * @version 2017年2月23日
 * @see EnrolService
 * @since
 */
@Service("jinXinDbService")
public class JinXinDbService
{

    @Autowired
    private JinXinZfDbDao jinXinZfDbDao;

    public int getCount(String sql, Object[] param)
    {
        return jinXinZfDbDao.queryNum(sql, param);
    }

}
