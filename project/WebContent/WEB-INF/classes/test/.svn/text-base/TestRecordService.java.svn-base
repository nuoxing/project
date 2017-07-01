/*
 * 文件名：TestRecordService.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年3月1日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jyzx.po.HttpResult;
import com.jyzx.service.RecordService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2017年3月1日
 * @see TestRecordService
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestRecordService
{

    @Autowired
    private RecordService recordService;
    /**
     * 
     */
    public TestRecordService()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Test
    public void testcheckIsRecord(){
        String regno = "";
        String uniscid = "";
        HttpResult result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
    @Test
    public void testcheckIsRecord_2(){
        String regno = "440101000099338";
        String uniscid = "";
        HttpResult result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的", result.getMessage());
        
      
    }
    
    
    @Test
    public void testcheckIsRecord_3(){
        String regno = "440112000035875";
        String uniscid = "";
        HttpResult result = recordService.checkIsRecord(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业不存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的", result.getMessage());
        
      
    }
    

}
