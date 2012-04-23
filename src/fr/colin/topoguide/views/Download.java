package fr.colin.topoguide.views;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.TopoGuideRepository;

/**
 * TODO
 *    - Input text -> input numero
 *    
 * @author colin
 *
 */
public class Download extends Activity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.download);
      
      
      Button confirmButton = (Button) findViewById(R.id.download);
      confirmButton.setOnClickListener(downloadButtonClickListener());
   }

   private OnClickListener downloadButtonClickListener() {
      return new View.OnClickListener() {
         public void onClick(View view) {
             onClickButtonDownload();
         }
     };
   }
   
   private void onClickButtonDownload() {
      EditText findViewById = (EditText) findViewById(R.id.topoId);
       try {
         TopoGuide topo = TopoGuide.fromId(findViewById.getText().toString());
         setResult(RESULT_OK, new Intent().putExtra("downloaded_topo", topo));
      } catch (IOException e) {
         setResult(RESULT_CANCELED);
         e.printStackTrace();
      }
       finish();
   }
}
