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
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;
import fr.colin.topoguide.repository.RepositoryException;
import fr.colin.topoguide.views.adapter.ImagesGridAdapter;

public class ItineraireTab extends Activity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.topo_itineraire);

      TopoGuide topoGuide = (TopoGuide) getIntent().getExtras().getParcelable("current_topo");
      fillImagesGridView(topoGuide);
      fillItineraireInformations(topoGuide);
   }

   private void fillImagesGridView(final TopoGuide topoGuide) {
      try {
         List<Bitmap> bitmaps = new ImageRepository(this).findByTopoId(topoGuide.id);
         GridView imagesGrid = (GridView) findViewById(R.id.myGrid);
         imagesGrid.setAdapter(new ImagesGridAdapter(this, R.layout.itinerary_grid_item, bitmaps));
         imagesGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               startActivity(imageFullScreenIntent(topoGuide, position));
            }

            private Intent imageFullScreenIntent(final TopoGuide topoGuide, int position) {
               Intent intent = new Intent(ItineraireTab.this, ImageFullScreenActivity.class);
               intent.putExtra(getString(R.string.extra_topo_id), topoGuide.id);
               intent.putExtra(getString(R.string.extra_img_id), position);
               return intent;
            }
         });
      } catch (RepositoryException e) {
         // media not readable
      }
   }

   // TODO
   private void fillItineraireInformations(TopoGuide topoGuide) {
      if (!topoGuide.variantes.isEmpty()) {
         // ((TextView)
         // findViewById(R.id.topo_itineraire_denivele)).setText(topoGuide.variantes.get(0).denivele);
         // ((TextView)
         // findViewById(R.id.topo_itineraire_description)).setText(topoGuide.variantes.get(0).description);
         // ((TextView)
         // findViewById(R.id.topo_itineraire_difficulte_ski)).setText(topoGuide.variantes.get(0).difficulteSki);
         // ((TextView)
         // findViewById(R.id.topo_itineraire_orientation)).setText(topoGuide.variantes.get(0).orientation);
         // ((TextView)
         // findViewById(R.id.topo_itineraire_voie)).setText(topoGuide.variantes.get(0).voie);
      }
   }
}
