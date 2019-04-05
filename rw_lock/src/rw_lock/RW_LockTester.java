package rw_lock;

import java.util.Random;

class RW_lock
{
	private boolean writing;
	private int readers;
	private int waitingWriters;
	
	public synchronized void startWrite()
		throws InterruptedException
	{
		while (readers > 0 || writing)
		{
			waitingWriters++;
			wait();
			waitingWriters--;
		}
		writing = true;
	}
	
	public synchronized void stopWrite()
	{
		writing = false;
		notifyAll();
	}
	
	public synchronized void startRead()
		throws InterruptedException
	{
		while (waitingWriters > 0 || writing) wait();
		readers++;
	}
	
	public synchronized void stopRead()
	{
		readers--;
		if (readers == 0) notifyAll();
	}
}

class IntWrapper
{
	int i;
	
	public IntWrapper() {
		i = 0;
	}
	
	public IntWrapper(int init) {
		i = init;
	}
}

public class RW_LockTester 
{
	public static void main(String[] args)
	{
		IntWrapper var = new IntWrapper();
		
		RW_lock lock = new RW_lock();
		
		Thread read1 = new Thread(new Runnable() 
		{
			public void run()
			{
				try {
					lock.startRead();
					System.out.println("Read1 var: " + var.i);
					lock.stopRead();
					
					lock.startWrite();
					var.i = 1;
					lock.stopWrite();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Thread read2 = new Thread(new Runnable() 
		{
			public void run()
			{
				try {
					lock.startRead();
					System.out.println("Read2 var: " + var.i);
					lock.stopRead();
					
					lock.startWrite();
					var.i = 2;
					lock.stopWrite();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Thread read3 = new Thread(new Runnable() 
		{
			public void run()
			{
				try {
					lock.startRead();
					System.out.println("Read3 var: " + var.i);
					lock.stopRead();
					
					lock.startWrite();
					var.i = 3;
					lock.stopWrite();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		read1.start();
		read2.start();
		read3.start();
		
		try
		{
			read1.join();
			read2.join();
			read3.join();
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		System.out.println("Var: " + var.i);
	}
}