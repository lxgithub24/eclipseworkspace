package repostApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ConDB {
	private Connection con = null;
	private PreparedStatement pst = null;

	private String url = "jdbc:mysql://localhost:3306/school";
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
}
