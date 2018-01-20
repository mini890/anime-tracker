package miguelsilva.a029187.pokemoncarddatabase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.AdaptadorBaseDados;
import miguelsilva.a029187.pokemoncarddatabase.JavaClasses.Card;

public class MyDeck extends Activity {

    protected TextView card_id, card_name;
    protected Button GoToCard, RemoveCard;
    protected Spinner spinner;
    //protected ImageView cardImage;

    protected AdaptadorBaseDados adaptadorBaseDados;

    protected ArrayList<Card> deckCards;

    protected Activity activity;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deck);

        card_id = findViewById(R.id.card_id_deck);
        card_name = findViewById(R.id.card_name_deck);
        GoToCard = findViewById(R.id.go_to_card_deck);
        RemoveCard = findViewById(R.id.remove_card_deck);

        //cardImage = findViewById(R.id.card_image_deck);

        spinner = findViewById(R.id.spinner_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = this;
        context = this;

        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        deckCards = (ArrayList<Card>) adaptadorBaseDados.getAllCards();

        ArrayAdapter<Card> adapter = new ArrayAdapter<Card>(this, android.R.layout.simple_spinner_item, deckCards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (deckCards.size() <= 0) {
            spinner.setEnabled(false);
            Toast.makeText(this, "No cards in the deck", Toast.LENGTH_SHORT).show();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                card_id.setText("Card ID: " + deckCards.get(i).getId());
                card_name.setText("Card Name: " + deckCards.get(i).getName());
                /*new ImageFactory(cardImage).execute(deckCards.get(i).getUrl());

                for (Card temp : deckCards) {
                    Toast.makeText(activity, temp.getName() + ":" + temp.getUrl(), Toast.LENGTH_SHORT).show();
                }*/

                GoToCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, CardInfo.class);
                        intent.putExtra("card_id", deckCards.get(i).getId());
                        startActivity(intent);
                    }
                });

                RemoveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adaptadorBaseDados.deleteCard(deckCards.get(i).getId());
                        Intent intent = new Intent(String.valueOf(context));
                        finish();
                        startActivity(getIntent());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
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
        }
    }
}