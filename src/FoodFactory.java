import java.util.Random;

public class FoodFactory {
	private static Random r = new Random();

	public static Node product() {
		int x, y;
		do {
			x = r.nextInt(Config.WIN_WIDTH - Config.UNIT_SIZE);
			y = r.nextInt(Config.WIN_HEIGHT - Config.UNIT_SIZE);
			// 循环条件确保生成的食物坐标能与蛇头重叠
		} while ((x % Config.UNIT_SIZE != 0) || (y % Config.UNIT_SIZE != 0));

		return new Node(x, y);
	}
}
