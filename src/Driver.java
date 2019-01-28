import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Driver extends Canvas implements Runnable, EventHandler<KeyEvent> {
	
	enum DirectionType {
		U, D, L, R
	}

	private GraphicsContext gc;
	private Snake snake;
	private DirectionType directionType = DirectionType.R; // 默认向右行走
	private Node food;

	private int foodCount = 0;
	private boolean isPause; // 暂停标识

	// 蛇的初始属性，出生点
	private int ox = Config.BIRTH_X; // 头
	private int oy = Config.BIRTH_Y; // 头
	private int length = Config.BODY_LENGTH; // 蛇身总长度(包括头)

	public Driver(GraphicsContext gc) {
		super();
		this.gc = gc;
		this.snake = new Snake(gc, this);
		snake.initAttr(ox, oy, length);
		gc.setStroke(Color.RED);
		gc.setLineWidth(5);
		gc.strokeRect(0, 0, Config.WIN_WIDTH, Config.WIN_HEIGHT);
	}

	public void start() {
		// 根据蛇的状态，如果蛇挂了，就退出循环
		while (snake.isAlive()) {
			if (isPause) {
				// 暂停，什么都不做
			} else {
				productFood();// 生产食物

				if (snake.eatFood(food)) { // 蛇吃到事物?
					foodCount = 0;
					snake.grow(); // 变长，增加一个节点
				}
				snake.move();
			}
			try {
				Thread.sleep(Config.SPEED * 50L); // 控制蛇的行走速度
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void pause() {
		if (isPause) {
			isPause = false;
		} else {
			isPause = true;
		}
	}

	public void reStart() {
		//重新开始，需要一下几个步骤： 1、清除屏幕上所有图形 2、蛇重置 3、重新生产食物
		isPause = true;
		gc.clearRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
		snake.alive();
		snake.initAttr(ox, oy, length);
		isPause = false;
		foodCount = 0;
		productFood();
	}

	public void exit() {
		System.exit(0);
	}

	public void productFood() {
		if (foodCount != 0) {
			return;
		}
		do {
			// factory只负责食物生产，不进行位置校验
			food = FoodFactory.product();
		} while (snake.isBody(food));

		gc.setFill(Config.FOOD_COLOR);
		gc.fillRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);

		foodCount = 1;
	}

	@Override
	public void handle(KeyEvent event) {
		KeyCode code = event.getCode();
		// 按键配置可以在config中修改
		if (code == Config.UP) {
			if (this.directionType != DirectionType.D) {
				this.directionType = DirectionType.U;
				snake.toUp();
			}
		} else if (code == Config.DOWN) {
			if (this.directionType != DirectionType.U) {
				this.directionType = DirectionType.D;
				snake.toDown();
			}
		} else if (code == Config.LEFT) {
			if (this.directionType != DirectionType.R) {
				this.directionType = DirectionType.L;
				snake.toLeft();
			}
		} else if (code == Config.RIGHT) {
			if (this.directionType != DirectionType.L) {
				this.directionType = DirectionType.R;
				snake.toRight();
			}
		} else if (code == Config.PAUSE) {
			pause();
		} else if (code == Config.RESTART) {
			reStart();
		} else {
			System.out.println("Invaild Key!");
		}
	}

	@Override
	public void run() {
		start();
	}

}
