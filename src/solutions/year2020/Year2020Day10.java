package solutions.year2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day10 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		int[] joltages = input.asSingleIntArray();
		Arrays.sort(joltages);
		int ones = 1;
		int threes = 1;
		for (int i = 0; i < joltages.length - 1; i++) {
			if (joltages[i + 1] - joltages[i] == 1) {
				ones++;
			} else {
				threes++;
			}
		}
		return ones * threes;

	}

	// the idea was read from somewhere, shame that I did not find a fast way of
	// doing this
	@Override
	public Object secondPart(InputParser input) {
		int[] joltages = input.asSingleIntArray();
		ArrayList<Integer> ints = new ArrayList<Integer>(Arrays.stream(joltages).boxed().toList());
		ints.add(0);
		ints.add(ints.stream().max(Integer::compare).get() + 3);
		Collections.sort(ints);
//		System.out.println(ints);

		ArrayList<ArrayList<Integer>> splits = new ArrayList<>();

		ArrayList<Integer> next = new ArrayList<>();
		for (Integer i : ints) {
			if (next.isEmpty()) {
				next.add(i);
			} else {
				if (i - next.get(next.size() - 1) != 3) {
					next.add(i);
				} else {
					splits.add(next);
					next = new ArrayList<Integer>();
					next.add(i);
				}
			}
		}
		if (!next.isEmpty())
			splits.add(next);
		System.out.println(splits);
		long result = 1;
		for (ArrayList<Integer> arr : splits) {
			result *= switch (arr.size()) {
			case 3:
				yield 2;
			case 4:
				yield 4;
			case 5:

				yield 7;
			default:
				yield 1;
			};
		}
		return result;
	}

	/**
	 * I looked at the solution and spoiled this task for me. Thefore I forced
	 * myself to figure out an alternate solution that tries every possible
	 * solution. <br>
	 * <br>
	 * As this would take weeks, the time is reduced by two ways: <br>
	 * 1. Keep track of invalid combinations (consecutive numbers) that cannot be
	 * removed at same time. Every time, when such combination is found, skip the
	 * whole subset. <br>
	 * 2. After the subset is skipped, skip all the known solutions as well up to
	 * certain bit lenght. This is done by memoizing the solutions in a table.
	 * 
	 * 
	 * 
	 * This completes in around 2,5 hours on i7-6850K
	 * 
	 * @param input
	 * @return
	 */
	public Object aHorribleSolution(InputParser input) {
		long time = System.currentTimeMillis();
		int[] joltages = input.asSingleIntArray();
		ArrayList<Integer> inputValues = new ArrayList<Integer>(Arrays.stream(joltages).boxed().toList());
		inputValues.add(0);
		inputValues.add(inputValues.stream().max(Integer::compare).get() + 3);
		Collections.sort(inputValues);
		System.out.println("all values:" + inputValues);
		Set<Integer> canRemove = new HashSet<>();
		for (int i = 1; i < inputValues.size() - 1; i++) {
			if (!(inputValues.get(i + 1) - inputValues.get(i - 1) > 3)) {
				canRemove.add(inputValues.get(i));
			}
		}
		ArrayList<Integer> ars = new ArrayList<>(canRemove);
		Collections.sort(ars);

		long iterations = (long) Math.pow(2, canRemove.size());

		ArrayList<Integer> mod = new ArrayList<>();
		for (Integer i : ars) {
			mod.add(inputValues.indexOf(i));
		}

		System.out.println("modifiable indices:" + mod);

		int[] modifiable = mod.stream().mapToInt(i -> i).toArray();
		System.out.println(iterations);
		long total = 0;

		ArrayList<Long> forbidden = new ArrayList<>();

		int[] adapters = inputValues.stream().mapToInt(i -> i).toArray();
		int[] originals = new int[adapters.length];

		System.arraycopy(adapters, 0, originals, 0, adapters.length);
		long[] pows = new long[Long.toBinaryString(iterations).length() - 1];
		for (int i = 0; i < pows.length; i++) {
			pows[i] = (long) Math.pow(2, i);
		}

		long[] validPerBit = new long[modifiable.length];
		validPerBit[0] = 2;
		long[] validSoFar = new long[modifiable.length];
		long[] skipped = new long[modifiable.length];
		int validIndex = 1;

		System.out.println("powers" + Arrays.toString(pows));
		outer:
		for (Long i = 0L; i < iterations; i++) {
			boolean doSkipping = true;
			int skipcnt = 0;
			do {
				doSkipping = false;
				for (Long l : forbidden) {
					if ((i & l) == l) {

						long val = Long.lowestOneBit(l);
						i = i + val;
						skipped[validIndex] += val;
						doSkipping = true;
						skipcnt++;

					}
				}
			} while (doSkipping);

			if (skipcnt > 0) {
				if (Long.bitCount(i) == 1 && i != 1 && i != 2) {
					long soFar = Arrays.stream(validPerBit).sum();
					validPerBit[validIndex] = total - soFar;
					validSoFar[validIndex] = soFar;
					validIndex += 1;
				}
				if (i >= iterations) {
					break;
				}
				boolean shouldAppend = true;
				removeValues(modifiable, adapters, pows, i);

				if (!testValid(adapters)) {
					shouldAppend = false;
					i--;
					continue;
				}
				restoreValues(modifiable, adapters, originals, pows, i);
				long lowest = Long.lowestOneBit(i);
				long known = lowest / 2 / 2;
				if (known != 0 && shouldAppend) {

					int index = (int) (Math.log(known) / Math.log(2) + 1e-10);
					long toAdd = validSoFar[index];
					total += toAdd;
					i += known;

					i--;
					continue outer;

				}
			} else if (Long.bitCount(i) == 1 && i != 1 && i != 2) {

				long soFar = Arrays.stream(validPerBit).sum();
				validPerBit[validIndex] = total - soFar;
				validSoFar[validIndex] = soFar;
				validIndex += 1;

			}

			if (i >= iterations) {
				break;
			}
			removeValues(modifiable, adapters, pows, i);

			boolean addedInvalid = false;
			if (testValid(adapters)) {
				total++;

			} else {
				ArrayList<Integer> removed = new ArrayList<>();
				System.out.println("invalid");
				for (int x = 0; x < pows.length; x++) {
					if ((pows[x] & i) == pows[x]) {
						removed.add(x);
					}
				}
				ArrayList<ArrayList<Integer>> consecutive = new ArrayList<>();
				ArrayList<Integer> next = new ArrayList<>();
				next.add(removed.get(0));
				for (int r = 0; r < removed.size() - 1; r++) {
					int a = removed.get(r);
					int b = removed.get(r + 1) - 1;
					if (a == b) {
						next.add(removed.get(r + 1));
					} else {
						if (next.size() == 3)
							consecutive.add(next);
						next = new ArrayList<>();
					}
				}
				if (next.size() >= 3) {
					consecutive.add(next);
				}
				for (ArrayList<Integer> cons : consecutive) {
					long skipper = 0;
					for (int bit : cons) {
						skipper |= (long) Math.pow(2, bit);
					}
					if (!forbidden.contains(skipper)) {
						forbidden.add(skipper);
						System.out.println("Skipping " + skipper);
						System.out.println(consecutive);
						addedInvalid = true;
					}
				}

			}

			restoreValues(modifiable, adapters, originals, pows, i);

			if (total % 10000000 == 0) {
				System.out.println(total);
				long elapsedSeconds = (System.currentTimeMillis() - time) / 1000;
				System.out.println("Seconds elapsed:" + elapsedSeconds + " " + i);
				System.out.println(i / (double) iterations);
			}

			if (addedInvalid) {
				i--;
			}
		}

		return total;
	}

	private void restoreValues(int[] modifiable, int[] ints, int[] originals, long[] pows, Long i) {
		for (int x = 0; x < pows.length; x++) {
			long p = pows[x];
			if (p > i)
				break;
			if ((p & i) == p) {
				int a = modifiable[x];
				ints[a] = originals[a];
			}
		}
	}

	private void removeValues(int[] modifiable, int[] ints, long[] pows, Long i) {
		for (int x = 0; x < pows.length; x++) {
			long p = pows[x];
			if (p > i)
				break;
			if ((p & i) == p) {
				int a = modifiable[x];
				ints[a] = ints[a - 1];
//					System.out.println("replaced");
			}
		}
	}

	public boolean testValid(int[] joltages) {

		for (int i = 0; i < joltages.length - 1; i++) {
			int diff = joltages[i + 1] - joltages[i];
			if (diff > 3) {
				return false;
			}
		}
		return true;

	}
}