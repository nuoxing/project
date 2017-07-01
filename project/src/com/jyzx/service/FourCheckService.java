/*
 * 文件名：FourCheckService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月23日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.jyzx.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jyzx.common.CommonService;
import com.jyzx.po.HttpResult;
import com.jyzx.util.CommonUtil;
import com.jyzx.util.FilterParam;


/**
 *  检查企业是否是可以做简易注销的四种类型之一 
 * 
 * @author swy
 * @version 2017年2月23日
 * @see FourCheckService
 * @since
 */

@Service("fourCheckService")
public class FourCheckService extends CommonService
{

    /**
     * 
     */
    public FourCheckService()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * 描述: 检查企业类型是否是适用简易注销<br> 
     * 
     * @param regno 注册号
     * @param uniscid 统一社会代码
     * @return
     * @see
     */
    public HttpResult checkEnt(String regno, String uniscid)
    {
        HttpResult result = new HttpResult();
        boolean isExist = false;

        if (regno.equals("") && uniscid.equals(""))
        {
            result.setResult("fail");
            result.setMessage("注册号，统一社会信用代码不能同时为空");
            return result;
        }

        List<Map<String, String>> res = null;
        List<FilterParam> params = new ArrayList<FilterParam>();

        StringBuffer sbf = new StringBuffer(); // 检查内资信息表，查找企业的信息
        sbf.append("select enttype from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select enttype from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" FETCH FIRST 1 ROWS ONLY");
        res = (List<Map<String, String>>)commonDao.listAll(sbf.toString(),
            params.toArray(new FilterParam[params.size()]));
        switch (res.size())
        {
            case 0:
                result.setResult("fail");
                result.setMessage("查找不到该企业的相关信息");
                break;
            case 1:
                isExist = true;
                break;
            default:
                result.setResult("fail");
                result.setMessage("查询异常");
        }

        if (isExist)
        { // 查找到企业信息再判断是否满足四种类型
            String enttype = res.get(0).get("enttype");
            String sql = "select senttypename from TB_JYZX_DIC_ENTTYPE where SCOUNTRYCODE in (select SCOUNTRYCODE from TB_DIC_ENTITY_TYPE where senttypecode=:enttype)";
            List<Map<String,String>> lists = (List<Map<String, String>>)commonDao.listAll(sql, new FilterParam("enttype", enttype));
            if (lists.size() > 0)
            {
                result.setResult("yes");
                result.setMessage(lists.get(0).get("senttypename"));
            }
            else
            {
                result.setResult("no");
                result.setMessage("该企业的类型不适用简易注销的企业类型");
            }
            return result;
        }

        result.setResult("fail");
        result.setMessage("查找不到该企业的相关信息");
        return result;
    }

}
