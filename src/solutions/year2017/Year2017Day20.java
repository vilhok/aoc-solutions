package solutions.year2017;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day20 extends DayX {

	class Triplet {
		final int x;
		final int y;
		final int z;

		public Triplet(int x, int y, int z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Triplet(String string) {
			string = string.substring(string.indexOf('<') + 1, string.length() - 1);
			String[] num = string.split(",");
			this.x = Integer.parseInt(num[0]);
			this.y = Integer.parseInt(num[1]);
			this.z = Integer.parseInt(num[2]);
		}

		public boolean matches(Triplet p) {
			return p.x == this.x && p.y == this.y && p.z == this.z;
		}

		public Triplet add(Triplet a) {

			return new Triplet(x + a.x, y + a.y, z + a.z);
		}

		public double vector() {
			return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

		}

		@Override
		public String toString() {
			return x + "," + y + "," + z;
		}

	}

	int partid = 0;

	class Particle implements Comparable<Particle> {

		Triplet p;
		Triplet v;
		Triplet a;
		int i = 0;

		public Particle(Triplet p, Triplet v, Triplet a) {
			super();
			this.p = p;
			this.v = v;
			this.a = a;
			i = partid++;
		}

		public boolean collides(Triplet other) {
			return other.matches(p);
		}

		public void accelerate() {
			v = v.add(a);
		}

		public void move() {
			p = p.add(v);
		}

		@Override
		public int compareTo(Particle o) {
			int ac =  Double.valueOf(a.vector()).compareTo(o.a.vector());
			if (ac != 0) {
				return ac;
			}
			int vel = Double.valueOf(v.vector()).compareTo(o.v.vector());
			if (vel != 0) {
				return vel;
			}
			return Double.valueOf(p.vector()).compareTo(o.p.vector());

		}
	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();
		ArrayList<Particle> particles = parseParticles(lines);
		Collections.sort(particles);
		return particles.get(0).i;// leastAcceleration + 1; // index

	}

	private ArrayList<Particle> parseParticles(List<String> lines) {
		ArrayList<Particle> particles = new ArrayList<>();
		for (String s : lines) {
			String[] data = s.split(", ");
			Triplet p, a, v;
			p = new Triplet(data[0]);
			v = new Triplet(data[1]);
			a = new Triplet(data[2]);
			particles.add(new Particle(p, v, a));
		}
		return particles;
	}

	/**
	 * The second part is bruteforcing until there are no collisions any more and
	 * this takes time and might not happen.
	 * 
	 * A better, non-simulation could be something like this:<br>
	 * 1. For each particle, calculate if they collide<br>
	 * 2. For each colliding particle, calculate when they would collide<br>
	 * 3. Resolve all collisions with least time discard the rest (as further
	 * collisions might not happen anymore)<br>
	 * 4. start over, until no more collisions will happen.<br>
	 */
	@Override
	public Object secondPart(InputParser input) {

		List<String> lines = input.getLines();
		ArrayList<Particle> particles = parseParticles(lines);

		ArrayList<Particle> toRemove = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			for (Particle p : particles) {
				for (Particle k : particles) {
					if (p.i == k.i)
						continue;
					if (p.p.matches(k.p)) {
						if (!toRemove.contains(p))
							toRemove.add(p);

					}
				}
			}

			for (Particle p : toRemove) {
				particles.remove(p);
			}
			toRemove.clear();
			for (Particle p : particles) {
				p.accelerate();
				p.move();
			}
		}

		System.out.println("\tWarning: bruteforced/uncertain result for Year 2017 day 20 part 2");

		return particles.size();

	}

//	private void writeToFile(ArrayList<Particle> particles, ArrayList<Particle> toRemove) throws IOException {
//		FileWriter fw = new FileWriter(new File("d20out.txt"));
//		fw.append("pos;");
//		for (Particle p : particles) {
//			if (!toRemove.contains(p))
//				fw.append(p.p + ";");
//		}
//		fw.append("\n");
//		fw.append("col;");
//		for (Particle p : toRemove) {
//			fw.append(p.p + ";");
//		}
//
//		fw.append("\n");
//		fw.flush();
//		fw.close();
//	}

}
