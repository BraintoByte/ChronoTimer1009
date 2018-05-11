package states.hardware;

import java.util.Scanner;
import Utils.Util;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;

/**
 * @author Andy & Matt
 *
 */
public class IOState extends State {


	/**
	 * @param ui
	 * @param input
	 * Constructor for IOState that takes UI and Scanner as parameters.
	 */
	public IOState(UI ui, Scanner input) {
		super(ui, input);
		input();
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

	/**
	 * The input method for this State which runs continuesly, waiting for user input.
	 */
	public void input(){

		Util.buildCommands(ui.getSimulator().getFilePath());
		InterfaceHandler.setFileIO(true);
		InterfaceHandler.setGUI(false);
		
		while(Util.areCommandIssued()){

			String str = Util.getNextCommand();
			String time = "TIME " + Util.getTimeCommand();
			InterfaceHandler.inputCommand(time);
			InterfaceHandler.inputCommand(str);

		}

		System.exit(1);

	}
}