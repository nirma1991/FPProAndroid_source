package com.ibt.nirmal.fpproandroid_source.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ibt.nirmal.fpproandroid_source.database.model.params;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ParamDefaults";

    public static JSONArray resultSet     = new JSONArray();

    ArrayList<params> listOfParameters = new ArrayList<params>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(params.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + params.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void insertParams(double r1factor, int r1sc0hr, int r1maxsc, int r2factor, int r2sc0hr, int r2maxsc,
                             int r3factor, int r3reset, double fatactfactor, double yellowsec, int orangesec,
                             int redsec, int maxdutyd, int  maxdutyn, int manrest, int ntfrom, int  ntuntil,
                             boolean avmdts, boolean restisduty, int dutyreset, boolean displaylabels,
                             boolean onerowperday, boolean oscrcor, double oscrcorfactor, int oscrcormindays,
                             int oscrcorindays ) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + params.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);
       if(cursor.getCount() <= 0)
        {
        ContentValues values = new ContentValues();

        values.put(params.r1factor, r1factor);
        values.put(params.r1sc0hr, r1sc0hr);
        values.put(params.r1maxsc, r1maxsc);
        values.put(params.r2factor, r2factor);
        values.put(params.r2sc0hr, r2sc0hr);
        values.put(params.r2maxsc, r2maxsc);
        values.put(params.r3factor, r3factor);
        values.put(params.r3reset, r3reset);
        values.put(params.fatactfactor, fatactfactor);
        values.put(params.yellowsec, yellowsec);
        values.put(params.orangesec, orangesec);
        values.put(params.redsec, redsec);
        values.put(params.maxdutyd, maxdutyd);
        values.put(params.maxdutyn, maxdutyn);
        values.put(params.manrest, manrest);
        values.put(params.ntfrom, ntfrom);
        values.put(params.ntuntil, ntuntil);
        values.put(params.avmdts, avmdts);
        values.put(params.restisduty, restisduty);
        values.put(params.dutyreset, dutyreset);
        values.put(params.displaylabels, displaylabels);
        values.put(params.onerowperday, onerowperday);
        values.put(params.oscrcor, oscrcor);
        values.put(params.oscrcorfactor, oscrcorfactor);
        values.put(params.oscrcormindays, oscrcormindays);
        values.put(params.oscrcorindays, oscrcorindays);

        // insert row
        long id = db.insert(params.TABLE_NAME, null, values);

        // close db connection
        db.close();
    }
        // return newly inserted row id
    }

    public params getNote() throws JSONException {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + params.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);


        /*Cursor cursor = db.query(params.TABLE_NAME,
                 new String[]{params.r1factor, params.r1sc0hr, params.r1maxsc},
                params.r1factor + "=?",
                 new String[]{String.valueOf(id)}, null, null, null, null);*/

        if( cursor != null && cursor.moveToFirst() ) {


            params note = new params();


            note.setR1factor_val(cursor.getDouble(cursor.getColumnIndex(params.r1factor)));
            note.setR1sc0hr_val(cursor.getInt(cursor.getColumnIndex(params.r1sc0hr)));
            note.setR1maxsc_val(cursor.getInt(cursor.getColumnIndex(params.r1maxsc)));
            note.setR2factor_val(cursor.getInt(cursor.getColumnIndex(params.r2factor)));
            note.setR2sc0hr_val(cursor.getInt(cursor.getColumnIndex(params.r2sc0hr)));
            note.setR2maxsc_val(cursor.getInt(cursor.getColumnIndex(params.r2maxsc)));
            note.setR3factor_val(cursor.getInt(cursor.getColumnIndex(params.r3factor)));
            note.setR3reset_val(cursor.getInt(cursor.getColumnIndex(params.r3reset)));
            note.setFatactfactor_val(cursor.getDouble(cursor.getColumnIndex(params.fatactfactor)));
            note.setYellowsec_val(cursor.getDouble(cursor.getColumnIndex(params.yellowsec)));
            note.setOrangesec_val(cursor.getInt(cursor.getColumnIndex(params.orangesec)));
            note.setRedsec_val(cursor.getInt(cursor.getColumnIndex(params.redsec)));

            note.setMaxdutyd_val(cursor.getInt(cursor.getColumnIndex(params.maxdutyd)));
            note.setMaxdutyn_val(cursor.getInt(cursor.getColumnIndex(params.maxdutyn)));
            note.setManrest_val(cursor.getInt(cursor.getColumnIndex(params.manrest)));
            note.setNtfrom_val(cursor.getInt(cursor.getColumnIndex(params.ntfrom)));
            note.setNtuntil_val(cursor.getInt(cursor.getColumnIndex(params.ntuntil)));
            note.setAvmdts_val(cursor.getInt(cursor.getColumnIndex(params.avmdts)));
            note.setRestisduty_val(cursor.getInt(cursor.getColumnIndex(params.restisduty)));
            note.setDutyreset_val(cursor.getInt(cursor.getColumnIndex(params.dutyreset)));
            note.setDisplaylabels_val(cursor.getInt(cursor.getColumnIndex(params.displaylabels)));
            note.setOnerowperday_val(cursor.getInt(cursor.getColumnIndex(params.onerowperday)));
            note.setOscrcor_val(cursor.getInt(cursor.getColumnIndex(params.oscrcor)));
            note.setOscrcorfactor_val(cursor.getDouble(cursor.getColumnIndex(params.oscrcorfactor)));
            note.setOscrcormindays_val(cursor.getInt(cursor.getColumnIndex(params.oscrcormindays)));
            note.setOscrcorindays_val(cursor.getInt(cursor.getColumnIndex(params.oscrcorindays)));

          /*  JSONObject jsonObj = new JSONObject();
            int totalColumn = cursor.getColumnCount();
            for( int i=0 ;  i < totalColumn ; i++ ) {
                jsonObj.put(cursor.getColumnName(i),listOfParameters.get(0));
            }
            System.out.println( jsonObj );*/
        }
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i < totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i) );
                            switch (cursor.getType(i))  {
                                case Cursor.FIELD_TYPE_FLOAT:
                                    rowObject.put(cursor.getColumnName(i), cursor.getFloat(i));
                                    break;
                                case Cursor.FIELD_TYPE_INTEGER:
                                    rowObject.put(cursor.getColumnName(i), cursor.getInt(i));
                                    break;
                                case Cursor.FIELD_TYPE_STRING:
                                    rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                                    break;
                            }
                           // rowObject.put(cursor.getColumnName(i) , cursor.getType(i).);
                            //rowObject.app
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage()  );
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

       /* // prepare note object
        params note = new params(
                cursor.getInt(cursor.getColumnIndex(params.r1factor)),
                cursor.getString(cursor.getColumnIndex(params.r1sc0hr)),
                cursor.getString(cursor.getColumnIndex(params.r1maxsc)));
*/
        // close the db connection
        cursor.close();

        return null;
    }

   /* public List<params> getAllNotes() {
        List<params> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + params.TABLE_NAME + " ORDER BY " +
                params.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                params note = new params();
                note.setId(cursor.getInt(cursor.getColumnIndex(params.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(params.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(params.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(params note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(params.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(params.TABLE_NAME, values, params.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(params note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(params.TABLE_NAME, params.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }*/
}