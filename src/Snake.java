import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public class Snake {
	private List<Node> bodys;
	private GraphicsContext gc;
	private Driver driver;

	private int state = 1;

	enum DirectionType {
		U, D, L, R
	}

	private DirectionType direction = DirectionType.R;

	public Snake(GraphicsContext gc, Driver driver) {
		super();
		this.gc = gc;
		this.driver = driver;
		this.bodys = new ArrayList<Node>();
	}

	public void move() {
		Node head = bodys.get(0);
		Node newHead = new Node();

		// �ߵ��ƶ���ʵ������ͷǰһ���ڵ��Ϊͷ��
		// β�ڵ�ȥ����β�ڵ�ǰһ���ڵ��Ϊβ�ڵ�
		// ����ǰ���ķ������������ͷ��λ��
		switch (direction) {
		case U:
			newHead.setY(head.getY() - Config.UNIT_SIZE);
			newHead.setX(head.getX());
			break;
		case D:
			newHead.setY(head.getY() + Config.UNIT_SIZE);
			newHead.setX(head.getX());
			break;
		case L:
			newHead.setX(head.getX() - Config.UNIT_SIZE);
			newHead.setY(head.getY());
			break;
		case R:
			newHead.setX(head.getX() + Config.UNIT_SIZE);
			newHead.setY(head.getY());
			break;
		}

		// ײ��������
		if (bodys.contains(newHead)) {
			driver.pause();
			return;
		}
		//ײ���߽�
		if (newHead.x <0 || newHead.x > Config.WIN_WIDTH || newHead.y <0 || newHead.y > Config.WIN_HEIGHT) {
			driver.pause();
			return;
		}
		bodys.add(0, newHead);

		Node tail = bodys.get(bodys.size() - 1);
		gc.clearRect(tail.getX(), tail.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
		bodys.remove(tail);

		paint();
	}

	public void grow() {
		Node tail = bodys.get(bodys.size() - 1);
		Node node = new Node();
		// �����Ľڵ���β������ͷ���������պ��෴
		switch (direction) {
		case U:
			node.setY(tail.getY() + Config.UNIT_SIZE);
			node.setX(tail.getX());
			break;
		case D:
			node.setY(tail.getY() - Config.UNIT_SIZE);
			node.setX(tail.getX());
			break;
		case L:
			node.setX(tail.getX() + Config.UNIT_SIZE);
			node.setY(tail.getY());
			break;
		case R:
			node.setX(tail.getX() - Config.UNIT_SIZE);
			node.setY(tail.getY());
			break;
		}

		bodys.add(node);
		paint();
		// ֪ͨdriver��������ʳ��
		driver.productFood();
	}

	public boolean eatFood(Node food) {
		// ͷ����ʳ���ص�����ʾ�Ե���ʳ��
		if (bodys.get(0).equals(food)) {
			gc.clearRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
			return true;
		}
		return false;
	}

	public void toUp() {
		direction = DirectionType.U;
		move();
	}

	public void toDown() {
		direction = DirectionType.D;
		move();
	}

	public void toLeft() {
		direction = DirectionType.L;
		move();
	}

	public void toRight() {
		direction = DirectionType.R;
		move();
	}

	public void initAttr(double x, double y, int length) {
		for (int i = 0; i < length; i++) {
			bodys.add(new Node(x - 10 * i, y));
		}
	}

	public boolean isBody(Node food) {
		return bodys.contains(food);
	}

	public boolean isAlive() {
		return state == 1 ? true : false;
	}

	// �ߵ����ã���������ϵ��ߣ��Լ�����ڵ�����
	public void alive() {
		for (Node node : bodys) {
			gc.clearRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
		}
		this.bodys.clear();
		this.direction = DirectionType.R;
	}

	public void paint() {
		gc.setFill(Config.BODY_COLOR);

		for (Node node : bodys) {
			gc.fillRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
		}
	}
}
