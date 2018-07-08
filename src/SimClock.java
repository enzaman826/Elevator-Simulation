// Nihal Zaman (81899650)
// Heather Fong (73399056)
public class SimClock
{
	private static int simulatedTime;
	
	public static int getSimulatedTime()
	{
		return simulatedTime;
	}

	public static void setSimulatedTime(int simulatedTime)
	{
		SimClock.simulatedTime = simulatedTime;
	}
	
	public SimClock(){
		SimClock.simulatedTime = 0;
	}
	
	public static void tick(){
		simulatedTime++;
	}

}
