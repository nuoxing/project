/*
 * 文件名：TestSecrecyService.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年2月23日
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
import com.jyzx.service.SecrecyService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2017年2月23日
 * @see TestSecrecyService
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestSecrecyService
{


    @Autowired
    private SecrecyService secrecyService;

 
    @Test
    public void testcheckIsApply(){
        String regno = "";
        String uniscid = "";
        HttpResult result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
    
    @Test
    public void testcheckIsApply2()
    {
        String regno = "440101000242767";
        String uniscid = "";
        HttpResult result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业属于保密企业", result.getMessage());
    }
    
    
    @Test
    public void testcheckIsApply3()
    {
        String regno = "440101400147026";
        String uniscid = "";
        HttpResult result = secrecyService.checkIsApply(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业不属于保密企业", result.getMessage());
    }
}
