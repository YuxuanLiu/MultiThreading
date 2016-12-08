//Kunyu Zhang 11045203
//Lewis Liu 24427400

public class PassengerArrival {

	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	//constructor for the passengers and pass the value into the constructor
	public PassengerArrival(int numPassengers, int destinationFloor, int timePeriod , int expectedTimeOfArrival){
		this.numPassengers = numPassengers;
		this.destinationFloor = destinationFloor;
		this.timePeriod = timePeriod;
		this.expectedTimeOfArrival = expectedTimeOfArrival;
	}
	
	//get passenger number
	public int getPassengerNum(){
		return this.numPassengers;
	}
	
	//get destination
	public int getDestinationFloor(){
		return this.destinationFloor;
	}
	
	//get timePeriod seconds
	public int getTimePeriod(){
		return this.timePeriod;
	}
	
	// get expected passenger arrival time
	public int getExpectedTimeArrival(){
		return this.expectedTimeOfArrival;
	}
	
	//set the expected passenger arrival time
	public void incrementExpectedArrivalTime(){
		this.expectedTimeOfArrival += this.timePeriod;
	}
	
}
