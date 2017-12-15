package leontrans.leontranstm.basepart.userprofile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import leontrans.leontranstm.R;
import leontrans.leontranstm.utils.SiteDataParseUtils;
import leontrans.leontranstm.utils.SystemServicesUtils;

public class UserCardOwenerProfile extends AppCompatActivity{
    private int userID;

    protected Toolbar toolbar;
    protected ProgressBar loaderView;
    protected ScrollView contentArea;
    protected int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loaderView = (ProgressBar) findViewById(R.id.loading_spinner);
        contentArea = (ScrollView) findViewById(R.id.content_area);
        contentArea.setVisibility(View.GONE);
        animationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        userID = getIntent().getIntExtra("userID",-1);
        if (userID > 0) new LoadFragmentData().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return true;
    }

    private class LoadFragmentData extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... voids) {
            return new SiteDataParseUtils().getSiteRequestResult("https://leon-trans.com/api/ver1/login.php?action=get_user&id=" + userID);
        }

        @Override
        protected void onPostExecute(String jsonStr) {

            try {
                JSONObject dataJson = new JSONObject(jsonStr);
                final SystemServicesUtils systemServicesUtils = new SystemServicesUtils();

                TextView userNameInfo = (TextView) findViewById(R.id.underImageInfo);
                final TextView TV_city_value = (TextView) findViewById(R.id.TV_city_value);
                final TextView TV_email_value = (TextView) findViewById(R.id.TV_email_value);
                final TextView TV_telephone_value = (TextView) findViewById(R.id.TV_telephone_value);
                TextView TV_skype_value = (TextView) findViewById(R.id.TV_skype_value);
                TextView TV_icq_value = (TextView) findViewById(R.id.TV_icq_value);
                final TextView TV_website_value = (TextView) findViewById(R.id.TV_website_value);
                TextView TV_occupation_value = (TextView) findViewById(R.id.TV_occupation_value);
                TextView TV_occupation_type_value = (TextView) findViewById(R.id.TV_occupation_type_value);
                TextView TV_occupation_description_value = (TextView) findViewById(R.id.TV_occupation_description_value);
                TextView TV_register_date_value = (TextView) findViewById(R.id.TV_register_date_value);
                TextView TV_last_online_value = (TextView) findViewById(R.id.TV_last_online_value);
                ImageView userAvatarImageView = (ImageView) findViewById(R.id.userAvatarImageView);


                Picasso.with(UserCardOwenerProfile.this)
                        .load("https://leon-trans.com/uploads/user-avatars/" + dataJson.getString("avatar"))
                        .error(R.drawable.default_avatar)
                        .into(userAvatarImageView);

                if (dataJson.getString("person_type").equals("individual")){
                    userNameInfo.setText(dataJson.getString("full_name") + "\n" + dataJson.getString("login"));
                }
                else {
                    userNameInfo.setText(dataJson.getString("nomination_prefix") + " " + dataJson.getString("nomination_name") +"\n" + dataJson.getString("login"));
                }

                TV_city_value.setText(dataJson.getString("location_city"));
                TV_email_value.setText(dataJson.getString("email"));
                TV_telephone_value.setText(dataJson.getString("phones"));
                TV_skype_value.setText(dataJson.getString("skype"));
                TV_icq_value.setText(dataJson.getString("icq"));
                TV_website_value.setText(dataJson.getString("website"));
                TV_occupation_value.setText(dataJson.getString("metier_type"));
                TV_occupation_type_value.setText(dataJson.getString("activity_kind"));
                TV_occupation_description_value.setText(dataJson.getString("activity_desc"));
                TV_register_date_value.setText(makeDate(dataJson.getString("date_registry")));
                TV_last_online_value.setText(makeDate(dataJson.getString("date_last_action")));

                TV_telephone_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        systemServicesUtils.startDial(UserCardOwenerProfile.this, TV_telephone_value.getText().toString());
                    }
                });

                TV_email_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        systemServicesUtils.startMail(UserCardOwenerProfile.this, TV_email_value.getText().toString());
                    }
                });

                TV_website_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TV_website_value.getText().toString().isEmpty()) {
                            systemServicesUtils.startInternetBrowser(UserCardOwenerProfile.this, TV_website_value.getText().toString());
                        }
                    }
                });

                TV_city_value.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TV_city_value.getText().toString().isEmpty()) {
                            systemServicesUtils.startMaps(UserCardOwenerProfile.this, TV_city_value.getText().toString());
                        }
                    }
                });

                crossfade();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeDate(String date){
        long dv;
        Date df;
        String dateFrom;
        dv = Long.valueOf(date) * 1000;
        df = new java.util.Date(dv);
        dateFrom = new SimpleDateFormat("dd.MM.yyyy").format(df);
        return dateFrom;
    }

    private void crossfade() {
        contentArea.setAlpha(0f);
        contentArea.setVisibility(View.VISIBLE);

        contentArea.animate()
                .alpha(1f)
                .setDuration(animationDuration)
                .setListener(null);

        loaderView.animate()
                .alpha(0f)
                .setDuration(animationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loaderView.setVisibility(View.GONE);
                    }
                });
    }
}
