// Nihal Zaman (81899650)
// Heather Fong (73399056)

import java.util.ArrayList;

public class BuildingManager
{
	public BuildingFloor floors[];
	
	// Initiates the building manager, which creates an array of all the floors and their
	// information in the building.
	public BuildingManager(){
		floors = new BuildingFloor[Lab4.numOfFloors];
		for (int i = 0; i < Lab4.numOfFloors; i++)
		{
			 floors[i] = new BuildingFloor();
		}
	}

	// Updates the current and total passenger requests for a building floor.
	public void updateCurrentAndTotalRequests(int floorNum, int destFloor, int numPassengers)
	{
		//floors[floorNum].getPassengerRequests()[destFloor] = numPassengers;
		floors[floorNum].getPassengerRequests()[destFloor] += numPassengers;
		floors[floorNum].getTotalDestinationRequests()[destFloor] += numPassengers;

	}
	
	// Updates the current and total passenger requests for a passenger arrival group.
	public synchronized void updateCurrentAndTotalRequestsForPAG(int floor,ArrayList<PassengerArrival> p){
		for (int i = 0; i < p.size(); i++){
	       updateCurrentAndTotalRequests(floor,p.get(i).getDestinationFloor(),p.get(i).getNumOfPassengers());
		}
	}

	// Updates the approaching elevator for a building floor. 
	public synchronized void updateElevatorApproaching(int elevatorId, int floorNum)
	{
		floors[floorNum].setApproachingElevator(elevatorId);
	}

	// Prints information that indicates which floor's information is being displayed.
	public void printFloors()
	{
		for (int i = 0; i < floors.length; i++)
		{
			System.out.println("starting to print floor number " + (i));
			floors[i].printFloorInfo();
			System.out.println("finishing to print floor number " + (i));
			System.out.println("\n");

		}
	}

	// Returns the number of passengers on a certain floor.
	public int getNumOfPassengersOnFloor(int floor)
	{
		int sum = 0;
		int[] passengerRequests = floors[floor].getPassengerRequests();
		for (int i = 0; i < passengerRequests.length; i++){
			sum += passengerRequests[i];
		}
		return sum;
	}

	// Sets the current passenger requests to 0 for a floor.
	public void clearFloor(int floor)
	{
		int[] passengerRequests = floors[floor].getPassengerRequests();
		for (int i = 0; i < passengerRequests.length; i++)
			passengerRequests[i] = 0;
	}

	// Updates the array of arrived passengers at the index which equals the elevatorID
	public synchronized void updateArrivedRequests(int floorNum,int numOfPassengers,int elevatorID)
	{
		floors[floorNum].getArrivedPassengers()[elevatorID] += numOfPassengers;
	}

	public synchronized boolean hasPassengers(int floor)
	{
		return floors[floor].hasPassengers();
	}
	
	public synchronized int getApproachingElevator(int floor)
	{
		return floors[floor].getApproachingElevator();
	}

	// If there are passengers waiting for an elevator and no elevator is currently moving to 
	// pick them up, an elevator get assigned to pick them up. Returns the floor that the passenger
	// is on. 
	public synchronized int searchForPassengers(int elevatorID)
	{
		for (int i = 0; i < floors.length; i++)
		{
			for (int j = 0; j < floors[i].getPassengerRequests().length; j++)
			{
				if (floors[i].getPassengerRequests()[j] > 0 && floors[i].getApproachingElevator() == -1)
				{
					floors[i].setApproachingElevator(elevatorID);
					return i;
				}
			}
		}
		return -1;
	}


}