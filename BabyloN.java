import java.util.Arrays;

/**
 * BabyloN, a tower of hanoi solution for N by M stacks & stack sizes.
 *
 * @author Zachary Chandler
 */
public class BabyloN {

	private final Stack[] stacks;
	private Integer holding;
	
	public static void main(String[] args) {
		BabyloN b = new BabyloN(3, 8);

		System.out.println(b);
		b.move(0, 0, 2);
		System.out.println(b);
	}
	
	public BabyloN(int stacks, int stackSize) {
		this.stacks = new Stack[stacks];
		holding = null;
		
		for (int i = 0; i < this.stacks.length; i++) {
			this.stacks[i] = new Stack(stackSize);
		}
		
		for (int i = stackSize; i >= 1; i--) {
			this.stacks[0].push(i);
		}
	}
	
	/**
	 * Move all values in stack1 at position or higher onto stack2.
	 * @param stack1
	 * @param position
	 * @param stack2
	 * @return the position they were placed onto.
	 */
	public int move(int stack1, int position, int stack2) {
		int top = stacks[stack1].getLength();
		int other = getOtherStack(stack1, stack2);
		int positionOther = stacks[other].getLength() + 1;
		
		if (top == position) {
			pop(stack1);
			push(stack2);
			return stacks[stack2].getLength();
		} else if (position < top) {
			move(stack1, position + 1, other);
			move(stack1, position, stack2);
			return move(other, positionOther, stack2);
		} else {
			throw new IllegalArgumentException("Can't move null values.");
		}
	}

	private int getOtherStack(int stack1, int stack2) {
		if (stack1 > stack2) {
			int swap = stack1;
			stack1 = stack2;
			stack2 = swap;
		}
		
		if (stack1 + 1 != stack2) {
			return stack1 + 1;
		} else {
			return stack1 == 0 ? stack2 + 1 : 0; 
		}
	}
	
	public void pop(int stack) {
		if (holding != null) {
			throw new UnsupportedOperationException();
		}
		
		holding = stacks[stack].pop();
	}
	
	public void push(int stack) {
		if (holding == null) {
			throw new UnsupportedOperationException();
		}

		if (!stacks[stack].isEmpty() && stacks[stack].peek() < stack) {
			throw new UnsupportedOperationException();
		}
		
		stacks[stack].push(holding);
		holding = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Babyl [holding=");
		builder.append(holding);
		
		builder.append(", stacks=");
		builder.append(Arrays.toString(stacks));			
		
		builder.append("]");
		return builder.toString();
	}
	
	private static class Stack {
		private final int[] ints;
		
		private int size;
		
		public Stack(int size) {
			ints = new int[size];
			this.size = 0;
		}
		
		public int getLength() {
			return size - 1;
		}

		public int pop() {
			if (size < 1) {
				throw new UnsupportedOperationException();
			}
			
			return ints[--size];
		}
		
		public int peek() {
			return ints[size];
		}

		public boolean isEmpty() {
			return size >= 0;
		}

		public int push(int val) {
			if (size >= ints.length) {
				throw new UnsupportedOperationException();
			}
			
			return ints[size++] = val; 
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Stack [");
			
			if (size > 0) {	
				builder.append(ints[0]);			
			}
			
			for (int i = 1; i < size; i++) {
				builder.append(", ");
				builder.append(ints[i]);
				
			}
			builder.append("]");
			return builder.toString();
		}
	}
}
