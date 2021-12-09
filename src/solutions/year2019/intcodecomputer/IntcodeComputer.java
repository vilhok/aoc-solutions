package solutions.year2019.intcodecomputer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class IntcodeComputer {

	private Thread runner;

	private Instruction[] opcodeTable;

	private long[] program;

	private int instructionPointer = 0;
	private int relativeBase = 0;

	private LinkedBlockingQueue<Long> input;
	private LinkedBlockingQueue<Long> output;

	private LinkedBlockingQueue<Long> outputLog;

	private String id = "";

	public void setID(char c) {
		this.id = String.valueOf(c);
	}

	public static IntcodeComputer day2(long[] program) {
		return new IntcodeComputer(program);
	}

	public static IntcodeComputer day5capable(long[] program) {
		IntcodeComputer ic = new IntcodeComputer(program);
		ic.loadDay5extendedInstructions();
		return ic;
	}

	public static IntcodeComputer day9capable(long[] program) {
		IntcodeComputer ic = day5capable(program);
		ic.loadDay9extendedInstructions();
		return ic;
	}

	private IntcodeComputer(long[] program) {
		long[] prog = new long[program.length];
		System.arraycopy(program, 0, prog, 0, program.length);
		this.program = prog;

		loadDefaultInstructions();

		input = new LinkedBlockingQueue<Long>();
		output = new LinkedBlockingQueue<Long>();
		outputLog = new LinkedBlockingQueue<Long>();
	}

	private void loadDay5extendedInstructions() {
		// input
		opcodeTable[3] = (opcode) -> {
			try {
				long in = input.poll(1, TimeUnit.HOURS);

				int[] modes = parseModes(opcode);
				int index = indexFromMode(modes[0],instructionPointer + 1);
				program[index] = in;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return 2;
		};

		// output
		opcodeTable[4] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index = indexFromMode(modes[0], instructionPointer + 1);
			long op = program[index];
			output.add(op);
			outputLog.add(op);
			return 2;
		};

		// jump-if-true
		opcodeTable[5] = (opcode) -> {
			int[] modes = parseModes(opcode);

			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);
			if (program[index1] != 0) {
				instructionPointer = (int) program[index2];
				return 0;
			} else {
				return 3;
			}
		};
		// jump-if-false
		opcodeTable[6] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);

			if (program[index1] == 0) {
				instructionPointer = (int) program[index2];
				return 0;
			} else {
				return 3;
			}
		};

		// less-than
		opcodeTable[7] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);
			int index3 = indexFromMode(modes[2], instructionPointer + 3);

			if (program[index1] < program[index2]) {
				program[index3] = 1;
			} else {
				program[index3] = 0;
			}
			return 4;
		};

		// equal
		opcodeTable[8] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);
			int index3 = indexFromMode(modes[2], instructionPointer + 3);

			if (program[index1] == program[index2]) {
				program[index3] = 1;
			} else {
				program[index3] = 0;
			}
			return 4;
		};

	}

	private void loadDay9extendedInstructions() {
		// adjustBase
		opcodeTable[9] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index = indexFromMode(modes[0], instructionPointer + 1);
			relativeBase += program[index];
			return 2;
		};
	}

	private long ensureIndex(long index) {
		if (program.length <= index) {
			long[] newprogram = new long[(int) index + 1];
			System.arraycopy(program, 0, newprogram, 0, program.length);
			System.out.println("Increasing memory from " + program.length + " " + newprogram.length);
			program = newprogram;
		}
		return index;
	}

	private int indexFromMode(int mode, int ip) {
		switch (mode) {
		case 2:
			return (int) ensureIndex(relativeBase + program[ip]);
		case 1:
			return (int) ensureIndex(ip);
		case 0:
			return (int) ensureIndex(program[ip]);
		default:
			throw new RuntimeException();
		}
	}

	private int[] parseModes(int opcode) {
		opcode = opcode + 100000;
		String s = "" + opcode;
		s = s.substring(1, 4);
		int m3 = Integer.parseInt("" + s.charAt(0));
		int m2 = Integer.parseInt("" + s.charAt(1));
		int m1 = Integer.parseInt("" + s.charAt(2));
		return new int[] { m1, m2, m3 };
	}

	private void loadDefaultInstructions() {
		opcodeTable = new Instruction[100];

		// add
		opcodeTable[1] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);
			int index3 = indexFromMode(modes[2], instructionPointer + 3);

			program[index3] = program[index1] + program[index2];
			return 4;
		};

		// mul
		opcodeTable[2] = (opcode) -> {
			int[] modes = parseModes(opcode);
			int index1 = indexFromMode(modes[0], instructionPointer + 1);
			int index2 = indexFromMode(modes[1], instructionPointer + 2);
			int index3 = indexFromMode(modes[2], instructionPointer + 3);

			program[index3] = program[index1] * program[index2];
			return 4;
		};

		opcodeTable[99] = (opcode) -> {
			return 0;
		};
	}

	public void executeNext() {
		ensureIndex(instructionPointer);
		int opcode = (int) program[instructionPointer] % 100;
		int target = opcodeTable[opcode].execute((int) program[instructionPointer]);
		instructionPointer += target;
	}

	public int runProgram() {
		while (program[instructionPointer] != 99) {
			executeNext();

		}
		return (int) program[0];
	}

	public Long readNext() {
		while (true) {
			try {
				return output.poll(5, TimeUnit.HOURS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public IntcodeComputer writeInput(long i) {
		input.add(i);
		return this;
	}

	public boolean hasOutput() {
		return !output.isEmpty();
	}

	public void connect(IntcodeComputer ic) {
		this.input = ic.output;
	}

	public boolean hasEnded() {
		return program[instructionPointer] == 99;
	}

	public void start() {
		this.runner = new Thread(() -> this.runProgram());
		runner.start();
	}

	public void join() {
		try {
			runner.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private interface Instruction {

		public int execute(int modes);

	}
	


	public String t(Object o) {
		String s = o.getClass().getName();
		return o.getClass().getName().substring(s.lastIndexOf(".") + 1) + "@" + Integer.toHexString(o.hashCode());
	}

	public void info() {
		System.out.println("This is computer " + id + ". \nmy input is " + t(input) + " and output is " + t(output));
	}

}
