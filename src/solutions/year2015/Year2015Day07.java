package solutions.year2015;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

/**
 * 
 * My awesome attempt from 2015. This looks like there would be a much shorter
 * solution available.
 *
 */
public class Year2015Day07 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		Simulation sim = new Simulation();
		int res = 0;
		res = sim.getWireA(input.getLines());

		return res;
	}

	@Override
	public Object secondPart(InputParser input) {
		Simulation sim = new Simulation();
		sim.getWireA(input.getLines());
		return sim.getWireAfterReset();
	}

	private interface Gate {

		public boolean writeWire();

	}

	public class Wire {

		private boolean receivingValue = false;
		private String value;
		final String id;
		final boolean resetable;

		/*
		 * creates a new wire with a id
		 */
		public Wire(String id) {
			boolean reset = true;
			this.id = id;
			try {
				int val = Integer.parseInt(id);
				String valu = Integer.toBinaryString(val);
				while (valu.length() < 16) {
					valu = "0" + valu;
				}
				value = valu;
				receivingValue = true;
				reset = false; // if this has a numerical value as input, this cannot be reset:
			} catch (Exception e) {

			}
			resetable = reset;
		}

		/**
		 * set new value in this wire
		 * 
		 * @param b
		 */
		public void setValue(String b) {
			this.value = b;
			receivingValue = true;
		}

		/**
		 * returns the signal in this string as 16 bit binary string
		 * 
		 * @return
		 */
		public String getValue() {
			return value;
		}

		/**
		 * returns if this wire is transmitting a signal
		 * 
		 * @return
		 */
		public boolean hasSignal() {
			return receivingValue;
		}

		/**
		 * resets this wire if resetable
		 */
		public void reset() {
			if (resetable) {
				receivingValue = false;
				value = "";
			}
		}

	}

	public class SingleInputGate implements Gate {

		Wire in, out;
		final String type;
		private int shiftParam;

		/**
		 * constructor for creating gate with single input.
		 * 
		 * @param in         inputwire
		 * @param out        outputwire
		 * @param type       type of the gate RSHIFT,LSHIFT,NOT,NA (NA=not available,
		 *                   acts just as a wire)
		 * @param shiftParam how much the bitshift will shift
		 */
		public SingleInputGate(Wire in, Wire out, String type, int shiftParam) {

			this.in = in;
			this.type = type;
			this.out = out;
			this.shiftParam = shiftParam;
		}

		public void pls() {

		}

		/**
		 * tries to write into outwire and returns success as boolean
		 * 
		 * @return
		 */
		@Override
		public boolean writeWire() {

			if (!in.hasSignal()) {
				return false;
			}
			if (out.hasSignal())
				return true;

			switch (type) {
			case "RSHIFT":
				out.setValue(rshift());
				break;
			case "LSHIFT":
				out.setValue(lshift());
				break;
			case "NOT":
				out.setValue(not());
				break;
			case "NA":
				out.setValue(in.getValue());
			}
			return true;
		}

		/**
		 * takes input from in and returns bitwise NOT
		 * 
		 * @return
		 */
		private String not() {
			String s = in.getValue();

			s = s.replace("0", "9");
			s = s.replace("1", "0");
			s = s.replace("9", "1");

			return s;
		}

		/**
		 * takes input from in and returns value leftshifted by shiftParam
		 * 
		 * @return
		 */
		private String lshift() {
			String s = in.getValue();

			s = s.substring(shiftParam, s.length());

			while (s.length() < 16) {
				s = s + "0";
			}
			return s;
		}

		/**
		 * takes input from in and returns value rightshifted by shiftParam
		 * 
		 * @return
		 */
		private String rshift() {
			String s = in.getValue();

			s = s.substring(0, s.length() - shiftParam);

			while (s.length() < 16) {
				s = "0" + s;

			}
			return s;
		}

	}

	public class TwoInputGate implements Gate {

		private Wire wire1, wire2, out; // wires

		final String type; // type of the gate

		/**
		 * creates a gate of certain type with two inputs and single output
		 * 
		 * @param input1 inputwire 1
		 * @param input2 inputwire 2
		 * @param out    output wire
		 * @param type   type of the gate(AND, OR)
		 */

		public TwoInputGate(Wire input1, Wire input2, Wire out, String type) {
			this.wire1 = input1;
			this.wire2 = input2;
			this.type = type;
			this.out = out;
		}

		/**
		 * tries to write into outwire and returns success as boolean
		 * 
		 * @return
		 */
		@Override
		public boolean writeWire() {

			if (!wire1.hasSignal() || !wire2.hasSignal()) {
				return false;
			}
			if (out.hasSignal())
				return true;
			switch (type) {

			case "AND":
				out.setValue(and());
				break;
			case "OR":
				out.setValue(or());
				break;

			}

			return true;

		}

		/**
		 * takes inputs from wire1 and wire1 and returns bitwise or
		 * 
		 * @return
		 */
		private String or() {
			String a = wire1.getValue();
			String b = wire2.getValue();
			String result = "";
			for (int i = 0; i < 16; i++) {
				if (a.charAt(i) == '1' || b.charAt(i) == '1') {
					result += "1";
				} else {
					result += "0";
				}
			}
			return result;
		}

		/**
		 * takes inputs from wire1 and wire1 and returns bitwise and
		 * 
		 * @return
		 */
		private String and() {
			String a = wire1.getValue();
			String b = wire2.getValue();
			String result = "";
			for (int i = 0; i < 16; i++) {
				if (a.charAt(i) == '1' && b.charAt(i) == '1') {
					result += "1";
				} else {
					result += "0";
				}
			}
			return result;
		}

	}

	public class Simulation {

		private ArrayList<Wire> wires = new ArrayList<Wire>();
		private ArrayList<Gate> gates = new ArrayList<Gate>();
		private ArrayList<String> gatenames = new ArrayList<String>();

		public Simulation() {

		}

		public int getWireA(List<String> lines) {
			String[] s = { "OR", "AND", "RSHIFT", "LSHIFT", "NOT" };
			for (String g : s) {
				gatenames.add(g);
			}
			loadGates(lines);
			Wire a = getWire("a"); // resultwire as parameter

			while (!a.hasSignal()) {
				updateGates();

			}
			return Integer.parseInt(a.getValue(), 2);

		}

		public int getWireAfterReset() {
			Wire b = getWire("b");
			Wire a = getWire("a");

			String value = a.getValue();

			for (Wire w : wires) {
				w.reset();
			}
			b.setValue(value);

			while (!a.hasSignal()) {
				updateGates();
			}
			a = getWire("a");
			return Integer.parseInt(a.getValue(), 2);
		}

		private void updateGates() {

			for (Gate g : gates) {
				g.writeWire();
			}

		}

		/**
		 * load gates from a file
		 * 
		 * @throws IOException
		 */
		private void loadGates(List<String> lines) {

			// create all the wires
			for (String line : lines) {

				String[] wireNames = getWireNames(line);

				for (String s : wireNames) {
					if (s.equals(""))
						continue; // no empty lines!

					if (!wireAdded(s)) {
						wires.add(new Wire(s)); // add wire if not added
					}
				}

			}
			// create gates
			for (String line : lines) {
				String gateType = getType(line);
				String[] wireNames = getWireNames(line);
				Gate g;
				switch (gateType) {

				case "OR":
					g = new TwoInputGate(getWire(wireNames[0]), getWire(wireNames[1]), getWire(wireNames[2]), "OR");
					gates.add(g);
					break;
				case "AND":
					g = new TwoInputGate(getWire(wireNames[0]), getWire(wireNames[1]), getWire(wireNames[2]), "AND");
					gates.add(g);
					break;
				case "RSHIFT":
					g = new SingleInputGate(getWire(wireNames[0]), getWire(wireNames[2]), "RSHIFT",
							Integer.parseInt(wireNames[1]));
					gates.add(g);
					break;
				case "LSHIFT":
					g = new SingleInputGate(getWire(wireNames[0]), getWire(wireNames[2]), "LSHIFT",
							Integer.parseInt(wireNames[1]));
					gates.add(g);
					break;
				case "NOT":
					g = new SingleInputGate(getWire(wireNames[1]), getWire(wireNames[2]), "NOT", -1);
					gates.add(g);
					break;
				case "NA":
					g = new SingleInputGate(getWire(wireNames[0]), getWire(wireNames[1]), "NA", -1);
					gates.add(g);
					break;
				default:
					System.out.println("Unknown gate type on line: " + line);
					System.exit(0);
				}
			}

		}

		private Wire getWire(String string) {
			for (Wire w : wires) {
				if (w.id.equals(string))
					return w;
			}
			return null;
		}

		/*
		 * test if certain wire is added to the wirelist
		 */
		private boolean wireAdded(String s) {
			for (Wire f : wires) {
				if (f.id.equals(s)) {
					return true;
				}
			}
			return false;
		}

		private String[] getWireNames(String string) {
			String[] wires = string.split(" -> |( *(NOT|RSHIFT|LSHIFT|OR|AND) *)");

			return wires;
		}

		private String getType(String s) {

			String[] params = s.split(" | -> ");

			for (String f : params) {
				if (gatenames.contains(f))
					return f;
			}
			return "NA";

		}

	}
}