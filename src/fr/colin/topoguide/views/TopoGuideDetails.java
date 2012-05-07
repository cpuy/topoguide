package fr.colin.topoguide.views;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;

public class TopoGuideDetails extends Activity {

   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topoguide_details);
      
      
      Bundle extras = getIntent().getExtras();
      TopoGuide topoGuide = (TopoGuide) extras.getParcelable("current_topo");
      TextView tv = (TextView) findViewById(R.id.tvDetail);
      tv.setText("Hello, Topo \nid: " + topoGuide.id +  "\nNom: " + topoGuide.nom + "\nMassif: " + topoGuide.sommet.massif + "\nAccess:" + topoGuide.depart.acces);
      
      ImageView imageVIew = (ImageView) findViewById(R.id.ivTest);
      
      List<Bitmap> findImagesForTopo = new ImageRepository(this).findByTopoId(topoGuide.id);
      Log.d("TOTO", String.valueOf(findImagesForTopo.size()));
      if (findImagesForTopo.size() > 0) {
         imageVIew.setImageBitmap(findImagesForTopo.get(0));
      }
   }

}
