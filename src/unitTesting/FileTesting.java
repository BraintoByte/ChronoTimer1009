package unitTesting;

import java.io.ByteArrayInputStream;
import org.junit.Test;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import interfaces.UI;
import main.Simulator;

public class FileTesting {

	private Simulator sim;
	private UI ui;
	private Clock clock;
	private RaceEventsManager raceManager;
	
	// NOTE to Andy!!!
	// I changed file input from full file path on the local machine to file name 
	// because its way easier to test/do it this way.
	//
	
	@Test
	public void TestFile1() {
				
		System.setIn(new ByteArrayInputStream(("f" + "CTS2RUN1.txt").getBytes()));
		System.setOut(System.out);
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
		sim.start();
		
	}
	
	@Test
	public void TestFile2() {
	
		System.setIn(new ByteArrayInputStream(("f" + "CTS2RUN2.txt").getBytes()));
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
		sim.start();
		
	}
	
	@Test
	public void TestSequence1() {
		
		System.setIn(new ByteArrayInputStream(("f" + "Sprint2TestFile.txt").getBytes()));
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
		sim.start();
		
	}
}