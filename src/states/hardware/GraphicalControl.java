package states.hardware;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import Utils.Printer;
import environment.Channels;
import hardware.user.Button;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;


public class GraphicalControl extends State {

	private enum COMMANDS{
		EXIT, NUM, NEWRUN, ENDRUN, CANCEL, SWAP, START, FINISH, TIMEDISP,
		RACETEST, RESET, EVENT, TIMEFREQ, CLR, EXPORT, TRIG, PRINT, DNF, TIME, CONN, TOG;
	}

	private StringBuilder sb;
	private int channelSelected;
	private String[] commands = { "RACETEST", "EVENT IND", "EVENT PARIND", 
			"EVENT GRP", "EVENT PARGRP", "NEWRUN", "ENDRUN", "CANCEL",
			"TIMEDISP", "RESET", "TIMEFREQ", "CLR ", 
			"EXPORT ", "PRINT ", "DNF ", "TIME ", "NUM " };

	private int cCount;
	private boolean functionActive;


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

				if(e.getSource() == ui.getUserInterface().getBtnPower()){

					InterfaceHandler.powerOnOff();
					ui.getUserInterface().enableContent();

				}
			}
		});

		ui.getUserInterface().getBtn0KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtn0KeyPad()){

					sb.append("0");

				}
			}
		});

		ui.getUserInterface().getBtn1KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtn1KeyPad()){

					sb.append("1");

				}
			}

		});

		ui.getUserInterface().getBtn2KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtn2KeyPad()){

					sb.append("2");

				}
			}
		});

		ui.getUserInterface().getBtn3KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtn3KeyPad()){
					sb.append("3");
				}
			}
		});

		ui.getUserInterface().getBtn4KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn4KeyPad()){
					sb.append("4");
				}
			}
		});

		ui.getUserInterface().getBtn5KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn5KeyPad()){
					sb.append("5");
				}
			}
		});

		ui.getUserInterface().getBtn6KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn6KeyPad()){
					sb.append("6");
				}
			}
		});

		ui.getUserInterface().getBtn7KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn7KeyPad()){
					sb.append("7");
				}
			}
		});

		ui.getUserInterface().getBtn8KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn8KeyPad()){
					sb.append("8");
				}
			}
		});


		ui.getUserInterface().getBtn9KeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == ui.getUserInterface().getBtn9KeyPad()){
					sb.append("9");
				}
			}
		});


		ui.getUserInterface().getBtnHashKeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnHashKeyPad()){

					if(commands[cCount].equalsIgnoreCase("racetest")){

						ui.getUserInterface().togChannelsForRace(true);

					}

					if(commands[cCount].equalsIgnoreCase("reset")){

						ui.getUserInterface().togChannelsForRace(false);

					}

					if(commands[cCount].equalsIgnoreCase("time ")){

						char[] tempChars = sb.toString().toCharArray();
						StringBuilder tempBuilder = new StringBuilder();

						for(int i = 0; i < tempChars.length; i++){

							if(tempChars.length == 6){

								if(i % 2 == 0 && i < tempChars.length - 1 && i != 0){

									tempBuilder.append(":");

								}
							}else if(tempChars.length == 7 || tempChars.length == 8){

								if(i % 2 == 0 && i != 0 && i < tempChars.length - 1){

									if(i != 6 && i != 7){

										tempBuilder.append(":");

									}else{
										tempBuilder.append(".");
									}
								}
							}
							
							tempBuilder.append(tempChars[i]);

						}

						sb = tempBuilder;
						
					}
					
					

					InterfaceHandler.inputCommand(Pattern.compile("\\s").matcher(commands[cCount]).find() ? commands[cCount] + sb.toString() : commands[cCount]);

				}

				sb.setLength(0);
				sb = new StringBuilder();

			}
		});

		ui.getUserInterface().getBtnStarKeyPad().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnStarKeyPad()){

					ui.getUserInterface().getTxtAConsole().append("This button was not defined!\n");

				}
			}
		});

		// new
		ui.getUserInterface().getBtnCh1bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh2bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh3bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});


		ui.getUserInterface().getBtnCh4bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh5bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh6bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh7bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});

		ui.getUserInterface().getBtnCh8bk().addMouseListener( new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

				if(!(e.getSource() instanceof JToggleButton)) {
					return;
				}

				JToggleButton tmp = (JToggleButton) e.getSource();
				if(tmp.isSelected()) {
					InterfaceHandler.inputCommand("DISC " + tmp.getName());
					return;
				}

				// need to do this so Menu knows what channel
				ui.getUserInterface().getSensorMenu().setName(tmp.getName());
				ui.getUserInterface().getSensorMenu().show(e.getComponent(), e.getX(), e.getY());

			}
		});
		// new

		ui.getUserInterface().getBtnTogCh1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh1()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 1");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "1" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh2()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 2");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "2" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh3().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh3()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 3");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "3" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh4().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh4()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 4");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "4" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh5().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh5()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 5");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "5" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh6().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh6()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 6");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "6" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh7().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh7()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 7");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "7" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getBtnTogCh8().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTogCh8()){

					InterfaceHandler.inputCommand(COMMANDS.TOG + " 8");

					ui.getUserInterface().getTxtAConsole().append("Channel number: " 
							+ "8" + " is "
							+ (ui.getRaceManager().getCurrentChannel().isEnabled() ? "enabled!\n" : "disabled!\n"));
				}
			}
		});

		ui.getUserInterface().getUsbBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getUsbBtn()){

					InterfaceHandler.inputCommand("EXPORT " + ui.getRaceManager().getRunNbr());

				}
			}
		});

		ui.getUserInterface().getBtnDown().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnDown()){

					ui.getUserInterface().getTxtAConsole().append("This button was not defined!\n");

				}
			}
		});

		ui.getUserInterface().getBtnFunction().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnFunction()){

					functionActive = !functionActive;

				}
			}
		});

		ui.getUserInterface().getBtnNxt().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnNxt()){

					if(functionActive){

						cCount = cCount + 1 == commands.length ? 0 : cCount + 1;
						ui.getUserInterface().getTxtAConsole().setText(commands[cCount] + "\n");

					}else{

						ui.getUserInterface().getTxtAConsole().append("Please press button function to use the arrows!" + "\n");

					}
				}
			}
		});

		ui.getUserInterface().getBtnPrevious().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnPrevious()){

					if(functionActive){

						cCount = cCount - 1 < 0 ? commands.length - 1 : cCount - 1;
						ui.getUserInterface().getTxtAConsole().setText(commands[cCount] + "\n");

					}else{

						ui.getUserInterface().getTxtAConsole().append("Please press button function to use the arrows!" + "\n");

					}
				}
			}
		});

		ui.getUserInterface().getBtnSwap().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnSwap()){

					InterfaceHandler.inputCommand("SWAP");

				}
			}
		});


		ui.getUserInterface().getBtnTrg1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg1()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 1");

				}
			}
		});

		ui.getUserInterface().getBtnTrg2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg2()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 2");

				}
			}
		});

		ui.getUserInterface().getBtnTrg3().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg3()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 3");

				}
			}
		});

		ui.getUserInterface().getBtnTrg4().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg4()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 4");

				}
			}
		});


		ui.getUserInterface().getBtnTr5().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTr5()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 5");

				}
			}
		});

		ui.getUserInterface().getBtnTrg6().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg6()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 6");

				}
			}
		});

		ui.getUserInterface().getBtnTrg7().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg7()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 7");

				}
			}
		});

		ui.getUserInterface().getBtnTrg8().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnTrg8()){

					InterfaceHandler.inputCommand(COMMANDS.TRIG + " 8");

				}
			}
		});

		ui.getUserInterface().getBtnUp().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(e.getSource() == ui.getUserInterface().getBtnUp()){


				}
			}
		});

		ui.getUserInterface().getBtnPrintPwr().addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Printer.clearPrinterTxt();

			}


		});

		ui.getUserInterface().getSensorMenu().add(new JMenuItem(new AbstractAction("GATE") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem tmp = (JMenuItem) e.getSource();

				InterfaceHandler.conn("CONN " + tmp.getText() + " " + tmp.getParent().getName());

			}

		}));

		ui.getUserInterface().getSensorMenu().add(new JMenuItem(new AbstractAction("EYE") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem tmp = (JMenuItem) e.getSource();

				InterfaceHandler.conn("CONN " + tmp.getText() + " " + tmp.getParent().getName());

			}

		}));

		ui.getUserInterface().getSensorMenu().add(new JMenuItem(new AbstractAction("PAD") {

			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem tmp = (JMenuItem) e.getSource();

				InterfaceHandler.conn("CONN " + tmp.getText() + " " + tmp.getParent().getName());

			}

		}));

		ui.getUserInterface().getSensorMenu().addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				JPopupMenu menu = (JPopupMenu) e.getSource();
				JToggleButton tmp = (JToggleButton) menu.getInvoker();
				tmp.doClick();

			}

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

			}
		});


	}

	private String[] getCommands(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}
}