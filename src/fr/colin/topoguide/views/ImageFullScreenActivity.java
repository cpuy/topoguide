package fr.colin.topoguide.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import fr.colin.topoguide.repository.ImageRepository;

public class ImageFullScreenActivity extends Activity {
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      long topoId = (Long) getIntent().getExtras().get(getString(R.string.extra_topo_id));
      int imageId = (Integer) getIntent().getExtras().get(getString(R.string.extra_img_id));
      
      Bitmap image = new ImageRepository(this).get(topoId, imageId);
      if (image != null) {
         ImageView view = new ImageView(this);
         view.setImageBitmap(image);
         setContentView(view);
      } else {
//         Toast.makeText(this, "Impossible de trouver l'image", LENGTH_SHORT).show();
         finish();
      }
   }
}