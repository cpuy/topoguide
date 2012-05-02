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
import fr.colin.topoguide.model.view.TopoListItem;
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

   private final List<TopoListItem> topos;
   private final Context context;
   
   List<Object> items;

   public TopoGuideListAdapter(Context context, List<TopoListItem> topos) {
      this.context = context;
      this.topos = topos;
      buildItemList();
   }

   private void buildItemList() {
      items = new ArrayList<Object>();
      Map<String, List<TopoListItem>> massifTopo = new HashMap<String, List<TopoListItem>>();
      for (TopoListItem topo : topos) {
          if (massifTopo.get(topo.massif) == null) {
             massifTopo.put(topo.massif, new ArrayList<TopoListItem>());
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
      if (item instanceof TopoListItem) {
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
      if (item instanceof TopoListItem) {
         return ((TopoListItem) getItem(position)).id;
      }
      else {
         return -1;
      }
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      Object item = getItem(position);
      if (item instanceof TopoListItem) {
         return itemView((TopoListItem) item);
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
   
   private TextView itemView(TopoListItem topo) {
      LayoutInflater inflater = LayoutInflater.from(context);
      TextView view = (TextView) inflater.inflate(R.layout.topoguide_row, null);
      view.setText(topo.nom);
      return view;
   }
}
