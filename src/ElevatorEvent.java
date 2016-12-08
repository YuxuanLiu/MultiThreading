//Kunyu Zhang 11045203
//Lewis Liu 24427400

public class ElevatorEvent {
	int destination;
	int expectedArrival;

	//Constructor of the Elevator Event. Initialize the destination and time.
	public ElevatorEvent(int destination, int expectedArrival ){
		this.destination = destination;
		this.expectedArrival = expectedArrival;
	}
	
	//set the destination
	public void setDestination(int destination){
		this.destination = destination;
	}
	
	//increment the arrival time
	public void incrementArrivalTime(){
		this.expectedArrival+= 10;
	}
	
	//set the expected Arrival time
	public void setExpectedArrival(int expectedArrival){
		this.expectedArrival = expectedArrival;
	}
	
	//get the destination
	public int getDestination (){
		return destination;
	}
	
	//get the expected arrival time
	public int getExpectedArrival(){
		return expectedArrival;
	}
	

}
