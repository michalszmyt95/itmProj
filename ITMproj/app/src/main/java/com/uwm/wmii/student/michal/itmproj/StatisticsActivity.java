/*
 __          __        _       _____          _____
 \ \        / /       | |     |_   _|        |  __ \
  \ \  /\  / /__  _ __| | __    | |  _ __    | |__) | __ ___   __ _ _ __ ___  ___ ___
   \ \/  \/ / _ \| '__| |/ /    | | | '_ \   |  ___/ '__/ _ \ / _` | '__/ _ \/ __/ __|
    \  /\  / (_) | |  |   <    _| |_| | | |  | |   | | | (_) | (_| | | |  __/\__ \__ \
     \/  \/ \___/|_|  |_|\_\  |_____|_| |_|  |_|   |_|  \___/ \__, |_|  \___||___/___/
                                                               __/ |
                                                              |___/
*/
package com.uwm.wmii.student.michal.itmproj;

import com.uwm.wmii.student.michal.itmproj.adapter.CustomListAdapter;
import com.uwm.wmii.student.michal.itmproj.model.Items;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    private List<Items> itemsList = new ArrayList<Items>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDB = null;

        try {

            //Create a Database if doesnt exist otherwise Open It

            myDB = this.openOrCreateDatabase("leaderboard", MODE_PRIVATE, null);

            //Create table in database if it doesnt exist allready

            myDB.execSQL("CREATE TABLE IF NOT EXISTS scores (name TEXT, score TEXT);");

            //Select all rows from the table

            Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);

            //If there are no rows (data) then insert some in the table

            if (cursor != null) {
                if (cursor.getCount() == 0) {

                    myDB.execSQL("INSERT INTO scores (name, score) VALUES ('Andy', '7');");
                    myDB.execSQL("INSERT INTO scores (name, score) VALUES ('Marie', '4');");
                    myDB.execSQL("INSERT INTO scores (name, score) VALUES ('George', '1');");

                }

            }


        } catch (Exception e) {

        } finally {

            //Initialize and create a new adapter with layout named list found in activity_main layout

            listView = (ListView) findViewById(R.id.list);
            adapter = new CustomListAdapter(this, itemsList);
            listView.setAdapter(adapter);

            Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);

            if (cursor.moveToFirst()) {

                //read all rows from the database and add to the Items array

                while (!cursor.isAfterLast()) {

                    Items items = new Items();

                    items.setName(cursor.getString(0));
                    items.setScore(cursor.getString(1));

                    itemsList.add(items);
                    cursor.moveToNext();


                }
            }

            //All done, so notify the adapter to populate the list using the Items Array

            adapter.notifyDataSetChanged();
        }

    }
}