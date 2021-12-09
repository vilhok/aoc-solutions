package solutions.year2019;

import java.util.ArrayList;
import java.util.Arrays;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

import solutions.year2019.intcodecomputer.IntcodeComputer;

public class Year2019Day09 extends DayX {

	public boolean copyItself() {
		long[] copyprog = { 109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99 };

		IntcodeComputer ic = IntcodeComputer.day9capable(copyprog);
		ic.runProgram();
		ArrayList<Long> out = new ArrayList<>();
		while (ic.hasOutput()) {
			out.add(ic.readNext());
		}

		long[] o = new long[out.size()];
		for (int i = 0; i < o.length; i++) {
			o[i] = out.get(i);
		}
		System.out.println(Arrays.toString(copyprog));
		System.out.println(Arrays.toString(o));

		return Arrays.equals(copyprog, o);

	}

	public boolean bigOutput() {
		long[] copyprog = { 1102, 34915192, 34915192, 7, 4, 7, 99, 0 };

		IntcodeComputer ic = IntcodeComputer.day9capable(copyprog);
		ic.runProgram();
		System.out.println(ic.readNext());
		ArrayList<Long> out = new ArrayList<>();
		while (ic.hasOutput()) {
			out.add(ic.readNext());
		}
		return false;

	}

	@Override
	public Object firstPart(InputParser input) {

		long[] program = input.intCodeProgram();
		IntcodeComputer ic = IntcodeComputer.day9capable(program);

		ic.writeInput(1);
		ic.runProgram();

		ArrayList<Long> out = new ArrayList<>();
		while (ic.hasOutput()) {
			out.add(ic.readNext());

		}

		for (int i = 0; i < out.size() - 1; i++) {
			long l = out.get(i);
			if (l != 0) {
				System.out.println("Faulty code:" + i);
			}
		}

		return out.get(out.size()-1);

	}

	@Override
	public Object secondPart(InputParser input) {
		long[] program = input.intCodeProgram();
		IntcodeComputer ic = IntcodeComputer.day9capable(program);
		ic.writeInput(2);
		ic.runProgram();
		return ic.readNext();
	}
}