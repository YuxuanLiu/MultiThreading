//Kunyu Zhang 11045203
//Lewis Liu 24427400

import java.util.ArrayList;

public class BuildingManager {

	private BuildingFloor[] floors;

	//Constructor of the building manager. Initialize the floors
	public BuildingManager(){
		floors = new BuildingFloor[5];
		for(int i = 0; i < floors.length; i++){
			floors[i] = new BuildingFloor();
		}
	}
	
	//Find the current location and destination for the passengers.
	public synchronized ArrayList<int[]> findDestination(int currentFloor, int elevatorID, int up){
		ArrayList<int[]> result = new ArrayList<int[]>();
		
		//check up first
		if(up == 1){
			for(int i= currentFloor; i < 5; i++){
				int havePassengers = getFloorPassenger(currentFloor, i);
				if( havePassengers > 0){
					int[] element = new int[3];
					element[0] = i;
					element[1] = havePassengers;
					element[2] = 1;
					//set passenger request to zero
					setRequest(currentFloor, i, -havePassengers);
					result.add(element);
					System.out.printf("Time: %d, Elevator %d currently at %dth floor and destination is %dth floor. Passenger number: %d.\n",
							SimClock.getTime(), elevatorID, currentFloor, i, havePassengers);
				}
			}
		}
		//check down
		if(up == 0){
			for(int i=currentFloor; i >=0; i--){
				int havePassengers = getFloorPassenger(currentFloor, i);
				if(havePassengers > 0){
					int[] element = new int[3];
					element[0] = i;
					element[1] = havePassengers;
					element[2] = 0;
					//set passenger request to zero
					setRequest(currentFloor, i, -havePassengers);
					result.add(element);
					System.out.printf("Time: %d, Elevator %d currently at %dth floor and destination is %dth floor. Passenger number picked: %d.\n", 
							SimClock.getTime(), elevatorID, currentFloor, i, havePassengers);
				}
			}
		}
		return result;
	}
	
	//set the passenger requests
	public synchronized void setRequest(int start, int destination, int number){
		floors[start].setPassenegerRequest(destination, number);
	}
	
	//increment the arrived passenger number from a elevator on a floor 
	public synchronized void incrementArrivialPassenger(int destination, int number, int id){
		floors[destination].incrementArrivalPassenger(id, number);
	}
	
	//get the arrived passenger number on a floor
	public synchronized int getArrivalPassenger(int number, int id){
		return floors[number].getArrivalPassenger(id);
	}
	
	//get the number of the passengers
	public synchronized int getFloorPassenger(int current, int destination){
		return floors[current].getPassengerRequests(destination);
	}
	
	//set the approaching elevator ID
	public synchronized void setApprocahingId(int index, int id){
		floors[index].setApprocahingElevator(id);
	}	
	
	//get the approaching elevator ID
	public synchronized int getApproachingElevatorId(int floor){
		return floors[floor].getApproachingId();
	}
	
	//find the destination for the elevator next move.
	public synchronized int getDestination(int id){
		for(int i=0; i < floors.length; i++){
			int approachingId = this.getApproachingElevatorId(i);
			for(int j=0; j < 5; j++){
				if(approachingId == -1 && getFloorPassenger(i,j) > 0){
					this.setApprocahingId(i, id);
					return i;
				}
			}
		}
		return -1;
	}
	
	//print the statistics about floor states
	public void printFloorStates(){
		for(int i=0; i < floors.length; i++){
			System.out.println("The floor " + i + ": ");
			floors[i].printFloorState();
			for(int j = 0; j < floors.length; j++){
				System.out.printf("Total number of passengers unloading from elevator %d are %d \n",
						j, floors[i].getArrivalPassenger(j) );
			}
			System.out.println();
		}
	}
	
}
