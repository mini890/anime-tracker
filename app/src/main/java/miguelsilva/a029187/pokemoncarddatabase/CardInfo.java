package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.AdaptadorBaseDados;

public class CardInfo extends Activity {

    protected Intent intent;
    protected String self_card_id, self_card_name, self_image_url;

    protected TextView card_id, card_name, dex_number, hp, rarity, types;
    protected ImageView cardImage;
    protected Button addToDeckButton;

    protected Activity activity;

    protected AsyncGenerator backgroundTask;
    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        card_id = findViewById(R.id.card_id_info);
        card_name = findViewById(R.id.card_name_info);
        dex_number = findViewById(R.id.dex_number_info);
        hp = findViewById(R.id.hp_info);
        rarity = findViewById(R.id.rarity_info);
        types = findViewById(R.id.types_info);

        cardImage = findViewById(R.id.card_image_info);

        addToDeckButton = findViewById(R.id.add_to_deck_info);
    }

    @Override
    protected void onStart() {
        super.onStart();

        intent = getIntent();
        self_card_id = intent.getStringExtra("card_id");

        activity = this;

        backgroundTask = new AsyncGenerator("http://api.pokemontcg.io", "/v1/cards/" + self_card_id, 80);
        backgroundTask.execute();

        adaptadorBaseDados = new AdaptadorBaseDados(activity).open();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
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
                JSONObject cards = j.getJSONObject("card");
                String card_id_string = cards.getString("id");
                String card_name_string = cards.getString("name");
                self_card_name = card_name_string;
                String dex_string = cards.getString("nationalPokedexNumber");
                String hp_string = cards.getString("hp");
                String rarity_string = cards.getString("rarity");
                String types_string = cards.getString("types");
                self_image_url = cards.getString("imageUrl");

                card_id.append(card_id_string);
                card_name.append(card_name_string);
                dex_number.append(dex_string);
                hp.append(hp_string);
                if (rarity_string == "") {
                    rarity_string = "Value Unavailable";
                }
                rarity.append(rarity_string);
                types.append(types_string);

                new ImageFactory(cardImage).execute(self_image_url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected class ImageFactory extends AsyncTask<String, Void, Bitmap> {
        ImageView card_image;

        public ImageFactory(ImageView card_image) {
            this.card_image = card_image;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap coverImage = null;
            try {

                InputStream inputStream = new URL(url).openStream();
                coverImage = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return coverImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            card_image.setImageBitmap(bitmap);
            addToDeckButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, self_image_url, Toast.LENGTH_SHORT).show();
                    adaptadorBaseDados.insertCard(self_card_id, self_card_name, self_image_url);
                    addToDeckButton.setEnabled(false);
                    Toast.makeText(activity, "Inserted", Toast.LENGTH_SHORT).show();
                    adaptadorBaseDados.close();
                    finish();
                }
            });
        }
    }
}