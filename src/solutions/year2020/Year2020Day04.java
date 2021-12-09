package solutions.year2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2020Day04 extends DayX {

	ArrayList<Passport> passports;

//	private final String birthyear = "19[2-9][0-9]|20[0-1][0-9]|2020";
//
//	private final String issueyear = "20(1[0-9]|20)";
//
//	private final String eyr = "20(2[0-9]|30)";
//
//	private String[] regexfields = { birthyear, issueyear, eyr };

	class Passport {

		Set<String> fieldsPart2 = new HashSet<>();
		Set<String> fieldsPart1 = new HashSet<>();
		String[] pwfields;

		public Passport(String[] strs) {

		}

		public void check1() {

		}

//		public boolean check2() {
//			long count = Arrays.stream(pwfields)//
//					.map(f -> Arrays.stream(regexfields)//
//							.map(regex -> f.matches(regex))//
//							.anyMatch(i -> i)//
//					//
//					).count();
//			return count == 8 || (count == 7  && ;
//		}

		public void addLine(String line) {
			String[] values = line.split(" ");
			for (String v : values) {
				String[] field = v.split(":");
				String key = field[0];
				String value = field[1];
				fieldsPart1.add(key);
				boolean valid = false;
				switch (key) {
				case "byr":
					int year = Integer.parseInt(value);
					if (year >= 1920 && year <= 2002) {
						valid = true;
					}
					break;
				case "iyr":
					int iyear = Integer.parseInt(value);
					if (iyear >= 2010 && iyear <= 2020) {
						valid = true;
					}
					break;

				case "eyr":
					int eyr = Integer.parseInt(value);
					if (eyr >= 2020 && eyr <= 2030) {
						valid = true;
					}
					break;
				case "hgt":
					int h;
					if (value.endsWith("cm")) {
						try {
							h = Integer.parseInt(value.substring(0, 3));
							if (h >= 150 && h <= 193) {
								valid = true;
							}
						} catch (NumberFormatException e) {

						}
					} else if (value.endsWith("in")) {
						try {
							h = Integer.parseInt(value.substring(0, 2));
							if (h >= 59 && h <= 76) {
								valid = true;
							}
						} catch (NumberFormatException e) {

						}
					}
					break;
				case "hcl":
					if (value.matches("#[0-9a-f]*") && value.length() == 7) {
						valid = true;
					}
					break;

				case "ecl":
					String colors = "amb blu brn gry grn hzl oth";
					if (colors.contains(value) && value.length() == 3) {
						valid = true;
					}
					break;

				case "pid":
					if (value.length() == 9 && value.matches("[0-9]*")) {
						valid = true;
					}
					break;
				case "cid":
					valid = true;
					break;
				}
				if (valid) {
					fieldsPart2.add(key);
				} else {
					System.out.println("invalid:" + v);
				}
			}
		}

		public boolean validPart1() {
			return fieldsPart1.size() == 8 || (fieldsPart1.size() == 7 && !fieldsPart1.contains("cid"));
		}

		public boolean validPart2() {
			return fieldsPart2.size() == 8 || (fieldsPart2.size() == 7 && !fieldsPart2.contains("cid"));
		}

	}

	@Override
	public Object firstPart(InputParser input) {
		List<String> lines = input.getLines();
		passports = new ArrayList<>();
//		Passport p = new Passport();
		for (String s : lines) {
			if (s.length() > 0) {
//				p.addLine(s);
			} else {
//				passports.add(p);
//				p = new Passport();
			}
		}
//		passports.add(p);

		return passports.stream().filter(Passport::validPart1).count();
	}

	@Override
	public Object secondPart(InputParser input) {
		if (passports == null) {
			firstPart(input);
		}
		return passports.stream().filter(pass -> pass.validPart2()).count();

	}
}