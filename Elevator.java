//Kunyu Zhang 11045203
//Lewis Liu 24427400

import java.util.ArrayList;
import java.util.Collections;

public class Elevator implements Runnable {

	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;
	private int unloadingTime;
	private boolean isUnloading;

	//Constructor of the elevator. Initialize the elevator's information
	public Elevator(int elevatorID, BuildingManager manager){
		this.elevatorID = elevatorID;
		this.manager = manager;
		this.moveQueue = new ArrayList<ElevatorEvent>();
		this.passengerDestinations = new int[5];
		this.isUnloading = false;
	}
	
	//run function for each elevator
	public void run(){
		while(!Thread.interrupted()){
			
			//check whether it is in the process of unloading 
			if(this.isUnloading){
				if(this.unloadingTime + 10 == SimClock.getTime()){
					System.out.printf("Time: %d, Elevator: %d Finished unloading! Currently at the %dth floor. \n", 
							SimClock.getTime(), this.elevatorID, this.currentFloor);
					this.moveQueue.remove(0);
					this.isUnloading = false;
					this.unloadPassenger(this.currentFloor);
					//For each other event, increase the arrival time.
					for(ElevatorEvent i : this.moveQueue){
						i.incrementArrivalTime();
					}
				}
				else{
					continue;
				}
			}
			
			//create event for elevator heading to the floor for picking up.
			if(moveQueue.isEmpty() && numPassengers == 0){
				this.createMovingEvent();
			}
			
			//uploading and loading the passengers & create elevator events
			if(!moveQueue.isEmpty()){
				ElevatorEvent currentMove = moveQueue.get(0);
				if(currentMove.getExpectedArrival() == SimClock.getTime()){
					this.currentFloor = currentMove.getDestination();
					//loading passengers
					if( passengerDestinations[this.currentFloor] == 0){
						int loadingTime = 10;
						int timer = SimClock.getTime();
						System.out.format("Time: %d, Elevator %d reaches %dth Floor for picking up passengers! \n",
								SimClock.getTime(), this.elevatorID, this.currentFloor);
						int up = 1;
						while( SimClock.getTime() < (timer + loadingTime) && !Thread.currentThread().isInterrupted()){
							if(SimClock.getTime() <= timer + loadingTime/2){
								ArrayList<int[]> list = this.manager.findDestination(this.currentFloor, this.elevatorID, up);
								if(list.size() == 0 && this.moveQueue.size() == 1){
									up = 0;
									continue;
								}
								
								for(int[] info : list){
									int expextedArriveTime = timer + loadingTime + Math.abs(this.currentFloor - info[0])*5;
									this.loadPassenger(info[1], info[0]);
									this.startMoving(info[0], expextedArriveTime);
									up = info[2];
								}
							}
						}
						this.manager.setApprocahingId(this.currentFloor, -1);
						this.moveQueue.remove(currentMove);
						
						boolean down = ( moveQueue.size() >0 && moveQueue.get(0).getDestination() < this.currentFloor) ? true : false;
						if(down){
							Collections.sort(moveQueue, (a, b)-> b.getDestination() - a.getDestination());
						}
						else{
							Collections.sort(moveQueue, (a, b)-> a.getDestination() - b.getDestination());
						}
						
						if(moveQueue.size()> 0){
							System.out.format("Time: %d, Elevator %d finished picking up from %dth Floor, going to %dth floor. \n",
									SimClock.getTime(), this.elevatorID, this.currentFloor, moveQueue.get(0).getDestination());
						}
						else{
							System.out.format("Time: %d, Elevator %d finished picking up from %dth Floor \n",
									SimClock.getTime(), this.elevatorID, this.currentFloor);
						}
						
					}
					//uploading passengers
					else{
						System.out.printf("Time: %d, Elevator %d is currently at %dth floor and unloading %d passengers. \n",
								SimClock.getTime(), this.elevatorID, this.currentFloor, this.passengerDestinations[this.currentFloor]);
						this.isUnloading = true;
						this.unloadingTime = SimClock.getTime();
						this.manager.incrementArrivialPassenger(this.currentFloor, this.passengerDestinations[this.currentFloor], this.elevatorID);
						
						//for debug purpose
//						this.moveQueue.remove(currentMove);
						
//						if(moveQueue.isEmpty()){
//							System.out.println(this.elevatorID+" empty! " + this.numPassengers + " Passengers ");
//						}
					}
				}
				
			}
		}
	}
	
	//print the status of elevator
	public void printStatusOfElevator(){
		System.out.printf("Elevator %d: \n", this.elevatorID);
		System.out.printf("Totally load %d passengers, totally unload %d passengers. \n", this.totalLoadedPassengers, this.totalUnloadedPassengers);
		for (int i = 0; i< 5; i++){
			System.out.printf("Total number of passengers are unloaded by this elevator on the floor %d are %d \n",
					i, manager.getArrivalPassenger(i, this.elevatorID));
		}
		System.out.println();
	}
	
	//create a pick up when elevator is stopped
	private void createMovingEvent(){
		int destination = manager.getDestination(this.elevatorID);
		if(destination >= 0){
			System.out.printf("Time: %d, Elevator %d is heading to %dth floor for pick up. Currently at %dth floor.\n", SimClock.getTime(), this.elevatorID, destination, this.currentFloor);
			int processTime = SimClock.getTime()+ 5* Math.abs(this.currentFloor - destination);
			this.moveQueue.add(new ElevatorEvent(destination, processTime));
		}	
	}
	
	//add elevator event into move queue
	private void startMoving(int destination, int time){
		this.moveQueue.add(new ElevatorEvent(destination, time));
	}
	
	//loading the passengers to their destination
	private void loadPassenger(int number, int destination){
		this.numPassengers += number;
		this.totalLoadedPassengers += number;
		this.passengerDestinations[destination] += number;
	}
	
	//Unloading the passengers on their destination
	private void unloadPassenger(int current){
		int unloadForthisFloor = this.passengerDestinations[current];
		this.totalUnloadedPassengers += unloadForthisFloor;
		this.numPassengers -= unloadForthisFloor;
		this.passengerDestinations[this.currentFloor] -= unloadForthisFloor;
	}
}
