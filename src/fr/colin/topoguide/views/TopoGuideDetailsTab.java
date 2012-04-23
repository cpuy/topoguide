package fr.colin.topoguide.views;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import fr.colin.topoguide.model.TopoGuide;

public class TopoGuideDetailsTab extends TabActivity {


   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.onglets);
      
      Bundle extras = getIntent().getExtras();
      
      TopoGuide topo = (TopoGuide) extras.getParcelable("current_topo");
      Intent intent = new Intent(this, TopoGuideDetails.class);
      intent.putExtra("current_topo", topo);

      TabHost tabHost = getTabHost();
      Resources res = getResources();
      tabHost.addTab(tabHost.newTabSpec("infos").setIndicator(res.getString(R.string.tab_info)).setContent(new Intent(this, Infos.class).putExtra("current_topo", topo)));
      tabHost.addTab(tabHost.newTabSpec("acces").setIndicator(res.getString(R.string.tab_acces)).setContent(intent));
      tabHost.addTab(tabHost.newTabSpec("itinerary").setIndicator(res.getString(R.string.tab_itinerary)).setContent(new Intent(this, Itineraire.class).putExtra("current_topo", topo)));
   }
}
