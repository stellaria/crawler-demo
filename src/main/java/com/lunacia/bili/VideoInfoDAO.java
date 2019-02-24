package com.lunacia.bili;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideoInfoDAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String username = "root";
	private String password = "123456";
	private String url = "jdbc:mysql://localhost:3306/bilibili?serverTimezone=GMT";

	private Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void insert(VideoInfo videoInfo) {
		String sql = "insert into videos (id, title, title_img, author, intro, view, dm, date)" +
				"values(?,?,?,?,?,?,?,?)";
		Connection conn = getConn();
		PreparedStatement pdst = null;
		try {
			pdst = conn.prepareStatement(sql);
			pdst.setLong(1, videoInfo.getId());
			pdst.setString(2, videoInfo.getTitle());
			pdst.setString(3, videoInfo.getTitle_img());
			pdst.setString(4, videoInfo.getAuthor());
			pdst.setString(5, videoInfo.getIntro());
			pdst.setString(6, videoInfo.getView());
			pdst.setString(7, videoInfo.getDm());
			pdst.setString(8, videoInfo.getDate());
			pdst.execute();
			pdst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main (String[] args) {
		VideoInfo videoInfo = new VideoInfo();
		videoInfo.setId(42611004);
		videoInfo.setTitle("【生僻字】我的世界版-史蒂夫的人骄傲地把头抬起！");
		videoInfo.setTitle_img("http://i1.hdslb.com/bfs/archive/b82cf405e415eb4728f194a5886fdc3cbd199c46.jpg");
		videoInfo.setAuthor("委屈到变形的航叔");
		videoInfo.setIntro(" 作品类型：翻唱区 翻唱版: av42958255 重置版: av43034691 原曲出处：陈柯宇 调教：@DS绝对小贱 重填词：@航叔 混音：@DS绝对小贱 PV：@航叔 @桜忆 @嘉乐 视频部分建筑素材：@豹先生 @国家建筑师 收藏过1500，放出我们的翻唱有毒版本(๑•̀ㅂ•́)و✧");
		videoInfo.setView("208507");
		videoInfo.setDm("2922");
		videoInfo.setDate("2019-02-04 17:00:57");
		VideoInfoDAO dao = new VideoInfoDAO();
		dao.insert(videoInfo);
	}
}
