/*
 * Author: 	Páraic Bradley
 * Date:	06/04/2020 
 */


package eventsWebScraper;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

public class Main {

	//URL for the webpage containing events details and array of relevant names in the HTML
	final static String eventsURL = "https://www.nusu.co.uk/whatson/";
	final static String[] htmlNames = {	"msl_eventlist",
										"msl_event_name",
										"msl_event_description",
										"msl_event_time",
										"msl_event_location",
										"msl_event_image"};
	
	public static void main(String[] args) {
		
		Elements daysWithEvents = new Elements();
		
		try {
			//Get the HTML from the specified web page. This may throw and error if the connection fails
			Document doc = Jsoup.connect(eventsURL).get();
			
			//Get a list of elements representing days with scheduled events from the web page's HTML
			daysWithEvents = doc.getElementsByClass(htmlNames[0]).first().children();
			
			//Array of elements to be stored in a JSON
			JsonArray eventList = new JsonArray();
				
			//For every day with events scheduled...
			for(Element e : daysWithEvents) {
				//For every event scheduled on that day...
				for(int i = 1; i < e.childrenSize(); i++) {
					//Add the event details to the eventList
					eventList.add(getEventDetails(e.child(i)));
				}
			}
			
			//Output the event details to a file
			printJsonToFile(eventList);
		}
		catch(IOException e) {
			//If connection fails, show the error and terminate
			e.printStackTrace();
		}
	}
	
	//Return the details of the event parameter as a JSON Object
	private static JsonObject getEventDetails(Element event) {
		//Declare JSON Objects
		JsonObject eventObject = new JsonObject();
		JsonObject eventDetails = new JsonObject();
		
		//Get HTML elements matching the desired names and saved their contents to the JSON Object
		eventDetails.put("Name", event.getElementsByClass(htmlNames[1]).first().text());
		eventDetails.put("Description", event.getElementsByClass(htmlNames[2]).first().text());
		eventDetails.put("Time", event.getElementsByClass(htmlNames[3]).first().text());
		eventDetails.put("Location", event.getElementsByClass(htmlNames[4]).first().text());
		eventDetails.put("Img Src URL", event.getElementsByClass(htmlNames[5]).first().children().first().attr("abs:src"));
		
		//Put the first JSON Object inside the second and return
		eventObject.put("event", eventDetails);
		
		return eventObject;
	}
	
	//Output the JsonArray to a file
	private static void printJsonToFile(JsonArray eventList) {
		try {
			//Open a file writer to write to the specified file name
			FileWriter file = new FileWriter("events.json");
			
			//Write the file, then close the file writer
			file.write(eventList.toJson());
			file.flush();
			file.close();
		}
		catch(IOException e) {
			//Catch any IO errors, show stack trace and terminate
			e.printStackTrace();
		}
	}
}
