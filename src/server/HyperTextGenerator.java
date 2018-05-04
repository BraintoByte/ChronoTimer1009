package server;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HyperTextGenerator {

	// @author Nic
	// generates the String of HTML for a single Run. Must send in that run's record
	// of racers
	private static Map<Integer, String> registeredRacers = new HashMap<Integer, String>();
	private static String existing = "";
	private static String header = "<style> table {font-family: \"Times New Roman\", Times, serif;\n"
			+ "    border-collapse: collapse;\n   width: 30%;}" + "td {font-size: 30px;\n"
			+ "    text-align: left;\n" + " padding: 8px;}" + "tr {border: 1px solid #00004d;}"
			+ "tr:nth-child(even) {background-color: #d9d9d9;}\n" + "th {font-size: 25px;\n text-align: left;\n"
			+ "    background-color: #00004d;\n" + " color: white;\n" + " padding-left: 6px;\n"
			+ "    padding-right: 6px;\n" + " padding-top: 12px;\n" + " padding-bottom: 12px;} </style>"
			+ "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<title>Run Results</title>\n" + "</head>\n" + "<body>\n"
			+ "<div>\n";

	private static String closer = "</div>\n" + "</body>\n" + "</html>";

	public static String generateHTML(Map<Integer, List<Racer>> record) {

		Iterator<Entry<Integer, List<Racer>>> it = record.entrySet().iterator();

		existing = "";
		if (it.hasNext()) {

			Entry<Integer, List<Racer>> tmp;
			Iterator<Racer> tmpIT;
			while (it.hasNext()) {

				tmp = it.next();
				tmpIT = tmp.getValue().iterator();

				existing += "<table>\n" + "<h style=\"font-size:30px;\">Run " + tmp.getKey() + "</h>" + "<tr>\n"
						+ "<th>Place</th>\n" + "<th>Bib</th>\n" + "<th>Name</th>\n" + "<th>Time</th>\n" + "</tr>\n";

				int i = 1;
				while (tmpIT.hasNext()) {
					Racer r = tmpIT.next();
					existing += "<tr>" + "<td>" + i + "</td>" + "<td>" + r.getBib() + "</td>";

					if (registeredRacers.containsKey(r.getBib())) {
						existing += "<td>" + registeredRacers.get(r.getBib()) + "</td>";
					} else {
						existing += "<td></td>";
					}

					if (r.isDNF()) {
						existing += "<td>DNF</td>";
					} else {
						existing += "<td>" + r.getTotalTime() + "</td>";
					}

					existing += "</tr>";
					i++;
				}
			}
			existing += "</table>\n" + "<p></p>\n<p></p><p></p>\n<p></p><p></p>\n<p></p>";

		} else {
			
			existing +=	"<table>\n" + "<h style=\"font-size:30px;\"> </h><tr>\n"
					+ "<th>Place</th>\n" + "<th>Bib</th>\n" + "<th>Name</th>\n" + "<th>Time</th>\n" + "</tr>\n "
							+ "<tr>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td></td>" + "</tr>";
		}
		String result = header + existing + closer;

		return result;
	}
	
	// encoding in racers.txt as follows
	// "bib name\n"
	public static void initRegisteredRecord() {
		
		//System.getProperty("usr.dir")
		try(Scanner fileScan = new Scanner(new File("racers.txt"))){
			
			String line;
			while(fileScan.hasNextLine()) {
				line = fileScan.nextLine();
				registeredRacers.put(Integer.parseInt(line.split(" ")[0]), line.split(" ")[1]);
			}
		} catch (NumberFormatException | IndexOutOfBoundsException | FileNotFoundException e) {
			System.out.println("racers.txt in incorrect format!");
		}
		
	}

}
