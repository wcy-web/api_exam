package cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pojo.CaseInfo;
import pojo.WriteBackInfo;
import utils.Authentication;
import utils.ExcelUtils;
import utils.HttpUtils;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 向阳
 * @date 2020/7/7-17:55
 */
public class Register extends BaseCase{
    Logger logger=Logger.getLogger(Register.class);

    @Test(dataProvider = "data")
    public void register(CaseInfo caseInfo) {
        //1、参数化
        getParames(caseInfo);
        //2、数据库前置查询
        //3、调用call方法发送请求
        HttpResponse response = HttpUtils.call(caseInfo.getUrl(), caseInfo.getParams(), caseInfo.getType(), caseInfo.getContentType(), Constants.HEADERS);
        String body = HttpUtils.printResponse(response);
        //4、获取响应，做响应断言
        boolean b = responseAssert(caseInfo, body);
        //5、数据库后置查询
        //6、数据库断言
        //7、把响应添加到批量回写集合中
        addWriteBackData(sheetIndex,caseInfo.getId(),Constants.REAL_RESULT_CELL_NUM,body);
        //把测试结果添加到批量回写集合中
        String s = b ? "通过" : "失败";
        addWriteBackData(sheetIndex,caseInfo.getId(),Constants.TEST_RESULT,s);
        //8、添加日志
        //9、报表断言
//        Assert.assertEquals(s,"通过");
    }

    @DataProvider
    public Object[] data(){
        Object[] datas = ExcelUtils.getDatas(sheetIndex, CaseInfo.class);
        return datas;
    }

}
