package constants;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 向阳
 * @date 2020/6/17-16:43
 */
public class Constants {
    //存放测试用例的EXCEL表路径
    public static final String EXCEL_PATH=System.getProperty("user.dir")+"/src/test/resources/cases.xlsx";
    //存放properties文件的路径
    public static final String PROPERTIES_PATH=System.getProperty("user.dir")+"/src/test/resources/data.properties";
    //默认请求头
    public static final Map<String,String> HEADERS=new HashMap<String,String>();
    //将测试实际运行结果回写到excel表中的第8列
    public static final int REAL_RESULT_CELL_NUM=8;
    //将测试结果回写到excel表中的第9列
    public static final int TEST_RESULT=9;


}
