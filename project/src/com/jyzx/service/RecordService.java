/*
 * 文件名：RecordService.java
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
 * 企业是否有正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的
 * 
 * @author Administrator
 * @version 2017年3月1日
 * @see RecordService
 * @since
 */

@Service("recordService")
public class RecordService extends CommonService
{


    @Autowired
    private JinXinDbService jinXinDbService;
    /**
     * 
     */
    public RecordService()
    {
        // TODO Auto-generated constructor stub
    }

    public HttpResult checkIsRecord(String regno,String uniscid)
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
        sbf.append("select regno,uniscid,entname from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append("select regno,uniscid,entname from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
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
            String nregno = res.get(0).get("regno");
            String nuniscid = res.get(0).get("uniscid");
            String sql = "SELECT count(*) FROM tb_ilc_pun_info_confirm WHERE ZCH!='' AND ZCH is not null and (ZCH=? or ZCH=?) AND SFLAG='0'";
            Object[] param = {nregno,nuniscid};
            int n = jinXinDbService.getCount(sql, param);
            if(n>0)
            {
                result.setResult("yes");
                result.setMessage("该企业存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的");
                return result;
            }
            //在注册号，统一码查询不出内容的情况下，使用企业名称查询
            sql = "SELECT count(*) FROM tb_ilc_pun_info_confirm WHERE DSRMC=? AND SFLAG='0'";
            Object[] param2 = {res.get(0).get("entname")};
            n = jinXinDbService.getCount(sql, param2);
            if(n>0)
            {
                result.setResult("yes");
                result.setMessage("该企业存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的");
                return result;
            }
            else
            {
                result.setResult("no");
                result.setMessage("该企业不存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的");
            }
        }
        
       return result;
    }
}
