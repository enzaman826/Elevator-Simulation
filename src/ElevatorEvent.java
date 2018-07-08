// Nihal Zaman (81899650)
// Heather Fong (73399056)

public class ElevatorEvent
{
	private int destinationFloor;
	private int expectedArrivalTime;

	public ElevatorEvent(int destinationFloor, int expectedArrivalTime)
	{
		this.destinationFloor = destinationFloor;
		this.expectedArrivalTime = expectedArrivalTime;
	}

	public int getDestinationFloor()
	{
		return destinationFloor;
	}

	public void setDestinationFloor(int destinationFloor)
	{
		this.destinationFloor = destinationFloor;
	}

	public int getExpectedArrivalTime()
	{
		return this.expectedArrivalTime;
	}

	public void setExpectedArrivalTime(int time)
	{
		this.expectedArrivalTime = time;
	}

	public String toString(){
		return "destinationFloor: " + destinationFloor + " expectedArrivalTime: " + expectedArrivalTime;
	}
}