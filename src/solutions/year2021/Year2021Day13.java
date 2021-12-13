package solutions.year2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Year2021Day13 extends DayX {

	record Point(int x, int y) {
	}

	String regex = "fold along (.)=(.*)";

	public int countPoints(int[][] m){
		int count = 0;
		for (int[] row : m) {
			for (int i : row) {
				if (i == 1) {
					count++;
				}
			}
		}
		return count;
	}

	public int[][] horizontalFoldFail(int[][] matrix, int foldLine){
//		printMatrix(matrix);
		System.out.println(
			"folding" + foldLine + " for width " + matrix[0].length);
		int newwidth;
		if (foldLine >= matrix[0].length / 2) {
			newwidth = foldLine + 1;
		} else {
			newwidth = matrix[0].length - foldLine;

		}

		int offset = 0;
		System.out.println("foldline:" + foldLine);
		if (foldLine < matrix[0].length / 2) {

			offset = newwidth - foldLine - 1;
		} else {

			System.out.println("No offset");

		}

//		printMatrix(matrix);
//		System.out.println(matrix.length + " " + matrix[0].length);
		int[][] newMatrix = new int[matrix.length][newwidth];
//		System.out.println(newwidth + " " + matrix.length);

		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < foldLine; x++) {
				newMatrix[y][x + offset] = matrix[y][x];
			}
		}
