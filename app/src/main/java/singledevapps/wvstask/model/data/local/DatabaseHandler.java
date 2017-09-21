package singledevapps.wvstask.model.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import singledevapps.wvstask.model.News;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "newsdb";
	private static final String TABLE_ALLNEWS = "allnews";

	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_URL = "sourceurl";
	private static final String KEY_IMAGE_URL = "imageurl";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_NEWS_SOURCE = "newssource";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_ALLNEWS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NEWS_SOURCE + " TEXT,"
				+ KEY_TITLE + " TEXT,"
				+ KEY_URL + " TEXT,"
				+ KEY_IMAGE_URL + " TEXT,"
				+ KEY_DESCRIPTION + " TEXT" + ")";
		db.execSQL(CREATE_NEWS_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		deleteAll();
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new News
	public void addNewsintoTable(List<News> newsList) {
		SQLiteDatabase db = this.getWritableDatabase();
		//TODO:REMOVE OLD INSERTED NEWS
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLNEWS);
//		onCreate(db);
		for (int i=0;i<newsList.size();i++) {
			ContentValues values = new ContentValues();
			values.put(KEY_NEWS_SOURCE, newsList.get(i).getNewsSouce());
			values.put(KEY_TITLE, newsList.get(i).getTitle());
			values.put(KEY_URL, newsList.get(i).getSourceUrl());
			values.put(KEY_IMAGE_URL, newsList.get(i).getUrlToImage());
			values.put(KEY_DESCRIPTION,newsList.get(i).getDescription());
			// Inserting Row
			db.insert(TABLE_ALLNEWS, null, values);
		}
		db.close(); // Closing database connection
	}

	
	// Getting All news
	public List<News> getOfflineNews(String newsSource) {
		List<News> newsList = new ArrayList<News>();
		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_ALLNEWS + " WHERE " + KEY_NEWS_SOURCE +"=?'"+newsSource+"'";
//		Log.i("SelectQuery","Query:"+selectQuery);
		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
		String selectQuery = "SELECT * FROM allnews WHERE newssource=?";
		Cursor cursor = db.rawQuery(selectQuery, new String[] { newsSource });
		if (cursor.moveToFirst()) {
			do {
				News News = new News();
//				News.setID(Integer.parseInt(cursor.getString(0)));
				News.setNewsSouce(cursor.getString(1));
				Log.i("titleFromDb","Title:"+cursor.getString(2));
				News.setTitle(cursor.getString(2));
				News.setSourceUrl(cursor.getString(3));
				News.setUrlToImage(cursor.getString(4));
				News.setDescription(cursor.getString(5));
				// Adding News to list
				newsList.add(News);
			} while (cursor.moveToNext());
		}
		// return News list
		Log.i("newsListSize","newsSize:"+newsList.size());
		return newsList;
	}


	// Getting news Count
	public int getnewsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ALLNEWS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int i =cursor.getCount();
		cursor.close();

		// return count
		return i;
	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLNEWS);
		onCreate(db);
	}

//	public List<News> getCategoryNews(String category) {
//		List<News> newsList = new ArrayList<News>();
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.query(true, TABLE_ALLNEWS, new String[] { KEY_TITLE,KEY_URL,KEY_IMAGE_URL,
//						KEY_DESCRIPTION }, KEY_TITLE + " LIKE ?",
//				new String[] {"%"+ category+ "%" }, null, null, null,
//				null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				News News = new News();
////				News.setID(Integer.parseInt(cursor.getString(0)));
//				News.setTitle(cursor.getString(0));
//				News.s(cursor.getString(1));
//				News.setPublisher(cursor.getString(2));
//				News.setCategory(cursor.getString(3));
//				News.setTimestamp(cursor.getString(4));
//				// Adding News to list
//				newsList.add(News);
//			} while (cursor.moveToNext());
//		}
//
//		// return News list
//		return newsList;
//	}

}
