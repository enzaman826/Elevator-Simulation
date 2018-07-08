// Nihal Zaman (81899650)
// Heather Fong (73399056)

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class ElevatorSimulation
{
	private BuildingManager manager;
	private int totalSimulatedTime;
	private int simulatedSecondRate;
	private ArrayList<ArrayList<PassengerArrival>> floorInfo;
	private Elevator[] elevatorArray;

	// Constructor for ElevatorSimulation
	public ElevatorSimulation()
	{
		manager = new BuildingManager();
		floorInfo = new ArrayList<ArrayList<PassengerArrival>>();
		elevatorArray = new Elevator[Lab4.numOfElevators];
		for (int i = 0; i < Lab4.numOfElevators; i++){
			elevatorArray[i] = new Elevator(i,manager);
			System.out.println("created Elevator ");
		}
	}

	public void print_array(String[] array)
	{
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + "  ");
		System.out.println();
	}

	public void printElevatorInfo()
	{
		for (int i = 0; i < this.floorInfo.size(); i++)
		{
			for (int j = 0; j < this.floorInfo.get(i).size(); j++)
			{
				System.out.println(this.floorInfo.get(i).get(j));
			}
		}
	}

	// Reads the configuration file, and sets the simulated time and the simulated rate.
	// The other information gets placed into an array list of an array list of PassengerArrival objects,
	// which contains the arrival information for all the passengers that spawn thoroughout the simulation.
	public void readFile(String fileName)
	{
		File inputFile = new File(fileName);	

		try
		{
			Scanner scan = new Scanner(inputFile);
			this.totalSimulatedTime = scan.nextInt();
			scan.nextLine();
			this.simulatedSecondRate = scan.nextInt();
			scan.nextLine();
			
			for (int i = 0; i < Lab4.numOfFloorRequests; i++)
			{
				String line = scan.nextLine();
				String[] toks = line.split(";");
				ArrayList<PassengerArrival> pA = new ArrayList<PassengerArrival>();
				for (int j = 0; j < toks.length; j++)
				{
					String[] info = toks[j].split(" ");

					int passengers = Integer.parseInt(info[0]);
					int floor = Integer.parseInt(info[1]);
					int timePeriod = Integer.parseInt(info[2]);
			
					PassengerArrival p = new PassengerArrival(passengers, floor, timePeriod);
					pA.add(p);			
				}
				floorInfo.add(pA);
			}
			scan.close();
		}
		catch (Exception e)
		{
			System.out.println(fileName + " does not exist");
		}	
	}

	// Starts the elevator threads
	public void activateElevators()
	{
		for (int i = 0; i < Lab4.numOfElevators; i++){
			elevatorArray[i].start();
		}
	}
	
	// Sends an interrupt signal to stop the elevator threads
	public void deactivateElevators()
	{
		for (int i = 0; i < Lab4.numOfElevators; i++)
		{
			elevatorArray[i].interrupt();
		}
	}
	 
	public ArrayList<ArrayList<PassengerArrival>> setPAS()
	{
		ArrayList<ArrayList<PassengerArrival>> toReturn =  new ArrayList<ArrayList<PassengerArrival>>();
		for (int i = 0; i < Lab4.numOfFloors; i++)
		{
			toReturn.add(new ArrayList<PassengerArrival>());
		}

		return toReturn;
	}

	// Reads the configuration file and saves the information into floorInfo.
	// The elevator threads then get activated, and they run while the current simulated time is less than
	// the total time specified within the file. The clock sleeps for the simulatedSecondRate, then ticks once.
	public void start(String fileName)
	{
		readFile(fileName);
		

		for (int i = 0; i < floorInfo.size(); i++){
			for (int j = 0; j < floorInfo.get(i).size(); j++){
				System.out.println("On floor "+ i + " "+ floorInfo.get(i).get(j));
			}
			System.out.println("\n");
		}
		activateElevators();
		while (totalSimulatedTime >= SimClock.getSimulatedTime())
		{
			for (int i = 0; i < floorInfo.size(); i++)
			{
				ArrayList<PassengerArrival> passengersOnCurrentFloor = floorInfo.get(i);
				ArrayList<PassengerArrival> toUpdate = new ArrayList<PassengerArrival>();

				for (int j = 0; j < passengersOnCurrentFloor.size(); j++)
				{
					PassengerArrival passengerArrivalGroup = passengersOnCurrentFloor.get(j);

					if (SimClock.getSimulatedTime() == passengerArrivalGroup.getExpectedArrivalTime() )
					{ 
						System.out.println("Passenger Group of size " + passengerArrivalGroup.getNumOfPassengers() + " at floor " + i + " requesting floor "+ passengerArrivalGroup.getDestinationFloor()+ " at time "+ SimClock.getSimulatedTime()+"\n\n");
						int destFloor = passengerArrivalGroup.getDestinationFloor();
						toUpdate.add(passengerArrivalGroup);
						passengerArrivalGroup.setExpectedArrivalTime(passengerArrivalGroup.getExpectedArrivalTime() + passengerArrivalGroup.getTimePeriod());
					}
				}

				manager.updateCurrentAndTotalRequestsForPAG(i, toUpdate);
			}
			try 
			{
				Thread.sleep(simulatedSecondRate);
			} catch (Exception e)
			{
			}
			SimClock.tick();
		
		}
		deactivateElevators();
		System.out.println("Finished Simulation");

		for (int i = 0; i < Lab4.numOfElevators; i++)
			elevatorArray[i].print_stats();

		manager.printFloors();
		int[] result = new int[Lab4.numOfFloors];
		for (int i = 0; i < manager.floors.length; i++){
			for (int j = 0; j < manager.floors.length; j++){
				result[i] += (manager.floors[j].getTotalDestinationRequests()[i] - manager.floors[j].getPassengerRequests()[i]);
			}
		}
		for (int i = 0; i < Lab4.numOfFloors; i++){
			System.out.print(result[i] + " ");
		}
		System.out.println();


	}

}
