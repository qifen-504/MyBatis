package cn.xdl.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCPUtil {
	private static DataSource ds = null;
	static {
		try {
			Properties ppt = new Properties();
			InputStream is = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
			ppt.load(is);
			ds = BasicDataSourceFactory.createDataSource(ppt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void close(Connection conn,Statement state,ResultSet result) {
		try {
			if(conn !=null) {
				conn.close();
			}
			if(state !=null) {
				state.close();
			}
			if(result !=null) {
				result.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
