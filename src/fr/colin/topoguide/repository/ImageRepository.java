package fr.colin.topoguide.repository;

import static fr.colin.topoguide.utils.IOUtils.writeInFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRepository {

   private File applicationBaseFolder;

   public ImageRepository(Context context) {
      applicationBaseFolder = context.getExternalFilesDir(null);
   }
   
   public File create(long topoId, long imageId, byte[] data) throws RepositoryException {
      File folderTopo = createTopoImageFolderIfNotExists(topoId);
      File file = new File(folderTopo, "img_" + imageId);
      try {
         writeInFile(data, file);
      } catch (IOException e) {
         throw new RepositoryException("Unable to create image " + imageId + " for topo " + topoId , e);
      }
      return file;
   }

   private File createTopoImageFolderIfNotExists(long topoId) {
      File folderTopo = getTopoImageFolder(topoId);
      if (!folderTopo.exists()) {
         folderTopo.mkdirs();
      }
      return folderTopo;
   }

   public List<Bitmap> findByTopoId(long topoId) {
      ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
      File topoImageDireectory = getTopoImageFolder(topoId);
      if (topoImageDireectory.exists()) {
         for (File image : topoImageDireectory.listFiles()) {
            bitmaps.add(BitmapFactory.decodeFile(image.getAbsolutePath()));
         }
      }
      return bitmaps;
   }

   private File getTopoImageFolder(long topoId) {
      return new File(getBaseFolder(), String.valueOf(topoId));
   }

   protected File getBaseFolder() {
      return new File(applicationBaseFolder, "images");
   }

   public Bitmap get(long topoId, long imageId) {
      File image = new File(getTopoImageFolder(topoId), "img_" + imageId);
      return BitmapFactory.decodeFile(image.getAbsolutePath());
   }
}
