package repostApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ConDB {
	private Connection con = null;
	private PreparedStatement pst = null;

	private String url = "jdbc:mysql://localhost:3306/opensns681";
	private String driver = "com.mysql.jdbc.Driver";
	private String user = "root";
	private String pass = "zijidelu";

	private void conn() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, pass);
			con.setAutoCommit(false);// Disables auto-commit.
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void addToDB(String name,int age,int xi) {
		try {
			this.conn();
			String sql = "insert into teacher(name,age,xi) values(?,?,?) ";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, name);
			pst.setInt(2, age);
			pst.setInt(3, xi);
			
			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void post(long uid,String content){
		try {
			this.conn();
			String sql = "insert into opensns_weibo(uid,content,create_time,comment_count,status,is_top,type,data,repost_count,`from`)values(?,?,?,?,?,?,?,?,?,'')";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	
			pst.setLong(1, uid);
			pst.setString(2, content);
			pst.setLong(3, System.currentTimeMillis()/1000);
			pst.setInt(4, 0);
			pst.setInt(5, 1);
			pst.setInt(6, 0);
			pst.setString(7, "feed");
			pst.setString(8, "a:0:{}");
			pst.setInt(9, 0);

			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void repost(long uid,String content,long srcDid){
		try {
			this.conn();
			String sql = "insert into opensns_weibo(uid,content,create_time,comment_count,status,is_top,type,data,repost_count,`from`)values(?,?,?,?,?,?,?,?,?,'')";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pst.setLong(1, uid);
			pst.setString(2, content);
			pst.setLong(3, System.currentTimeMillis()/1000);
			pst.setInt(4, 0);
			pst.setInt(5, 1);
			pst.setInt(6, 0);
			pst.setString(7, "repost");
			String repostData_prefix = "a:2:{s:6:\"source\";N;s:8:\"sourceId\";i:";
			String repostData_postfix = ";}";
			pst.setString(8, repostData_prefix+String.valueOf(srcDid)+repostData_postfix);
			pst.setInt(9, 0);

			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();
			updateRepostCount(srcDid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateRepostCount(long srcDid){
		try {
			this.conn();
			String sql = "update opensns_weibo set repost_count=repost_count+1 where id="+String.valueOf(srcDid);
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void comment(long uid,long did,String content){
		try {
			this.conn();
			String sql = "insert into opensns_weibo_comment(uid,weibo_id,content,create_time,status,to_comment_id)values(?,?,?,?,1,0)";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pst.setLong(1, uid);
			pst.setLong(2, did);
			pst.setString(3, content);
			pst.setLong(4, System.currentTimeMillis()/1000);
			
			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void support(long uid,long did){
		try {
			this.conn();
			String sql = "insert into opensns_support(appname,row,uid,create_time,`table`)values('Weibo',?,?,?,'weibo')";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pst.setLong(1, did);
			pst.setLong(2, uid);
			pst.setLong(3, System.currentTimeMillis()/1000);
			
			pst.executeUpdate();
			con.commit();
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public long uidToUname(String username){
		long uid = 0;
		try {
			this.conn();
			String sql = "select id from opensns_ucenter_member where username='"+username+"'";
			pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = pst.executeQuery(sql);
			
			while(rs.next()){
				uid = rs.getLong(1);
				break;
			}
			
//			con.commit();
			pst.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uid;
	}
	
}



//id,uid,content,create_time,comment_count,status,is_top,type,data,repost_count,from