package environment;

import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import org.junit.Test;
import entitiesStatic.Clock;
import states.State;
import states.hardware.Idle.Run_Types;

public class racerTesting {

	@Test
	public void testBib(){
		
		Racer r1 = new Racer(1);			//create racer with bib number 1
		assertTrue(r1.getBib() == 1);		//first racer's bib should be 1
		
		Racer r2 = new Racer(-1);			//second racer has negative bib number; not allowed
		Racer r3 = new Racer(1001);			//third racer has bib number > 999; not allowed
		assertEquals(-1, r2.getBib());		//second racer constructed should not be valid
		assertEquals(1001, r3.getBib());	//third racer constructed should not be valid
		
	}
	
	@Test
	public void testDNF(){
		
		Racer r1 = new Racer(5);			//create new racer with bib 5
		assertFalse(r1.isDNF());			//the new racer should NOT be DNF flagged
		
		r1.setDNF();						//sets the racer to DNF
		assertTrue(r1.isDNF());				//racer should be DNF flagged
		
		r1.setDNF();						//sets the racer to DNF again
		assertTrue(r1.isDNF());				//racer should still be DNF flagged
		
	}
	
	@Test
	public void testWithREM(){
		
		RaceEventsManager r = new RaceEventsManager();		//create new race event manager
		r.makeRacers(1);									//create new racer with bib 1
		assertEquals(1, r.getPool().first().getBib());	//only racer in pool should have Bib 1
		
		assertFalse(r.getPool().first().isDNF());		//only racer in pool should not be DNF
		
		r.getPool().first().setDNF();					//set the only racer in the pool to DNF
		assertTrue(r.getPool().first().isDNF());		//only racer in pool should now be DNF
		
		r.getPool().first().setDNF();					//set the only racer in the pool to DNF again (no change)
		assertTrue(r.getPool().first().isDNF());		//only racer in pool should still be DNF (no change)
		
		r.makeRacers(2); 									//make another racer, bib 2
		
		assertTrue(r.getPool().first().equals(r.getPool().removeRacerBeginning()));	//starting new racer should return the 1st racer in the pool
		
		assertEquals(2, r.getPool().first().getBib());	//after removal of racer 1, racer 2 (bib 2) should be the only racer in pool
		
		//add more for extensivity 
		
	}
	
	@Test
	public void testBibWithREM(){
		
		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
		r.resetPool();
		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
		r.setChannelSelected(1);
		r.CONN(false, true, false); 							//connect gate to channel 1
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//connect gate to channel 2
		
		r.setNewRun();;										//start new run
		
		r.makeRacers(1);										//create racer with bib 1
		assertEquals(1, r.getPool().first().getBib());		//first racer's bib should be 1

		r.getPool().removeRacerBeginning(); //start racer 1, removing from pool
		
		r.makeRacers(-1); 									//make racer with negative bib number, should not be allowed
		assertEquals(0, r.getPool().getRacersAmount());					//racer could not be made, thus not added to pool
		
		r.makeRacers(1001); 									//make racer with bib > 999, should not be allowed
		assertEquals(0, r.getPool().getRacersAmount());					//racer could not be made, thus not added to pool
		
	}
	
	@Test
	public void testDNFWithREM(){
		
		enableChanels();
		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
		r.resetPool();
		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
		r.setChannelSelected(1);
		r.CONN(false, true, false); 							//connect gate to channel 1
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//connect gate to channel 2
		r.setType(Run_Types.IND);
		r.setNewRun();											//start new run	
		
		r.makeRacers(5); 										//create new racer with bib 5
		assertEquals(1, r.getPool().getRacersAmount());
		assertFalse(r.getPool().first().isDNF());			//new racer should not be DNF flagged
		
		r.trig("TRIG 1", false);								//start the racer
		assertEquals(0, r.getPool().getRacersAmount());
		r.trig("TRIG 2", false);								//finish the racer without DNF flag
		assertFalse(r.getPool().first().isDNF());			//racer should not have DNF flag
		assertEquals(1, r.getPool().getRacersAmount());
		r.trig("TRIG 1", false);								//start the racer
		r.trig("TRIG 2", true);									//DNF the Racer
		assertEquals(1, r.getPool().getRacersAmount());
		assertTrue(r.getPool().last().isDNF());			//racer should now be DNF
		
	}
	
	private void enableChanels() {
		for(Channels c: Channels.channels) {
			c.enable(true);
		}
	}

}
