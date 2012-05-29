package fr.colin.topoguide.views;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;
import fr.colin.topoguide.repository.RepositoryException;
import fr.colin.topoguide.views.adapter.ImagesGridAdapter;

public class ItineraireTab extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.itineraire);
      TopoGuide topoGuide = (TopoGuide) getIntent().getExtras().getParcelable("current_topo");
      fillPage(topoGuide);
   }

   private void fillPage(TopoGuide topoGuide) {
      fillImagesGridView(topoGuide);
      fillItineraireInformations(topoGuide.itineraire);
      fillVariantes(topoGuide.variantes);
   }

   private void fillImagesGridView(final TopoGuide topoGuide) {
      try {
         List<Bitmap> bitmaps = new ImageRepository(this).findByTopoId(topoGuide.id);
         GridView imagesGrid = (GridView) findViewById(R.id.myGrid);
         imagesGrid.setAdapter(new ImagesGridAdapter(this, R.layout.itineraire_grid_item, bitmaps));
         imagesGrid.setOnItemClickListener(imageGridClickListener(topoGuide));
      } catch (RepositoryException e) {
         // media not readable
      }
   }

   private OnItemClickListener imageGridClickListener(final TopoGuide topoGuide) {
      return new OnItemClickListener() {
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            startActivity(imageFullScreenIntent(topoGuide, position));
         }

         private Intent imageFullScreenIntent(final TopoGuide topoGuide, int position) {
            Intent intent = new Intent(ItineraireTab.this, ImageFullScreenActivity.class);
            intent.putExtra(getString(R.string.extra_topo_id), topoGuide.id);
            intent.putExtra(getString(R.string.extra_img_id), position);
            return intent;
         }
      };
   }

   private void fillItineraireInformations(Itineraire itineraire) {
      TextView description = (TextView) findViewById(R.id.itineraire_description);
      description.setText(itineraire.description);
   }

   private void fillVariantes(List<Itineraire> variantes) {
      ViewGroup list = (ViewGroup) findViewById(R.id.itineraire_variantes);
      LayoutInflater inflater = LayoutInflater.from(this);
      for (Itineraire variante : variantes) {
         View view = inflater.inflate(R.layout.itineraire_list_item, null);
         setText(view, R.id.variante_voie, variante.voie);
         setText(view, R.id.variante_denivele, variante.denivele);
         setText(view, R.id.variante_difficulte_ski, variante.difficulteSki);
         setText(view, R.id.variante_orientation, variante.orientation);
         setText(view, R.id.variante_description, variante.description);
         list.addView(view);
      }
   }

   private void setText(View parentView, int viewId, Object text) {
      TextView view = (TextView) parentView.findViewById(viewId);
      view.setText(String.valueOf(text));
   }
}
