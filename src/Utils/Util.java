package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	//	12:01:02.0	POWER
	//	12:01:04.0	POWER
	//	12:01:06.0	POWER
	//	12:01:20.0	EVENT IND
	//	12:01:25.0	NEWRUN
	//	12:02:00.0	TOG 1
	//	12:02:04.0	TOG 2
	//	12:02:10.0	NUM 234
	//	12:02:25.0	NUM 315
	//	12:02:50.0	TRIG 1
	//	12:02:52.0	TRIG 3
	//	12:03:35.0	TRIG 2
	//	12:03:55.0	TRIG 4
	//	12:04:50.0	PRINT
	//	12:06:00.0	ENDRUN
	//	12:06:10.0	NEWRUN
	//	13:01:00.0	EVENT IND
	//	13:02:10.0	NUM 167
	//	13:02:15.0	NUM 166
	//	13:02:20.0	NUM 200
	//	13:02:25.0	NUM 201
	//	13:04:30.0	TRIG 1
	//	13:05:00.0	TRIG 1
	//	13:05:02.0	TRIG 2
	//	13:05:15.0	TRIG 2
	//	13:05:18.0	TRIG 4
	//	13:05:19.0	PRINT
	//	13:05:25.3	POWER
	//	13:05:26.3	POWER
	//	13:06:00.0	NEWRUN
	//	13:35:52.0	POWER
	//	13:35:55.0	EXIT
	//	POWER	 	 	 	 (if	off)	Turn	system	on,	enter	quiescent	state	
	//	POWER	 	 	 	 (if	on)	Turn	system	off	(but	stay	in	simulator)	
	//	EXIT	 	 	 	 	 Exit	the	simulator	
	//	RESET		 	 	 	 Resets	the	System	to	initial	state	
	//	TIME		<hour>:<min>:<sec>	 Set	the	current	time.		Default	time	is	the	host	
	//	system	time	
	//	TOG	<channel>	 	 	 Toggle	the	state	of	the	channel	<CHANNEL>	
	//	CONN	 <sensor>	<NUM>	 	 Connect	a	type	of	sensor	to	channel	<NUM>	
	//		 	 	 	 	 <sensor>	=	{EYE,	GATE,	PAD}	
	//	DISC	<NUM>	 	 	 	 Disconnect	a	sensor	from	channel	<NUM>	
	//	EVENT	<TYPE>	 	 	 IND	|	PARIND	|	GRP	|	PARGRP	
	//	NEWRUN	 	 	 	 Create	a	new	Run	(must	end	a	run	first)	
	//	ENDRUN	 	 	 	 Done	with	a	Run	
	//	PRINT	<RUN>	 	 	 Print	the	run	on	stdout	
	//	EXPORT	<RUN>	 	 	 Export	run	in	XML	to	file	“RUN<RUN>”	
	//	NUM	<NUMBER>	 	 	 Set	<NUMBER>	as	the	next	competitor	to	start.	
	//	CLR	<NUMBER>	 	 	 Clear	<NUMBER>	the	competitor	from	queue	
	//	SWAP	 	 	 	 	 Exchange	next	two	competitors	to	finish	in	IND	
	//	DNF		 	 	 	 	 The	next	competitor	to	finish	will	not	finish	
	//	TRIG	<NUM>	 	 	 	 Trigger	channel	<NUM>	
	//	START		 	 	 	 Start	trigger	channel	1	(shorthand	for	TRIG	1)	
	//	FINISH	 	 	 	 Finish	trigger	channel	2	(shorthand	for	TRIG	2)

	private static class ProcessFile{

		private static Stack<String> commands = new Stack<>();


		private static void commandProcessing(){

			String[] splitted = fileContent.toString().split("\\s");

			for(int i = splitted.length - 1; i >= 0; i--){

				if(!splitted[i].equals("") && !splitted[i].equals(" ") && (!splitted[i].contains(":"))){

					if(splitted[i].contains("TIME")){


						commands.push(splitted[i + 1].replace("\\n", "").replace("\\r", "").replace("\\b", "").replace("\\d", "").trim());
						commands.push(splitted[i].replace("\\n", "").replace("\\r", "").replace("\\b", "").replace("\\d", "").trim());
						
					}else{
						
						commands.push(splitted[i].replace("\\n", "").replace("\\r", "").replace("\\b", "").replace("\\d", "").trim());
						
					}
				}
			}


			makeCommands();

		}

		private static void makeCommands(){

			Deque<String> temp = new LinkedList<>();

			StringBuilder sb = new StringBuilder();



			while(!commands.isEmpty()){     //C:\Users\TheLaw\git\ChronoTimer1009\CTS1RUN2.txt

				sb.append(commands.pop().trim());

				if(sb.toString().equals("TIME") || sb.toString().equals("TRIG") || sb.toString().equals("EVENT") || sb.toString().equals("NUM") || sb.toString().equals("TOG")){

					sb.append(" " + commands.pop());

				}else if(sb.toString().equals("CONN")){

					sb.append(" " + commands.pop() + " " + commands.pop());

				}

				temp.add(sb.toString());

				sb.setLength(0);
				sb = new StringBuilder();

			}

			while(!temp.isEmpty()){

				commands.push(temp.removeLast());

			}
		}
	}


	private static StringBuilder fileContent;

	/**
	 * Opens and reads a file, and returns the contents as one String.
	 */
	/**
	 * @param filename
	 * @return
	 */
	public static void readFileAsString(String filename) {

		if(fileContent != null){

			fileContent.setLength(0);

		}

		fileContent = new StringBuilder();

		try{

			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;

			while ((line = reader.readLine()) != null) {

				fileContent.append(line + "\n");

			}

			reader.close();

		}catch(IOException ex){

			ex.printStackTrace();

		}

		ProcessFile.commandProcessing();
	}

	public static String getNextCommand(){

		return ProcessFile.commands.isEmpty() ? null : ProcessFile.commands.pop();

	}

	public static boolean areCommandIssued(){

		return !ProcessFile.commands.isEmpty();

	}
	
	
	
	public static void save(){
		
		
		
		
	}
	
	
}