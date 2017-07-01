package com.jyzx.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jyzx.common.CommonService;
import com.jyzx.po.HttpResult;
import com.jyzx.util.FilterParam;

/**
 * 简易注销同步信息 插入信息类
 * @author ljj
 *
 */
@Service("syninfoService")
public class SynInfoService extends CommonService
{

	 /**
     * 无参构造方法
     */
    public SynInfoService()
    {
    		
    }
    
    /**
     * 描述: 公告信息插入
     * 
     * @param pripid 企业主键
     * @param id 公告信息表id
     * @return 操作结果
     * @see
     */
    public HttpResult insertSimpleCancel(String pripid,String id)
    {
    	HttpResult result = new HttpResult();
    	List<FilterParam> listFilterParam = new ArrayList<FilterParam>();
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("insert into TB_ZC_JYZX_SYN(ID,ASSOCIATEDID,ASSOCIATEDTYPE,PRIPID,SYNSTATE,INSERTTIME) values")
    	.append("(:ID,:ASSOCIATEDID,'1',:PRIPID,'0',current timestamp)");
    	listFilterParam.add(new FilterParam("ID", UUID.randomUUID().toString()));
    	listFilterParam.add(new FilterParam("ASSOCIATEDID", id));
    	listFilterParam.add(new FilterParam("PRIPID", pripid));
    	
        int num = commonDao.executeUpdateBySql(sql.toString(), listFilterParam
				.toArray(new FilterParam[listFilterParam.size()]));
        if (num > 0)
        {
        	result.setResult("yes");
            result.setMessage("插入成功！");
        }
        else
        {
        	result.setResult("fail");
            result.setMessage("插入失败！");
        }
        return result;
    }
    
    /**
     * 描述: 异议信息插入
     * 
     * @param pripid 企业主键
     * @param id 公告信息表id
     * @return 操作结果
     * @see
     */
    public HttpResult insertObjection(String pripid,String id)
    {
    	HttpResult result = new HttpResult();
    	List<FilterParam> listFilterParam = new ArrayList<FilterParam>();
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("insert into TB_ZC_JYZX_SYN(ID,ASSOCIATEDID,ASSOCIATEDTYPE,PRIPID,SYNSTATE,INSERTTIME) values")
    	.append("(:ID,:ASSOCIATEDID,'2',:PRIPID,'0',current timestamp)");
    	listFilterParam.add(new FilterParam("ID", UUID.randomUUID().toString()));
    	listFilterParam.add(new FilterParam("ASSOCIATEDID", id));
    	listFilterParam.add(new FilterParam("PRIPID", pripid));
    	
        int num = commonDao.executeUpdateBySql(sql.toString(), listFilterParam
				.toArray(new FilterParam[listFilterParam.size()]));
        if (num > 0)
        {
        	result.setResult("yes");
            result.setMessage("插入成功！");
        }
        else
        {
        	result.setResult("fail");
            result.setMessage("插入失败！");
        }
        return result;
    }
}
