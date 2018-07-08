// Nihal Zaman (81899650)
// Heather Fong (73399056)

import java.util.ArrayList;

public class Elevator extends Thread
{
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int unLoadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;
	public int insertToQueueCount;

	// Constructor for the Elevator class.
	public Elevator(int elevatorID, BuildingManager manager)
	{
		this.elevatorID = elevatorID;
		this.manager = manager;
		moveQueue = new ArrayList<ElevatorEvent>();
		passengerDestinations = new int[Lab4.numOfFloors];
	}

	// If the floor number is not -1, then create an ElevatorEvent to move from the current floor 
	// to the floor that the passengers are, and add that ElevatorEvent to the move queue.
	public void updateMoveQueue()
	{				
		int floorNum = manager.searchForPassengers(elevatorID);

		if (floorNum != -1)
		{
			ElevatorEvent pickUpPassengerEvent = new ElevatorEvent(floorNum, SimClock.getSimulatedTime()+ 5 * Math.abs(this.currentFloor - floorNum));
			insertToQueueCount++;
			moveQueue.add(pickUpPassengerEvent);

			System.out.println("Elevator "+elevatorID +  " on floor " + currentFloor + " has detected passengers and is about to process ElevatorEvent "+ pickUpPassengerEvent);
		}
	}

	// Events only occur if the simulated time is equal to a multiple of one of the passenger request's rates of occurrence.
	// If there are passengers in the elevator (whether this be determined by passengers in the passengerRequests array 
	// or events in the move queue that still need to be completed), drop the passengers off at the current floor and update the statistics.
	// If there are no passengers in the elevator, then pick up passengers at the current floor and update the statistics.
	public int travelToDestination(ElevatorEvent e){

		if (SimClock.getSimulatedTime() == e.getExpectedArrivalTime())
		{
			currentFloor = e.getDestinationFloor();
			System.out.println("Elevator " + elevatorID + " has reached destination floor " + currentFloor + " at time "+SimClock.getSimulatedTime());
			if (numPassengers != 0){
				unLoadedPassengers += passengerDestinations[currentFloor];
				manager.updateArrivedRequests(currentFloor, passengerDestinations[currentFloor], elevatorID);
				numPassengers -= passengerDestinations[currentFloor];
				passengerDestinations[currentFloor] = 0;
				System.out.println("The elevator "+ elevatorID  +" has "+numPassengers + " passengers left and total unloaded  "
						+unLoadedPassengers + " at time "+ SimClock.getSimulatedTime());
			}
			else
			{
				System.out.println("Elevator "+elevatorID + " has reached floor "+ currentFloor+ " and is now picking up passengers");
				int[] passengerRequests = manager.floors[currentFloor].getPassengerRequests();

				for (int i = currentFloor+1; i < passengerRequests.length; i++)
				{
					if (passengerRequests[i] != 0)
					{
						int expectedArrivalTime = SimClock.getSimulatedTime() + 5 * Math.abs(currentFloor - i) + 10*moveQueue.size();
						passengerDestinations[i] = passengerRequests[i];
						numPassengers += passengerRequests[i];
						totalLoadedPassengers += passengerRequests[i];
						passengerRequests[i] = 0;

						ElevatorEvent toAdd = new ElevatorEvent (i, expectedArrivalTime);
						moveQueue.add(toAdd);
						insertToQueueCount++;
					}
				}
				if (moveQueue.size() == 1)
				{
					for (int i = currentFloor - 1; i >= 0; i--)
					{
						if (passengerRequests[i] != 0)
						{
							int expectedArrivalTime = SimClock.getSimulatedTime() + 5 * Math.abs(currentFloor - i) + 10*moveQueue.size();
							passengerDestinations[i] = passengerRequests[i];
							numPassengers += passengerRequests[i];
							totalLoadedPassengers += passengerRequests[i];
							passengerRequests[i] = 0;

							ElevatorEvent toAdd = new ElevatorEvent (i, expectedArrivalTime);
							moveQueue.add(toAdd);
							insertToQueueCount++;

						}

					}
				}
                if (moveQueue.size() > 1){
			   	    ElevatorEvent lastEvent = moveQueue.get(moveQueue.size() - 1);
				    lastEvent.setExpectedArrivalTime(lastEvent.getExpectedArrivalTime() + 10);
                }
				for (int i = 1; i < moveQueue.size(); i++)
				{
					System.out.println("      Elevator " + elevatorID+ " at floor "+ currentFloor + 
						" now adding ElevatorEvent " + moveQueue.get(i));		  
				}
				manager.updateElevatorApproaching(-1, currentFloor); 
			}

			return 1;	

		}
		return 0;
	}

	public int[] getPassengerDestinations()
	{
		return passengerDestinations;
	}

	public void printMoveQueue()
	{
		System.out.println("Elevator "+ elevatorID + "'s move queue");
		for (ElevatorEvent e: moveQueue)
		{
			System.out.println("     "+ e);
		}
	}
	
	public void print_stats()
	{
		System.out.printf("numPassengers: %d totalLoadedPassengers: %d unloadedPassengers: %d insertToQueueCount %d\n",numPassengers,totalLoadedPassengers,unLoadedPassengers,insertToQueueCount);
	}

	// While the Elevator threads are not interrupted, check the size of the move queue.
	// If the queue is empty, then add to it. If it isn't, then use the first item in the queue to deal with
	// if passengers need to be picked up and dropped off.
	public void run()
	{
		while(Thread.currentThread().isInterrupted() == false)
		{
			if (moveQueue.size() == 0)
			{
				updateMoveQueue();	
			}
			else
			{
				ElevatorEvent toProcess = moveQueue.get(0);
				int result = travelToDestination(toProcess);
				if (result == 1)
				{
					System.out.println("Elevator " + elevatorID + " about to remove from its moveQueue "+ toProcess);
					moveQueue.remove(0);
				}
			}
		}
		if(moveQueue.size() > 0)
		travelToDestination(moveQueue.get(0));
	}
}
