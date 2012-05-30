package fr.colin.topoguide.views;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import fr.colin.topoguide.model.TopoGuide;

public class Tabs extends TabActivity {

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tabs);

      TopoGuide topo = (TopoGuide) getIntent().getExtras().getParcelable(getString(R.string.extra_current_topo));
      TabHost tabHost = getTabHost();
      tabHost.addTab(tab("infos", R.string.tab_info, TabInfos.class, topo));  
      tabHost.addTab(tab("depart", R.string.tab_depart, TabDepart.class, topo));  
      tabHost.addTab(tab("itinerary", R.string.tab_itinerary, TabItineraire.class, topo));
   }

   private TabSpec tab(String tabSpecTag, int tabNameStringId,
         Class<? extends Activity> activityClass, TopoGuide topo) {
      return getTabHost().newTabSpec(tabSpecTag).setIndicator(getResources().getString(tabNameStringId))
            .setContent(tabIntent(activityClass, topo));
   }

   private Intent tabIntent(Class<? extends Activity> activityClass, TopoGuide topo) {
      return new Intent(this, activityClass).putExtra(getString(R.string.extra_current_topo), topo);
   }
}
