/*
 * 文件名：NeedApproService.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年3月1日
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
 * 法律，行政法规或者国务院决定规定在注销登记前需经批准的
 * 
 * @author suwy
 * @version 2017年3月1日
 * @see NeedApproService
 * @since
 */
@Service("needApproService")
public class NeedApproService extends CommonService
{

    /**
     * 
     */
    public NeedApproService()
    {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * 
     * 描述: 企业是否是法律，行政法规或者国务院决定规定在注销登记前需经批准的<br>
     * 
     * @param regno 注册号
     * @param uniscid 统一社会信用代码
     * @return json串
     * @see
     */
    public HttpResult checkNeedApprove(String regno,String uniscid)
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
        sbf.append("select entname,opscope from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select entname,opscope from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
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

        if (isExist)
        {
            String entname = CommonUtil.NullToEmp(res.get(0).get("entname"));
            String opscope = CommonUtil.NullToEmp(res.get(0).get("opscope"));
            if(entname.contains("银行")||entname.contains("保险")||entname.contains("证券")
                ||opscope.contains("电影和影视节目制作")||opscope.contains("其他烟草制品制造")
                ||opscope.contains("烟叶复烤")||opscope.contains("卷烟制造"))
            {
                result.setResult("yes");
                result.setMessage("该企业是法律，行政法规或者国务院决定规定在注销登记前需经批准的");
            }
            else
            {
                result.setResult("no");
                result.setMessage("该企业不是法律，行政法规或者国务院决定规定在注销登记前需经批准的");
            }
        }
        return result;
    }

}
