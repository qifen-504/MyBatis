package cn.xdl.util;


import cn.xdl.bean.SqlBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class SqlUtil {
    private static Map<String,Object> maps = new HashMap<>();
    private static  Connection conn;
    private static PreparedStatement statement ;
    private static ResultSet resultSet;
    private static Class clazz;
    //声明一个List集合存储SqlBean对象
    /**
     * 有参数或无参查询
     */
    public static void selectInfo (SqlBean sqlBean,String param){
        System.out.println(sqlBean.getSql());
        conn=DBCPUtil.getConnection();
        try {
            statement=conn.prepareStatement(sqlBean.getSql());
            //获取参数的元数据
            ParameterMetaData parameterMetaData = statement.getParameterMetaData();
            //判断sql语句中的参数是否为null,若不是空则向sql中赋值 (查询一个)
            if (sqlBean.getParameterType()!=null){
                //通过元数据获取参数类型
               // int parameterType = parameterMetaData.getParameterType(1);
                //数据库中返回的参数类型
              //  String parameterTypeName = parameterMetaData.getParameterTypeName(1);
                //判断参数类型
                if (sqlBean.getParameterType().equals("int")){
                    statement.setInt(1,1);
                    resultSet=statement.executeQuery();
                }else if (sqlBean.getParameterType().equals("String")){
                    System.out.println("有待扩展");
                }
                //无参查询 (查询所有)
            }else {
                resultSet=statement.executeQuery();
            }
            //遍历结果集 将数据存储到map集合中
            //获取结果集元数据
            ResultSetMetaData resultSetmetaData = resultSet.getMetaData();
            //获取查询出来的总列数
            int count = resultSetmetaData.getColumnCount();
            System.out.println("总列数:"+count);
            String columnName=null;
            if (maps.size()!=0){
                maps.clear();
            }
            for (int i =1;i<=count;i++){
                //列名
                columnName = resultSetmetaData.getColumnName(i);
                //列名类型
                String columnTypeName = resultSetmetaData.getColumnTypeName(i);
                System.out.println(columnName+"<--->"+columnTypeName);
                maps.put(columnName,columnTypeName);
            }
                try {
                    SqlUtil.createBeans(sqlBean,resultSet,maps);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBCPUtil.close(conn,statement,resultSet);
        }
    }

    /**
     * 打印集合中的对象
     * @param objects
     */
    public static void printUserMap(List<Object> objects){
        for (Object list:objects) {
            System.out.println(list.toString());
        }
    }
    /**
     * 根据查询出来的结果创建对象
     * @param sqlBean
     * @param resultSet
     * @param maps
     * @return
     * @throws Exception
     */
    public static List<Object> createBeans(SqlBean sqlBean,ResultSet resultSet,Map<String,Object> maps) throws Exception {
        //System.out.println("1");
        String classname=sqlBean.getResultType();
        clazz = Class.forName(classname);
        List<Object> objects=new LinkedList<>();
        List<String> names=new ArrayList<>();
        //获取数据库的实际列名
        for (Map.Entry<String,Object> map:maps.entrySet()) {
            //获取列名
            String name = map.getKey();
            if (names.size()!=0){
                for (int i = 0;i<names.size();i++){
                    //如果List集合中不存在此列名
                    if (!name.equals(names.get(i))){
                        names.add(name);
                    }
                }
            }else{
                names.add(name);
            }
        }
        while (resultSet.next()){
            Object obj = clazz.newInstance();
            for (int i =0;i<names.size();i++){

                String name = names.get(i);
                //拼接Set方法
                String methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);

                Object value = resultSet.getObject(name);

                //获取成员变量
                //String sname=name.toLowerCase();
                Field f = clazz.getDeclaredField(name);
                //获取成员变量类型
                Type type = f.getGenericType();
                Method method = clazz.getMethod(methodName, (Class) type);
                method.invoke(obj, value);
            }
            objects.add(obj);
        }
       // System.out.println("这个方法调用了");
        printUserMap(objects);
        return objects;
    }


}
