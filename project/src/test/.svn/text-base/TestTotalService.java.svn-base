/*
 * 文件名：TestTotalService.java 版权：Copyright by www.chinauip.com 描述： 修改人：Administrator 修改时间：2017年2月24日
 * 跟踪单号： 修改单号： 修改内容：
 */

package test;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jyzx.po.HttpResult;
import com.jyzx.service.TotalService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/config/applicationContext.xml",
    "file:WebContent/WEB-INF/config/applicationContext-jinxin.xml"})
public class TestTotalService
{

    @Autowired
    private TotalService totalService;

    public TestTotalService()
    {
        // TODO Auto-generated constructor stub
    }

    @Test
    public void testcheckIsCancel_1()
    {
        String regno = "";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("注册号，统一社会信用代码不能同时为空", result.getMessage());

        regno = "21322";
        result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());

        regno = "";
        uniscid = "333433";
        result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());

        regno = "32333";
        uniscid = "333433";
        result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("查找不到该企业的相关信息", result.getMessage());

        regno = "32333";
        uniscid = "333433";
        result = totalService.checkIsCancel(regno, uniscid, "");
        Assert.assertEquals("fail", result.getResult());
        Assert.assertEquals("参数异常", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_2()
    {
        String regno = "440101NA000015X";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，不满足注销的企业类型", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_3()
    {
        String regno = "440101000242822";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，企业是保密企业", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_4()
    {
        String regno = "440104000023833";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，企业被列入经营异常名录", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_5()
    {
        String regno = "440106000074636";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，企业存在严重违法记录", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_6()
    {
        String regno = "440101400142688";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("yes", result.getResult());
        Assert.assertEquals("可以简易注销", result.getMessage());
    }

    @Test
    public void testcheckIsCancel_7()
    {
        String regno = "440101000099338";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，该企业存在正在被立案调查或采取行政强制，司法协助，被予以行政处罚等情形的", result.getMessage());

    }

    @Test
    public void testcheckIsCancel_8()
    {
        String regno = "440122400000897";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，企业存在股权出质", result.getMessage());

    }

    @Test
    public void testcheckIsCancel_9()
    {
        String regno = "440111000192582";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，企业存在动产抵押", result.getMessage());

    }

    @Test
    public void testcheckIsCancel_10()
    {
        String regno = "440126000252121";
        String uniscid = "";
        HttpResult result = totalService.checkIsCancel(regno, uniscid, "0");
        Assert.assertEquals("no", result.getResult());
        Assert.assertEquals("不能做简易注销，该企业是法律，行政法规或者国务院决定规定在注销登记前需经批准的", result.getMessage());

    }

}
