package com.philsutech.mobile.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Dash extends ActionBarActivity {

	private DrawerLayout drawerLayout;
	private ListView listView;
	private ActionBarDrawerToggle drawerListener;
	private MyAdapter myadapter;
	Fragment fragment;
	String[] navigation;
	MenuItem reload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash);
		Log.w("Database", "Check here.");
		navigation = getResources().getStringArray(R.array.navigation);
		drawerLayout = (DrawerLayout) findViewById(R.id.dLayout);
		drawerListener = new ActionBarDrawerToggle(Dash.this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);

			}
		};
		drawerLayout.setDrawerListener(drawerListener);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		listView = (ListView) findViewById(R.id.lvDrawer);
		myadapter = new MyAdapter(Dash.this);
		listView.setAdapter(myadapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedItem(position);
				displayView(position);
			}

			public void selectedItem(int position) {
				listView.setItemChecked(position, true);
			}

			public void setTitle(String title) {
				getSupportActionBar().setTitle(title);
			}

			private void displayView(int position) {
//				Toast.makeText(Dash.this, navigation[position],
//						Toast.LENGTH_SHORT).show();
				switch (position) {
				case 0:
					fragment = new Home();
					break;
				case 1:
					fragment = new AllEvents();
					break;
				case 2:
					fragment = new Login();
					break;
				default:
					break;
				}

				if (fragment != null) {
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.content_frame, fragment).commit();

					listView.setItemChecked(position, true);
					listView.setSelection(position);
					getSupportActionBar().setTitle(navigation[position]);
					drawerLayout.closeDrawer(listView);
				} else {
					Log.e("MainActivity", "Error in creating fragment");
				}
			}
		});
		setDefaultHome();
	}

	private void setDefaultHome() {
		fragment = new Home();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		listView.setItemChecked(0, true);
		listView.setSelection(0);
		getSupportActionBar().setTitle(navigation[0]);
		drawerLayout.closeDrawer(listView);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerListener.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dash, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerListener.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_reload:
			Intent i = new Intent(getApplicationContext(), EventActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			break;

		default:
			break;
		}

		if (drawerListener.onOptionsItemSelected(item)) {
			return true;
		}
		return true;
	}

	class MyAdapter extends BaseAdapter {
		private Context context;
		private String[] navigation;
		int[] images = { R.drawable.ic_action_event,
				R.drawable.ic_action_go_to_today, R.drawable.ic_action_accounts };

		public MyAdapter(Context context) {
			this.context = context;
			navigation = context.getResources().getStringArray(
					R.array.navigation);
		}

		@Override
		public int getCount() {
			return navigation.length;
		}

		@Override
		public Object getItem(int position) {
			return navigation[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.custom_row, parent, false);
			} else {
				row = convertView;
			}
			TextView title = (TextView) row.findViewById(R.id.tvNavName);
			ImageView titleImage = (ImageView) row.findViewById(R.id.imIcon);
			title.setText(navigation[position]);
			titleImage.setImageResource(images[position]);
			return row;
		}

		public List<Comments> addComments() {
			List<Comments> newComments = new ArrayList<Comments>();
			String[] comments = { "comment1", "comment2", "comment3",
					"comment4" };
			String[] username = { "user1", "user2", "user3", "user4" };
			int[] userid = { 1, 2, 3, 4 };
			int[] likes = { 0, 1, 1, 0 };
			int[] dislikes = { 0, 0, 0, 0 };

			for (int i = 0; i < dislikes.length; i++) {
				Comments comments2 = new Comments();
				comments2.message = comments[i];
				comments2.username = username[i];
				comments2.userid = userid[i];
				comments2.likes = likes[i];
				comments2.dislikes = dislikes[i];
				newComments.add(comments2);
			}

			return newComments;
		}
	}
}
