package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.Card;

public class MainActivity extends Activity {

    protected Button my_deck_button, search_name_button, search_id_button;
    protected EditText SearchCardByName, SearchCardById;

    protected Activity activity;

    protected AsyncGenerator backgroundTask;

    protected ArrayList<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_deck_button = findViewById(R.id.my_deck_button);
        search_name_button = findViewById(R.id.search_name_button);
        search_id_button = findViewById(R.id.search_id_button);

        SearchCardByName = findViewById(R.id.SearchCardByName);
        SearchCardById = findViewById(R.id.SearchCardById);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = this;
        cardList = new ArrayList<Card>();
        search_name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DO ASYNC TASK
                //ADD TO cardList;
                backgroundTask = new AsyncGenerator("http://api.pokemontcg.io/", "v1/cards?name=" + SearchCardByName.getText().toString(), 80);
                backgroundTask.execute();
            }
        });

        search_id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCardById(SearchCardById.getText().toString());
            }
        });

        my_deck_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyDeck.class);
                startActivity(intent);
            }
        });
    }

    private void OpenSearchActivity(ArrayList<Card> cardList) {
        Intent intent = new Intent(activity, SearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardList", (Serializable) cardList);
        intent.putExtra("bundledCardArrayList", bundle);
        startActivity(intent);
    }

    private void OpenCardById(String card_id) {
        Intent intent = new Intent(activity, CardInfo.class);
        intent.putExtra("card_id", card_id);
        startActivity(intent);
    }

    public final class AsyncGenerator extends AsyncTask<JSONObject, Void, JSONObject> {

        protected String host, path;
        protected int port;

        public AsyncGenerator(String host, String path, int port) {
            this.host = host;
            this.path = path;
            this.port = port;
        }

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            int responseCode = -1;
            JSONObject jsonResponse = null;
            StringBuilder builder = new StringBuilder();
            HttpClient client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(host + path);

            try {
                HttpResponse response = client.execute(httpget);
                StatusLine statusLine = response.getStatusLine();
                responseCode = statusLine.getStatusCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    jsonResponse = new JSONObject(builder.toString());
                } else {
                    System.out.println("Error connecting");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject j) {
            super.onPostExecute(j);
            try {
                JSONArray cards = j.getJSONArray("cards");
                for (int i = 0; i < cards.length(); i++) {
                    JSONObject jsonObject = cards.getJSONObject(i);
                    String id = jsonObject.get("id").toString();
                    String name = jsonObject.get("name").toString();
                    String url = jsonObject.get("imageUrl").toString();
                    cardList.add(new Card(id, name, url));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OpenSearchActivity(cardList);
        }
    }
}