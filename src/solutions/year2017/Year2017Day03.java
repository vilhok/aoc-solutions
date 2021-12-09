package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day03 extends DayX {

	public Object firstPart(InputParser input) {
		final int DATA = input.integer();
		int x = 0, y = 0;
		int current = 1;
		int next = 0;
		while (current < DATA) { // ugh..
			next++;
			x += next;
			current += next;
			if (current >= DATA) {
				x -= current - DATA;
				break;
			}
			y += next;
			current += next;
			if (current >= DATA) {
				y -= current - DATA;
				break;
			}
			next++;
			x -= next;
			current += next;
			if (current >= DATA) {
				x += current - DATA;
				break;
			}
			y -= next;
			current += next;
			if (current >= DATA) {
				y += current - DATA;
				break;
			}

		}
		return Math.abs(x) + Math.abs(y);
	}

	/*
	 * Creates an array, starts from the center. May throw out of bounds exception.
	 * This might not be the best code anyway.
	 */
	public Object secondPart(InputParser input) {
		final int DATA = input.integer();
		int arraySize = 1000;

		int[][] array = new int[arraySize][arraySize];
		int x = array.length / 2;
		int y = array.length / 2;
		array[x][y] = 1;
		int stepsToDo = 1;
		int currentSteps = 0;
		int nextSteps = 1;
		int sum = 0;
		Direction d = Direction.RIGHT;
		while (sum < DATA) {
			currentSteps++;

			array[x + d.x][y + d.y] = sum(array, x + d.x, y + d.y);
			sum = array[x + d.x][y + d.y];
			x += d.x;
			y += d.y;
			if (currentSteps == stepsToDo) {
				d = d.next;
				if (nextSteps == stepsToDo) {
					nextSteps++;
				} else {
					stepsToDo = nextSteps;
				}
				currentSteps = 0;
			}

		}
		return sum;
	}

	enum Direction {
		UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);

		final int x;
		final int y;
		Direction next;
		static {
			UP.next = LEFT;
			LEFT.next = DOWN;
			DOWN.next = RIGHT;
			RIGHT.next = UP;
		}

		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private static int sum(int[][] array, int x, int y) {
		int sum = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				sum += array[x + i][y + j];
			}
		}
		return sum;
	}

}
