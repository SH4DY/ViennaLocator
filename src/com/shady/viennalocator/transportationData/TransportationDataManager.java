package com.shady.viennalocator.transportationData;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.shady.viennalocator.jsonSchemas.offline.ObjectFeature;

public class TransportationDataManager {
	private static TransportationDataManager instance;

	private TaskRetrieveOfflineJSON _offlineDataTask;

	private String _offlineDataJSON;

	public static TransportationDataManager getInstance() {
		if (instance == null) {
			instance = new TransportationDataManager();
		}
		return instance;
	}

	private TransportationDataManager() {
		_offlineDataTask = new TaskRetrieveOfflineJSON();
		_offlineDataTask.execute("");
	}

	public ObjectFeature getOfflineDataJSON() {
		
		if(_offlineDataJSON != ""){
			parseOfflineDataJSON();
		}
		return parseOfflineDataJSON();
	}

	private ObjectFeature parseOfflineDataJSON(){
		Gson gson  = new Gson();
		ObjectFeature obj = gson.fromJson(_offlineDataJSON, ObjectFeature.class);
		return obj;
	}
	private class TaskRetrieveOfflineJSON extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
//			String responseEntity = ClientBuilder.newClient()
//					.target("http://www.wienerlinien.at")
//					.path("/ogd_realtime/monitor?rbl=147&sender=t7YjOZBcqw")
//					.request(MediaType.APPLICATION_JSON)
//					.header("Content-type", "application/json")
//					.get(String.class);
//			return responseEntity;

			String result = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(
					"http://www.wienerlinien.at/ogd_realtime/monitor?rbl=147&sender=t7YjOZBcqw");
			request.addHeader("Content-type", "application/json");
			request.addHeader("Accept", "application/json");
			ResponseHandler<String> handler = new BasicResponseHandler();
			try {
				result = httpclient.execute(request, handler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			httpclient.getConnectionManager().shutdown();
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			_offlineDataJSON = result;
			Log.d("Shady", "Result of the HTTP GET " + result);
		}
	}
}