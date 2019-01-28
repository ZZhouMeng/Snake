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
	private DirectionType directionType = DirectionType.R; // Ĭ����������
	private Node food;

	private int foodCount = 0;
	private boolean isPause; // ��ͣ��ʶ

	// �ߵĳ�ʼ���ԣ�������
	private int ox = Config.BIRTH_X; // ͷ
	private int oy = Config.BIRTH_Y; // ͷ
	private int length = Config.BODY_LENGTH; // �����ܳ���(����ͷ)

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
		// �����ߵ�״̬������߹��ˣ����˳�ѭ��
		while (snake.isAlive()) {
			if (isPause) {
				// ��ͣ��ʲô������
			} else {
				productFood();// ����ʳ��

				if (snake.eatFood(food)) { // �߳Ե�����?
					foodCount = 0;
					snake.grow(); // �䳤������һ���ڵ�
				}
				snake.move();
			}
			try {
				Thread.sleep(Config.SPEED * 50L); // �����ߵ������ٶ�
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
		//���¿�ʼ����Ҫһ�¼������裺 1�������Ļ������ͼ�� 2�������� 3����������ʳ��
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
			// factoryֻ����ʳ��������������λ��У��
			food = FoodFactory.product();
		} while (snake.isBody(food));

		gc.setFill(Config.FOOD_COLOR);
		gc.fillRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);

		foodCount = 1;
	}

	@Override
	public void handle(KeyEvent event) {
		KeyCode code = event.getCode();
		// �������ÿ�����config���޸�
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
