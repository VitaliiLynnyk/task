package leontrans.leontranstm.basepart.favouritespart;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kcode.bottomlib.BottomDialog;

import java.util.ArrayList;

import leontrans.leontranstm.R;
import leontrans.leontranstm.basepart.cardpart.AdvertisementInfo;
import leontrans.leontranstm.basepart.userprofile.UserCardOwenerProfile;
import leontrans.leontranstm.utils.SystemServicesUtils;

public class AdvertisementAdapterSelectedItem extends ArrayAdapter<AdvertisementInfo> {
    private FavouriteCardsActivity activity;
    private LayoutInflater inflater;
    private ArrayList<AdvertisementInfo> advertisementInfoList;
    public  ImageView icon_asterisk;
    public static DBHelper dbHelper;

    public AdvertisementAdapterSelectedItem(FavouriteCardsActivity activity, int resource, ArrayList<AdvertisementInfo> advertisementInfoList) {
        super(activity, resource, advertisementInfoList);
        this.advertisementInfoList = advertisementInfoList;
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_layout, parent, false);
        }
        dbHelper = new DBHelper(getContext());

        icon_asterisk = (ImageView) view.findViewById(R.id.icon_asterisk);

        icon_asterisk.setImageResource(R.drawable.icon_favourite);


        TextView trans_type = (TextView) view.findViewById(R.id.trans_type);
        TextView date_from = (TextView) view.findViewById(R.id.date_from);
        ImageView telephone = (ImageView) view.findViewById(R.id.telephone);
        TextView date_to = (TextView) view.findViewById(R.id.date_to);
        Button country_from_ru = (Button) view.findViewById(R.id.country_from_ru);

        Button country_to_ru = (Button) view.findViewById(R.id.country_to_ru);
        Button city_to_ru = (Button) view.findViewById(R.id.city_to_ru);

        Button city_from_ru = (Button) view.findViewById(R.id.city_from_ru);
        Button goods = (Button) view.findViewById(R.id.goods);
        Button pay_type = (Button) view.findViewById(R.id.pay_type);

        pay_type.setText(advertisementInfoList.get(position).getPay_type() + " "+advertisementInfoList.get(position).getPay_form_moment());

        if(advertisementInfoList.get(position).getTrans_capacity().equals("0")){
            goods.setText(advertisementInfoList.get(position).getGoods_load_type()+" "+advertisementInfoList.get(position).getGoods()+" "+advertisementInfoList.get(position).getTrans_weight()+"т ");
        }else if(advertisementInfoList.get(position).getGoods().isEmpty()){
            goods.setText(advertisementInfoList.get(position).getGoods_load_type()+" "+advertisementInfoList.get(position).getTrans_weight()+"т " +advertisementInfoList.get(position).getTrans_capacity()+"м3 ");
        }else if(advertisementInfoList.get(position).getGoods().isEmpty()&&advertisementInfoList.get(position).getTrans_capacity().equals("0")){
            goods.setText(advertisementInfoList.get(position).getGoods_load_type()+" "+advertisementInfoList.get(position).getTrans_weight()+"т");
        }
        else{
            goods.setText(advertisementInfoList.get(position).getGoods_load_type()+" "+advertisementInfoList.get(position).getGoods()+" "+advertisementInfoList.get(position).getTrans_weight()+"т " +advertisementInfoList.get(position).getTrans_capacity()+"м3 ");
        }

        if(!advertisementInfoList.get(position).getTrans_height().equals("0")){
            goods.setText(goods.getText()+" "+ advertisementInfoList.get(position).getTrans_height()+"м");
        }

        if(!advertisementInfoList.get(position).getTrans_length().equals("0")){
            goods.setText(goods.getText()+" "+advertisementInfoList.get(position).getTrans_length()+"м");
        }
        if(!advertisementInfoList.get(position).getTrans_width().equals("0")){
            goods.setText(goods.getText()+" "+advertisementInfoList.get(position).getTrans_width()+"м");
        }

        if(!advertisementInfoList.get(position).getTrans_trailer().isEmpty()){
            goods.setText(goods.getText()+" "+advertisementInfoList.get(position).getTrans_trailer());
        }

        String [] telephoneNumbers = advertisementInfoList.get(position).getTelephone().split(",");
        telephone.setOnClickListener(getTelephoneFieldListener(telephoneNumbers));

        trans_type.setText(advertisementInfoList.get(position).getTrans_type());
        date_from.setText(advertisementInfoList.get(position).getDate_from());
        date_to.setText(advertisementInfoList.get(position).getDate_to());

        country_from_ru.setText(advertisementInfoList.get(position).getCountry_from());
        country_to_ru.setText(advertisementInfoList.get(position).getCountry_to());

        city_from_ru.setText(advertisementInfoList.get(position).getCity_from());
        city_to_ru.setText(advertisementInfoList.get(position).getCity_to());


        Button name = (Button) view.findViewById(R.id.name);
        name.setText(advertisementInfoList.get(position).getFull_name());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,UserCardOwenerProfile.class);
                intent.putExtra("userID",Integer.parseInt(advertisementInfoList.get(position).getUserid_creator()));
                activity.startActivity(intent);
            }
        });

        date_from.setBackgroundColor(Color.parseColor("#bb6b6b"));
        date_to.setBackgroundColor(Color.parseColor("#bb6b6b"));
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        if(advertisementInfoList.get(position).getGoods().isEmpty()){
            imageView.setBackgroundColor(Color.parseColor("#627ea1"));
            imageView.setImageResource(R.drawable.icon_truck);
        }else{
            imageView.setBackgroundColor(Color.parseColor("#83a84a"));
            imageView.setImageResource(R.drawable.icon_cargo);
        }

        icon_asterisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(advertisementInfoList.get(position).getId());
                advertisementInfoList.remove(position);
                notifyDataSetChanged();
            }
        });
        dbHelper.close();

        return view;
    }

    private View.OnClickListener getTelephoneFieldListener(final String [] telephoneNumbers){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog bottomDialog = BottomDialog.newInstance(activity.getResources().getString(R.string.telephone_numbers), activity.getResources().getString(R.string.close_telephone_dialog), telephoneNumbers);
                bottomDialog.setListener(new BottomDialog.OnClickListener() {
                    @Override
                    public void click(int i) {
                        new SystemServicesUtils().startDial(activity, telephoneNumbers[i]);
                    }
                });
                bottomDialog.show(activity.getSupportFragmentManager(),"dialogTag");
            }
        };
    }

}
