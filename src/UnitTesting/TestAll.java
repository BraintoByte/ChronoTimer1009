package UnitTesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import states.hardware.TestStates;

@RunWith(Suite.class)
@SuiteClasses({ TestStates.class, TestInternalsUI.class })
public class TestAll {
	
	
	

}
