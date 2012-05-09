package fr.colin.topoguide.views;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;
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

   // TODO : to be finished -> pass image in intent with references ... et
   // recharger dans nouvelle activity
   private void fillImagesGridView(final TopoGuide topoGuide) {
      List<Drawable> asList = Arrays.asList(getResources().getDrawable(R.drawable.sure_test), getResources()
            .getDrawable(R.drawable.sure_test2));
      GridView images = (GridView) findViewById(R.id.myGrid);
      List<Bitmap> findByTopoId = new ImageRepository(this).findByTopoId(topoGuide.id);

      images.setAdapter(new ImagesGridAdapter(this, R.layout.itinerary_grid_item, asList));

      images.setOnItemClickListener(new OnItemClickListener() {
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
