package fr.colin.topoguide.views.task;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static fr.colin.topoguide.model.TopoGuide.UNKNOWN_TOPOGUIDE;
import static fr.colin.topoguide.repository.RemoteTopoGuideRepository.skitour;
import static fr.colin.topoguide.views.DownloadActivity.RESULT_KO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.repository.ImageRepository;
import fr.colin.topoguide.repository.LocalTopoGuideRepository;
import fr.colin.topoguide.repository.RepositoryException;
import fr.colin.topoguide.utils.Downloader;

public class DownloadTopoGuideTask extends AsyncTask<Long, Integer, TopoGuide> {

   private final Activity activity;

   public DownloadTopoGuideTask(Activity activity) {
      this.activity = activity;
   }

   @Override
   protected TopoGuide doInBackground(Long... param) {
      if (param != null && param.length > 0) {
         try {
            return downloadTopo(param[0]);
         } catch (RepositoryException e) {
            // TODO
         }
      }
      return UNKNOWN_TOPOGUIDE;
   }

   private TopoGuide downloadTopo(Long numeroTopo) throws RepositoryException {
      TopoGuide topo = skitour().fetchTopoById(numeroTopo);
      createTopoInLocalRepository(topo);
      List<byte[]> images = downloadImages(topo.imageUrls);
      saveImages(topo, images);
      return topo;
   }

   private void saveImages(TopoGuide topo, List<byte[]> images) throws RepositoryException {
      ImageRepository imageRepository = new ImageRepository(activity);
      for (int i = 0; i < images.size(); i++) {
         imageRepository.create(topo.id, i, images.get(i));
      }
   }

   private void createTopoInLocalRepository(TopoGuide topo) {
      LocalTopoGuideRepository topoGuideRepository = LocalTopoGuideRepository.fromContext(activity);
      topoGuideRepository.open();
      topoGuideRepository.create(topo);
      topoGuideRepository.close();
   }

   private List<byte[]> downloadImages(List<String> imageUrls) {
      List<byte[]> images = new ArrayList<byte[]>();
      for (String url : imageUrls) {
         try {
            images.add(Downloader.DownloadFile(url));
         } catch (IOException e) {
            // TODO
         }
      }
      return images;
   }

   @Override
   protected void onPostExecute(TopoGuide result) {
      super.onPostExecute(result);
      try {
         TopoGuide topo = this.get();
         if (!topo.isUnknown()) {
            setResultOk(topo);
         } else {
            setResultKo();
         }
      } catch (Exception e) {
         setResultKo();
      }
      activity.finish();
   }

   private void setResultKo() {
      Toast.makeText(activity, "Une erreur est apparue durant le téléchargement du topoguide", LENGTH_LONG).show();
      activity.setResult(RESULT_KO);
   }

   private void setResultOk(TopoGuide topo) {
      activity.setResult(RESULT_OK);
      StringBuilder message = new StringBuilder();
      message.append("\"").append(topo.nom).append(" (").append(topo.sommet.massif).append(" )\"")
         .append(" téléchargé");
      Toast.makeText(activity, message.toString(), LENGTH_LONG).show();
   }

   @Override
   protected void onCancelled() {
      activity.setResult(RESULT_CANCELED);
      super.onCancelled();
   }
}
