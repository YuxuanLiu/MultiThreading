//Kunyu Zhang 11045203
//Lewis Liu 24427400

public class lab4 {
	
	//start the program and print the statistics.
	public static void main(String[] args) throws InterruptedException{
		ElevatorSimulation simulation = new ElevatorSimulation();
		simulation.start();
		simulation.printBuildingState();
	}
}
