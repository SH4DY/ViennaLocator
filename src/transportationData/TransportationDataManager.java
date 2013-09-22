package transportationData;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import android.os.AsyncTask;
import android.util.Log;

public class TransportationDataManager {
	private static TransportationDataManager instance;
	
	private TaskRetrieveRealtimeJSON realtimeTask;
	
	private String realtimeJSON;
	
	public static TransportationDataManager getInstance() {
		if (instance == null) {
			instance = new TransportationDataManager();
		}
		return instance;
	}

	private TransportationDataManager() {
	}

	public String getRealtimeJSON(){
		realtimeTask.execute("");
		return realtimeJSON;
	}
	private class TaskRetrieveRealtimeJSON extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String responseEntity = ClientBuilder.newClient()
					.target("http://www.wienerlinien.at")
					.path("/ogd_realtime/monitor?rbl=147&sender=t7YjOZBcqw")
					.request(MediaType.APPLICATION_JSON)
					.header("Content-type", "application/json")
					.get(String.class);
			return responseEntity;
		}

		@Override
		protected void onPostExecute(String result) {
			realtimeJSON = result;
			Log.d("Shady", "Result of the HTTP GET " + result);
		}
	}
}