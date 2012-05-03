package fr.colin.topoguide.views;

import static fr.colin.topoguide.repository.RemoteTopoGuideRepository.skitour;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.RepositoryException;


public class Download extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.download);
//      setTitle(R.string.view_download_title);
   }

   public void onCancel(View view) {
      setResult(RESULT_CANCELED);
      finish();
   }

   public void onDownload(View view) {
      EditText findViewById = (EditText) findViewById(R.id.topoId);
      try {
         TopoGuide topo = skitour().fetchTopoById(Long.parseLong(findViewById.getText().toString()));
         setResult(RESULT_OK, new Intent().putExtra("downloaded_topo", topo));
      } catch (RepositoryException e) {
         setResult(RESULT_CANCELED);
         e.printStackTrace();
      }
      finish();
   }
   
   public void showHelp(View view) {
      Toast.makeText(this, "Coucou c'est de l'aide", Toast.LENGTH_SHORT).show();
   }
}
