package com.example.joakim.mattekampen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class SeeHistoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_history);

        ArrayAdapter<Result> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewResults);
        list.setAdapter(adapter);
    }

    public void goBack(View v){
        System.out.println("GO BACK");
        onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdapter extends ArrayAdapter<Result> {

        public MyListAdapter() {
            super(SeeHistoryActivity.this, R.layout.activity_see_history, MainActivity.user.getResults());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_result_list, parent, false);
            }

            Result currentResult = MainActivity.user.getResults().get(position);

            TextView date = (TextView) itemView.findViewById(R.id.date);
            SimpleDateFormat df = new SimpleDateFormat("E MM/dd '\nkl' hh:mm");
            df.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
            date.setText(df.format(currentResult.getDate()));
            TextView correct = (TextView) itemView.findViewById(R.id.correct);
            correct.setText(Integer.toString(currentResult.getCorrect()));
            TextView wrong = (TextView) itemView.findViewById(R.id.wrong);
            wrong.setText(Integer.toString(currentResult.getWrong()));
            TextView percentage = (TextView) itemView.findViewById(R.id.percentage);
            percentage.setText(Integer.toString(currentResult.getPercentage())+"%");

            return itemView;
        }
    };
}
