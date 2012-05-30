package fr.colin.topoguide.views;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoGuide;

public class TabDepart extends Activity {
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tab_depart);
      TopoGuide topo = (TopoGuide) getIntent().getExtras().getParcelable(getString(R.string.extra_current_topo));
      fillView(topo);
   }

   private void fillView(TopoGuide topo) {
      ((TextView) findViewById(R.id.depart_nom)).setText(topo.depart.nom);
      ((TextView) findViewById(R.id.depart_altitude)).setText(String.valueOf(topo.depart.altitude) + " m");
      ((TextView) findViewById(R.id.depart_acces)).setText(topo.depart.acces);
   }
}
