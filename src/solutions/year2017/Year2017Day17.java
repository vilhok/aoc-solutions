package solutions.year2017;

import com.github.aoclib.api.InputParser;
import com.github.aoclib.solver.DayX;

public class Year2017Day17 extends DayX{
 

	@Override
	public Object firstPart(InputParser input){
		int data = input.integer();
		LinkedList<Integer> i = new LinkedList<>(0);
		fillArray(i, data, 2017);
		Node<Integer> n = i.head;
		do{
			n = n.next;
		} while(n.value != 2017);
		return n.next.value;
	}

	/*
	 * This is extremely slow with large values of range!!
	 */
	private void fillArray(LinkedList<Integer> values, int data, int range){
		Node<Integer> n = values.head;
		for(int i = 1; i <= range; i++){
			n = n.next;
			for(int j = 0; j < data; j++){
				n = n.next;

			}
			values.add(n, new Node<>(i));
			if(i % 10000 == 0){
				System.out.println(i);
			}
		}
	}

	@Override
	public Object secondPart(InputParser input){

		int data = input.integer();
		int value = 0;
		int index = 0;
		for(int i = 1; i <= 50000000; i++){
			index = ((index + data) % i) + 1;

			if(index == 1){
				value = i;
			}

		}
		return value;
	}

	public Node<Integer> alternativeSecondPart(int data){
		LinkedList<Integer> i = new LinkedList<>(0);
		fillArray(i, data, 50000000);
		Node<Integer> n = i.head;
		do{
			n = n.next;
		} while(n.value != 0);
		return n;
	}

	// why O_O
	class LinkedList<T> {
		Node<T> head;
		int size = 0;

		public LinkedList(T val){
			head = new Node<>(val);
			head.next = head;
			size++;
		}

		void add(Node<T> parent, Node<T> next){
			next.next = parent.next;
			parent.next = next;
			size++;
		}
	}

	class Node<T> {
		Node<T> next;
		T value;

		public Node(T value){
			this.value = value;
		}
	}
}
