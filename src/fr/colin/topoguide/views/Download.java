package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.colin.topoguide.views.task.DownloadTopoGuideTask;

public class Download extends Activity {

   public static int RESULT_KO = RESULT_FIRST_USER;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.download);
   }

   /**
    * Button cancel
    */
   public void onCancel(View view) {
      setResult(RESULT_CANCELED);
      finish();
   }

   /**
    * Button download
    */
   public void onDownload(View view) {
      Long numeroTopo = getTopoGuideNumberEnteredByUser();
      setContentView(R.layout.download_progressbar);
      new DownloadTopoGuideTask(this).execute(numeroTopo);
   }

   private long getTopoGuideNumberEnteredByUser() {
      EditText numeroTopo = (EditText) findViewById(R.id.topoId);
      return Long.parseLong(numeroTopo.getText().toString());
   }

   /**
    * Button help 
    */
   public void showHelp(View view) {
      Toast.makeText(this, "Coucou c'est de l'aide", Toast.LENGTH_SHORT).show();
   }
}
