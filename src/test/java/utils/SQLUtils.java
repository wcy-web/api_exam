package utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 向阳
 * @date 2020/6/24-17:45
 */
public class SQLUtils {
    public static void main(String[] args) {
        String sql="select m.leave_amount,i.amount from member m,invest i where m.id=i.member_id and m.id=215344;";
        Map data = getDataAsMap(sql);
        System.out.println(data);

    }

    /**
     * 获得数据库查询结果:返回单个结果
     * @param sql sql查询语句
     * @return
     */
    public static Object getData(String sql){
        //获得数据库连接
        Connection conn=JDBCUtils.getConnection();
        Object result=null;

        try {
            //QueryRunner中提供对sql语句操作的API
            QueryRunner runner = new QueryRunner();
            //定义返回结果集，只有一个数据
            ScalarHandler handler=new ScalarHandler();
            //query方法用于执行 select操作
            result = runner.query(conn, sql, handler);

        }catch (Exception e){
            e.getStackTrace();
        }finally {
            JDBCUtils.close(conn);
        }
        return result;
    }

    /**
     * 获得数据库查询结果:返回Map
     * @param sql
     * @return
     */
    public static Map<String, Object> getDataAsMap(String sql){
        //获得数据库连接
        Connection conn=JDBCUtils.getConnection();
        Map<String, Object> map=new HashMap<>();
        try {
            //QueryRunner中提供对sql语句操作的API
            QueryRunner runner = new QueryRunner();
             MapHandler mapHandler = new MapHandler();
            //query方法用于执行 select操作
            map = runner.query(conn, sql, mapHandler);
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            JDBCUtils.close(conn);
        }
        return map;
    }
}
