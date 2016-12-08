//Kunyu Zhang 11045203
//Lewis Liu 24427400

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorSimulation {

	private int totalSimulationTime;
	private int simulatedRate;
	private ArrayList<ArrayList<PassengerArrival>> trackArrival;
	private BuildingManager manager;
	private Thread[] threads;
	private Elevator[] elevators;
	
	
	//Constructor for the elevator simulation, initialize the threads and runnable elevator object.
	public ElevatorSimulation(){
		this.totalSimulationTime = 0;
		this.simulatedRate = 0;
		this.trackArrival = new ArrayList<ArrayList<PassengerArrival >>();
		this.manager = new BuildingManager();
		this.threads = new Thread[5];
		this.elevators = new Elevator[5]; 
		new SimClock();
		for(int i = 0; i < this.elevators.length; i++){
			this.elevators[i] = new Elevator(i, this.manager);
		}
		for(int i=0; i < this.threads.length; i++){
			this.threads[i] = new Thread(this.elevators[i]);
		}
	}


	//run the main thread
	public void start() throws InterruptedException{
		this.readConfig("ElevatorConfig.txt");
		createThreads();
		while(SimClock.getTime() < this.totalSimulationTime){
			Thread.sleep(this.simulatedRate);
			SimClock.tick();
			//for debug purpose
//			System.out.println(SimClock.getTime());
			this.updateStatus();
		}
		//System.out.println("out");
		this.interruptThreads();
		

	}
	
	//print the statistics of Building and Elevator
	public void printBuildingState(){
		this.manager.printFloorStates();
		for(int i=0; i< elevators.length ; i++){
			elevators[i].printStatusOfElevator();
		}
	}
	
	//for each simulation second, update the states of the building manager and 
	private void updateStatus(){
		for(int i = 0; i < trackArrival.size(); i++){
			for(int j = 0; j < trackArrival.get(i).size(); j++){
				PassengerArrival getPassenger = trackArrival.get(i).get(j);
				if(getPassenger.getExpectedTimeArrival() == SimClock.getTime()){
					int passengerNum = getPassenger.getPassengerNum();
					int destination = getPassenger.getDestinationFloor();
					//output
					System.out.format("Time: %d, %d Passenger arrive. Currently at: %dth floor. Destination is: %dth floor. \n",
							SimClock.getTime(), passengerNum, i ,destination);
					//increment the time for next expected arrival 
					getPassenger.incrementExpectedArrivalTime();
					//change the status of building
					this.manager.setRequest(i, destination, passengerNum);
				}
			}
		}
	}
	
	//read file and check the file exists or not
	private void readConfig(String fileName){
		try {
			Scanner scanner = new Scanner(new File(fileName));
			
			this.totalSimulationTime = scanner.nextInt();
			this.simulatedRate = scanner.nextInt();
			
			while(scanner.hasNextLine()){
				String floor = scanner.nextLine();
				if(!floor.equals("")){
					String[] parts = floor.split(";");
					trackArrival.add(getEachFloorInfo(parts));		
				}
			}
			scanner.close();  
        }
        catch(FileNotFoundException ex){
            System.out.println( "Unable to open file '" + fileName + "'");                
        }
	}
	
	//Assign and get the information from files to PassengerArrival
	private ArrayList<PassengerArrival> getEachFloorInfo(String[] input){
		ArrayList<PassengerArrival> result = new ArrayList<PassengerArrival>();
		for(int i = 0; i < input.length; i++){
			String[] parts = input[i].split(" ");
			int numPassengers = Integer.parseInt(parts[0]);
			int destinationFloor = Integer.parseInt(parts[1]);
			int timePeriod = Integer.parseInt(parts[2]);
			PassengerArrival passenger = new PassengerArrival(numPassengers, destinationFloor, timePeriod, timePeriod);
			result.add(passenger);
		}
		return result;
	}

	//Create the threads
	private void createThreads(){
		for(int i=0; i < this.threads.length; i++){
			this.threads[i].start();
		}
	}
	
	//Interrupt the threads
	private void interruptThreads(){
		for(int i=0; i < this.threads.length; i++){
			this.threads[i].interrupt();
		}
	}

}
