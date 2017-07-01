/*
 * 文件名：TotalService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月24日
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
 * 总接口的service
 * 
 * @author suwy
 * @version 2017年2月24日
 * @see TotalService
 * @since
 */

@Service("totalService")
public class TotalService extends CommonService
{

    @Autowired
    private JinXinDbService jinXinDbService;

    /**
     * 
     */
    public TotalService()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * 描述: <br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会代码
     * @param progress
     *            简易注销是否正在进行，1是进行中 0是刚开始 必传 0查询执法的表 1是什么什么审核中心取
     * @return
     * @see
     */

    public HttpResult checkIsCancel(String regno, String uniscid, String progress)
    {
        HttpResult result = new HttpResult();;
        if (regno.equals("") && uniscid.equals(""))
        {
            result.setResult("fail");
            result.setMessage("注册号，统一社会信用代码不能同时为空");
            return result;
        }

        if (progress == null || (!progress.equals("0") && !progress.equals("1")))
        {
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("参数异常");
            return result;
        }

        List<Map<String, String>> res = null;
        List<FilterParam> params = new ArrayList<FilterParam>();
        StringBuffer sbf = new StringBuffer(); // 检查内资信息表，查找企业的信息
        sbf.append(
            "select enttype,regno,pripid,uniscid,entname,opscope from TB_ZC_NZ_BASEINFO where ENTSTATE LIKE '003%'");
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" union all ");
        sbf.append(
            "select enttype,regno,pripid,uniscid,entname,opscope  from TB_ZC_WZ_BASEINFO where ENTSTATE LIKE '003%'");// 再查外资表
        sbf.append(CommonUtil.handlerCondition(regno, uniscid, params));
        sbf.append(" FETCH FIRST 1 ROWS ONLY");
        res = (List<Map<String, String>>)commonDao.listAll(sbf.toString(),
            params.toArray(new FilterParam[params.size()]));
        if (res.size() == 0)
        {
            result.setResult("fail");
            result.setMessage("查找不到该企业的相关信息");
            return result;
        }
        if (!isFourEntType(res.get(0).get("enttype")))
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，不满足注销的企业类型");
            return result;
        }
        if (isApprove(res.get(0).get("entname"), res.get(0).get("opscope")))
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，该企业是法律，行政法规或者国务院决定规定在注销登记前需经批准的");
            return result;
        }
        if (progress.equals("0") && isSecrecy(res.get(0).get("pripid")))
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业是保密企业");
            return result;
        }
        if (progress.equals("1"))// 暂留，需要调用接口
        {

        }
        int num = isEnrol(res.get(0).get("regno"));
        if (num == 1)
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业被列入经营异常名录");
            return result;
        }
        if (num == 2)
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业存在严重违法记录");
            return result;
        }
        num = isFreeze(res.get(0).get("pripid"), res.get(0).get("regno"),
            res.get(0).get("uniscid"));
        if (num == 1)
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业存在股权（投资权益）被冻结");
            return result;
        }
        if (num == 2)
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业存在股权出质");
            return result;
        }
        if (num == 3)
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，企业存在动产抵押");
            return result;
        }
        if (isRecord(res.get(0).get("regno"), res.get(0).get("uniscid"),res.get(0).get("entname")))
        {
            result.setResult("no");
            result.setMessage("不能做简易注销，该企业存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的");
            return result;
        }
        if(isEnd(res.get(0).get("pripid")))
        {
            result.setResult("no");
            result.setMessage("该企业曾被终止简易注销程序");
            return result;
        }
        result.setResult("yes");
        result.setMessage("可以简易注销");
        return result;
    }

    /**
     * 描述: 企业是否是四种企业类型<br>
     * 
     * @param enttype
     * @return
     * @see
     */
    public boolean isFourEntType(String enttype)
    {
        String sql = "select senttypename from TB_JYZX_DIC_ENTTYPE where SCOUNTRYCODE in (select SCOUNTRYCODE from TB_DIC_ENTITY_TYPE where senttypecode=:enttype)";
        List<Map<String, String>> lists = (List<Map<String, String>>)commonDao.listAll(sql,
            new FilterParam("enttype", enttype));
        if (lists.size() > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 描述: 企业是不是保密企业<br>
     * 
     * @param pripid 主体身份代码
     * @return
     * @see
     */
    public boolean isSecrecy(String pripid)
    {
        String sql = "select 1 from TB_ZC_COM_SECRETENT where pripid=:pripid";
        int num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
        if (num > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 描述: 查询企业是否被列入企业经营异常名录或严重违法失信企业名单的企业<br>
     * 
     * @param nregno
     * @return
     * @see
     */
    public int isEnrol(String nregno)
    {
        // 经营异常名录
        String sql = "select count(*) from TB_SHS_ENTITY where sregno=? and sremovetype is null";
        Object[] param = {nregno};
        int num = jinXinDbService.getCount(sql, param);
        if (num > 0)
        {
            return 1;
        }

        // 严重违法：
        sql = "select count(*) from TB_YZWF_APPROVE where sregno=? and ssptype='001' and  isdelete='0'";
        num = jinXinDbService.getCount(sql, param);
        if (num > 0)
        {
            return 2;
        }
        return 0;
    }

    /**
     * 描述:查询企业是否存在股权（投资权益）被冻结，出质或动产抵押等情形 <br>
     * 
     * @param pripid
     *            主体身份代码
     * @param nregno
     *            注册号
     * @param nuniscid
     *            统一社会信用代码
     * @return
     * @see
     */
    public int isFreeze(String pripid, String nregno, String nuniscid)
    {
        String sql = "select 1 from TB_JUDICIAL_ASSISTANCE_FROZEN where pripid=:pripid and froto>current timestamp and frozstate='1'";
        int num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
        if (num > 0)
        {
            return 1;
        }
        sql = "select 1 from TB_ZC_COM_SHARESIMPAWN where pripid=:pripid and impstate='1'";
        num = commonDao.countAll(sql, new FilterParam("pripid", pripid));
        if (num > 0)
        {
            return 2;
        }
        sql = "SELECT count(*) FROM TB_CNTR_MTG_BASEINFO WHERE  SCERTNO!='' and SCERTNO is not null and (SCERTNO=? or SCERTNO=?) AND NOT EXISTS "
              + "(SELECT 1 FROM TB_CNTR_MTG_LOGOUTINFO WHERE TB_CNTR_MTG_LOGOUTINFO.STRANSID=TB_CNTR_MTG_BASEINFO.STRANSID)";
        Object[] param = {nregno, nuniscid};
        int n = jinXinDbService.getCount(sql, param);
        if (n > 0)
        {
            return 3;
        }
        return 0;
    }

    /**
     * 描述: 企业是否有正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的，行政处罚信息<br>
     * 
     * @param nregno
     *            注册号
     * @param nuniscid
     *            统一社会信用代码
     * @return
     * @see
     */
    public boolean isRecord(String nregno, String nuniscid,String entname)
    {
        String sql = "SELECT count(*) FROM tb_ilc_pun_info_confirm WHERE ZCH!='' and ZCH is not null and (ZCH=? or ZCH=?) AND SFLAG='0'";
        Object[] param = {nregno, nuniscid};
        int n = jinXinDbService.getCount(sql, param);
        if (n > 0)
        {
            return true;
        }
        
        //在注册号，统一码查询不出内容的情况下，使用企业名称查询
        sql = "SELECT count(*) FROM tb_ilc_pun_info_confirm WHERE DSRMC=? AND SFLAG='0'";
        Object[] param2 = {entname};
        n = jinXinDbService.getCount(sql, param2);
        if(n>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 描述:企业是否是法律，行政法规或者国务院决定规定在注销登记前需经批准的 <br>
     * 
     * @param entname
     *            企业名称
     * @param opscope
     *            经营范围
     * @return true or false
     * @see
     */
    public boolean isApprove(String entname, String opscope)
    {
        entname = CommonUtil.NullToEmp(entname);
        opscope = CommonUtil.NullToEmp(opscope);
        if (entname.contains("银行") || entname.contains("保险") || entname.contains("证券")
            || opscope.contains("电影和影视节目制作") || opscope.contains("其他烟草制品制造")
            || opscope.contains("烟叶复烤") || opscope.contains("卷烟制造"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    /**
     * 
     * 描述: 检查企业是否曾被终止简易注销程序<br>
     * 
     * @param pripid
     * @return 
     * @see
     */
    public boolean isEnd(String pripid)
    {
        String sql = "SELECT 1 FROM TB_ZC_EASY_LOGOUT_INFO WHERE PRIPID=:pripid AND EASYLOGOUT='1' AND (RESULT='2' OR RESULT='3' OR RESULT='5' OR RESULT='6')";
        int num = commonDao.countAll(sql, new FilterParam("pripid",pripid));
        if(num>0) //被终止
        {
            return true;
        }
        else
        {
           return false;
        }
    }
}
