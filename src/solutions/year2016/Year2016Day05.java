package solutions.year2016;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2016Day05 extends DayX {

	@Override
	public Object firstPart(InputParser input) {
		String s = input.string();
		StringBuilder sb = new StringBuilder();

		MessageDigest mesg = null;
		try {
			mesg = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		char[] chrs = "0123456789abcdef".toCharArray();

		int i = 0;
		while (sb.length() < 8) {
			int val = i++;
			byte[] bytes = mesg.digest((s + val).getBytes(Charset.forName("UTF-8")));
			if (bytes[0] == 0 && bytes[1] == 0 && (bytes[2] & 0xf0) == 0) {
				int a = bytes[2] & 0xf;
				sb.append(chrs[a]);
			}
		}
		return sb;
	}

	@Override
	public Object secondPart(InputParser input) {
		String s = input.string();
		StringBuilder sb = new StringBuilder("_".repeat(8));

		MessageDigest mesg = null;
		try {
			mesg = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		char[] chrs = "0123456789abcdef".toCharArray();
		
		int i = 0;
		while (sb.chars().anyMatch(ch -> ch == '_')) {
			int val = i++;
			byte[] bytes = mesg.digest((s + val).getBytes(Charset.forName("UTF-8")));

			if ((bytes[0] == 0 && bytes[1] == 0 && (bytes[2] & 0xf0) == 0)) {
				int position = bytes[2] & 0xf;
				if (position < 8 && sb.charAt(position) == '_') {
					sb.setCharAt(position, chrs[position]);

				}
			}
		}
		return sb;
	}

	protected void insertTestsPart1(List<Test> tests) {
		tests.add(new Test("abc", "18f47a30"));
	}

}