package fr.colin.topoguide.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import fr.colin.topoguide.model.TopoGuide;
import fr.colin.topoguide.utils.Downloader;

public class ImageRepository {

   private File applicationBaseFolder;

   public ImageRepository(Context context) {
      applicationBaseFolder = context.getExternalFilesDir(null);
   }
   
   public void addImagesForTopo(TopoGuide topo) throws IOException {
      createTopoImagesFolderIfNotExists(topo.numero);
      int compteur = 1;
      for (String url : topo.imageUrls) {
         Log.d("FILE", url);
         Log.d("FOLDER", new File(getTopoImageFolder(topo.numero), "img_" + compteur++).getAbsolutePath());
         Downloader.DownloadFile(url, new File(getTopoImageFolder(topo.numero), "img_" + compteur++));
      }
   }
   
   public void createTopoImagesFolderIfNotExists(long numeroTopo) {
      File folder = getTopoImageFolder(numeroTopo);
      if (!folder.exists()) {
         folder.mkdirs();
      }
   }
   
   public List<Bitmap> findImagesForTopo(TopoGuide topo) {
      ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
      File topoImageDireectory = getTopoImageFolder(topo.numero);
      if (topoImageDireectory.exists()) {
      for (File image : topoImageDireectory.listFiles()) {
         bitmaps.add(BitmapFactory.decodeFile(image.getAbsolutePath()));
      }
      }
      else
      {
         Log.d("REPO", topoImageDireectory.getAbsolutePath() + " existe pas");
      }
      return bitmaps;
   }
   
   private File getTopoImageFolder(long numeroTopo) {
      return new File(applicationBaseFolder, "skitour_" + numeroTopo);
   }
}
