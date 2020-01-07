package cn.xdl;

import cn.xdl.bean.SqlBean;
import cn.xdl.util.SqlUtil;
import cn.xdl.util.XMLUtil;

import java.util.List;
import java.util.Scanner;

public class MybatisTest {
    private static SqlUtil sqlUtil= new SqlUtil();

    public static void main(String[] args) {
        XMLUtil x = new XMLUtil();
        List<SqlBean> list =x.getInfo();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入方法名");
        String name = sc.next();
        for (int i =0;i<list.size();i++){
            if (list.get(i).getId().equals(name)){
                //是否为查询语句
                if (list.get(i).getType().equals("select")){
                    System.out.println(list.get(i).getType());
                    sqlUtil.selectInfo(list.get(i),"1");
                }
            }
        }
    }
}
