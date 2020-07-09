package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 向阳
 * @date 2020/6/24-15:59
 */
public class JDBCUtils {
    /**
     * 创建数据库连接
     */
    public static Connection getConnection(){
        Connection conn=null;
        try {
            //数据库驱动
            conn= DriverManager.getConnection(
                    //jdbc:数据库类型://ip:port/数据库名?字符的编码、解码格式
                    "jdbc:mysql://api.lemonban.com:3306/futureloan?userUnicode=true&characterEncoding=utf-8",
                    "future", "123456");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     * @param conn
     */
    public static void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
