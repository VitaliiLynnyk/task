package leontrans.leontranstm.basepart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;

import leontrans.leontranstm.DBinformation;
import leontrans.leontranstm.R;
import leontrans.leontranstm.basepart.cardpart.AdvertisementAdapter;
import leontrans.leontranstm.basepart.cardpart.AdvertisementAdapterSelectedItem;
import leontrans.leontranstm.basepart.cardpart.AdvertisementInfo;
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
    public AdvertisementAdapterSelectedItem selected_item_adapter;
    public ArrayList<AdvertisementInfo> arrayListSelectedItem = new ArrayList<>();
    private ListView advertisementListView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loaderView = (ProgressBar) findViewById(R.id.loading_spinner);
        loaderView.setVisibility(View.GONE);

        mainNavigationDrawer = new NavigationDrawerMain(this, toolbar, Constants.NAVMENU_FAQ).getMainNavigationDrawer();
        informationList = dbHelper.getAllTODOLIST();

        selected_item_adapter = new AdvertisementAdapterSelectedItem(this,R.layout.list_selected_item_layout,arrayListSelectedItem);


        advertisementListView = (ListView)findViewById(R.id.listView);
        advertisementListView.setAdapter(selected_item_adapter);

        Log.d("select", informationList.size()+"");
        for(int i = 0 ; i < informationList.size() ; i++){
            for(int j = 0 ; j < arrayListAdvertisement.size() ; j++){
                Log.d("select",informationList.get(i).getId_selected_item()+" "+arrayListAdvertisement.get(j).getId() );
                if(informationList.get(i).getId_selected_item()==arrayListAdvertisement.get(j).getId()){
                    arrayListSelectedItem.add(arrayListAdvertisement.get(j));
                }
            }
        }
        selected_item_adapter.notifyDataSetChanged();

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
