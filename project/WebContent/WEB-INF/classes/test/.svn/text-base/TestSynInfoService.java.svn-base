/*
 * 文件名：TestSynInfoService.java
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
import com.jyzx.service.SynInfoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestSynInfoService
{

    @Autowired
    private SynInfoService syninfoService;
    
    @Test
    public void testInsertSimpleCancel(){
        String pripid = "111111111111111111";
        String id = "111111111111111111";
        HttpResult result = syninfoService.insertSimpleCancel(pripid, id);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("插入成功！", result.getMessage());
        
    }
    
    
 
    @Test
    public void  testcheckIsEnrol2(){
    	String pripid = "222222222222222";
        String id = "222222222222222";
        HttpResult result = syninfoService.insertObjection(pripid, id);
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("插入成功！", result.getMessage());
    }
    
    
}
