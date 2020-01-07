package cn.xdl;

import cn.xdl.MySqlSession.MySqlSession;
import cn.xdl.bean.SqlBean;
import cn.xdl.util.XMLUtil;

import java.util.List;

public class MybayisTest1 {
    public static void main(String[] args) {
        XMLUtil x =new XMLUtil();
        List<SqlBean> info = x.getInfo();
        MySqlSession m = new MySqlSession(info);
        List<Object> data = m.query("findUser");
        System.out.println(data);
        for (Object li: data) {
            System.out.println(li);
        }
    }
}
