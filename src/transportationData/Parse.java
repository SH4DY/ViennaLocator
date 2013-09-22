package transportationData;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class Parse {
	public static void main(String[] args) {

		 String responseEntity = ClientBuilder.newClient()
		 .target("http://www.wienerlinien.at").path("/ogd_realtime/monitor?rbl=147&sender=t7YjOZBcqw")
		 .request(MediaType.APPLICATION_JSON)
		 .header("Content-type", "application/json")
		 .get(String.class);
//		DateFormat date = new SimpleDateFormat("dd/MM/yyyy'T'hh:mm:ss.SSSzz");
//		String dateString = "11/11/2012T08:00:11.234CEST";
//		Date past;
		 System.out.println(responseEntity);
	}

}