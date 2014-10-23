package mariocraft.ui;

/**
 * The core class, containing the game loop.
 * 
 * @author Erik Odenman
 * @version 2011-05-03
 *
 */
public class Core {
	
	ScreenController sc;
	
	public static double averageFPS;
	public static double averageUPS;
	private static double[] storeFPS;
	private static double[] storeUPS;
	private static final int NUM_FPS = 10;
	private static final int NUM_UPS = 10;
	private static long gameStart;
	private static int frameCount;
	private static int frameSkips;
	private static int skips;
	
	private static final int REQUESTED_FPS = 60;	// The FPS that we want
	private static final int FRAME_PERIOD = 1000000000/REQUESTED_FPS; // in nanoseconds
	private static final int MAX_NUMBER_OF_SKIPS = 5;
	
	/**
	 * Create an instance of class Core.
	 */
	public Core() {
		setup();
	}
	
	/* 
	 * Sets up the ScreenController without making it full screen yet.
	*/
	private void setup() {
		sc = new ScreenController();
		storeFPS = new double[NUM_FPS];
		storeUPS = new double[NUM_UPS];
	}
	
	/**
	 * Start the game. Makes the ScreenController set full screen and
	 * initiates the game loop.
	 */
	public void start() {
		sc.start();
		gameLoop();
	}
	
	/* 
	 * Run the game loop. The loop sleeps for a while between each
	 * update according to SLEEP_TIME.
	 */
	private void gameLoop() {
		
		long beforeTime, afterTime, timeSpent, sleepTime, overTime;
		overTime = 0;
		long totalOverTime = 0;
		
		gameStart = System.nanoTime();
		beforeTime = gameStart;
		
		while (sc.isRunning()) {
			sc.update();
			sc.paint();
			afterTime = System.nanoTime();
			timeSpent = afterTime - beforeTime;
			sleepTime = FRAME_PERIOD - timeSpent - overTime;
			if (sleepTime < 0) { // Updating took longer than the frame period
				totalOverTime -= sleepTime;
				overTime = 0;
			} else {
				try {
					Thread.sleep(sleepTime/1000000); // ns --> ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				overTime = System.nanoTime() - afterTime - sleepTime;
			}
			
			beforeTime = System.nanoTime();
			
			// Update the game without rendering if speed-up is required to fulfill fps-request
			skips = 0;
			while (totalOverTime > FRAME_PERIOD && skips < MAX_NUMBER_OF_SKIPS) {
				sc.update();
				totalOverTime -= FRAME_PERIOD;
				skips++;
			}
			
			updateStats();
		}
		System.out.println("Average FPS: "+averageFPS);
	}
	
	/*
	 * Update the UPS and FPS stats
	 */
	private void updateStats() {
		long elapsedTime = System.nanoTime() - gameStart;
		frameSkips += skips;
		frameCount++;
		
		double actualFPS = (double)frameCount/elapsedTime*1000000000;
		double actualUPS = (double)(frameCount + frameSkips)/elapsedTime*1000000000;
		
		storeFPS[ frameCount % NUM_FPS] = actualFPS;
		storeUPS[ frameCount % NUM_UPS] = actualUPS;
		
		// Count total
		
		double totalFPS = 0;
		for (int i = 0; i < NUM_FPS; i++) {
			totalFPS += storeFPS[i];
		}

		double totalUPS = 0;
		for (int i = 0; i < NUM_UPS; i++) {
			totalUPS += storeUPS[i];
		}
		
		// Update average
		
		if (frameCount < NUM_FPS) {
			averageFPS = totalFPS / frameCount;
		} else {
			averageFPS = totalFPS / NUM_FPS;
		}
		
		if (frameCount < NUM_UPS) {
			averageUPS = totalUPS / frameCount;
		} else {
			averageUPS = totalUPS / NUM_UPS;
		}
	}
}
