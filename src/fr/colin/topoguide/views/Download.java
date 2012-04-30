package fr.colin.topoguide.views;

import static fr.colin.topoguide.repository.RemoteTopoGuideRepository.skitour;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.RepositoryException;

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
         TopoGuide topo = skitour().fetchTopoById(Long.parseLong(findViewById.getText().toString()));
         setResult(RESULT_OK, new Intent().putExtra("downloaded_topo", topo));
      } catch (NumberFormatException e) {
         setResult(RESULT_CANCELED);
         e.printStackTrace();
      } catch (RepositoryException e) {
         setResult(RESULT_CANCELED);
         e.printStackTrace();
      }
       finish();
   }
}
