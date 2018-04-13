package hardware.user;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;

import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Run.Race;
import interfaces.UI;

public class InterfaceHandler {
	
	private UserGraphical userInterface;
	private static UI ui;
	
	public InterfaceHandler(UI ui){
		
		this.ui = ui;
		
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		
		this.userInterface = UserGraphical.getSingleton();
//		this.userInterface.setVisible(false);     //Temporary true
		
	}
	
	
	
	
	public static void setTime(String str){

		try{

			DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
			ui.getSimulator().getClock().setTime(new Time(formatter.parse(str.split("\\s")[1].trim()).getTime()));


			if(!ui.getSimulator().getClock().isClockRunning()){

				ui.getSimulator().getClock().clockStart();


				try {

					Thread.sleep(800);

				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

		}catch(ParseException | InputMismatchException ex){

			System.out.println("You know it's wrong to input that!");

		}
	}

	/**
	 * Toggles the power of the system.
	 */
	public static void powerOnOff(){

		boolean isOn = !ui.getBtnHandler().getPowerState();
		ui.getBtnHandler().setPowerOnOff(isOn);
		ui.getSimulator().getClock().setActive(isOn);

		// happens for powering on only
		if(isOn) {
			ui.getRaceManager().resetRun();
			ui.getSimulator().getClock().setTime(new Date());
		}

		System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

	}
	
	
	
	

	/**
	 * @param str
	 * Connects the channel and sensor specified in str, where str = "CONN <Sensor> <chanID>"
	 */

	public static void conn(String str){


		try{

			int channelSelected = Integer.parseInt(str.split("\\s")[2]);

			if(channelSelected > 0 && channelSelected <= 4){

				ui.getRaceManager().setChannelSelected(channelSelected);

				if(!ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
							str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
				}
			}

		}catch(InputMismatchException | NumberFormatException ex){

			System.out.println("Please don't do that, come on! You know that's wrong!");

		}
	}


	public static void keepPrint(int run){     //Should the runs be replaced?!

		try{

			Iterator<Race> it = ui.getRaceManager().getRecords();

			boolean exists = false;
			Race temp = null;

			if(!it.hasNext()){

				System.out.println("No race!");

			}else{

				System.out.println("Run <" + run + ">");
				boolean isGroup = false;
				int groupID = 1;
				
				while(it.hasNext()){

					temp = it.next();

					if(temp != null && temp.getRun() == run){

						Iterator<Racer> it2 = temp.getRecord();
						exists = true;
						isGroup = temp.isGRP();
						
						while(it2.hasNext()){

							Racer tempRacer = it2.next();
							
							if(tempRacer != null) {
								if(isGroup) {
									
									// TODO
									// still have to allow for user to change finish numbers for GRP
									
									if(!tempRacer.isDNF()) {
										System.out.format("<%05d> ", groupID);
										System.out.println("<" + ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">");
									}
									else
										System.out.format("<%05d> <DNF>\n", groupID);
									groupID++;
								}
								else {
								
									if(!tempRacer.isDNF()){

										System.out.println("<" + tempRacer.getBib() + ">" + " " + "<" + ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">");
									}else{

										System.out.println("<" + tempRacer.getBib() + ">" + " " + "<" + "DNF" + ">");
									}
								}
							}
						}
					}
					
				}
			}

			if(!exists){

				System.out.println("No such run!");

			}
			

		}catch(ConcurrentModificationException e){

			System.out.println("Something went wrong in retriving the race, did you ended run?!");

		}
	}

	/**
	 * @param from
	 * @return the number of enabled channels with ID in the range [from, 4)
	 * Counts the number of enabled channels from parameter 'from'.
	 */
	public static int channelsEnabled(int from){

		int count = 0;

		for(int i = from; i < 4; i += 2){

			ui.getRaceManager().setChannelSelected(i);

			if(ui.getRaceManager().getCurrentChannel().isEnabled()){

				count++;

			}
		}

		return count;

	}
	
	
	
	public UserGraphical getUserInterface() {
		return userInterface;
	}
}