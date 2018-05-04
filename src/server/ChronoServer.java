package server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class ChronoServer {

	private static Map<Integer, List<Racer>> records = new HashMap<Integer, List<Racer>>();
	private static Gson g = new Gson();
	HttpServer displayServer = null;
	HttpServer sendServer = null;
	DisplayHandler handler;
	
	public ChronoServer(){
		
		try {
			
			handler = new DisplayHandler();
			System.out.println("Starting server...");
			displayServer = HttpServer.create(new InetSocketAddress(20020), 0);
			sendServer = HttpServer.create(new InetSocketAddress(20021), 0);
			
			displayServer.createContext("/displayresults", handler);

			// create a context to get the request for the POST
			
			sendServer.createContext("/sendresults", handler);
			sendServer.setExecutor(null); // creates a default executor

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		refresh();
		displayServer.start();
		sendServer.start();
	}
	
	public void refresh(){
		
		if(displayServer != null){
			
			displayServer.createContext("/displayresults", handler);
			sendServer.createContext("/sendresults", handler);
			// create contexts to get the request to display the results
			// get it going
			System.out.println("Refreshing...");
			HyperTextGenerator.initRegisteredRecord();

			
		}else{
			System.out.println("Cannot connect!");
		}
	}
	
	
	// a shared area where we get the POST data and then use it in the other handler
	private static String sharedResponse = "";


	static class DisplayHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			String response;
			response = HyperTextGenerator.generateHTML(records);

			// write out the response
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class PostHandler implements HttpHandler {
		@SuppressWarnings("unchecked")
		public void handle(HttpExchange transmission) throws IOException {

			//  shared data that is used with other handlers
			sharedResponse = "";

			// set up a stream to read the body of the request
			InputStream inputStr = transmission.getRequestBody();

			// set up a stream to write out the body of the response
			OutputStream outputStream = transmission.getResponseBody();

			// string to hold the result of reading in the request
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up the sharedResponse
			int nextChar = inputStr.read();
			while (nextChar > -1) {
				sb=sb.append((char)nextChar);
				nextChar=inputStr.read();
			}

			// create our response String to use in other handler
			sharedResponse = sharedResponse + sb.toString();

			//respond to the POST with ROGER
			String postResponse = "ROGER JSON RECEIVED here";

			if(sharedResponse.startsWith("ADD ")) {
				String[] tokens = sharedResponse.split(" ");
				records.put(Integer.parseInt(tokens[1]), (List<Racer>) g.fromJson(tokens[2], new TypeToken<List<Racer>>(){}.getType()));

			}
			else if(sharedResponse.equals("CLEAR")) {
				records.clear();
			}

			System.out.println("response: " + sharedResponse);

			// assume that stuff works all the time
			transmission.sendResponseHeaders(300, postResponse.length());

			// write it and return it
			outputStream.write(postResponse.getBytes());

			outputStream.close();
		}
	}
}