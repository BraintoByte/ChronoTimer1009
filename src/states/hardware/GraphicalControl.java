package states.hardware;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;
import java.util.Scanner;

import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;

public class GraphicalControl extends State {
	
	
	private StringBuilder sb;

	public GraphicalControl(UI ui, Scanner input) {
		super(ui, input);
		setActions();
		sb = new StringBuilder();
	}



	@Override
	public void update() {}

	@Override
	public void display() {}


	private void setActions(){

		ui.getUserInterface().getBtnPower().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("In power");
				InterfaceHandler.powerOnOff();
				
			}
		});

		ui.getUserInterface().getBtn0KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("0");
				
			}
		});

		ui.getUserInterface().getBtn1KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("1");
				
			}
		});

		ui.getUserInterface().getBtn2KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("2");
				
			}
		});

		ui.getUserInterface().getBtn3KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("3");
				
			}
		});

		ui.getUserInterface().getBtn4KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("4");
				
			}
		});

		ui.getUserInterface().getBtn5KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("5");
				
			}
		});

		ui.getUserInterface().getBtn6KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("6");
				
			}
		});

		ui.getUserInterface().getBtn7KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("7");
				
			}
		});

		ui.getUserInterface().getBtn8KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("8");
				
			}
		});


		ui.getUserInterface().getBtn9KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				sb.append("9");
				
			}
		});


		ui.getUserInterface().getBtnHashKeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{

					ui.getRaceManager().makeRacers(Integer.parseInt(sb.toString().split("\\s")[1].trim()));

				}catch(InputMismatchException | NumberFormatException ex){

					System.out.println("Wrong input!");

				}
				
				sb.setLength(0);
				sb = new StringBuilder();
				
			}
		});


		ui.getUserInterface().getBtnStarKeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



			}
		});
	}
}