package Utils;

import javax.swing.JTextArea;

import hardware.user.InterfaceHandler;
import interfaces.UI;

public class Printer {

	private static UI userInterface;
	
	
	public static void setUI(UI ui) {
		userInterface = ui;
	}
	
	public static void printToPrinter(String str) {

		if (InterfaceHandler.isGUI()) {
			
			if(userInterface.getUserInterface().getBtnPrintPwr().isSelected())
				userInterface.getUserInterface().getprinterTextArea().append(str);
		}
		else {
			System.out.print(str);
		}

	}

	public static void printToMiddle(int index, String str) {

		if(index < 0 || index > 2)
			return;
		
		if (InterfaceHandler.isGUI()) {
			getArea(index).append(str);
		}else {
			System.out.print(str);
		}

	}

	public static void printToConsole(String str) {

		if (InterfaceHandler.isGUI()) {
			userInterface.getUserInterface().getTxtAConsole().append(str);
		}else {
			System.out.print(str);
		}

	}
	
	public static void clearMiddleTxt(int index) {
		if(index < 0 || index > 2)
			return;
		getArea(index).setText("");
	}
	
	public static void clearPrinterTxt() {

		userInterface.getUserInterface().getprinterTextArea().setText("");
	}
	
	private static JTextArea getArea(int index) {
		
		JTextArea tmp;
		switch(index) {
		case 0:
			tmp = userInterface.getUserInterface().getTxtAMiddle();
			break;
		case 1:
			tmp = userInterface.getUserInterface().getTxtBMiddle();
			break;
		default:
			tmp = userInterface.getUserInterface().getTxtCMiddle();
				
		}
		return tmp;
	}

}
package Utils;

import javax.swing.JTextArea;

import hardware.user.InterfaceHandler;
import interfaces.UI;

public class Printer {

	private static UI userInterface;
	
	
	public static void setUI(UI ui) {
		userInterface = ui;
	}
	
	public static void printToPrinter(String str) {

		if (InterfaceHandler.isGUI()) {
			
			if(userInterface.getUserInterface().getBtnPrintPwr().isSelected())
				userInterface.getUserInterface().getprinterTextArea().append(str);
		}
		else {
			System.out.print(str);
		}

	}

	public static void printToMiddle(int index, String str) {

		if(index < 0 || index > 2)
			return;
		
		if (InterfaceHandler.isGUI()) {
			getArea(index).append(str);
		}else {
			System.out.print(str);
		}

	}

	public static void printToConsole(String str) {

		if (InterfaceHandler.isGUI()) {
			userInterface.getUserInterface().getTxtAConsole().append(str);
		}else {
			System.out.print(str);
		}

	}
	
	public static void clearMiddleTxt(int index) {
		if(index < 0 || index > 2)
			return;
		
		getArea(index).setText("");
	}
	
	public static void clearPrinterTxt() {

		userInterface.getUserInterface().getprinterTextArea().setText("");
	}
	
	private static JTextArea getArea(int index) {
		
		JTextArea tmp;
		switch(index) {
		case 0:
			tmp = userInterface.getUserInterface().getTxtAMiddle();
			break;
		case 1:
			tmp = userInterface.getUserInterface().getTxtBMiddle();
			break;
		default:
			tmp = userInterface.getUserInterface().getTxtCMiddle();
				
		}
		return tmp;
	}

}
package Utils;

import javax.swing.JTextArea;

import hardware.user.InterfaceHandler;
import interfaces.UI;

public class Printer {

	private static UI userInterface;
	
	
	public static void setUI(UI ui) {
		userInterface = ui;
	}
	
	public static void printToPrinter(String str) {

		if (InterfaceHandler.isGUI()) {
			
			if(userInterface.getUserInterface().getBtnPrintPwr().isSelected())
				userInterface.getUserInterface().getprinterTextArea().append(str);
		}
		else {
			System.out.print(str);
		}

	}

	public static void printToMiddle(int index, String str) {

		if(index < 0 || index > 2)
			return;
		
		if (InterfaceHandler.isGUI()) {
			getArea(index).append(str);
		}else {
			System.out.print(str);
		}

	}

	public static void printToConsole(String str) {

		if (InterfaceHandler.isGUI()) {
			userInterface.getUserInterface().getTxtAConsole().append(str);
		}else {
			System.out.print(str);
		}

	}
	
	public static void clearMiddleTxt(int index) {
		if(index < 0 || index > 2)
			return;
		
		getArea(index).setText("");
	}
	
	public static void clearPrinterTxt() {

		userInterface.getUserInterface().getprinterTextArea().setText("");
	}
	
	private static JTextArea getArea(int index) {
		
		JTextArea tmp;
		switch(index) {
		case 0:
			tmp = userInterface.getUserInterface().getTxtAMiddle();
			break;
		case 1:
			tmp = userInterface.getUserInterface().getTxtBMiddle();
			break;
		default:
			tmp = userInterface.getUserInterface().getTxtCMiddle();
				
		}
		return tmp;
	}

}
