package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageFullScreenActivity extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      long topoId = (Long) getIntent().getExtras().get(getString(R.string.extra_topo_id));
      int img_id = (Integer) getIntent().getExtras().get(getString(R.string.extra_img_id));
      
      Toast.makeText(this, "topo " + topoId + " img " + img_id, Toast.LENGTH_SHORT).show();
      
      ImageView view = new ImageView(this);
      view.setImageDrawable(getResources().getDrawable(R.drawable.sure_test));
      setContentView(view);
   }
}