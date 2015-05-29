package projecte.kangapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sergi on 28/5/15.
 */
public class ProvaBDActivity extends AppCompatActivity {

    // Log
    protected static final String TAG = "ProvaBDActivity";

    TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_bd);

        responseTextView = (TextView) findViewById(R.id.bd_nom);

        new GetItemByUserIdTask().execute(new ApiConnector());
    }

    public void setTextToTextView(JSONArray jsonArray) {
        String s  = "";
        for(int i=0; i<jsonArray.length();i++){

            JSONObject json = null;
            try {
                json = jsonArray.getJSONObject(i);
                s = s + "UserId: " + json.getString("userextend_id") + ", Company: " + json.getString("company") + ", Model: " + json.getString("model") + "\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        this.responseTextView.setText(s);
    }


    private class GetItemByUserIdTask extends AsyncTask<ApiConnector,Long,JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread
            return params[0].GetItemByUserId(24);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setTextToTextView(jsonArray);
        }
    }
}
