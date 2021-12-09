package solutions.year2020.computer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Computer {

	public static final int NOP = 0;
	public static final int ACC = 1;
	public static final int JMP = 2;
	static final Map<String, Integer> operations = Map.of("nop", NOP, "acc", ACC, "jmp", JMP);

	private int[] program;
	private int programCounter = 0;
	private long acc = 0;

	public Computer(int[] program) {
		this.program = program;
	}
	
	public Computer(List<List<String>> rawInput) {
		this.program = getProgram(rawInput);
	}

	public void executeNext() {
		int instruction = program[programCounter];

		switch (instruction) {
		case NOP:
			programCounter += 2;
			break;
		case ACC:
			acc += program[programCounter + 1];
			programCounter += 2;
			break;
		case JMP:
			programCounter += program[programCounter + 1] * 2;
			break;
		default:
			throw new RuntimeException("Unknown instruction:" + instruction);

		}
	}

	public boolean detectInfiniteLoop() {
		boolean[] visited = new boolean[program.length];

		do {
			visited[programCounter] = true;
			executeNext();
		} while (programCounter < program.length && !visited[programCounter]);

		return programCounter < program.length;
	}

	public long getAcc() {
		return acc;
	}

	public static int[] getProgram(List<List<String>> input) {
		List<Integer> program = new ArrayList<>();

		for (List<String> i : input) {
			program.add(operations.get(i.get(0)));
			program.add(Integer.parseInt(i.get(1)));
		}

		return program//
				.stream()//
				.mapToInt(i -> i.intValue())//
				.toArray();
	}

}
