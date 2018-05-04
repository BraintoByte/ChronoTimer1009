package testing;

import static org.junit.Assert.*;
import org.junit.Test;
import environment.Channels;
import environment.RaceEventsManager;

/*
 * @author Nic
 * Tests the Channels class of the environment package.
 */

public class ChannelsTesting {

	private RaceEventsManager r = new RaceEventsManager();

	@Test
	public void testSetInvalidChannel() {

		r.setChannelSelected(1); // set channel to 1
		assertTrue(r.getChannelSelected() == 1);

		assertFalse(r.setChannelSelected(9)); // test invalid channels
		assertFalse(r.setChannelSelected(-2));

		assertTrue(r.getChannelSelected() == 1); // selected channel should remain 1

	}

	@Test
	public void testToggle() {

		for (Channels c : Channels.channels) {
			c.enable(false);
		}

		for (int i = 0; i < Channels.channels.length; i++) {
			Channels.channels[i].enable(true);
			assertTrue(Channels.channels[i].isEnabled());
			Channels.channels[i].enable(false);
			assertFalse(Channels.channels[i].isEnabled());
		}

	}

}
