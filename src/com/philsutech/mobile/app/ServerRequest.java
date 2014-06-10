package com.philsutech.mobile.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class ServerRequest {
	static String url = "";

	public List<Events> getAllEvent() {
		url = url + "http://192.168.1.22:8080/pstServer/";
		DefaultHttpClient httpclient = new DefaultHttpClient(
				new BasicHttpParams());
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Content-type", "application/json");
		InputStream inputStream = null;
		String result = null;
		List<Events> events = new ArrayList<Events>();

//		if (isReachableByPing(url)) {
//			Log.w("Server Request.", "Connection stablished.");
			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"), 8);
				StringBuilder theStringBuilder = new StringBuilder();
				String line = null;

				while ((line = reader.readLine()) != null) {
					theStringBuilder.append(line + "\n");
				}

				result = theStringBuilder.toString();
				Log.w("Server Request.", result);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (inputStream != null)
						inputStream.close();
				} catch (Exception e) {

				}
			}

			JSONArray jArray;
			try {
				jArray = new JSONArray(result);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject array = jArray.getJSONObject(i);
					events.add(new Events());
					events.get(i).name = array.getString("eventname");
					events.get(i).description = array
							.getString("eventdescription");
					events.get(i).date = array.getString("eventdate");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return events;
//		} else {
//			Log.w("Server Request.", "Failed to stablish connection.");
//			return null;
//		}
	}

	public static boolean isReachableByPing(String host) {
		try {
			Log.w("Server Request.", "Check for server connection.");
			String cmd = "";

			if (System.getProperty("os.name").startsWith("Windows"))
				cmd = "ping -n 1 " + host; // For Windows
			else
				cmd = "ping -c 1 " + host; // For Linux and OSX

			Process myProcess = Runtime.getRuntime().exec(cmd);
			myProcess.waitFor();

			return myProcess.exitValue() == 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
