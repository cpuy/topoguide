package fr.colin.topoguide.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Download extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.download);
   }

   public void onCancel(View view) {
      setResult(RESULT_CANCELED);
      finish();
   }

   public void onDownload(View view) {
      EditText findViewById = (EditText) findViewById(R.id.topoId);
      setResult(RESULT_OK, new Intent().putExtra("downloaded_topo", Long.parseLong(findViewById.getText().toString())));
      finish();
   }
   
   public void showHelp(View view) {
      Toast.makeText(this, "Coucou c'est de l'aide", Toast.LENGTH_SHORT).show();
   }
}
