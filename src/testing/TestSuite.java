package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import entitiesDynamic.poolTesting;
import environment.RaceEventsManagerTesting;
import environment.RaceTesting;
import environment.RacerTesting;
import states.hardware.FileTesting;

@RunWith(Suite.class)
@SuiteClasses({RaceTesting.class, RacerTesting.class, poolTesting.class, 
	
ChannelsTesting.class, RaceEventsManagerTesting.class, SystemTest.class})

public class TestSuite {}