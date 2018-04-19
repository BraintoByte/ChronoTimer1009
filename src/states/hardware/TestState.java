package states.hardware;

import java.util.Scanner;

import interfaces.UI;
import states.State;

public class TestState extends State {

	public TestState(UI ui, Scanner input) {
		super(ui, input);
	}

	/* (non-Javadoc)
	 * @see states.State#update()
	 */
	@Override
	public void update() {
		input();
	}

	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {

		input();
	}

	public void input(){



	}
}
