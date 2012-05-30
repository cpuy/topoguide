package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;

public class TabInfos extends Activity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tab_infos);
      
      TopoGuide topo = (TopoGuide) getIntent().getExtras().getParcelable(getString(R.string.extra_current_topo));
      fillView(topo);
   }

   private void fillView(TopoGuide topoGuide) {
      ((TextView) findViewById(R.id.infos_massif)).setText(topoGuide.sommet.massif);
      ((TextView) findViewById(R.id.infos_secteur)).setText(topoGuide.sommet.secteur);
      ((TextView) findViewById(R.id.infos_orientation)).setText(topoGuide.itineraire.orientation.value());
      ((TextView) findViewById(R.id.infos_denivele)).setText(String.valueOf(topoGuide.itineraire.denivele) + " m");
      ((TextView) findViewById(R.id.infos_difficulte_monte)).setText(topoGuide.itineraire.difficulteMontee);
      ((TextView) findViewById(R.id.infos_difficulte_ski)).setText(topoGuide.itineraire.difficulteSki);
      ((TextView) findViewById(R.id.infos_nb_jours)).setText(String.valueOf(topoGuide.itineraire.dureeJour));
      ((TextView) findViewById(R.id.infos_type)).setText(topoGuide.itineraire.type.value());
      ((TextView) findViewById(R.id.infos_materiel)).setText(topoGuide.itineraire.materiel);
      ((TextView) findViewById(R.id.infos_depart)).setText(topoGuide.depart.nom);
      ((TextView) findViewById(R.id.infos_altitude_depart)).setText(String.valueOf(topoGuide.depart.altitude) + " m");
      ((TextView) findViewById(R.id.infos_pente)).setText(String.valueOf(topoGuide.itineraire.pente));
   }
}
