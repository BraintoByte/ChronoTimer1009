package states.hardware;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import entitiesStatic.Clock;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import main.Simulator;
import states.State;





public class FileTesting {
	
	private UI ui;
	private Clock clock;
	
	@Test
	public void TestFileInput1(){
		
		Simulator sim = new Simulator();
		sim.setFilePath(System.getProperty("user.dir") + "//CTS1RUN2.txt");			//Please change this if you want another file
		this.ui = new UI(sim);
		this.ui.setUserInterface(new InterfaceHandler(this.ui).getUserInterface());
		this.clock = new Clock();
		sim.setClock(clock);
		this.ui.setRaceManager(new RaceEventsManager());
		this.ui.setBtnHandler(new ButtonHandler());
		new IOState(ui, null);
		
	}
}