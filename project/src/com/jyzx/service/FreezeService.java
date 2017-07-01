/*
 * 文件名：FreezeService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jyzx.common.CommonService;
import com.jyzx.otherDBSource.JinXinDbService;
import com.jyzx.po.HttpResult;
import com.jyzx.util.CommonUtil;
import com.jyzx.util.FilterParam;

/**
 * 存在股权（投资权益）被冻结，出质或动产抵押等情形
 * 〈功能详细描述〉
 * @author suwy
 * @version 2017年3月1日
 * @see FreezeService
 * @since
 */

@Service("freezeService")
public class FreezeService  extends CommonService
{

    @Autowired
    private JinXinDbService jinXinDbService;
    /**
     * 
     */
    public FreezeService()
    {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * 
     * 描述: 查询企业是否存在股权（投资权益）被冻结，出质或动产抵押等情形<br>
     * 
     * @param regno 注册号
     * @param uniscid 统一社会信用代码
     * @return json串 
     * @see
     */
    public HttpResult checkIsGjDy(String regno,String uniscid)
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
        sbf.append("select pripid,regno,uniscid from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select pripid,regno,uniscid from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
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
            //是否存在股权（投资权益）被冻结
            String pripid = res.get(0).get("pripid");
            String nregno = res.get(0).get("regno");
            String nuniscid = res.get(0).get("uniscid");
            String sql = "select 1 from TB_JUDICIAL_ASSISTANCE_FROZEN where pripid=:pripid and froto>current timestamp and frozstate='1'";
            int num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
            if (num > 0)
            {
                result.setResult("yes");
                result.setMessage("该企业存在股权（投资权益）被冻结");
                return result;
                
            }
            sql = "select 1 from TB_ZC_COM_SHARESIMPAWN where pripid=:pripid and impstate='1'";
            num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
            if (num > 0)
            {
                result.setResult("yes");
                result.setMessage("该企业存在股权出质");
                return result;
                
            }
            
            sql = "SELECT count(*) FROM TB_CNTR_MTG_BASEINFO WHERE SCERTNO!='' and SCERTNO is not null and (SCERTNO=? or SCERTNO=?) AND NOT EXISTS "
                + "(SELECT 1 FROM TB_CNTR_MTG_LOGOUTINFO WHERE TB_CNTR_MTG_LOGOUTINFO.STRANSID=TB_CNTR_MTG_BASEINFO.STRANSID)";
            Object[] param = {nregno,nuniscid};
            int n = jinXinDbService.getCount(sql,param);
            if(n>0)
            {
                result.setResult("yes");
                result.setMessage("该企业存在动产抵押");
                return result;
            }
        }
        
        result.setResult("no");
        result.setMessage("该企业不存在股权（投资权益）被冻结，出质或动产抵押等情形");
        return result;
    }

}
