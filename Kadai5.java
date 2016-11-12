import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Kadai5 {

	public static void main(String args[]) {

		Kadai5 kadai = new Kadai5();
		Kadai5_dao dao = new Kadai5_dao();
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			// 接続
			con = dao.connectDB(con);
			if (con != null) {
				kadai.SetScore();

			}

		} catch (Exception e) {
			System.out.println("処理に失敗しました。");
			System.out.println(e.toString());

		} finally {
			// クローズ
			dao.closeDB(con, stm, rs);
		}
	}

	// スコアの設定
	private void SetScore() {

		Scanner sc = new Scanner(System.in);
		int score;

		try {

			for (int i = 1; i <= 10; i++) {
				System.out.println("=========== " + String.valueOf(i) + "フレーム ===========");
				for (int j = 1; j <= 3; j++) {
					if (j == 3 && i != 10) {
						break;
					}
					System.out.println(String.valueOf(j) + "投目のスコアを入力してください。");
					System.out.print("⇒");
					score = sc.nextInt();
				}
				System.out.println("");
			}

		} catch (Exception e) {
			System.out.println("処理に失敗しました。");
			System.out.println(e.toString());

		} finally {
			sc.close();
		}
	}

}
