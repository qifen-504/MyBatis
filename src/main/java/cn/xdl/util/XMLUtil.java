package cn.xdl.util;
import cn.xdl.bean.SqlBean;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil {
    static List<SqlBean> list = new ArrayList<>();
    //声明一个List集合存储Proxy对象
    public static List<SqlBean> getInfo() {
        try {
        InputStream is = XMLUtil.class.getClassLoader().getResourceAsStream("sql.xml");
        SAXReader sr = new SAXReader();
        Document doc = sr.read(is);
            //根节点mapper
            Element root = doc.getRootElement();
            //获取所有子节点--select
            List<Element> elements =root.elements();
            for (Element e: elements) {
                String id = e.attributeValue("id");
                String resultType = e.attributeValue("resultType");
                String parameterType=e.attributeValue("parameterType");
                String type = e.getName();
                //删除头尾空白字符串
                String sql = e.getText().trim();
                SqlBean sqlBean = new SqlBean(id,resultType,sql,parameterType,type);
                list.add(sqlBean);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return list;
    }
}
