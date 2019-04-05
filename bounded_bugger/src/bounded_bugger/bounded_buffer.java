//package bounded_bugger;
//
//public class bounded_buffer {
//	
//	
//	public synchronized void put(int item) throws InterruptedException
//	{
//		while (numberInBuffer == size) wait();
//		
//		last = (last + 1) % size;
//		numberInBuffer++;
//		
//		buffer[last] = item;
//		notifyAll();
//	}
//	
//	public synchronized int get() throws InterruptedException
//	{
//		while (numberInBuffer == 0) wait();
//		
//		first = (first + 1) % size;
//		numberInBuffer--;
//		
//		notifyAll();
//		return buffer[first];
//	}
//}
