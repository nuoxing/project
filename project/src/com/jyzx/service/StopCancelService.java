/*
 * 文件名：StopCancelService.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年3月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
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
 * 检查企业是否曾被终止简易注销程序的接口
 * @author suwy
 * @version 2017年3月7日
 * @see StopCancelService
 * @since
 */

@Service("stopCancelService")
public class StopCancelService extends CommonService
{

    /**
     * 
     */
    public StopCancelService()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 描述: 检查企业是否曾被终止简易注销程序的<br> 
     * 
     * @param regno 注册号
     * @param uniscid 统一社会代码
     * @return
     * @see
     */
    public HttpResult checkEnd(String regno, String uniscid)
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
        sbf.append("SELECT pripid FROM TB_ZC_NZ_BASEINFO WHERE ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("SELECT pripid FROM TB_ZC_WZ_BASEINFO WHERE ENTSTATE LIKE '003%'");// 再查外资表
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" FETCH FIRST 1 ROWS ONLY");
        res = (List<Map<String, String>>)commonDao.listAll(sbf.toString(),
            params.toArray(new FilterParam[params.size()]));
        switch (res.size())
        {
            case 0:
                result.setResult("fail");
                result.setMessage("查找不到该企业的相关信息");
                return result;
            case 1:
                isExist = true;
                break;
            default:
                result.setResult("fail");
                result.setMessage("查询异常");
        }
        if(isExist)
        {
            String pripid = res.get(0).get("pripid");
            String sql = "SELECT 1 FROM TB_ZC_EASY_LOGOUT_INFO WHERE PRIPID=:pripid AND EASYLOGOUT='1' AND   (RESULT='2' OR RESULT='3' OR RESULT='5' OR RESULT='6')";
            int num = commonDao.countAll(sql, new FilterParam("pripid",pripid));
            if(num>0)
            {
                result.setMessage("该企业曾被终止简易注销程序");
                result.setResult("yes");
            }
            else
            {
                result.setMessage("该企业不曾被终止简易注销程序");
                result.setResult("no");
            }
        }
        return result;
        
    }

}
