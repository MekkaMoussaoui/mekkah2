package dz.atelier.ntic.remplircases.game;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import static android.widget.Toast.makeText;

public class GameMic extends Activity implements RecognitionListener {

    ////////////////////////////////////////////////////
    RatingBar ratingBar;
    int score;
    ImageView caption_image;
    int image_sphinx;


    String grammar_sphinx = "mot.gram";
    String dictionnaire_sphinx = "cmudict-fr-fr.dict";
    String ptm_sphinx = "fr-fr-ptm";
    ////////////////////////////////////////////////////

    private static final String WORD_SEARCH = "";
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private SpeechRecognizer recognizer;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_game_mic);

        Bundle extras = getIntent().getExtras();
        //grammar_sphinx = extras.getString("grammar");
        image_sphinx = extras.getInt("image");


        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        new SetupTask(this).execute();
    }

    private class SetupTask extends AsyncTask<Void, Void, Exception> {
        WeakReference<GameMic> activityReference;
        SetupTask(GameMic activity) {
            this.activityReference = new WeakReference<>(activity);
        }

        @Override
        protected Exception doInBackground(Void... params) {
            try {
                Assets assets = new Assets(activityReference.get());
                File assetDir = assets.syncAssets();
                activityReference.get().setupRecognizer(assetDir);
                } catch (IOException e) {
                return e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exception result) {

            caption_image = (ImageView)findViewById(R.id.caption_image);
            caption_image.setImageResource(image_sphinx);
        }
    }

     @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new SetupTask(this).execute();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (recognizer != null) {

            recognizer.cancel();
            recognizer.shutdown();
        }
    }


    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    public void DigitsClick(View view) {
        recognizer.stop();
        recognizer.startListening(WORD_SEARCH, 10000);
    }

    ////////////////////////////////////////////////
    public Boolean getAnswer(String mot_a_prononce, Hypothesis hypothesis){
        if (hypothesis.getHypstr().equals(mot_a_prononce))
            return true;
        else
            return false;
    }
    /////////////////////////////////////////////////////////

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;
        //  String text = hypothesis.getHypstr();
        // ((TextView) findViewById(R.id.result_text)).setText(text);
        score = hypothesis.getBestScore();
    }

    //This callback is called when we stop the recognizer.
    @Override
    public void onResult(Hypothesis hypothesis) {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(0);

        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            ratingBar.setRating(getScoreStars(score));
            TextView result_text = (TextView) findViewById(R.id.result_text);

            //result_text.setText(toString().valueOf(getAnswer(text, hypothesis)));

        }
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    // We stop recognizer here to get a final result

    @Override
    public void onEndOfSpeech() {
        recognizer.stop();
    }



    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, ptm_sphinx))
                .setDictionary(new File(assetsDir, dictionnaire_sphinx))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)

                .getRecognizer();
        recognizer.addListener(this);

        /* In your application you might not need to add all those searches.
          They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(WORD_SEARCH, WORD_SEARCH);

        // Create grammar-based search for digit recognition
        File digitsGrammar = new File(assetsDir, grammar_sphinx);
        recognizer.addGrammarSearch(WORD_SEARCH, digitsGrammar);
    }

    @Override
    public void onError(Exception error) {
        error.getMessage();
    }

    @Override
    public void onTimeout() {


    }


    ////////////////////////////////////////////////////
    public float getScoreStars(int getBestScore){
        if (getBestScore <= 0 && getBestScore > -1000)
            return 3.0f;
        else if (getBestScore <= -1000 && getBestScore > -2000)
            return 2.5f;
        else if (getBestScore <= -2000 && getBestScore > -3000)
            return 2;
        else if (getBestScore <= -3000 && getBestScore > -4000)
            return 1.5f;
        else if (getBestScore <= -4000 && getBestScore > -5000)
            return 1;
        else if (getBestScore <= -5000 && getBestScore > -6000)
            return 0.5f;
        else
            return 0;
    }
    ///////////////////////////////////////////////////

}