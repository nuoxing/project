/*
 * 文件名：TestEntCheckController.java
 * 版权：Copyright by www.chinauip.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2017年2月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  
@ContextConfiguration(locations={"file:WebContent/WEB-INF/config/applicationContext.xml","file:WebContent/WEB-INF/config/applicationContext-jinxin.xml",
    "file:WebContent/WEB-INF/config/spring-mvc-config.xml"})
public class TestEntCheckController  extends AbstractTransactionalJUnit4SpringContextTests
{

    @Autowired  
    private WebApplicationContext wac;  
  
    private MockMvc mockMvc; 
    
    @Before  
    public void setup() {   
        this.mockMvc = webAppContextSetup(this.wac).build();  
    } 
    
    
    
    @Test  
    public void testcheckIsCancel() throws Exception {  
        mockMvc.perform((post("/checkIsCancel").param("regno", "440101000099338").param("uniscid", "").param("progress", "0"))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
    
    @Test  
    public void testcheckType() throws Exception {  
        mockMvc.perform((post("/checkType").param("regno", "440101000243884").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
    @Test  
    public void testcheckIsApply() throws Exception {  
        mockMvc.perform((post("/checkIsApply").param("regno", "").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
    @Test  
    public void testcheckIsEnrol() throws Exception {  
        mockMvc.perform((post("/checkIsEnrol").param("regno", "440101000243884").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
 
    @Test  
    public void testcheckIsFreeze() throws Exception {  
        mockMvc.perform((post("/checkIsFreeze").param("regno", "440101000243884").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
    @Test  
    public void testcheckIsRecord() throws Exception {  
        mockMvc.perform((post("/checkIsRecord").param("regno", "440101000243884").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    
    @Test  
    public void testcheckNeedApprove() throws Exception {  
        mockMvc.perform((post("/checkNeedApprove").param("regno", "440106000512517").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
    @Test  
    public void testcheckIsEnd() throws Exception {  
        mockMvc.perform((post("/checkIsEnd").param("regno", "440106000512517").param("uniscid", ""))).andExpect(status().isOk())  
                .andDo(print());  
    } 
}
