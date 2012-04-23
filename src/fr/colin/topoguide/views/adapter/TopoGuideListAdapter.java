package fr.colin.topoguide.views.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.colin.topoguide.model.TopoMinimal;
import fr.colin.topoguide.views.R;

/**
 * TODO : 
 *    - Clean code
 *    - Mise en cache pour ne pas tout le temps chercher les données en base si pas de nouveau download.
 *    - Massifs par ordre alphabetique
 * 
 * @author colin
 *
 */
public class TopoGuideListAdapter extends BaseAdapter {

   private final List<TopoMinimal> topos;
   private final Context context;
   
   List<Object> items;

   public TopoGuideListAdapter(Context context, List<TopoMinimal> topos) {
      this.context = context;
      this.topos = topos;
      buildItemList();
   }

   private void buildItemList() {
      items = new ArrayList<Object>();
      Map<String, List<TopoMinimal>> massifTopo = new HashMap<String, List<TopoMinimal>>();
      for (TopoMinimal topo : topos) {
          if (massifTopo.get(topo.massif) == null) {
             massifTopo.put(topo.massif, new ArrayList<TopoMinimal>());
          }
          massifTopo.get(topo.massif).add(topo);
      }
      
      for (String massif : massifTopo.keySet()) {
         items.add(massif);
         items.addAll(massifTopo.get(massif));
      }
   }
   
   @Override
   public boolean areAllItemsEnabled() {
      return false;
   }
   
   @Override
   public boolean isEnabled(int position) {
      Object item = getItem(position);
      if (item instanceof TopoMinimal) {
         return true;
      }
      else {
         return false;
      }
   }

   public int getCount() {
      return items.size();
   }

   public Object getItem(int position) {
      return items.get(position);
   }

   public long getItemId(int position) {
      Object item = getItem(position);
      if (item instanceof TopoMinimal) {
         return ((TopoMinimal) getItem(position)).id;
      }
      else {
         return -1;
      }
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      Object item = getItem(position);
      if (item instanceof TopoMinimal) {
         return itemView((TopoMinimal) item);
      }
      else {
         return headerView((String) item);
      }
   }

   private TextView headerView(String massif) {
      LayoutInflater inflater = LayoutInflater.from(context);
      TextView view = (TextView) inflater.inflate(R.layout.topo_list_header, null);
      view.setText(massif);
      return view;
   }
   
   private TextView itemView(TopoMinimal topo) {
      LayoutInflater inflater = LayoutInflater.from(context);
      TextView view = (TextView) inflater.inflate(R.layout.topoguide_row, null);
      view.setText(topo.nom);
      return view;
   }
}
