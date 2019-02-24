package com.lunacia.novels;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NovelInfoDAO {
	private String className = "com.mysql.cj.jdbc.Driver";
	private String username = "root";
	private String password = "123456";
	private String url = "jdbc:mysql://localhost:3306/novels";
	private Connection conn;

	private Connection getConn() {
		try {
			Class.forName(className);
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void insert(NovelInfo novelInfo) {
		Connection conn = getConn();
		String sql = "insert into `novel_list` (id,name,author,intro,img_url) values (?,?,?,?,?)";
		String sql2 = "insert into `title_list` (parent_id,title,content_url) values (?,?,?)";

		PreparedStatement pdst;
		try {
			pdst = (PreparedStatement) conn.prepareStatement(sql);
			pdst.setString(1, novelInfo.getId());
			pdst.setString(2, novelInfo.getName());
			pdst.setString(3, novelInfo.getAuthor());
			pdst.setString(4, novelInfo.getIntro());
			pdst.setString(5, novelInfo.getImg_url());
//			System.out.println(pdst.toString());
			pdst.execute();
			pdst = conn.prepareStatement(sql2);
			for (int i = 0; i < novelInfo.getTitles().size(); i++) {
				pdst.setString(1, novelInfo.getId());
				pdst.setString(2, novelInfo.getTitles().get(i));
				pdst.setString(3, novelInfo.getContent_urls().get(i));
				pdst.execute();
			}
			pdst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		NovelInfo novelInfo = new NovelInfo();
		novelInfo.setId("15409");
		novelInfo.setName("牧神记");
		novelInfo.setAuthor("宅猪");
		novelInfo.setImg_url("http://www.xbiquge.la/files/article/image/15/15409/15409s.jpg");
		NovelInfoDAO novelInfoDAO = new NovelInfoDAO();
		novelInfoDAO.insert(novelInfo);
	}

}
