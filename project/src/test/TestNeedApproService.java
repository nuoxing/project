/*
 * 文件名：TestNeedApproService.java
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
import com.jyzx.service.NeedApproService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 2017年3月1日
 * @see TestNeedApproService
 * @since
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestNeedApproService
{
    @Autowired
    private NeedApproService needApproService;

    /**
     * 
     */
    public TestNeedApproService()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Test
    public void testNeedApproService_1(){
        String regno = "";
        String uniscid = "";
        HttpResult result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
    @Test
    public void testNeedApproService_2(){
        String regno = "440106000512517";
        String uniscid = "";
        HttpResult result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业是法律，行政法规或者国务院决定规定在注销登记前需经批准的", result.getMessage());
        
    }
    
    @Test
    public void testNeedApproService_3(){
        String regno = "440101000059390";
        String uniscid = "";
        HttpResult result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业是法律，行政法规或者国务院决定规定在注销登记前需经批准的", result.getMessage());
        
    }

    @Test
    public void testNeedApproService_4(){
        String regno = "440101000250033";
        String uniscid = "";
        HttpResult result = needApproService.checkNeedApprove(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业不是法律，行政法规或者国务院决定规定在注销登记前需经批准的", result.getMessage());
        
    }

}
