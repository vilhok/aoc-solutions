package solutions.year2015;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;

public class Year2015Day12 extends DayX {

	public int sumNumbers(JsonNode node, boolean part2) {
		Iterator<JsonNode> nodes = node.elements();
		int sum = 0;
		while (nodes.hasNext()) {
			JsonNode n = nodes.next();
			if (n.isArray() || n.isObject()) {
				sum += sumNumbers(n, part2);
			} else if (n.isInt()) {
				sum += n.asInt();
			} else if (part2 && node.isObject() && n.textValue().equals("red")) {
				return 0;
			}
		}
		return sum;
	}

	@Override
	public Object firstPart(InputParser input) {
		String in = input.joinLinesToString(Delimiter.NONE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode actualObj = mapper.readTree(in);
			return sumNumbers(actualObj, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// FAILED if this is reached
		return NOT_SOLVED;
	}

	@Override
	public Object secondPart(InputParser input) {
		String in = input.joinLinesToString(Delimiter.NONE);

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode actualObj = mapper.readTree(in);
			return sumNumbers(actualObj, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// FAILED if this is reached
		return NOT_SOLVED;
	}

	public Object alternateFirstPart(InputParser input) {
		// instead of using a library, we can just find all numbers
		Matcher m = Pattern.compile("-?[0-9]+").matcher(input.string());
		int sum = 0;
		while (m.find()) {
			sum += Integer.parseInt(m.group());
		}
		return sum;
	}
}