/*
 * 文件名：CommonUtil.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年2月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.jyzx.util;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2017年2月23日
 * @see CommonUtil
 * @since
 */

public class CommonUtil
{

    /**
     * 
     */
    public CommonUtil()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 
     * 描述: <br>
     * 
     * @return 
     * @see
     */
    public static String NullToEmp(String str)
    {
        if(str==null)
        {
            return "";
        }
        return str.trim();
    }
    
    public static String handlerCondition(String regno,String  uniscid,List<FilterParam> params)
    {
        StringBuffer sbf = new StringBuffer();
        if(!regno.equals("")){
            sbf.append(" and regno=:regno");
            FilterParam par_1 = new FilterParam("regno", regno);
            params.add(par_1);
        }
        if(!uniscid.equals("")){
           sbf.append(" and uniscid=:uniscid");
            FilterParam par_2 = new FilterParam("uniscid", uniscid);
            params.add(par_2);
        }
        return sbf.toString();
    }
    

}
