package interfaces;

import hardware.user.ButtonHandler;
import main.Simulator;

public class UI {
	
	private Simulator sim;
	private ButtonHandler btnHandler;
	
	
	public UI(Simulator sim){
		
		this.sim = sim;
		
	}
	
	
	public Simulator getSimulator(){
		
		return sim;
		
	}
	
	public void setBtnHandler(ButtonHandler btnHandler) {
		this.btnHandler = btnHandler;
	}
	
	public ButtonHandler getBtnHandler() {
		return btnHandler;
	}
}
