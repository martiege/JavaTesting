package resource_manager_test;


class ResourceManager
{
	private final int maxResources = 1;
	private int resourcesFree;
	
	public ResourceManager() 
	{
		resourcesFree = maxResources;
	}
	
	public synchronized void  allocate(int size)
	{
		while (resourcesFree < size)
		{
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		resourcesFree = resourcesFree - size;
	}
	
	public synchronized void free(int size)
	{
		resourcesFree = resourcesFree + size;
		notifyAll();
	}
}


public class ResourceManagerTest 
{
	public static void main(String[] args)
	{
		ResourceManager rm = new ResourceManager();
		
		int arr[] = {0};
		
		Thread t1 = new Thread(new Runnable() 
		{
			public void run()
			{
				rm.allocate(1);
				
				int sum = 0;
				for (int i = 0; i < 1000; i++)
				{
					arr[0] = i;
					sum += arr[0];
				}
				
				System.out.println("Current sum: " + sum);
				rm.free(1);
			}
		});
		
		Thread t2 = new Thread(new Runnable() 
		{
			public void run()
			{
				rm.allocate(1);
				
				int sum = 0;
				for (int i = 0; i < 1000; i++)
				{
					arr[0] = -i;
					sum += arr[0];
				}
				
				System.out.println("Current sum: " + sum);
				rm.free(1);
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
		
		System.out.println("Array: " + arr[0]);
	}
}






