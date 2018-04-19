package testing;

import org.junit.Test;

import entitiesStatic.Clock;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import main.Simulator;
import states.State;
import states.hardware.ButtonsActivation;
import states.hardware.IOState;
import states.hardware.Idle;

public class FileTesting {
	
	private UI ui;
	private Clock clock;
	
	@Test
	public void TestFileInput(){
		
		Simulator sim = new Simulator();
		sim.setFilePath(System.getProperty("user.dir") + "//CTS1RUN2.txt");
		this.ui = new UI(sim);
		this.ui.setUserInterface(new InterfaceHandler(this.ui).getUserInterface());
		this.clock = new Clock();
		sim.setClock(clock);
		this.ui.setRaceManager(new RaceEventsManager());
		this.ui.setBtnHandler(new ButtonHandler());
		State io = new IOState(ui, null);
		sim.setFileState(io);
		
	}
}