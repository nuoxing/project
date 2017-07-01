/*
 * 文件名：TestFourCheckService.java
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
import com.jyzx.service.FourCheckService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestFourCheckService
{

    @Autowired
    private FourCheckService fourCheckService;
    
  
    @Test
    public void testcheckEnt_1(){
        String regno = "";
        String uniscid = "";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());
        
        regno = "21322";
        result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "";
        uniscid = "333433";
        result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
        
        
        regno = "32333";
        uniscid = "333433";
        result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());
    }
    
    
    @Test
    public void testcheckEnt_2(){
        String regno = "440101NA000015X";
        String uniscid = "";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业的类型不适用简易注销的企业类型", result.getMessage());
     
    }
    
    @Test
    public void testcheckEnt_3(){
        String regno = "440101000243884";
        String uniscid = "";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
      System.out.println(result.getMessage());
    }
    
    
    //外资
    @Test
    public void testcheckEnt_3_1(){
        String regno = "440101400147139";
        String uniscid = "";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
     
    }
    
    @Test
    public void testcheckEnt_4(){
        String regno = "";
        String uniscid = "914401017854785472";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
     
    }
    
    //外资
    @Test
    public void testcheckEnt_4_1(){
        String regno = "";
        String uniscid = "91440101MA59ADHU66";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("yes", result.getResult());
     
    }
    
    @Test
    public void testcheckEnt_5(){
        String regno = "";
        String uniscid = "93440101MA59ABP08X";
        HttpResult result = fourCheckService.checkEnt(regno, uniscid);
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("该企业的类型不适用简易注销的企业类型", result.getMessage());
        
     
    }
}
