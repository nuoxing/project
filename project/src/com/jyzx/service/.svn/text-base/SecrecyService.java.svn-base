/*
 * 文件名：SecrecyService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月23日
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
 * 检查企业是否是保密企业,保密企业不能做简易注销
 * @author suwy
 * @version 2017年2月23日
 * @see SecrecyService
 * @since
 */

@Service("secrecyService")
public class SecrecyService extends CommonService
{

    /**
     * 
     */
    public SecrecyService()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * 描述: 检查企业是否是保密企业,保密企业不能做简易注销<br> 
     * 
     * @param regno 注册号
     * @param uniscid 统一社会代码
     * @return
     * @see
     */
    public HttpResult checkIsApply(String regno, String uniscid)
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
        sbf.append("select pripid from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select pripid from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
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
        {
            String pripid = res.get(0).get("pripid");
            String sql = "select 1 from TB_ZC_COM_SECRETENT where pripid=:pripid";
            int num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
            if (num > 0)
            {
                result.setResult("yes");
                result.setMessage("该企业属于保密企业");
            }
            else
            {
                result.setResult("no");
                result.setMessage("该企业不属于保密企业");
            }
            return result;
        }

        result.setResult("fail");
        result.setMessage("查找不到该企业的相关信息");
        return result;
    }

}
