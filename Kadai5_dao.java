
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kadai5_dao {

	// 接続
	public Connection connectDB(Connection con) {

		try {
			con = DriverManager.getConnection(Kadai5_const.SQL_URL, Kadai5_const.SQL_USER, Kadai5_const.SQL_PASS);

		} catch (Exception e) {
			System.out.println("MySQLへの接続に失敗しました。");
			System.out.println(e.toString());
		}
		return con;
	}

	// クローズ
	public boolean closeDB(Connection con, Statement stm, ResultSet rs) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("MySQLのクローズに失敗しました。");
				System.out.println(e.toString());
				return false;
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				System.out.println("MySQLのクローズに失敗しました。");
				System.out.println(e.toString());
				return false;
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("MySQLのクローズに失敗しました。");
				System.out.println(e.toString());
				return false;
			}
		}

		return true;
	}

	// SELECT文の実行
	public ResultSet selectDB(Connection con, Statement stm, String sql) {

		ResultSet rs = null;
		try {
			stm = con.createStatement();
			rs = stm.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("SQLの実行に失敗しました。");
			System.out.println(e.toString());
		}
		return rs;
	}

	// INSERT文の実行
	public int executeDB(Connection con, Statement stm, String sql) {

		int result = 0;
		try {
			stm = con.createStatement();
			result = stm.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("SQLの実行に失敗しました。");
			System.out.println(e.toString());
		}
		return result;
	}

	// WHERE句の設定
	public String addWhere(String sql, String where, Object value) {

		if (sql.contains("WHERE")) {
			sql += " AND ";
		} else {
			sql += " WHERE ";
		}

		return sql + where.replace("?", makeValue(value));
	}

	// SET句の設定
	public String addSet(String sql, String key, Object value) {

		if (sql.contains("SET")) {
			sql += ", ";
		} else {
			sql += " SET ";
		}

		return sql + key + " = " + makeValue(value);
	}

	// 値の加工
	public String makeValue(Object value) {
		if (value instanceof Integer) {
			return String.valueOf(value).replace("'", "''");
		} else {
			return "'" + value.toString().replace("'", "''") + "'";
		}
	}

}
