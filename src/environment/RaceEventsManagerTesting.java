package environment;

import static org.junit.Assert.*;
import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import hardware.user.InterfaceHandler;
import states.hardware.Idle.Run_Types;





@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class RaceEventsManagerTesting {

	private RaceEventsManager manager;
	
	@Before
	public void setUp(){
		manager = new RaceEventsManager();
		manager.setGui(false);
		System.out.println("Racers are starting please wait!");
	}
	
	
	@Test
	public void testREMIND(){
		
		makeREMIND();
		int rand = makeRandomInRange(1, 100);
		makeNRacers(rand + 50);
		assertEquals(rand + 50, manager.getPool().getRacersAmount());
		startRacersInRaces(0, rand);
		assertTrue(manager.getCurrentRun().getRaces()[0].isActive());
		assertEquals(rand, manager.getCurrentRun().getRaces()[0].getActive().size());
		assertEquals(50, manager.getPool().getRacersAmount());
		assertTrue(manager.getPool().clearPool());
		
	}
	
	
	@Test
	public void testREMPARIND(){
		
		makeREMPARIND();
		int rand = makeRandomInRange(40, 100);
		makeNRacers(rand + 50);
		assertEquals(rand + 50, manager.getPool().getRacersAmount());
		int a, b, c, d;
		a = rand / 10;
		b = c = d = a;
		startRacersInRaces(0, a);
		startRacersInRaces(1, b);
		startRacersInRaces(2, c);
		startRacersInRaces(3, d);
		assertTrue(manager.getCurrentRun().getRaces()[0].isActive());
		assertEquals(a, manager.getCurrentRun().getRaces()[0].getActive().size());
		assertEquals(b, manager.getCurrentRun().getRaces()[1].getActive().size());
		assertEquals(c, manager.getCurrentRun().getRaces()[2].getActive().size());
		assertEquals(d, manager.getCurrentRun().getRaces()[3].getActive().size());
		assertEquals(rand - (a*4) + 50, manager.getPool().getRacersAmount());
		assertTrue(manager.getPool().clearPool());
		
	}
	
	
	@Test
	public void testREMGRP(){
		
		makeGRP();
		int rand = makeRandomInRange(1, 100);
		makeNRacers(rand + 50);
		assertEquals(rand + 50, manager.getPool().getRacersAmount());
		manager.getCurrentRun().getRaces()[0].startNRacers(rand, manager.getPool());
		assertTrue(manager.getCurrentRun().getRaces()[0].isActive());
		assertEquals(rand, manager.getCurrentRun().getRaces()[0].getActive().size());
		assertEquals(50, manager.getPool().getRacersAmount());
		assertTrue(manager.getPool().clearPool());
		
	}
	
	@Test
	public void testPARGRP(){
		
		makePARGRP();
		int rand = makeRandomInRange(1, 100);
		makeNRacers(rand + 50);
		assertEquals(rand + 50, manager.getPool().getRacersAmount());
		manager.trig("TRIG 1", false);
		assertTrue(manager.getCurrentRun().getRaces()[0].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[1].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[2].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[3].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[4].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[5].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[6].isActive());
		assertTrue(manager.getCurrentRun().getRaces()[7].isActive());
		manager.trig("TRIG 2", false);
		manager.trig("TRIG 3", false);
		assertTrue(!manager.getCurrentRun().getRaces()[1].isActive());
		manager.trig("TRIG 4", false);
		assertTrue(!manager.getCurrentRun().getRaces()[2].isActive());
		manager.trig("TRIG 5", false);
		assertTrue(!manager.getCurrentRun().getRaces()[3].isActive());
		manager.trig("TRIG 6", false);
		assertTrue(!manager.getCurrentRun().getRaces()[4].isActive());
		manager.trig("TRIG 7", false);
		assertTrue(!manager.getCurrentRun().getRaces()[5].isActive());
		manager.trig("TRIG 8", false);
		manager.trig("TRIG 1", false);
		assertTrue(!manager.getCurrentRun().getRaces()[0].isActive());
		assertTrue(!manager.getCurrentRun().getRaces()[6].isActive());
		assertTrue(manager.getPool().clearPool());
		
	}
	

	private int makeRandomInRange(int min, int max){

		if(min > 0 && max > 0){

			SecureRandom rand = new SecureRandom();
			return rand.nextInt(max-min+1)+min;

		}
		return -1;
	}
	
	
	private void startRacersInRaces(int n, int rand){
		
		manager.getCurrentRun().getRaces()[n].startNRacers(rand, manager.getPool());
		
	}
	
	
	private void makeREMIND(){
		
		manager.getPool().clearPool();
		manager.setType(Run_Types.IND);
		manager.setNewRun();
		manager.getCurrentRun().setNewRace(0);
		
	}
	
	private void makeREMPARIND(){
		
		manager.getPool().clearPool();
		manager.setType(Run_Types.PARIND);
		manager.setNewRun();
		manager.getCurrentRun().setNewRace(0);
		manager.getCurrentRun().setNewRace(2);
		manager.getCurrentRun().setNewRace(3);
		manager.getCurrentRun().setNewRace(5);
		manager.getCurrentRun().setNewRace(7);
		
	}
	
	private void makeGRP(){
		
		manager.getPool().clearPool();
		manager.setType(Run_Types.GRP);
		manager.setNewRun();
		manager.getCurrentRun().setNewRace(0);
		
	}
	
	private void makePARGRP(){
		
		manager.getPool().clearPool();
		manager.setType(Run_Types.PARGRP);
		manager.setChannelSelected(1);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(2);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(3);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(4);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(5);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(6);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(7);
		manager.getCurrentChannel().enable(true);
		manager.setChannelSelected(8);
		manager.getCurrentChannel().enable(true);
		manager.setNewRun();
		
	}
	
	private void makeNRacers(int n){
		IntStream.range(1, n + 1).forEach(x->{
			manager.makeRacers(x);
		});
		System.out.println(n + " Racers were created...");
	}
}