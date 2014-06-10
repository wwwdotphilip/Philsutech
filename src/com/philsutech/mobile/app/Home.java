package com.philsutech.mobile.app;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Home extends Fragment {
	private DBAdapter database;
	private ServerRequest sr;
	List<Events> eventlist;
	String list;

	public Home() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		database = new DBAdapter(getActivity());
		sr = new ServerRequest();
		eventlist = database.getAllEvents();
		// new getEventData().execute();
		if (eventlist.size() == 0) {
			Log.w("Event list", "Event list is empty.");
			addEvents();
		}
		displayEvents();
	}

	private void displayEvents() {
		list = new String();
		for (Events event : eventlist) {
			list = list + "Event name: " + event.name
					+ ",  Event Description: " + event.description
					+ ",  Event Date: " + event.date + ",  Event Location: "
					+ event.location + ",  Event Status: " + event.status
					+ "\n";
		}

		Toast.makeText(getActivity(), list, Toast.LENGTH_LONG).show();
	}

	private void addEvents() {
		eventlist = new ArrayList<Events>();
		String[] events = { "event1", "event2", "event3", "event4" };
		String[] description = { "description1", "description2",
				"description3", "description4" };
		String[] date = { "date1", "date2", "date3", "date4" };
		String[] location = { "location1", "location2", "location3",
				"location4" };
		String[] status = { "status1", "status2", "status3", "status4" };

		for (int i = 0; i < events.length; i++) {
			Events events2 = new Events();
			events2.name = events[i];
			events2.description = description[i];
			events2.date = date[i];
			events2.location = location[i];
			events2.status = status[i];
			eventlist.add(events2);
		}
		database.insertEvents(eventlist);
	}

	private class getEventData extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			eventlist = sr.getAllEvent();
			return null;
		}

	}

}
