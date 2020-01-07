package cn.xdl.main;

import cn.xdl.util.DBCPUtil;
import org.testng.annotations.Test;

import java.sql.*;

/**
 * 获取数据库元数据
 */
public class SqlUtilTest {
    @Test
    public  void getMetaData() throws SQLException {
        //得到连接
        Connection conn = DBCPUtil.getConnection();
        DatabaseMetaData metaData = conn.getMetaData();
        //获取数据库名称
        String databaseProductName = metaData.getDatabaseProductName();
        System.out.println(databaseProductName);
        //获取驱动版本
        String driverVersion = metaData.getDriverVersion();
        System.out.println(driverVersion);
        //获取数据库url
        String url = metaData.getURL();
        System.out.println(url);
        //得到数据库连接用户名
        String userName = metaData.getUserName();
        System.out.println(userName);
        conn.close();
    }
    /**
     * 获取SQL执行对象元数据
     */
    @Test
    public void getParamterMetaData() throws  Exception{
        //获取连接
        Connection conn = DBCPUtil.getConnection();
        //sql执行语句
        String sql = "insert into DEPT(id, username,age) values(?,?,?)";
        //预编译sql执行环境
        PreparedStatement state = conn.prepareStatement(sql);
        //获取参数元数据
        ParameterMetaData parameterMetaData = state.getParameterMetaData();
        //取到参数个数
        int parameterCount = parameterMetaData.getParameterCount();
        System.out.println(parameterCount);
        //获取参数的sql类型
        int parameterType = parameterMetaData.getParameterType(0);
        System.out.println(parameterType);
        //获取参数类型名称
        String parameterTypeName = parameterMetaData.getParameterTypeName(0);
        System.out.println(parameterTypeName);
        conn.close();

    }
    /**
     * 获取结果集元数据
     */
    @Test
    public void  getResultSetMetaData() throws  Exception{
        //获取连接对象
        Connection conn = DBCPUtil.getConnection();
        //sql语句
        String sql = "SELECT * FROM DEPT";
        //获取sql执行对象
        PreparedStatement state = conn.prepareStatement(sql);
        //获取结果集
        ResultSet result = state.executeQuery();
        //获取结果集元数据
        ResultSetMetaData metaData = result.getMetaData();
        //获取查询出来的总列数
        int count = metaData.getColumnCount();
        System.out.println("count:"+count);
        //获取指定列的类型常量
        int columnType = metaData.getColumnType(2);
        System.out.println(columnType);
        //获取指定列的类型字符串 INT /VARCHAR2
        String columnTypeName = metaData.getColumnTypeName(2);
        System.out.println(columnTypeName);
        String columnName = metaData.getColumnName(2);
        //获取指定列的列名
        System.out.println(columnName);
        conn.close();
    }
}
