package solutions.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

/**
 * <p>
 * AUTOGENERATED BY DayGenerator.java
 * </p>
 *
 * <p>
 * https://github.com/vilhok/aoc-lib
 * </p>
 *
 * <p>
 * Edits in this file will not be overwritten.
 * </p>
 *
 */
public class Year2021Day21 extends DayX {

	String regex = "Player 1 starting position: (.*)";

	@Override
	public Object firstPart(InputParser input) {
		List<String> players = input.getLines();
		int[] positions = new int[2];
		int[] score = new int[2];
		int dice = 1;
		int dicerolls = 0;
		for (int i = 0; i < players.size(); i++) {
			int space = players.get(i).lastIndexOf(' ') + 1;
			positions[i] = Integer.parseInt(players.get(i).substring(space));
		}
		int turn = 0;

		while (Integer.max(score[0], score[1]) < 1000) {
			int result = 0;
			for (int i = 0; i < 3; i++) {
				result += dice++;
				dicerolls++;
				if (dice == 101) {
					dice = 1;
				}
			}
			if (result >= 10) {
				result %= 10;
			}
			positions[turn % 2] += result;

			if (positions[turn % 2] > 10)
				positions[turn % 2] %= 10;
			score[turn % 2] += positions[turn % 2];
			turn++;

		}

		return Integer.min(score[0], score[1]) * dicerolls;
	}

	record Wins(long p1wins, long p2wins) {
		public long max() {
			return Long.max(p1wins, p2wins);
		}

		public Wins add(Wins other) {
			return new Wins(this.p1wins + other.p1wins, this.p2wins + other.p2wins);
		}
	}

	record GameState(long p1score, long p2score, long p1board, long p2board, boolean p1turn) {

		public long max() {
			return Long.max(p1score, p2score);
		}

		public GameState inc1andSwitch(int[] board, int turn) {
			return new GameState(p1score + board[turn], p2score, board[0], board[1], !p1turn);
		}

		public GameState inc2andSwitch(int[] board, int turn) {
			return new GameState(p1score, p2score + board[turn], board[0], board[1], !p1turn);
		}

	}

	/**
	 * How many combinations of dice throws causes this player to win.
	 * 
	 * @param startState
	 * @return
	 */
	public Wins playAllDiceCombinations(GameState startState) {

		return null;
	}

	public Wins playAllUniverses(HashMap<GameState, Wins> winsFromState, GameState currentState, int[] boardPosition,
			int wincondition) {
		if (winsFromState.containsKey(currentState)) {
			return winsFromState.get(currentState);
		}

		Wins result = new Wins(0, 0);

		int wins = 0;
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {
				for (int k = 1; k <= 3; k++) {
					int dicesum = i + j + k;
					int turn = currentState.p1turn ? 0 : 1;
					int[] boardData = copyboard(boardPosition);
					boardData[turn] += dicesum;
					if (boardData[turn] > 10) {
						boardData[turn] %= 10;
					}
					GameState newState;
					if (turn == 0)
						newState = currentState.inc1andSwitch(boardData, turn);
					else {
						newState = currentState.inc2andSwitch(boardData, turn);
					}

					if (newState.max() >= wincondition) {
						wins++;
					} else {
						result = result.add(playAllUniverses(winsFromState, newState, boardData, wincondition));
					}
				}
			}
		}
		if (currentState.p1turn) {
			Wins current = new Wins(wins, 0);
			current = current.add(result);
			winsFromState.put(currentState, current);
			return current;
		} else {
			Wins current = new Wins(0, wins);
			current = current.add(result);
			winsFromState.put(currentState, current);
			return current;
		}

	}

	public int[] copyboard(int[] board) {
		return new int[] { board[0], board[1] };
	}

	@Override
	public Object secondPart(InputParser input) {
		List<String> players = input.getLines();
		int[] positions = new int[2];
		for (int i = 0; i < players.size(); i++) {
			int space = players.get(i).lastIndexOf(' ') + 1;
			positions[i] = Integer.parseInt(players.get(i).substring(space));
		}
		HashMap<GameState, Wins> wins = new HashMap<>();
		Wins result = playAllUniverses(wins, new GameState(0, 0, positions[0], positions[1], true), positions, 21);

		return result.max();
	}

	/*
	 * Optional: add tests for each part in the following methods
	 *
	 * These methods have blank implementations in superclass as well and can be
	 * deleted if you don't want to include tests.
	 *
	 * Add test as follows:
	 *
	 * new Test("sampleinput", expectedSolution);
	 *
	 * Collect the tests from the task web page.
	 */

	@Override
	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("""
				Player 1 starting position: 4
				Player 2 starting position: 8
						""", 739785));

	}

	@Override
	protected void insertTestsPart2(List<Test> tests) {
		tests.add(new Test("""
				Player 1 starting position: 4
				Player 2 starting position: 8
						""", 444356092776315L));
	}
}
