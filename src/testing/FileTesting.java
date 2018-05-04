package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import Utils.Printer;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import main.Simulator;
import states.hardware.IOState;
import states.hardware.TestingIOState;

/** 
 * @author Matt
 * Tests the file inputs that were given to us as well as some input sequences that we created.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileTesting {

	private Simulator sim;
	private UI ui;
	private Clock clock;
	private PrintStream console = System.out;
	
	@Test
	public void Before() {
		System.out.println("...Creating Output files...\nPlease wait\n");
	}
	
	@Test
	public void TestFile1() {
		
		File f = new File(System.getProperty("user.dir") + "/CTS1RUN2OUT.txt");
		
		try {
			
			System.out.println(f.createNewFile()? "File Created" : "File Exists");
			
		} catch (IOException e) {
			System.out.println("Error creating file");
		}
		
		f.setWritable(true);
		
		System.out.println(f.getAbsolutePath());
		
		setSysOut(f);
		sim = new Simulator();
		
		sim.setFilePath(System.getProperty("user.dir") + "/CTS1RUN2.txt");
		
		setSim();
		
		System.setOut(console);

	}
	
	@Test
	public void TestFile2() {
		
		File f = new File(System.getProperty("user.dir") + "/CTS2RUN1OUT.txt");
		
		try {
			
			System.out.println(f.createNewFile()? "File Created" : "File Exists");
			
		} catch (IOException e) {
			System.out.println("Error creating file");
		}
		
		f.setWritable(true);
		
		System.out.println(f.getAbsolutePath());
		
		setSysOut(f);
		sim = new Simulator();
		
		sim.setFilePath(System.getProperty("user.dir") + "/CTS2RUN1.txt");
		
		setSim();
		
		System.setOut(console);
	}
	
	@Test
	public void TestFile3() {
		
		File f = new File(System.getProperty("user.dir") + "/Group TestOUT.txt");
		
		try {
			
			System.out.println(f.createNewFile()? "File Created" : "File Exists");
			
		} catch (IOException e) {
			System.out.println("Error creating file");
		}
		
		f.setWritable(true);
		
		System.out.println(f.getAbsolutePath());
		
		setSysOut(f);
		sim = new Simulator();
		
		sim.setFilePath(System.getProperty("user.dir") + "/Group Test.txt");
		
		setSim();
		
		System.setOut(console);
		
	}
	
	@Test
	public void TestFile4() {
		
		
		File f = new File(System.getProperty("user.dir") + "/Sprint2TestFileOUT.txt");
		
		try {
			
			System.out.println(f.createNewFile()? "File Created" : "File Exists");
			
		} catch (IOException e) {
			System.out.println("Error creating file");
		}
		
		f.setWritable(true);
		
		System.out.println(f.getAbsolutePath());
		
		setSysOut(f);
		sim = new Simulator();
		
		sim.setFilePath(System.getProperty("user.dir") + "/Sprint2TestFile.txt");
		
		setSim();
		
		System.setOut(console);
		
	}
	
	private void setSim() {
		
		ui = new UI(sim);
		Printer.setUI(ui);
		ui.setUserInterface(new InterfaceHandler(this.ui).getUserInterface());
		clock = new Clock();
		sim.setClock(clock);
		ui.setRaceManager(new RaceEventsManager());
		ui.getRaceManager().theseManySensors(100, 100, 100);
		ui.setBtnHandler(new ButtonHandler());
		ui.getBtnHandler().setPowerOnOff(false);
		new TestingIOState(ui, null);
		
	}
	
	private void setSysOut(File f) {
		
		try {
			
			System.setOut(new PrintStream(new FileOutputStream(f.getAbsolutePath())));
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found!!!");
		}
	}
	
}