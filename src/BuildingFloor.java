//Kunyu Zhang 11045203
//Lewis Liu 24427400

public class BuildingFloor {

	private int[] totalDestinationRequests;
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int approachingElevator;

	//Constructor of the building floor, initialize the requests and passengers array.
	public BuildingFloor(){
		this.totalDestinationRequests = new int[5];
		this.arrivedPassengers= new int[5];
		this.passengerRequests = new int[5];
		this.approachingElevator = -1;
	}
	
	//set the total request
	public void incrementTotalRequest(int index, int number){
		this.totalDestinationRequests[index] += number;
	}
	
	//set the arrival passengers
	public void incrementArrivalPassenger(int index, int number){
		this.arrivedPassengers[index] += number;
	}
	
	//set the approaching elevator
	public void setApprocahingElevator(int id){
		this.approachingElevator = id;
	}
	
	//set the passenger requests
	public void setPassenegerRequest(int index , int number){
		this.passengerRequests[index] += number;
		if(number > 0){
			this.incrementTotalRequest(index, number);
		}
	}
	

	//get the passenger requests
	public int getPassengerRequests(int destination){
		return this.passengerRequests[destination];
	}
	
	//get the arrival passengers
	public int getArrivalPassenger(int id){
		return arrivedPassengers[id];
	}
	//get the approaching elevator ID
	public int getApproachingId(){
		return this.approachingElevator;
	}
	
	
	//print the statistics of each floor state
	public void printFloorState(){
		System.out.println("The approaching elevator ID is: " + this.approachingElevator);
		int totalRequest = 0;
		int totalPassenger = 0;
		for(int i = 0; i < 5; i++){
			totalRequest += this.totalDestinationRequests[i];
			totalPassenger += this.arrivedPassengers[i];
		}
		System.out.println("Total number of passengers who sent destination request from this floor are: " + totalRequest);
		System.out.println("Total number of each elevator arrived and unloaded passengers: "+ totalPassenger);
	}
	
}
