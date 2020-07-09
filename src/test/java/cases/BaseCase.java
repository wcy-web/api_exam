package cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pojo.CaseInfo;
import pojo.WriteBackInfo;
import utils.Authentication;
import utils.ExcelUtils;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 向阳
 * @date 2020/7/7-17:57
 */
public class BaseCase {

    public  int sheetIndex;
    Logger logger=Logger.getLogger(BaseCase.class);

    @BeforeSuite
    public void init(){
        //添加默认请求头
        Constants.HEADERS.put("Content-Type","application/json");
        logger.info("==============初始化==============");
    }

    //从testng.xml中读取sheetIndex的值
    @BeforeClass
    @Parameters({"sheetIndex"})
    public void getSheetIndex(int sheetIndex){
        this.sheetIndex=sheetIndex;
    }
    /**
     * 参数化替换
     * @param caseInfo
     */
    public void getParames(CaseInfo caseInfo) {
        FileInputStream fis=null;
        try {
            //从properties文件中加载配置的参数化信息
            Properties properties = new Properties();
            fis= new FileInputStream(Constants.PROPERTIES_PATH);
            properties.load(fis);
            //将配置的参数化信息放到作为环境变量的Map中
            Authentication.ENV.putAll((Map)properties);
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            ExcelUtils.close(fis);
        }
        //从Map中循环取出参数信息，进行替换
        Set<String> keySet = Authentication.ENV.keySet();
        for (String key : keySet) {
            //根据key取出Map中的值
            Object value = Authentication.ENV.get(key);
            String params=caseInfo.getParams();
            String expect=caseInfo.getExpectResult();
            String url=caseInfo.getUrl();
            if(StringUtils.isNoneBlank(params)) {
                //参数中的占位符替换成Map中的值
                params = params.replace(key, value.toString());
                //把新的参数设置到caseinfo对象中
                caseInfo.setParams(params);

            }
            if(StringUtils.isNoneBlank(expect)) {
                //参数中的占位符替换成Map中的值
                expect = expect.replace(key, value.toString());
                //把新的参数设置到caseinfo对象中
                caseInfo.setExpectResult(expect);

            }
            if(StringUtils.isNoneBlank(url)) {
                //参数中的占位符替换成Map中的值
                url = url.replace(key, value.toString());
                //把新的参数设置到caseinfo对象中
                caseInfo.setUrl(url);

            }
        }
    }

    /**
     * 响应断言
     * @param caseInfo
     * @param body
     */
    public boolean responseAssert(CaseInfo caseInfo, String body) {
        Map<String,Object> map = JSONObject.parseObject(caseInfo.getExpectResult(), Map.class);
        Set<String> keySet = map.keySet();
        boolean flag=false;
        for (String key : keySet) {
            Object expectValue = map.get(key);
            Object actualValue = JSONPath.read(body, key);
            if(expectValue.equals(actualValue)){
                System.out.println("响应断言通过");
                flag=true;
            }else{
                System.out.println("响应断言失败");
            }
        }
        return flag;
    }

    /**
     * 将批量回写对象添加到集合中
     * @param sheetIndex sheet页编号
     * @param rowNum       行号
     * @param cellNum       列号
     * @param body          回写内容
     */
    public void addWriteBackData(int sheetIndex,int rowNum,int cellNum, String body) {
        //创建回写对象
        WriteBackInfo writeBackInfo=new WriteBackInfo(sheetIndex,rowNum,cellNum,body);
        ////将回写对象添加到回写集合中
        ExcelUtils.list.add(writeBackInfo);

    }

    /**
     * 执行批量回写
     */
    @AfterSuite
    public void writeBack(){
        //执行批量回写到excel中
        ExcelUtils.writeExcel(ExcelUtils.list);
        logger.info("==================批量回写===================");
    }
}
