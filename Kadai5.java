import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Kadai5 {

	public static void main(String args[]) {

		Kadai5 kadai = new Kadai5();
		Kadai5_dao dao = new Kadai5_dao();
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		Hashtable<Integer, ArrayList<Integer>> scoreTable = new Hashtable<>();

		try {
			// 接続
			// con = dao.connectDB(con);
			// if (con != null) {
			kadai.setScore(scoreTable);

			kadai.setDisp(scoreTable);
			// }

		} catch (Exception e) {
			System.out.println("処理に失敗しました。");
			System.out.println(e.toString());

		} finally {
			// クローズ
			// dao.closeDB(con, stm, rs);
		}
	}

	// スコアの設定
	private void setScore(Hashtable<Integer, ArrayList<Integer>> scoreTable) {

		Scanner sc = new Scanner(System.in);
		int score = 0; // 1投ごとのスコア
		int frameScore = 0; // フレームごとのスコア
		ArrayList<Integer> scoreList = new ArrayList<>();

		boolean isErr = false; // 入力値が正常か

		try {

			// フレームごと
			for (int i = 1; i <= 10; i++) {

				scoreList = new ArrayList<>();
				frameScore = 0;

				System.out.println("=========== " + String.valueOf(i) + "フレーム ===========");

				// 1投ごと
				for (int j = 1; j <= 3; j++) {

					if (j == 3 && i != 10) {
						// 3投目は10フレーム目だけ
						break;
					}

					System.out.println(String.valueOf(j) + "投目のスコアを入力してください。");
					while (true) {

						isErr = false;

						try {
							System.out.print("⇒");
							score = Integer.parseInt(sc.next());

							if (score < 0 || score > 10) {
								// スコアは0から10まで
								isErr = true;

							} else if (i != 10 && j == 2 && (frameScore + score) > 10) {
								// 10フレーム以外は1投目と2投目の合計は10以下
								isErr = true;
							}

						} catch (Exception ex) {
							isErr = true;

						} finally {
							if (!isErr) {
								frameScore += score;
								scoreList.add(score);
								break;

							} else {
								System.out.println("再入力してください。");
							}
						}
					}
				}

				scoreTable.put(i, scoreList);
				// System.out.println(String.valueOf(i) + "フレームのスコア ⇒ " +
				// String.valueOf(frameScore));
				System.out.println("");
			}

		} catch (Exception e) {
			System.out.println("処理に失敗しました。");
			System.out.println(e.toString());

		} finally {
			sc.close();
		}
	}

	// スコアの表示
	private void setDisp(Hashtable<Integer, ArrayList<Integer>> scoreTable) {

		ArrayList<Integer> list = new ArrayList<>();
		int totalScore = 0;

		System.out.println("");
		System.out.println(
				"============================================================= 結果 =============================================================");
		System.out.println("");

		System.out.print("｜");
		for (int i = 1; i <= 9; i++) {
			System.out.print("   " + String.format("%3s", String.valueOf(i)) + "    ｜");
		}
		System.out.println("      10       ｜");

		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");

		System.out.print("｜");

		for (int i = 1; i <= 10; i++) {
			list = scoreTable.get(i);
			for (Integer score : list) {
				System.out.print(String.format("%4s", String.valueOf(score)) + "｜");
			}
		}

		System.out.println("");
		System.out.println("――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――");

		System.out.print("｜");
		for (int i = 1; i <= 9; i++) {
			System.out.print("   " + String.format("%3s", totalScore += getResult(scoreTable, i)) + "    ｜");
		}
		System.out.println("      " + String.valueOf(totalScore += getResult(scoreTable, 10)) + "       ｜");
	}

	// スコアの計算
	private int getResult(Hashtable<Integer, ArrayList<Integer>> scoreTable, int frame) {

		int result = 0;
		int score = 0;
		int count = 0;
		ArrayList<Integer> scoreList = new ArrayList<>();
		ArrayList<Integer> workList = new ArrayList<>();

		try {
			scoreList = scoreTable.get(frame);

			for (int i = 0; i < scoreList.size(); i++) {
				score = scoreList.get(i);

				if (i == 0 && score == 10) {
					// ストライクの場合

					result += score;
					for (int j = frame + 1; j <= 10; j++) {
						workList = scoreTable.get(j);
						for (Integer work : workList) {
							if (work != 0) {
								result += work;
								count += 1;
							}
							if (count == 2) {
								break;
							}
						}
						if (count == 2) {
							break;
						}
					}

				} else {
					// それ以外
					result += score;
				}
			}

		} catch (Exception e) {
			System.out.println("処理に失敗しました。");
			System.out.println(e.toString());
		}

		return result;
	}

}
