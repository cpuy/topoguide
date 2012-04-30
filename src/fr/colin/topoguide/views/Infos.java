package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;

public class Infos extends Activity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topo_detail_infos);
      Bundle extras = getIntent().getExtras();
      TopoGuide topoGuide = (TopoGuide) extras.getParcelable("current_topo");
      ((TextView) findViewById(R.id.infos_massif)).setText(topoGuide.sommet.massif);
      ((TextView) findViewById(R.id.infos_secteur)).setText(topoGuide.sommet.secteur);
      ((TextView) findViewById(R.id.infos_orientation)).setText(topoGuide.itineraire.orientation);
   }
}
