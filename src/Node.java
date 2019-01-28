
public class Node {
	protected double x;
	protected double y;

	public Node() {
	}

	public Node(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		// list.contains用到了该方法，需要重写
		Node node = (Node) obj;
		if (this.x == node.getX() && this.y == node.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "[" + this.x + ".." + this.y + "]";
	}
}
