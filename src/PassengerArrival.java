// Nihal Zaman (81899650)
// Heather Fong (73399056)

public class PassengerArrival
{
	private int numOfPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedArrivalTime;

	public PassengerArrival(int numOfPassengers, int destinationFloor, int timePeriod)
	{
		this.numOfPassengers = numOfPassengers;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
		this.expectedArrivalTime = timePeriod;
	}

	public int getNumOfPassengers()
	{
		return numOfPassengers;
	}

	public void setNumOfPassengers(int numOfPassengers)
	{
		this.numOfPassengers = numOfPassengers;
	}

	public int getDestinationFloor()
	{
		return destinationFloor;
	}

	public void setDestinationFloor(int destinationFloor)
	{
		this.destinationFloor = destinationFloor;
	}

	public int getTimePeriod()
	{
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod)
	{
		this.timePeriod = timePeriod;
	}

	public int getExpectedArrivalTime()
	{
		return expectedArrivalTime;
	}

	public void setExpectedArrivalTime(int expectedArrivalTime)
	{
		this.expectedArrivalTime = expectedArrivalTime;
	}

	public String toString(){
		return this.numOfPassengers + " " + this.destinationFloor + " "+ this.timePeriod + " " + this.expectedArrivalTime;
	}
}
