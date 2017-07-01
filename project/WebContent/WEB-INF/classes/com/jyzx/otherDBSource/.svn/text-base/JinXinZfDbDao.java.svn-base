/*
 * 文件名：EnrolService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月23日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.jyzx.otherDBSource;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * 〈一句话功能简述〉 链接金信执法数据库的Dao 〈功能详细描述〉
 * 
 * @author Administrator
 * @version 2017年2月23日
 * @see EnrolService
 * @since
 */
public class JinXinZfDbDao extends JdbcDaoSupport
{

    public JinXinZfDbDao()
    {

    }

    /**
     * 执行 delete insert update语句
     * 
     * @param sql
     * @param param
     * @return
     */
    public int saveAndUpdateInfo(final String sql, final Object[] param)
    {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        int n = jdbcTemplate.update(new PreparedStatementCreator()
        {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                throws SQLException
            {
                PreparedStatement ps = connection.prepareStatement(sql);
                if (param != null && param.length > 0)
                {
                    for (int i = 0; i < param.length; i++ )
                    {
                        ps.setObject(i + 1, param[i]);
                    }
                }
                return ps;
            }
        });
        return n;
    }

    /**
     * 返回sql查询结果的数量 select count(0)这种
     * 
     * @param sql
     * @param param
     * @return
     */
    public int queryNum(final String sql, final Object[] param)
    {
        return this.getJdbcTemplate().queryForInt(sql, param);
    }
}
