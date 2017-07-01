/*
 * 文件名：TestFreezeService.java
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
import com.jyzx.service.FreezeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestFreezeService
{

    @Autowired
    private FreezeService freezeService;
    
    public TestFreezeService()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Test
    public void testFreezeService_1(){
        String regno = "";
        String uniscid = "";
        HttpResult result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
   

    @Test
    public void testFreezeService_2(){
        String regno = "440112000035875";
        String uniscid = "";
        HttpResult result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业存在股权（投资权益）被冻结", result.getMessage());
        
    }
    
    @Test
    public void testFreezeService_3(){
        String regno = "440122400000897";
        String uniscid = "";
        HttpResult result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业存在股权出质", result.getMessage());
        
    }
    
    @Test
    public void testFreezeService_4(){
        String regno = "440111000192582";
        String uniscid = "";
        HttpResult result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业存在动产抵押", result.getMessage());
        
    }
    
    @Test
    public void testFreezeService_5(){
        String regno = "440101000250033";
        String uniscid = "";
        HttpResult result = freezeService.checkIsGjDy(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业不存在股权（投资权益）被冻结，出质或动产抵押等情形", result.getMessage());
        
    }
    
}
