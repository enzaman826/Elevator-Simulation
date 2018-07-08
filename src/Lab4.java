// Nihal Zaman (81899650)
// Heather Fong (73399056)

import java.io.File;
import java.util.Scanner;

public class Lab4
{	
	public static int numOfFloors = 5;
	public static int numOfElevators = 5;
	public static int numOfFloorRequests = 5;

	public static void main(String[] args){
       ElevatorSimulation e = new ElevatorSimulation();
       e.start("example.txt");     
	}

}
