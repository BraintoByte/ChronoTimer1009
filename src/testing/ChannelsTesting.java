package testing;

import hardware.external.*;
import hardware.external.sensor.pad.Pad;
import exceptions.*;
import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import org.junit.Test;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import states.State;

/*
 * @author Nic
 * Tests the Channels class of the environment package.
 */

public class ChannelsTesting {

	private  Clock clock = new Clock();
	private  RaceEventsManager r = new RaceEventsManager();
	
	@Test
	public void testConnectingSensors() {
		
		r.theseManySensors(1, 2, 2);							//1 gate sensor
		
		r.setChannelSelected(1);								//set to channel 1
		
		r.CONN(true, false, false); 							//connect gate to channel 1
		
		r.setChannelSelected(2);								//set to channel 2
		
//		r.CONN(true, false, false);								//should not connect gate sensor; not enough gates.
		assertFalse(r.getCurrentChannel().isPairedToSensor()); 	//channel 2 should not have a connected sensor
		
		r.CONN(false, false, true);								//connect pad to channel 2
		assertTrue(r.getCurrentChannel().isPairedToSensor());	//channel 2 should have a connected (pad) sensor
		r.resetRun();											//reset channel 2
		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 2 should no longer have a sensor
		
		r.setChannelSelected(1);								//resent channel to 1
		r.CONN(false, true, false);								//connect eye to channel 1
		
		assertTrue(r.getCurrentChannel().isPairedToSensor()); 	//channel 1 should have a connected (eye) sensor
		
		r.getCurrentChannel().unPairToSensor();					//disconnect sensor from channel 1
		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 1 should not have sensor
		
		r.setChannelSelected(3);								//set channel to 3
		try {
			r.CONN(false, true, true); 							//try connecting multiple sensors to channel 3. Should prioritize: gate > eye > pad.	
			assertTrue(false);									//should throw exception
		}catch(IllegalAccessError e) {}
		
		Pad padSensor = new Pad(3);								//create new sensor, specifically a pad.
		r.setChannelSelected(4);								//set channel to 4
		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 4 should not have a sensor
		r.getCurrentChannel().pairToSensor(padSensor); 			//connect pad to channel 4
		assertTrue(r.getCurrentChannel().isPairedToSensor());	//channel 4 should have a connected (pad) sensor
		
		
	}
	
	@Test
	public void testSetInvalidChannel() { 
		
		r.setChannelSelected(1);						//set channel to 1
		assertTrue(r.getChannelSelected() == 1);
		
		assertFalse(r.setChannelSelected(9));			//test invalid channels
		assertFalse(r.setChannelSelected(-2));
		
		assertTrue(r.getChannelSelected() == 1);		//selected channel should remain 1
		
	}
	
	
	
}
