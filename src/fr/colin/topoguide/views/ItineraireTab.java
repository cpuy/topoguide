package fr.colin.topoguide.views;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import fr.colin.topoguide.model.Itineraire;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;
import fr.colin.topoguide.repository.RepositoryException;
import fr.colin.topoguide.views.adapter.ImagesGridAdapter;
import fr.colin.topoguide.views.adapter.VarianteListAdapter;

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
      ListView list = (ListView) findViewById(R.id.itineraire_variantes);
      TextView header = new TextView(this);
      header.setText("Variantes");
      list.addHeaderView(header);
      list.setAdapter(new VarianteListAdapter(this, variantes));
   }
}
