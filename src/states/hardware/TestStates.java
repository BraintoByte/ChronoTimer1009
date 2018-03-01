package states.hardware;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import interfaces.UI;
import main.Simulator;
import states.State;

public class TestStates {


	private Simulator sim;


	@Before
	public void setUp(){



	}


	@Test
	public void TestButtons(){

		System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		sim.start();
		UI ui = new UI(sim);
		Clock clock = new Clock();
//		Idle idleState = new Idle(ui, new Scanner(System.in));
		ButtonHandler btns = new ButtonHandler();
		RaceEventsManager race = new RaceEventsManager();
		race.setChannelSelected(1);
		assertTrue(race.getChannelSelected() == 1);
		assertTrue(race.getAmountConnectedOnSelectedChannel() == 0);
		assertEquals(0, race.allPairedSensors().length);
		race.theseManySensors(1, 1, 1);


		race.CONN(true, false, false);
		assertEquals(1, race.allPairedSensors().length);

		race.CONN(false, true, false);
		assertEquals(2, race.allPairedSensors().length);

		race.CONN(false, false, true);
		assertEquals(3, race.allPairedSensors().length);



		race.propRace();
		race.startNRacers(200);
		
		assertEquals(race.racersActive(), 200);
		
		race.stopLastRace();
		
		assertEquals(race.racersActive(), 0);

	}
}