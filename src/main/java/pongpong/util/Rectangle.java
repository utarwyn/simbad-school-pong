package pongpong.util;

public class Rectangle {

	private double x;

	private double y;

	private double width;

	private double height;

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public double getLeft() {
		return this.x;
	}

	public double getTop() {
		return this.y;
	}

	public double getRight() {
		return this.x + this.width;
	}

	public double getBottom() {
		return this.y + this.height;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	@Override
	public Rectangle clone() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	@Override
	public String toString() {
		return "Rectangle{" +
				"x=" + x +
				", y=" + y +
				", width=" + width +
				", height=" + height +
				'}';
	}

}
