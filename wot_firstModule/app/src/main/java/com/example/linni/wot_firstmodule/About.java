package com.example.linni.wot_firstmodule;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.linni.view.R;

public class About extends android.support.v4.app.Fragment{
    int[] integers=null;
    public final static String MARK_RECEIVE = "mark_receive";
    ProgressBar indicatorBar;
    TextView statusView;
    LinearLayout indicatorL;
    Bundle args ;
    LinearLayout informationL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.about_wot, container, false);
        setRetainInstance(true);
        integers = new int[100];
        for(int i=0;i<100;i++) {
            integers[i] = i + 1;
        }
        args = getArguments();
        informationL = (LinearLayout) view.findViewById(R.id.informationL);
        informationL.setVisibility(View.INVISIBLE);
        indicatorL = (LinearLayout) view.findViewById(R.id.indicatorL);
        indicatorBar = (ProgressBar)
                view.findViewById(R.id.indicator);
        statusView = (TextView)
                view.findViewById(R.id.statusView);
       new ProgressTask().execute();

       return view;
    }
   public class ProgressTask extends AsyncTask<Void, Integer, Void>
    {
        @Override
        protected Void doInBackground(Void... unused) {
            for (int i = 0; i<integers.length;i+=9) {
                publishProgress(i);
                SystemClock.sleep(400);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... items) {
            indicatorBar.setProgress(items[0]+1);
            statusView.setText("Статус: " + String.valueOf(items[0]+1));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            indicatorL.setVisibility(View.GONE);
            informationL.setVisibility(View.VISIBLE);
        }
    }
}
