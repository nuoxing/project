/*
 * 文件名：TestStopCancelService.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年3月7日
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
import com.jyzx.service.StopCancelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestStopCancelService
{
    @Autowired
    private StopCancelService stopCancelService;

    public TestStopCancelService()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Test
    public void testStopCancelService_1()
    {
        String regno = "";
        String uniscid = "";
        HttpResult result = stopCancelService.checkEnd(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = stopCancelService.checkEnd(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = stopCancelService.checkEnd(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = stopCancelService.checkEnd(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }

    
    @Test
    public void testStopCancelService_2()
    {
        String regno = "440101000250025";
        String uniscid = "";
        HttpResult result = stopCancelService.checkEnd(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
      //  Assert.assertEquals("该企业不曾被终止简易注销程序", result.getMessage());
        
    
    }

}
