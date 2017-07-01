/*
 * 文件名：EnrolService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月23日
 * 跟踪单号： 修改单号： 修改内容：
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
 * 检查是否被列入企业经营异常名录或严重违法失信企业名单的；不能做简易注销
 * @author suwy
 * @version 2017年2月23日
 * @see EnrolService
 * @since
 */
@Service("enrolService")
public class EnrolService extends CommonService
{

    @Autowired
    private JinXinDbService jinXinDbService;

    /**
     * 
     */
    public EnrolService()
    {}

    /*
     * 描述: 被列入企业经营异常名录或严重违法失信企业名单的；不能做简易注销<br> 
     * @param regno 注册号 
     * @param uniscid 统一社会代码
     * @return 结果
     * @see
     */
    public HttpResult checkIsEnrol(String regno, String uniscid)
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
        sbf.append("select regno from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select regno from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
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
            String nregno = res.get(0).get("regno");
            // 经营异常名录
            String sql = "select count(*) from TB_SHS_ENTITY where sregno=?  and sremovetype is null";
            Object[] param = {nregno};
            int num = jinXinDbService.getCount(sql, param);
            if (num > 0)
            {
                result.setResult("yes");
                result.setMessage("该企业在经营异常名录中有记录");
                return result;
            }

            // 严重违法：
            sql = "select count(*) from TB_YZWF_APPROVE where sregno=? and ssptype='001' and  isdelete='0'";
            num = jinXinDbService.getCount(sql, param);
            if (num > 0)
            {
                result.setResult("yes");
                result.setMessage("该企业在严重违法中有记录");
                return result;
            }
            result.setResult("no");
            result.setMessage("没有被列入");
            return result;
        }

        return result;
    }
    
    

}
