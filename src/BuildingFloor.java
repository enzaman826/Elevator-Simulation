// Nihal Zaman (81899650)
// Heather Fong (73399056)

public class BuildingFloor
{
	private int[] totalDestinationRequests = new int[Lab4.numOfFloors];
	private int[] arrivedPassengers = new int[Lab4.numOfElevators];
	private int[] passengerRequests = new int[Lab4.numOfFloors];
	private int approachingElevator = -1;
		
	// Checks to see if a building floor has passengers waiting for an elevator.
	public boolean hasPassengers()
	{
		for (int i = 0; i < passengerRequests.length; i++)
		{
			if (passengerRequests[i] > 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int[] getTotalDestinationRequests()
	{
		return totalDestinationRequests;
	}
	
	public void setTotalDestinationRequests(int[] totalDestinationRequests)
	{
		this.totalDestinationRequests = totalDestinationRequests;
	}
	
	public int[] getArrivedPassengers()
	{
		return arrivedPassengers;
	}
	
	public void setArrivedPassengers(int[] arrivedPassengers)
	{
		this.arrivedPassengers = arrivedPassengers;
	}
	
	public int[] getPassengerRequests()
	{
		return passengerRequests;
	}
	
	public void setPassengerRequests(int[] passengerRequests)
	{
		this.passengerRequests = passengerRequests;
	}
	
	public int getApproachingElevator()
	{
		return approachingElevator;
	}
	
	public void setApproachingElevator(int approachingElevator)
	{
		this.approachingElevator = approachingElevator;
	}
	
	// Prints the total passenger requests for the floor, the current passenger requests for the floor, and the 
	// the passengers who have arrived on the floor from each elevator.
	public void printFloorInfo()
	{
		System.out.println("starting to print totalRequests ");
		for (int i = 0; i < Lab4.numOfFloors; i++)
		{
			System.out.print(totalDestinationRequests[i] + " ");
		}
		
		System.out.println("\nstarting to print currentRequests ");
		for (int i = 0; i < Lab4.numOfFloors; i++)
		{
			System.out.print(passengerRequests[i] + " ");
		}
		
		System.out.println("\n starting to print arrivedPassengers");
		int sum = 0;
		for (int i = 0; i < Lab4.numOfElevators; i++){
			System.out.print(arrivedPassengers[i]+" ");
			sum += arrivedPassengers[i];
		}
		System.out.println("Total Arrived Passengers: "+ sum);
		System.out.println();
	}

	
    
}
