//Kunyu Zhang 11045203
//Lewis Liu 24427400

public class SimClock {
	private static int simulatedTime;
	
	//Construct of the Sim Clock. Initialize the simulated time
	public SimClock(){
		simulatedTime = 0;
	}

	//increment the simulation time
	public static void tick(){
		 ++simulatedTime;
	}

	//get the simulated time
	public static int getTime(){
		return simulatedTime;
	}

}
