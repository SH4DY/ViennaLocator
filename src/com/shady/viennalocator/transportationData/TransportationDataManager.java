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

	private TaskRetrieveRealtimeJSON _realtimeDataTask;

	private String _offlineDataJSON;

	private TaskRetrieveOfflineJSON _offlineDataTask;

	public String _realtimeDataJSON;

	public static TransportationDataManager getInstance() {
		if (instance == null) {
			instance = new TransportationDataManager();
		}
		return instance;
	}

	private TransportationDataManager() {
		_realtimeDataTask = new TaskRetrieveRealtimeJSON();
		_realtimeDataTask.execute("");

		_offlineDataTask = new TaskRetrieveOfflineJSON();
		_offlineDataTask.execute("");
	}

	//TODO Die beiden Methoden machen wenig Sinn....
	public ObjectFeature getOfflineDataJSON() {
		return parseOfflineDataJSON();
	}

	private ObjectFeature parseOfflineDataJSON() {
		Gson gson = new Gson();
		ObjectFeature obj = gson
				.fromJson(_offlineDataJSON, ObjectFeature.class);

		return obj;
	}

	private class TaskRetrieveOfflineJSON extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String result = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(
					"http://data.wien.gv.at/daten/wfs?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:OEFFHALTESTOGD&srsName=EPSG:4326&outputFormat=json");
			// request.addHeader("Content-type", "application/json");
			// request.addHeader("Accept", "application/json");
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
			Log.d("Shady", "Result of the HTTP GET OFFLINE DATA" + result);
		}
	}

	// TODO Eingabeparameter in INT???
	private class TaskRetrieveRealtimeJSON extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			// String responseEntity = ClientBuilder.newClient()
			// .target("http://www.wienerlinien.at")
			// .path("/ogd_realtime/monitor?rbl=147&sender=t7YjOZBcqw")
			// .request(MediaType.APPLICATION_JSON)
			// .header("Content-type", "application/json")
			// .get(String.class);
			// return responseEntity;

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
			_realtimeDataJSON = result;
			Log.d("Shady", "Result of HTTP GET REALTIME DATA " + result);
		}
	}

}