package states.hardware;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;

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
	private UI ui;
	private Clock clock;
	private RaceEventsManager raceManager;
	
	public void setUp(){

		System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
	}
}