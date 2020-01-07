package cn.xdl.MySqlSession;

import cn.xdl.bean.SqlBean;
import cn.xdl.util.DBCPUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlSession {
    private static List<SqlBean> list;
    private static ResultSet resultSet;
    private static String resultType;
    private static List<Object> data;
    public MySqlSession(List<SqlBean> list){
        this.list=list;
    }

    /**
     * 获取xml信息 得到结果集
     * @param id
     * @return
     */
    public static  List<Object> query(String id){
        Connection conn =null;
        Statement statement =null;
        try {
            conn = DBCPUtil.getConnection();
            statement = conn.createStatement();
            String sql = null;
            for (SqlBean bean:list) {
                if (bean.getId().equals(id)){
                   sql=bean.getSql();
                   resultType=bean.getResultType();
               }
            }
            resultSet=statement.executeQuery(sql);
            getBean();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBCPUtil.close(conn,statement,resultSet);
        }
        return data;
    }
    public static List<Object> getBean(){
        List<Map<String ,Object>> l = new ArrayList<>();
        data=new ArrayList<>();
            try {
                ResultSetMetaData metaData = resultSet.getMetaData();

                int columnCount = metaData.getColumnCount();
                while (resultSet.next()){
                    Map<String,Object> map =map=new HashMap<>();

                    //获取列名
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = metaData.getColumnName(i+1);
                        //根据列名获取字段值
                        Object value = resultSet.getObject(columnName);
                        map.put(columnName,value);
                    }
                    l.add(map);
                }
                //将map集合通过反射转换为对象
                Class<?> aClass = Class.forName(resultType);
                for (int i = 0; i < l.size(); i++) {
                    Object obj = aClass.newInstance();
                    BeanUtils.populate(obj,l.get(i));
                    data.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return data;
    }
}
