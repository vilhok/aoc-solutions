package solutions.year2017;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;
import com.github.aoclib.utils.Delimiter;
import com.github.aoclib.utils.Instruction;

public class Year2017Day18 extends DayX{

	@Override
	public Object firstPart(InputParser input){
		List<Instruction> lines = input.as2017Instruction(Delimiter.SPACE);

		Queue<Long> q0 = new LinkedList<>();
		Program p1 = new Program(0,lines, q0, q0);
		LongWrapper val = new LongWrapper();

		p1.setCallBack(() -> {
			val.val = p1.lastSnd(); // what ensures that this value is going to be correct if this is fired multiple
									// times.
		});
		while(!p1.isWaiting()){
			p1.next();
		}

		return val.val;
	}

	@Override
	public Object secondPart(InputParser input){
		List<Instruction> lines = input.as2017Instruction(Delimiter.SPACE);
		Queue<Long> q0 = new LinkedList<>();
		Queue<Long> q1 = new LinkedList<>();
		Program p0 = new Program(0,lines, q0, q1);
		Program p1 = new Program(1,lines, q1, q0);
		while(!p1.isWaiting() || !p0.isWaiting()){
			p0.next();
			p1.next();
		}
		return p1.sent;
	}

}

class Program{

	int pointer = 0;
	long pid;
	List<Instruction> prog1;
	Map<String, Long> registers = new HashMap<>();
	Queue<Long> snd;
	Queue<Long> rcv;
	boolean waiting;
	int sent = 0;
	Long previousrcv = 0L;

	Runnable callback = () -> {
	};

	public Program(int id,List<Instruction> prog1, Queue<Long> snd, Queue<Long> rcv){
		this.prog1 = prog1;
		this.snd = snd;
		this.rcv = rcv;
		this.pid = id;
		registers.put("p", pid);
	}

	public boolean isWaiting(){
		return waiting;
	}

	public Long lastSnd(){
		Long l = -1L;
		while(snd.peek() != null){
			l = snd.poll();
		}
		return l;
	}

	public void setCallBack(Runnable r){
		this.callback = r;
	}

	public void next(){
		if(pointer >= prog1.size()){
			waiting = true;
			return;

		}
		if(rcv.peek() != null){
			waiting = false;
		}
		if(waiting)
			return;
		Instruction ins = prog1.get(pointer);
		// System.out.println(pid + " " + ins.name() + " " + pointer);
		switch(ins.name()) {
		case "snd":
			if(snd != null)
				snd.add(getValue(ins.arg(0), registers));
			pointer++;
			sent++;
			return;
		case "rcv":
			if(rcv.peek() == null){
				waiting = true;
				return;
			}
			if(rcv.peek() > 0){
				callback.run();
			}
			registers.put(ins.arg(0), rcv.poll());
			pointer++;
			return;
		case "jgz":
			if(getValue(ins.arg(0), registers) > 0){
				long val = getValue(ins.arg(1), registers);
				// System.out.println(val);
				pointer += val != 0 ? val : 1;

			} else{
				pointer++;
			}

			return;
		}

		long targetRegValue = registers.getOrDefault(ins.arg(0), 0L);
		long src = getValue(ins.arg(1), registers);
		String target = ins.arg(0);
		switch(ins.name()) {
		case "set":
			registers.put(target, src);
			break;
		case "add":
			registers.put(target, targetRegValue + src);
			break;
		case "mul":
			registers.put(target, targetRegValue * src);

			break;
		case "mod":
			registers.put(target, targetRegValue % src);
		}
		pointer++;
	}

	private static boolean isRegister(String arg){
		return arg.matches("[a-z]");
	}

	private static long getValue(String a, Map<String, Long> reg){
		return isRegister(a) //
				? reg.getOrDefault(a, 0L)
				: Long.parseLong(a);
	}
}

//mutable integer wrapper
class LongWrapper{
	Long val = 0L;
}
