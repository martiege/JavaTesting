




class Pot
{
	private int m_servingsLeft = 3;
	private boolean m_awakenCook = false;
	
	public synchronized void eat()
	{
		if (m_servingsLeft > 0)
		{
			m_servingsLeft--;
		}
		else
		{
			m_awakenCook = true;
			notifyAll();
			while (m_servingsLeft == 0) 
			{
				try {
					wait();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			m_servingsLeft--;
		}
	}
	
	public synchronized void cook()
	{
		while (!m_awakenCook) 
		{
			try {
				wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("New servings");
		m_awakenCook = false;
		m_servingsLeft = 3;
		notifyAll();
	}
}


public class PotTest 
{
	Pot p = new Pot();
	
	Thread t1 = new Thread(new Runnable() 
	{
		public void run()
		{
			for (int i = 0; i < 100; i++)
			{
				p.eat();
			}
		}
	});
	
}