//		System.out.println("after copy");
//		printMatrix(newMatrix);
		for (int y = 0; y < matrix.length; y++) {
			int i = 0;
			for (int x = foldLine; x < matrix[y].length; x++) {
				if (matrix[y][x] == 1)
					newMatrix[y][newwidth - 1 - i] = matrix[y][x];
				i++;
			}
		}

		printMatrix(newMatrix);
		return newMatrix;
	}

	public int[][] horizontalFold(int[][] matrix, int foldCount){
//		printMatrix(matrix);
		System.out.println(
			"folding" + foldCount + " for width " + matrix[0].length);
		int newwidth;
		newwidth = foldCount + 1;
		int[][] newMatrix = new int[matrix.length][newwidth];
//		System.out.println(newwidth + " " + matrix.length);

		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < foldCount; x++) {
				newMatrix[y][x] = matrix[y][x];
			}
		}
		for (int y = 0; y < matrix.length; y++) {
			int i = 0;
			for (int x = foldCount; x < matrix[y].length; x++) {
				if (matrix[y][x] == 1)
					newMatrix[y][newwidth - 1 - i] = matrix[y][x];
				i++;
			}
		}

		printMatrix(newMatrix);
		return newMatrix;
	}

	public int[][] verticalFold(int[][] matrix, int foldLine){
//		printMatrix(matrix);
		System.out
				.println("folding" + foldLine + " for height " + matrix.length);
		int newHeight = foldLine + 1;

		System.out.println("new: " + newHeight);
		int[][] newMatrix = new int[newHeight][matrix[0].length];
		printMatrix2(matrix);
		for (int y = 0; y < foldLine; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				newMatrix[y][x] = matrix[y][x];
			}
		}
		printMatrix(newMatrix);
		int i = 0;
		for (int y = foldLine; y < matrix.length; y++) {

			for (int x = 0; x < matrix[0].length; x++) {
//				System.out.println(y + " " + i + " " + x);
				if (matrix[y][x] == 1)
					newMatrix[newMatrix.length - 1 - i][x] = matrix[y][x];

			}
			i++;
		}
		printMatrix(newMatrix);

		return newMatrix;
	}

	public int[][] verticalFoldFail(int[][] matrix, int foldLine){
//		printMatrix(matrix);
		System.out
				.println("folding" + foldLine + " for height " + matrix.length);
		int newHeight;
		if (foldLine >= matrix.length / 2) {
			newHeight = foldLine + 1;
		} else {
			newHeight = matrix.length - foldLine;

		}

		int offset = 0;
		if (foldLine < matrix.length / 2) {
			System.out.println("fold stats:" + matrix.length / 2);
			offset = newHeight - foldLine - 1;
		}

		System.out.println("new: " + newHeight);
		System.out.println(offset);
		int[][] newMatrix = new int[newHeight][matrix[0].length];
		printMatrix2(matrix);
		for (int y = 0; y < foldLine; y++) {
			for (int x = 0; x < matrix[0].length; x++) {
				newMatrix[y + offset][x] = matrix[y][x];
			}
		}
		printMatrix(newMatrix);
		int i = 0;
		for (int y = foldLine; y < matrix.length; y++) {

			for (int x = 0; x < matrix[0].length; x++) {
//				System.out.println(y + " " + i + " " + x);
				if (matrix[y][x] == 1)
					newMatrix[newMatrix.length - 1 - i][x] = matrix[y][x];

			}
			i++;
		}
		printMatrix(newMatrix);

		return newMatrix;
	}

	public void printMatrix(int[][] matrix){
		for (int[] i : matrix) {
			String s = Arrays.toString(i);
			s = s.replaceAll("[\\[\\] ,]", "");
			s = s.replace("0", ".");
			s = s.replaceAll("1", "#");
			System.out.println(s);
		}
		System.out.println();
	}

	public void printMatrix2(int[][] matrix){

		for (int[] i : matrix) {
			String s = Arrays.toString(i);
			s = s.replaceAll("[\\[\\] ,]", "");
			s = s.replace("0", ".");
			s = s.replaceAll("1", "#");
			System.out.println(s);
		}
		System.out.println();
	}

	@Override
	public Object firstPart(InputParser input){
		List<List<String>> in = input.getGroups();
		List<String> folds = in.get(1);
		List<String> coordinates = in.get(0);
		List<Point> points = new ArrayList<>();
		int maxX = 0;
		int maxY = 0;
		for (String s : coordinates) {
			String[] vals = s.split(",");
			int x = Integer.parseInt(vals[0]);
			int y = Integer.parseInt(vals[1]);
			if (x > maxX) {
				maxX = x;
			}
			if (y > maxY) {
				maxY = y;
			}
			points.add(new Point(x, y));
		}

		int[][] matrix = new int[maxY + 1][maxX + 1];

		for (Point p : points) {
			matrix[p.y][p.x] = 1;
		}

//		for (String firstRow : folds) {
		String firstRow = folds.get(0);
		System.out.println(firstRow);
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(firstRow);
		m.matches();

		char dir = m.group(1).charAt(0);
		int fold = Integer.parseInt(m.group(2));
		System.out.println(dir + " " + fold);
		if (dir == 'x') {
			matrix = horizontalFold(matrix, fold);
		} else {

			matrix = verticalFold(matrix, fold);
		}
//		}
		int result = countPoints(matrix);
//		printMatrix(matrix);
		System.out.println(result);
		return result;
	}

	@Override
	public Object secondPart(InputParser input){
		List<List<String>> in = input.getGroups();
		List<String> folds = in.get(1);
		List<String> coordinates = in.get(0);
		List<Point> points = new ArrayList<>();
		int maxX = 0;
		int maxY = 0;
		for (String s : coordinates) {
			String[] vals = s.split(",");
			int x = Integer.parseInt(vals[0]);
			int y = Integer.parseInt(vals[1]);
			if (x > maxX) {
				maxX = x;
			}
			if (y > maxY) {
				maxY = y;
			}
			points.add(new Point(x, y));
		}

		int[][] matrix = new int[maxY + 1][maxX + 1];

		for (Point p : points) {
			matrix[p.y][p.x] = 1;
		}

		for (String firstRow : folds) {
//			String firstRow = folds.get(0);
			System.out.println(firstRow);
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(firstRow);
			m.matches();

			printMatrix(matrix);
			char dir = m.group(1).charAt(0);
			int fold = Integer.parseInt(m.group(2));
			System.out.println(dir + " " + fold);
			if (dir == 'x') {
				matrix = horizontalFold(matrix, fold);
			} else {

				matrix = verticalFold(matrix, fold);
			}

//			printMatrix(matrix);
		}
		printMatrix2(matrix);
		System.out.println(matrix.length + " " + matrix[0].length);
//		NOT_SOLVED_IS_PASSED=true;
		return NOT_SOLVED;
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
	protected void insertTestsPart1(List<Test> tests){
//		String s = """
//				0,0
//				1,1
//				2,2
//				3,3
//				4,4
//				5,5
//				6,6
//				7,7
//				8,8
//
//				fold along x=""";
//		for (int i = 0; i < 9; i++)
//			tests.add(new Test(s + i, -1));

		tests.add(
			new Test("6,10\n" + "0,14\n" + "9,10\n" + "0,3\n" + "10,4\n"
					+ "4,11\n" + "6,0\n" + "6,12\n" + "4,1\n" + "0,13\n"
					+ "10,12\n" + "3,4\n" + "3,0\n" + "8,4\n" + "1,10\n"
					+ "2,14\n" + "8,10\n" + "9,0\n" + "\n" + "fold along y=7\n"
					+ "fold along x=5", 17));

	}

	@Override
	protected void insertTestsPart2(List<Test> tests){
//		String s = """
//				0,0
//				1,1
//				2,2
//				3,3
//				4,4
//				5,5
//				6,6
//				7,7
//				8,8
//				9,9
//
//				fold along y=""";
//		for (int i = 0; i < 10; i++)
//			tests.add(new Test(s + i, -1));
//		tests.add(
//			new Test("6,10\n" + "0,14\n" + "9,10\n" + "0,3\n" + "10,4\n"
//					+ "4,11\n" + "6,0\n" + "6,12\n" + "4,1\n" + "0,13\n"
//					+ "10,12\n" + "3,4\n" + "3,0\n" + "8,4\n" + "1,10\n"
//					+ "2,14\n" + "8,10\n" + "9,0\n" + "\n" + "fold along y=7\n"
//					+ "fold along x=5", 17));
//
//		tests.add(new Test(hamatti, 5));
	}

	String hamatti = """
			159,344
			771,626
			1140,868
			790,25
			607,254
			375,140
			924,198
			293,548
			1068,798
			1140,26
			890,750
			960,506
			510,886
			592,87
			72,627
			1131,110
			1173,760
			249,690
			291,841
			1019,501
			465,312
			1062,84
			1206,696
			594,414
			1191,651
			5,33
			557,16
			388,480
			246,862
			1082,446
			582,822
			556,222
			266,739
			304,506
			137,121
			301,519
			596,343
			654,479
			497,715
			309,250
			966,520
			1176,119
			587,728
			239,767
			1109,539
			433,826
			587,480
			1169,383
			49,257
			1260,297
			1280,44
			141,455
			671,112
			835,784
			1268,215
			1092,266
			1268,7
			428,87
			206,551
			25,519
			1252,775
			771,275
			393,654
			1206,198
			1034,530
			826,387
			274,719
			335,605
			1173,691
			59,304
			967,232
			377,749
			651,158
			691,119
			335,737
			45,156
			1280,492
			6,868
			813,795
			512,869
			1285,375
			649,257
			199,186
			1091,121
			1295,176
			95,250
			335,829
			274,175
			816,357
			42,7
			321,459
			380,719
			93,637
			771,619
			475,784
			318,179
			810,491
			279,700
			261,215
			1121,536
			137,760
			1292,631
			219,569
			743,879
			248,810
			403,444
			278,595
			465,569
			103,763
			899,707
			498,698
			1297,555
			1250,739
			2,637
			395,463
			734,574
			619,36
			83,259
			1221,625
			909,196
			1014,477
			1248,680
			759,266
			30,641
			417,868
			1183,691
			783,87
			1195,306
			1215,868
			97,379
			619,53
			443,268
			1228,648
			1014,507
			1114,371
			544,206
			564,374
			890,872
			509,532
			483,737
			465,536
			1071,749
			1144,537
			58,306
			144,886
			1308,698
			733,624
			587,838
			301,534
			1121,858
			611,243
			867,268
			13,555
			703,640
			992,267
			753,515
			527,190
			413,568
			421,77
			1203,121
			1236,507
			652,644
			497,534
			642,873
			797,159
			20,892
			431,439
			248,285
			1128,31
			1129,33
			1083,26
			852,371
			659,102
			803,155
			279,194
			796,455
			191,432
			771,140
			1009,88
			883,26
			1155,381
			1144,357
			1294,117
			211,44
			182,863
			552,803
			639,827
			980,705
			1063,126
			763,708
			905,288
			445,704
			1066,638
			527,87
			509,301
			425,318
			345,642
			720,520
			967,616
			1078,518
			1009,375
			104,887
			714,159
			960,58
			840,25
			1250,155
			276,536
			170,868
			602,427
			0,52
			915,463
			853,375
			473,369
			837,369
			1113,843
			1038,516
			1165,77
			90,520
			254,851
			263,194
			17,637
			935,754
			594,155
			242,833
			853,395
			525,570
			662,698
			436,266
			236,598
			950,257
			810,641
			567,127
			703,642
			1221,420
			753,16
			889,77
			514,252
			472,456
			249,204
			845,536
			827,737
			754,222
			790,473
			38,245
			874,182
			1155,513
			698,7
			355,238
			80,382
			1099,460
			838,8
			1256,712
			1004,826
			233,33
			1250,403
			574,730
			602,19
			238,28
			1064,557
			1049,240
			596,159
			775,467
			639,379
			764,628
			60,491
			145,481
			835,54
			791,120
			718,456
			423,235
			552,539
			575,707
			704,595
			681,131
			557,515
			1213,291
			189,858
			291,393
			406,810
			594,29
			525,324
			306,739
			1176,473
			189,708
			1305,637
			1017,71
			1213,379
			1225,18
			209,190
			1027,651
			818,703
			1307,736
			701,604
			447,19
			1096,862
			1297,746
			1230,382
			877,826
			708,427
			720,117
			311,830
			1225,288
			736,606
			1093,264
			30,44
			1245,414
			1173,614
			574,288
			1079,340
			1165,810
			300,303
			251,179
			124,679
			845,235
			361,647
			1207,579
			589,567
			262,184
			840,2
			980,481
			1196,497
			1044,155
			877,75
			206,103
			783,203
			465,179
			833,756
			386,887
			189,355
			268,646
			279,340
			393,688
			719,472
			157,99
			375,340
			833,260
			1087,192
			271,260
			840,886
			527,651
			104,63
			232,518
			980,413
			888,376
			223,254
			574,836
			403,226
			448,551
			602,175
			1077,861
			1082,894
			473,749
			902,414
			55,708
			1156,253
			703,192
			144,415
			413,158
			1036,467
			589,327
			1006,518
			960,836
			810,627
			761,628
			874,628
			291,501
			95,698
			1295,782
			333,540
			330,705
			668,873
			1230,730
			1176,7
			88,70
			35,393
			594,739
			194,877
			955,238
			0,842
			977,540
			977,316
			935,642
			581,196
			862,791
			783,691
			147,840
			698,315
			803,739
			189,325
			649,705
			393,575
			1243,474
			64,744
			345,250
			184,869
			1121,708
			196,371
			304,56
			55,186
			1099,588
			20,378
			1307,774
			1019,138
			1072,866
			393,206
			716,739
			274,467
			393,319
			17,257
			965,250
			453,539
			1206,831
			1063,544
			723,166
			182,438
			1246,441
			242,798
			157,715
			1221,269
			805,756
			1150,357
			189,312
			253,196
			853,499
			427,84
			590,520
			1111,691
			793,121
			1251,142
			1161,298
			967,448
			304,836
			244,638
			775,691
			848,327
			229,196
			686,641
			349,467
			1121,312
			405,158
			1227,187
			500,253
			796,642
			137,582
			1304,868
			893,26
			730,387
			581,308
			1263,700
			52,467
			812,698
			35,858
			472,723
			743,767
			465,599
			1048,710
			238,866
			473,77
			674,527
			1001,705
			383,634
			1305,379
			1071,145
			659,288
			1042,159
			1087,254
			1252,588
			411,208
			790,775
			241,400
			917,43
			1280,726
			1001,250
			393,240
			930,719
			965,189
			223,192
			248,84
			1267,467
			43,243
			436,488
			1069,781
			1129,525
			682,292
			1248,214
			1099,306
			457,395
			867,476
			800,232
			1063,824
			863,19
			296,417
			831,229
			219,345
			648,698
			512,25
			62,680
			223,455
			5,481
			477,756
			1256,630
			1141,171
			582,520
			965,392
			114,621
			319,737
			334,499
			813,534
			1078,600
			228,446
			577,288
			60,539
			1176,869
			309,196
			607,640
			182,31
			1217,145
			330,180
			360,257
			95,144
			407,483
			517,569
			999,830
			1058,163
			639,247
			6,26
			1141,771
			1079,428
			177,472
			231,728
			920,662
			134,775
			309,257
			321,588
			671,827
			801,413
			243,187
			691,53
			547,186
			845,569
			510,415
			247,96
			771,306
			392,486
			1017,619
			607,702
			899,208
			656,842
			1017,395
			1205,68
			484,26
			375,442
			145,616
			1116,185
			463,120
			1203,773
			714,735
			425,739
			651,102
			838,662
			672,117
			1131,211
			1128,367
			691,36
			662,868
			281,19
			127,651
			219,840
			309,54
			845,325
			408,836
			517,728
			388,414
			1208,539
			82,246
			1289,254
			1168,8
			1289,640
			396,40
			882,807
			281,106
			228,894
			283,243
			321,435
			897,494
			547,392
			1170,455
			1096,166
			1061,606
			1223,463
			182,456
			874,404
			648,26
			908,731
			671,624
			720,632
			406,161
			349,875
			98,5
			1049,127
			803,211
			218,266
			1212,889
			704,635
			345,257
			1103,875
			658,257
			475,278
			552,701
			120,775
			761,714
			458,371
			519,774
			169,771
			892,519
			510,8
			343,662
			803,683
			1091,569
			1238,627
			182,415
			462,567
			1285,728
			897,158
			639,647
			422,376
			2,196
			1064,32
			758,193
			80,512
			746,374
			231,166
			393,215
			1252,119
			1293,705
			885,155
			206,791
			681,203
			93,145
			247,544
			443,418
			145,593
			1119,432
			917,319
			652,250
			1031,194
			672,777
			62,255
			674,695
			234,144
			15,176
			758,803
			624,253
			328,724
			654,842
			243,208
			1215,280
			1118,869
			1017,548
			304,376
			907,715
			170,26
			514,439
			299,120
			159,792
			559,78
			371,651
			1191,467
			986,637
			656,415
			639,278
			999,446
			965,698
			268,567
			333,683
			530,537
			793,728
			865,704
			803,879
			1079,302
			513,327
			95,78
			350,616
			436,628
			84,833
			1001,840
			343,232
			134,7
			1091,345
			1243,207
			1047,472
			1068,385
			417,26
			969,474
			989,528
			95,868
			1228,246
			668,469
			5,637
			1186,679
			345,644
			1066,256
			629,71
			281,754
			103,315
			724,182
			228,453
			1310,191
			1153,795
			770,273
			1031,700
			933,593
			539,82
			798,25
			783,634
			261,127
			488,357
			723,838
			1200,878
			497,179
			780,357
			837,749
			49,301
			898,294
			596,306
			1011,120
			241,333
			589,414
			982,170
			309,189
			574,830
			1001,196
			1044,865
			1304,698
			977,77
			417,756
			1150,89
			577,382
			736,730
			254,374
			649,481
			413,669
			90,879
			933,861
			42,215
			330,238
			887,235
			611,427
			642,21
			893,138
			1104,103
			612,579
			754,0
			472,8
			386,24
			433,490
			1196,397
			567,15
			1062,138
			231,302
			115,12
			960,616
			345,189
			805,876
			303,847
			89,420
			1006,58
			378,373
			393,43
			3,736
			875,345
			264,456
			800,886
			1252,63
			535,203
			875,54
			1006,388
			965,54
			89,625
			248,609
			15,616
			60,851
			268,159
			826,26
			525,499
			119,467
			1103,691
			879,192
			246,32
			266,865
			934,819
			254,491
			830,694
			505,18
			671,247
			559,302
			582,72
			246,557
			611,651
			1039,634
			736,830
			333,354
			189,536
			418,879
			433,819
			967,446
			1285,519
			692,467
			965,257
			739,259
			492,703
			54,404
			845,179
			462,697
			661,257
			1215,144
			738,190
			274,427
			1285,166
			95,18
			1255,186
			247,126
			36,467
			75,603
			641,250
			1154,238
			1121,569
			1063,70
			1099,754
			333,316
			1074,598
			418,519
			710,264
			1280,850
			658,644
			45,315
			335,65
			375,252
			1036,175
			634,135
			89,474
			607,666
			651,606
			840,869
			1191,691
			590,632
			709,121
			874,490
			1262,595
			991,289
			728,72
			1096,614
			83,187
			739,824
			892,851
			1016,819
			1051,32
			455,203
			965,642
			247,350
			624,402
			721,567
			937,875

			fold along x=655
			fold along y=447
			fold along x=327
			fold along y=223
			fold along x=163
			fold along y=111
			fold along x=81
			fold along y=55
			fold along x=40
			fold along y=27
			fold along y=13
			fold along y=6""";
}
