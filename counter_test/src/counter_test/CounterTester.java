package counter_test;

class NonWorkingCounter
{
	private static int count = 0;
	
	public void increment()
	{
		count++;
	}
	
	public void decrement()
	{
		count--;
	}
	
	public void print()
	{
		System.out.println("count: " + count);
	}
}

class WorkingCounter
{
	private static int count = 0;
	
	synchronized public void increment()
	{
		count++;
	}
	
	synchronized public void decrement()
	{
		count--;
	}
	
	public void print()
	{
		System.out.println("count: " + count);
	}
}

public class CounterTester 
{
	public static void main(String[] args)
	{
		NonWorkingCounter nwc = new NonWorkingCounter();
		
		Thread t1 = new Thread(new Runnable() 
		{
			public void run()
			{
				for (int i = 0; i < 100000; i++)
				{
					nwc.increment();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() 
		{
			public void run()
			{
				for (int i = 0; i < 100000; i++)
				{
					nwc.decrement();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try
		{
			t1.join();
			t2.join();
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		nwc.print();
		
		WorkingCounter wc = new WorkingCounter();
		
		Thread t3 = new Thread(new Runnable() 
		{
			public void run()
			{
				for (int i = 0; i < 100000; i++)
				{
					wc.increment();
				}
			}
		});
		
		Thread t4 = new Thread(new Runnable() 
		{
			public void run()
			{
				for (int i = 0; i < 100000; i++)
				{
					wc.decrement();
				}
			}
		});
		
		t3.start();
		t4.start();
		
		try
		{
			t3.join();
			t4.join();
		} catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		wc.print();
	}
}