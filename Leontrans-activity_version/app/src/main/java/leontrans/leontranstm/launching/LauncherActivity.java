package leontrans.leontranstm.launching;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import leontrans.leontranstm.R;
import leontrans.leontranstm.basepart.cardpart.CardsActivity;
import leontrans.leontranstm.utils.SiteDataParseUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        isUserAlreadySignedin();
    }

    public void isUserAlreadySignedin(){
        SharedPreferences userPasswordSharedPreferences = getSharedPreferences("hashPassword", MODE_PRIVATE);
        String userPassword = userPasswordSharedPreferences.getString("userPassword","");

        if (userPassword.isEmpty()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_area, new SignInFragment()).commit();
        }
        else{
            new Sync().execute(userPassword);
        }
    }

    private class Sync extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            return new SiteDataParseUtils().getUserIdByHashpassword("https://leon-trans.com/api/ver1/login.php?action=get_hash_id&hash=" + strings[0]);
        }

        @Override
        protected void onPostExecute(Integer userID) {
            super.onPostExecute(userID);
            if (userID > 0){
                Intent intent = new Intent(LauncherActivity.this, CardsActivity.class);
                startActivity(intent);
            }
            else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_area, new SignInFragment()).commit();
            }
        }
    }
}
