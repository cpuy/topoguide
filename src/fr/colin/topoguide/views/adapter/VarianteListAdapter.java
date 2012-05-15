package fr.colin.topoguide.views.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.views.R;

import android.content.Context;
import android.widget.SimpleAdapter;

public class VarianteListAdapter extends SimpleAdapter {

   private static final String VOIE = "voie";
   private static final String DENIVELE = "denivele";
   private static final String DIFFICULTE_SKI = "difficulte_ski";
   private static final String ORIENTATION = "orientation";
   private static final String DESCRIPTION = "description";

   private static final String[] FROM = new String[] { VOIE, DENIVELE, DIFFICULTE_SKI, ORIENTATION,
         DESCRIPTION };
   private static final int[] TO = new int[] { R.id.variante_voie, R.id.variante_denivele,
         R.id.variante_difficulte_ski, R.id.variante_orientation, R.id.variante_description };

   public VarianteListAdapter(Context context, List<Itineraire> variantes) {
      super(context, getData(variantes), R.layout.itineraire_list_item, FROM, TO);
   }

   private static List<Map<String, String>> getData(List<Itineraire> variantes) {
      List<Map<String, String>> listItem = new ArrayList<Map<String, String>>();
      for (Itineraire variante : variantes) {
         Map<String, String> map = new HashMap<String, String>();
         map.put(VOIE, variante.voie);
         map.put(DENIVELE, String.valueOf(variante.denivele));
         map.put(DIFFICULTE_SKI, variante.difficulteSki);
         map.put(ORIENTATION, String.valueOf(variante.orientation));
         map.put(DESCRIPTION, variante.description);
         listItem.add(map);
      }
      return listItem;
   }
}
