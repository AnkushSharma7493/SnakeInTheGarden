package com.game.snake;

import java.util.Random;
import java.util.Scanner;

public class Ground {
	private static int X;
	private static int Y;
	private static Snake snake;
	private static Snake HEAD;
	private static int length;
	private static int food_X = 3;
	private static int food_Y = 3;
	private static Scanner sc;
	private static int STAGE = 1;
	private static char snakeSkin = '@';

	public static void main(String[] args) {
		start();
	}

	private static void initialize() {
		sc = new Scanner(System.in);
		X = 5;
		Y = 5;
		HEAD = new Snake(0, 0, null, null, '@');
		snake = HEAD;
		length = 1;
		System.out.println("									CONTROLS");
		System.out.println();
		System.out
				.println("					Keys :: 8 -> UP , 2 -> DOWN , 4 -> BACKWORD , 6 -> FORWARD, 9 -> EXIT ");
		System.out.println("					@ --> SNAKE HEAD");
		System.out.println("					# --> FOOD");
	}

	public static void start() {
		initialize();
		print();
		String action=null;
		try {
			 action = sc.nextLine();
		} catch (Exception e) {
			System.out.println("Something went wrong :( ");
			System.out.println("Please launch the game again . . .");
		}
		boolean move = false;
		while (!action.equals("9")) {
			switch (action) {
			case "8":
				move = up();
				break;
			case "2":
				move = down();
				break;
			case "4":
				move = backward();
				break;
			case "6":
				move = forward();
				break;
			default:
				System.out.println(
						"					Expected Keys :: 8 -> UP , 2 -> DOWN , 4 -> BACKWORD , 6 -> FORWARD ");
			}
			if (move) {
				print();
			} else {
				System.out.println("					Invalid key");
			}
			action = sc.nextLine();
		}
		System.out.println("                             Thank you for Playing :)");
	}

	private static boolean foodgrabed(int x, int y) {
		if (x == food_X && y == food_Y) {
			length++;
			HEAD = new Snake(x, y, snake, null, '@');
			HEAD.next = snake;
			snake.previous = HEAD;
			snake.value = '0';
			snake = HEAD;

			// reassign food coordinates
			Random r = new Random();
			int next_food_x = r.nextInt(X - 2) + 1;
			int next_food_y = r.nextInt(X - 2) + 1;
			food_X = (next_food_x != food_X ? next_food_x : next_food_x - 1);
			food_Y = (next_food_y != food_Y ? next_food_y : next_food_y - 1);

			if (length > X - 2 && length > Y - 2) {
				STAGE++;
				System.out
						.println("                             CONGRATULATION, YOU HAVE ENTERED INTO STAGE : " + STAGE);
				System.out.println();
				System.out.println();
				System.out.println();
				X += 5;
				Y += 5;
			}
			// check food location is not on snake length
			while (doexist(snake, food_X, food_Y)) {
				food_X++;
				food_Y++;
			}

			return true;
		}
		return false;
	}

	private static boolean doexist(Snake s, int x, int y) {
		while (s != null) {
			if (s.x == x && s.y == y) {
				snakeSkin = s.value;
				return true;
			}
			s = s.next;
		}
		return false;
	}

	private static void print() {
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				if (doexist(snake, j, i)) {
					System.out.print(snakeSkin + "   ");
					continue;
				}
				if (food_X == j && food_Y == i) {
					System.out.print("#   ");
					continue;
				}
				System.out.print("*   ");
			}
			System.out.println();
			System.out.println();
		}
	}

	private static boolean up() {
		// if head is in first row
		boolean flag = false;
		if (snake.y > 0) {
			flag = true;
			int y = snake.y;
			if (foodgrabed(snake.x, --y)) {
				return flag;
			}

			snakeRun(snake);
			snake.y--;
		}
		return flag;
	}

	private static boolean down() {
		boolean flag = false;
		if (snake.y < Y) {
			flag = true;
			int y = snake.y;
			if (foodgrabed(snake.x, ++y)) {
				return flag;
			}
			snakeRun(snake);
			snake.y++;
		}
		return flag;
	}

	private static boolean forward() {
		boolean flag = false;
		if (snake.x < X) {
			flag = true;
			int x = snake.x;
			if (foodgrabed(++x, snake.y)) {
				return flag;
			}
			snakeRun(snake);
			snake.x++;
		}
		return flag;
	}

	private static boolean backward() {
		boolean flag = false;
		if (snake.x > 0) {
			flag = true;
			int x = snake.x;
			if (foodgrabed(--x, snake.y)) {
				return flag;
			}
			snakeRun(snake);
			snake.x--;
		}
		return flag;
	}

	private static void snakeRun(Snake snake) {
		if (snake.next != null) {
			snakeRun(snake.next);
			snakeStep(snake);
		} else {
			snakeStep(snake);
		}
	}

	public static void snakeStep(Snake snake) {
		if (snake.previous != null) {
			snake.x = snake.previous.x;
			snake.y = snake.previous.y;
		}
	}
}

class Snake {
	int x;
	int y;
	Snake next;
	Snake previous;
	char value;

	public Snake(int x, int y, Snake next, Snake previous, char value) {
		this.x = x;
		this.y = y;
		this.next = next;
		this.previous = previous;
		this.value = value;
	}

}
