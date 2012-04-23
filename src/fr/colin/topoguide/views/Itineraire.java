package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import fr.colin.topoguide.model.TopoGuide;

public class Itineraire extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topo_itineraire);

      Bundle extras = getIntent().getExtras();
      TopoGuide topoGuide = (TopoGuide) extras.getParcelable("current_topo");
      if (!topoGuide.variantes.isEmpty()) {
//         ((TextView) findViewById(R.id.topo_itineraire_denivele)).setText(topoGuide.variantes.get(0).denivele);
//         ((TextView) findViewById(R.id.topo_itineraire_description)).setText(topoGuide.variantes.get(0).description);
//         ((TextView) findViewById(R.id.topo_itineraire_difficulte_ski)).setText(topoGuide.variantes.get(0).difficulteSki);
//         ((TextView) findViewById(R.id.topo_itineraire_orientation)).setText(topoGuide.variantes.get(0).orientation);
//         ((TextView) findViewById(R.id.topo_itineraire_voie)).setText(topoGuide.variantes.get(0).voie);
         Toast.makeText(this, "Coucou", 1);
      }

   }
}
