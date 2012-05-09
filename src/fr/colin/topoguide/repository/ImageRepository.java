package fr.colin.topoguide.repository;

import static fr.colin.topoguide.utils.IOUtils.writeInFile;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class ImageRepository {

   private final static String IMAGES_BASE_FOLDER = "images";
   private static final String IMG_PREFIX = "img_";

   private File applicationBaseFolder;

   public ImageRepository(Context context) {
      applicationBaseFolder = context.getExternalFilesDir(null);
   }

   /** */
   public Bitmap get(long topoId, long imageId) throws RepositoryException {
      checkMediaReadable();
      return BitmapFactory.decodeFile(image(topoId, imageId).getAbsolutePath());
   }

   /** */
   public void create(long topoId, long imageId, byte[] data) throws RepositoryException {
      checkMediaWritable();
      createTopoImageFolderIfNotExists(topoId);
      createImage(topoId, imageId, data);
   }

   private void createTopoImageFolderIfNotExists(long topoId) {
      File folderTopo = getTopoImageFolder(topoId);
      if (!folderTopo.exists()) {
         folderTopo.mkdirs();
      }
   }

   private void createImage(long topoId, long imageId, byte[] data) throws RepositoryException {
      try {
         writeInFile(data, image(topoId, imageId));
      } catch (IOException e) {
         throw new RepositoryException(format("Unable to create image %d  for topo %d ", imageId, topoId), e);
      }
   }

   /** */
   public List<Bitmap> findByTopoId(long topoId) throws RepositoryException {
      checkMediaReadable();
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

   private void checkMediaReadable() throws RepositoryException {
      String state = Environment.getExternalStorageState();
      if (!Environment.MEDIA_MOUNTED.equals(state) && !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
         throw new RepositoryException("Media not readable");
      }
   }

   private void checkMediaWritable() throws RepositoryException {
      String state = Environment.getExternalStorageState();
      if (!Environment.MEDIA_MOUNTED.equals(state)) {
         throw new RepositoryException("Media not mounted");
      }
   }
}
