package com.durgasamples412.watchit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.widget.Toast;

import com.durgasamples412.watchit.POJO.Result;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 08-02-2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FavouriteMovies.db";
    private static final String TABLE_NAME = "favouriteMovies";
    private static final String COLOUMN_ID ="id";
    private static final String MOVIE_ID = "movie_id";
    private static final String MOVIE_NAME = "movie_name";
    private static final String POSTER_PATH = "poster_path";
    private static final int DATABASE_VERSION = 5;
   private Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NAME+"("+COLOUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+MOVIE_ID+" INTEGER,"+MOVIE_NAME+" TEXT,"+POSTER_PATH+" TEXT"
                +")";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertMovie(int id,String title,String poster_path)
    {
        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID,id);
        values.put(MOVIE_NAME,title);
        values.put(POSTER_PATH,poster_path);

        if(!isMovieFavourite(id)){
            long result = database.insert(TABLE_NAME,null,values);
            if(result!=-1){
                Toast.makeText(context, "added to favourites", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Error inserting", Toast.LENGTH_SHORT).show();
            }
        }
        database.close();

    }

    public List<Result> getmoviedetailsfromdb(){
        List<Result>movieidslist  = new ArrayList <>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT*FROM "+TABLE_NAME,null);
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(MOVIE_ID));
                String title = cursor.getString(cursor.getColumnIndex(MOVIE_NAME));
                String poster_path = cursor.getString(cursor.getColumnIndex(POSTER_PATH));
                Result result = new Result();
                result.setId(id);
                result.setTitle(title);
                result.setPosterPath(poster_path);
                movieidslist.add(result);
                cursor.moveToNext();
            }
            cursor.close();

        }
        return movieidslist;
    }

    public void removeFromFav(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, MOVIE_ID+"=?",new String[]{String.valueOf(id)});
        database.close();
    }
    public boolean isMovieFavourite(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,null,MOVIE_ID+ "="+id,null,null,null,null);
       if (cursor.getCount()>0){
           database.close();
           return true;
       }else
           return false;
    }
}
