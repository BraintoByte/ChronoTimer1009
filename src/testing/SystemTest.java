package testing;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

import org.junit.Test;
import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import main.Simulator;
import states.State;

/**
 * @author Andy & Matt
 * Tests some of the system level tests like connecting sensors to channels and ensuring the clock works.
 * TestINDRun() and TestPARINDRun are stress tests that ensure the two event types work properly.
 */
public class SystemTest {

	private  RaceEventsManager raceManager = new RaceEventsManager();

	@Test
	public void testClockTime(){
		
		Clock clock = new Clock();
		System.out.println("NOTE: SOMETIMES THE CLOCK MIGHT BE A FEW SS OFF ALTHOUGH IT WILL SEEM LIKE"
				+ "LIKE IT'S 1 SECOND OFF, THIS HAPPENS BECAUSE IF YOU ARE AT 12:00:00.59 and the test hits at 12:00:01.00"
				+ "\n this can also be because the thread sleeps a few nanoseconds before!"
				+ "\nIT WILL COME OUT WRONG, PLEASE LOOK AT THE TEST CASE!");

		clock.setTime(new Time((new Date().getTime())));

		clock.clockStart();
		
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e2) {}


		assertEquals(new Time((new Date().getTime())) + "", ClockInterface.getCurrentTimeFormatted() + "");

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
		try {

			clock.setTime(new Time(formatter.parse("12:00:11.00").getTime()));


			System.out.println("TESTING 10 SECONDS ELAPSE, PLEASE WAIT");
			
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {}
			
			assertTrue((new Time(formatter.parse("12:00:19.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + "")
					|| (new Time(formatter.parse("12:00:20.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + "")
					|| (new Time(formatter.parse("12:00:21.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + ""));

		} catch (ParseException e1) {}
	}



	// more extensive tests for the clock & clock interface will come later
	@Test
	public void TestClock() {

		Clock clock = new Clock();
		State.setState(null);														// set initial state

		assertFalse(clock.isClockRunning());	

		clock.clockStart();															// start clock
		clock.setActive(true);														// activate clock

		assertTrue(clock.isActive());												// check if active

	}
	
	
	
	private void connectSensors(){

		for(int i = 1; i < 5; i++){

			raceManager.setChannelSelected(i);

			if(!raceManager.getCurrentChannel().isPairedToSensor()){

				conn("CONN EYE " + i);

			}
		}
	}

	private void conn(String str){


		try{

			int channelSelected = Integer.parseInt(str.split("\\s")[2]);

			if(channelSelected > 0 && channelSelected <= 4){

				raceManager.setChannelSelected(channelSelected);

				if(!raceManager.getCurrentChannel().isPairedToSensor()){

					raceManager.CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
							str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
				}
			}

		}catch(InputMismatchException ex){

			ex.printStackTrace();

		}
	}

}