package main;

import com.github.aoclib.db.DBManager;
import com.github.aoclib.solver.Solver;

public class Main {

	public static void main(String[] args){
		DBManager.setFile("aoc.db");
		new Solver(args).run();
		
	}
}
