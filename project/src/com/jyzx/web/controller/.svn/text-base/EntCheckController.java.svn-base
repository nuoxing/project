/*
 * 文件名：单独.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月22日 跟踪单号： 修改单号：
 * 修改内容：
 */

package com.jyzx.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jyzx.po.HttpResult;
import com.jyzx.service.EnrolService;
import com.jyzx.service.FourCheckService;
import com.jyzx.service.FreezeService;
import com.jyzx.service.NeedApproService;
import com.jyzx.service.RecordService;
import com.jyzx.service.SecrecyService;
import com.jyzx.service.StopCancelService;
import com.jyzx.service.SynInfoService;
import com.jyzx.service.TotalService;
import com.jyzx.util.CommonUtil;


/**
 * 简易注销的controller
 * 
 * @author suwy
 * @version 2017年2月22日
 * @see 单独
 * @since
 */

@Controller
@EnableWebMvc
public class EntCheckController
{

    Log log = LogFactory.getLog( this .getClass());
    
    @Autowired
    private FourCheckService fourCheckService;

    @Autowired
    private SecrecyService secrecyService;

    @Autowired
    private EnrolService enrolService;

    @Autowired
    private TotalService totalService;

    @Autowired
    private FreezeService freezeService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private NeedApproService needApproService;
    
    @Autowired
    private StopCancelService stopCancelService;
   
    
    @Autowired
    private SynInfoService synInfoService;
    

    public EntCheckController()
    {}

    /**
     * 描述:查询企业是否可以简易注销总接口<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @param progress
     *            简易注销是否正在进行，1是进行中 0是刚开始 必传 0查询执法的表 1是什么什么审核中心取
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkIsCancel")
    @ResponseBody
    public HttpResult checkIsCancel(String regno, String uniscid, String progress)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        System.out.println("nreg=" + nreg);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = totalService.checkIsCancel(nreg, nuniscid, progress);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }

    /**
     * 描述: 检查企业是否是四种可以做简易注销的企业类型<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkType")
    @ResponseBody
    public HttpResult checkEntType(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = fourCheckService.checkEnt(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }

    /**
     * 描述: 检查企业是否,不适用企业简易注销的其他企业（保密企业） 是查保密企业，保密企业不能做简易注销<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkIsApply")
    @ResponseBody
    public HttpResult checkIsApply(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = secrecyService.checkIsApply(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }

    /**
     * 描述: 检查企业是否被列入企业经营异常名录或严重违法失信企业名单的； （申请简易注销公告时，正在被列入经营异常名录或者严重违法失信企业名单的企业；不能做简易注销<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @param progress
     *            简易注销是否正在进行，1是进行中 0是刚开始 必传 0查询执法的表 1是什么什么审核中心取
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkIsEnrol")
    @ResponseBody
    public HttpResult checkIsEnrol(String regno, String uniscid, String progress)
    {
        HttpResult result = null;
        if (progress != null && progress.equals("0"))
        {
            String nreg = CommonUtil.NullToEmp(regno);
            String nuniscid = CommonUtil.NullToEmp(uniscid);
            try
            {
                result = enrolService.checkIsEnrol(nreg, nuniscid);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                result = new HttpResult();
                result.setResult("fail");
                result.setMessage("系统异常");
                return result;
            }
        }
        else
        {
            result = new HttpResult();
            result.setMessage("参数异常");
        }

        return result;
    }

    /**
     * 描述:查询企业是否存在股权（投资权益）被冻结，出质或动产抵押等情形 <br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkIsFreeze")
    @ResponseBody
    public HttpResult checkIsFreeze(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = freezeService.checkIsGjDy(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }

    /**
     * 描述:企业是否有正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return 返回json结果
     * @see
     */
    @RequestMapping(value = "/checkIsRecord")
    @ResponseBody
    public HttpResult checkIsRecord(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = recordService.checkIsRecord(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }

    /**
     * 描述: 企业是否是法律，行政法规或者国务院决定规定在注销登记前需经批准的<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return json串
     * @see
     */
    @RequestMapping(value = "/checkNeedApprove")
    @ResponseBody
    public HttpResult checkNeedApprove(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = needApproService.checkNeedApprove(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }
    
    
    
    /**
     * 描述: 检查企业是否曾被终止简易注销程序<br>
     * 
     * @param regno
     *            注册号
     * @param uniscid
     *            统一社会信用代码
     * @return json串
     * @see
     */
    @RequestMapping(value = "/checkIsEnd")
    @ResponseBody
    public HttpResult checkIsEnd(String regno, String uniscid)
    {
        HttpResult result = null;
        String nreg = CommonUtil.NullToEmp(regno);
        String nuniscid = CommonUtil.NullToEmp(uniscid);
        try
        {
            result = stopCancelService.checkEnd(nreg, nuniscid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }
    
    /**
     * 描述: 公告信息插入简易注销同步表<br>
     * 
     * @param pripid
     *            企业主体标识
     * @param id
     *            公告表id
     * @return json串
     * @see
     */
    @RequestMapping(value = "/insertSimpleCancel")
    @ResponseBody
    public HttpResult insertSimpleCancel(String pripid, String id)
    {
        HttpResult result = null;
        pripid = CommonUtil.NullToEmp(pripid);
        id = CommonUtil.NullToEmp(id);
        try
        {
            result = synInfoService.insertSimpleCancel(pripid, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }
    
    
    /**
     * 描述: 异议信息插入简易注销同步表<br>
     * 
     * @param pripid
     *            企业主体标识
     * @param id
     *            异议表id
     * @return json串
     * @see
     */
    @RequestMapping(value = "/insertObjection")
    @ResponseBody
    public HttpResult insertObjection(String pripid, String id)
    {
        HttpResult result = null;
        pripid = CommonUtil.NullToEmp(pripid);
        id = CommonUtil.NullToEmp(id);
        try
        {
            result = synInfoService.insertObjection(pripid, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = new HttpResult();
            result.setResult("fail");
            result.setMessage("系统异常");
        }
        return result;
    }
    
}
