package transportationData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import jsonSchemas.Feature;
import jsonSchemas.ObjectFeature;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class main {
	static String dataWienURI = "http://data.wien.gv.at/daten/wfs?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:OEFFHALTESTOGD&srsName=EPSG:4326&outputFormat=json";

	public static void main(String[] args) {
		String jsonText = "";

		try {
			InputStream is = new URL(dataWienURI).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			jsonText = readAll(br);
			String bla = "asdfasdf";
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		
		int i = 0;
		JsonArray jsonArray = jsonParser.parse(jsonText).getAsJsonObject()
				.getAsJsonArray("features");
		List<Feature> features = new ArrayList<Feature>();
		while(i < jsonArray.size()){
			
			JsonObject feature = jsonArray.get(i).getAsJsonObject();
			features.add(gson.fromJson(feature, Feature.class));
			i++;
		}

		
		ObjectFeature obj = gson.fromJson(jsonText, ObjectFeature.class);
		System.out.println("FINISH");
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
