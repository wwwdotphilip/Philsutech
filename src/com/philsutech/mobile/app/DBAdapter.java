package com.philsutech.mobile.app;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	DBHelper helper;

	public DBAdapter(Context context) {
		helper = new DBHelper(context);
	}

	public List<Events> getAllEvents() {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<Events> eventList = new ArrayList<Events>();
		String[] columns = { DBHelper.EID, DBHelper.EVENTNAME,
				DBHelper.EVENTDESCRIPTION, DBHelper.EVENTLOCATION,
				DBHelper.EVENTSTATUS, DBHelper.EVENTDATE };
		Cursor cursor = db.query(DBHelper.TABLE_EVENTS, columns, null, null,
				null, null, null);
		int index = 0;

		while (cursor.moveToNext()) {
			eventList.add(new Events());
			int index0 = cursor.getColumnIndex(DBHelper.EID);
			int index1 = cursor.getColumnIndex(DBHelper.EVENTNAME);
			int index2 = cursor.getColumnIndex(DBHelper.EVENTDESCRIPTION);
			int index3 = cursor.getColumnIndex(DBHelper.EVENTLOCATION);
			int index4 = cursor.getColumnIndex(DBHelper.EVENTSTATUS);
			int index5 = cursor.getColumnIndex(DBHelper.EVENTDATE);

			eventList.get(index).id = cursor.getInt(index0);
			eventList.get(index).name = cursor.getString(index1);
			eventList.get(index).description = cursor.getString(index2);
			eventList.get(index).location = cursor.getString(index3);
			eventList.get(index).location = cursor.getString(index4);
			eventList.get(index).date = cursor.getString(index5);

			index++;
		}

		return eventList;
	}

	public List<Comments> getComments(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<Comments> commentList = new ArrayList<Comments>();
		String[] columns = { DBHelper.CONTENT, DBHelper.USERNAME,
				DBHelper.LIKES, DBHelper.DISLIKES };
		Cursor cursor = db.query(DBHelper.TABLE_EVENTS, columns,
				DBHelper.USERID + " = " + id, null, null, null, null);
		int index = 0;

		while (cursor.moveToNext()) {
			commentList.add(new Comments());
			int index0 = cursor.getColumnIndex(DBHelper.CONTENT);
			int index1 = cursor.getColumnIndex(DBHelper.USERNAME);
			int index2 = cursor.getColumnIndex(DBHelper.LIKES);
			int index3 = cursor.getColumnIndex(DBHelper.DISLIKES);

			commentList.get(index).message = cursor.getString(index0);
			commentList.get(index).username = cursor.getString(index1);
			commentList.get(index).likes = cursor.getInt(index2);
			commentList.get(index).dislikes = cursor.getInt(index3);

			index++;
		}

		return commentList;
	}

	public long insertEvents(List<Events> eventlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		long id = 0;
		for (Events event2 : eventlist) {
			contentValues.put(DBHelper.EVENTNAME, event2.name);
			contentValues.put(DBHelper.EVENTDESCRIPTION, event2.description);
			contentValues.put(DBHelper.EVENTLOCATION, event2.location);
			contentValues.put(DBHelper.EVENTSTATUS, event2.status);
			contentValues.put(DBHelper.EVENTDATE, event2.date);
			id = db.insert(DBHelper.TABLE_EVENTS, null, contentValues);
		}
		return id;
	}

	public long insertComments(List<Comments> commentlist) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		long id = 0;
		for (Comments comment2 : commentlist) {
			contentValues.put(DBHelper.USERID, comment2.userid);
			contentValues.put(DBHelper.CONTENT, comment2.message);
			contentValues.put(DBHelper.USERNAME, comment2.username);
			contentValues.put(DBHelper.LIKES, comment2.likes);
			contentValues.put(DBHelper.DISLIKES, comment2.dislikes);
			id = db.insert(DBHelper.TABLE_EVENTS, null, contentValues);
		}
		return id;
	}

	static class DBHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "data";
		private static final String TABLE_EVENTS = "events";
		private static final String TABLE_COMMENTS = "comment";
		private static final int DATABASE_VERSION = 1;
		private static final String EID = "_id";
		private static final String EVENTNAME = "eventname";
		private static final String EVENTDESCRIPTION = "eventdescription";
		private static final String EVENTLOCATION = "eventlocation";
		private static final String EVENTSTATUS = "eventstatus";
		private static final String EVENTDATE = "eventdate";
		private static final String CID = "_id";
		private static final String CONTENT = "content";
		private static final String USERID = "userid";
		private static final String USERNAME = "username";
		private static final String LIKES = "likes";
		private static final String DISLIKES = "dislikes";
		private static final String CREATE_TABLE_EVENTS = "CREATE TABLE "
				+ TABLE_EVENTS + " (" + EID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + EVENTNAME
				+ " VARCHAR(255), " + EVENTDESCRIPTION + " VARCHAR(255), "
				+ EVENTLOCATION + " VARCHAR(255), " + EVENTSTATUS
				+ " VARCHAR(255), " + EVENTDATE + " VARCHAR(255));";
		private static final String CREATE_TABLE_COMMENTS = "CREATE TABLE "
				+ TABLE_COMMENTS + " (" + CID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTENT
				+ " VARCHAR(255), " + USERID + " INTEGER, " + USERNAME
				+ " VARCHAR(255), " + LIKES + " INTEGER, " + DISLIKES
				+ " INTEGER);";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS"
				+ TABLE_EVENTS;
		private Context context;

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE_EVENTS);
				db.execSQL(CREATE_TABLE_COMMENTS);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				onCreate(db);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
