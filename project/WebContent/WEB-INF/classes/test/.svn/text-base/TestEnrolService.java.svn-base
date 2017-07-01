/*
 * 文件名：TestEnrolService.java
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
import com.jyzx.service.EnrolService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestEnrolService
{

    @Autowired
    private EnrolService enrolService;
    
    @Test
    public void testcheckIsEnrol(){
        String regno = "";
        String uniscid = "";
        HttpResult result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
    
 
    @Test
    public void  testcheckIsEnrol2(){
        String regno = "440101000243884";
        String uniscid = "";
        HttpResult result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("没有被列入", result.getMessage());
    }
    
    
    @Test
    public void  testcheckIsEnrol3(){
        String regno = "440104000023833";
        String uniscid = "";
        HttpResult result = enrolService.checkIsEnrol(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("该企业在经营异常名录中有记录", result.getMessage());
    }
    

}
