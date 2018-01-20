package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.AdaptadorBaseDados;

public class CardInfo extends Activity {

    protected Intent intent;
    protected String self_card_id;

    protected TextView card_id, card_name, dex_number, hp, rarity, types;
    protected ImageView cardImage;
    protected Button addToDeckButton;

    protected Activity activity;

    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        //POR os FINDVIEWBYID
    }

    @Override
    protected void onStart() {
        super.onStart();

        intent = getIntent();
        self_card_id = intent.getStringExtra("card_id");

        activity = this;

        //ASYNC TASK TO GET ALL INFO
        /*
         * Get JSON String
         * Insert into JSONArray
         * Set vars with each value:
         *  final String game_id = jsonArray.getJSONObject(0).getString("id");
         *  final String ratingValue = jsonArray.getJSONObject(0).getDouble("rating");
         *  final String coverURLValue = jsonArray.getJSONObject(0).getJSONObject("cover").getString("url");
         *
         *  REPLACE STRING FROM HTTPS FOR HTTP in imageUrl
         */

        //START IMAGE FACTORY

        //SET TEXTVIEWS

        //WAIT TO LOAD INFO
        SystemClock.sleep(5);

        if (adaptadorBaseDados.exists(self_card_id)) {
            //DISABLE BUTTON
        } else {
            /*BUTTON CLICK LISTENER {
                * INSERE BD
                * DISABLE BUTTON
                * MOSTRAR INSERÇÃO COM SUCESSO
            */

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }

    /*protected class AsyncGenerator extends AsyncTask<String, Void, Bitmap> {

        public AsyncGenerator(ImageView cover) {
            this.cover = cover;
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
            cover.setImageBitmap(bitmap);
        }
    }*/

}