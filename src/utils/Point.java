package utils;

public record Point(int x, int y) {

	public Point add(Point other) {
		return new Point(this.x + other.x, this.y + other.y);
	}

}