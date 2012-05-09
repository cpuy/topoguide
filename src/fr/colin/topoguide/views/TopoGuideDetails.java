package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;

public class TopoGuideDetails extends Activity {

   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topoguide_details);
      
      Bundle extras = getIntent().getExtras();
      TopoGuide topoGuide = (TopoGuide) extras.getParcelable("current_topo");
      TextView tv = (TextView) findViewById(R.id.tvDetail);
      tv.setText("Hello, Topo \nid: " + topoGuide.id +  "\nNom: " + topoGuide.nom + "\nMassif: " + topoGuide.sommet.massif + "\nAccess:" + topoGuide.depart.acces);
   }
}
