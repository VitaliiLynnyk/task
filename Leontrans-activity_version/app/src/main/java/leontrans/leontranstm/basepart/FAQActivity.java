package leontrans.leontranstm.basepart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import leontrans.leontranstm.DBinformation;
import leontrans.leontranstm.R;
import leontrans.leontranstm.basepart.cardpart.CardsActivity;
import leontrans.leontranstm.utils.Constants;
import leontrans.leontranstm.utils.NavigationDrawerMain;

import static leontrans.leontranstm.basepart.cardpart.AdvertisementAdapter.dbHelper;
import static leontrans.leontranstm.basepart.cardpart.CardsActivity.arrayListAdvertisement;


public class FAQActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Drawer.Result mainNavigationDrawer;
    private ArrayList<DBinformation> informationList;
    private ProgressBar loaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selected_item_layout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainNavigationDrawer = new NavigationDrawerMain(this, toolbar, Constants.NAVMENU_FAQ).getMainNavigationDrawer();
        informationList = dbHelper.getAllTODOLIST();

        for(int i = 0 ; i < dbHelper.numberOfRows() ; i++){
            Log.d("helper",  informationList.get(i).getId_selected_item()+"");
        }
    }

    public void onBackPressed(){
        if(mainNavigationDrawer.isDrawerOpen()){
            mainNavigationDrawer.closeDrawer();
        }
        else{
            startActivity(new Intent(FAQActivity.this, CardsActivity.class));
        }
    }
}
