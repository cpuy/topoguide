package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;

public class TabInfos extends Activity {


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topo_detail_infos);
      
      TopoGuide topo = (TopoGuide) getIntent().getExtras().getParcelable(getString(R.string.extra_current_topo));
      fillView(topo);
   }

   private void fillView(TopoGuide topoGuide) {
      ((TextView) findViewById(R.id.infos_massif)).setText(topoGuide.sommet.massif);
      ((TextView) findViewById(R.id.infos_secteur)).setText(topoGuide.sommet.secteur);
      ((TextView) findViewById(R.id.infos_orientation)).setText(topoGuide.itineraire.orientation.value());
   }
}
