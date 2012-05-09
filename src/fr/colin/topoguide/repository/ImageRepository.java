package fr.colin.topoguide.repository;

import static fr.colin.topoguide.utils.IOUtils.writeInFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRepository {

   private final static String IMAGES_BASE_FOLDER = "images";
   private static final String IMG_PREFIX = "img_";

   private File applicationBaseFolder;

   public ImageRepository(Context context) {
      applicationBaseFolder = context.getExternalFilesDir(null);
   }

   /** */
   public Bitmap get(long topoId, long imageId) {
      return BitmapFactory.decodeFile(image(topoId, imageId).getAbsolutePath());
   }

   /** */
   public void create(long topoId, long imageId, byte[] data) throws RepositoryException {
      createTopoImageFolderIfNotExists(topoId);
      try {
         writeInFile(data, image(topoId, imageId));
      } catch (IOException e) {
         throw new RepositoryException("Unable to create image " + imageId + " for topo " + topoId, e);
      }
   }

   private void createTopoImageFolderIfNotExists(long topoId) {
      File folderTopo = getTopoImageFolder(topoId);
      if (!folderTopo.exists()) {
         folderTopo.mkdirs();
      }
   }

   /** */
   public List<Bitmap> findByTopoId(long topoId) {
      ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
      for (File image : listImagesOrderedByImageId(topoId)) {
         bitmaps.add(BitmapFactory.decodeFile(image.getAbsolutePath()));
      }
      return bitmaps;
   }

   private File[] listImagesOrderedByImageId(long topoId) {
      File topoImageDirectory = getTopoImageFolder(topoId);
      if (topoImageDirectory.exists()) {
         return listFilesOrdered(topoImageDirectory);
      }
      return new File[0];
   }

   private File[] listFilesOrdered(File folder) {
      File[] files = folder.listFiles();
      Arrays.sort(files);
      return files;
   }

   private File getTopoImageFolder(long topoId) {
      return new File(getBaseFolder(), String.valueOf(topoId));
   }

   protected File getBaseFolder() {
      return new File(applicationBaseFolder, IMAGES_BASE_FOLDER);
   }

   protected File image(long topoId, long imageId) {
      return new File(getTopoImageFolder(topoId), IMG_PREFIX + imageId);
   }
}
