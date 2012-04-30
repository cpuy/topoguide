package fr.colin.topoguide.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Downloader {

   public static Document downloadDocument(String url) throws IOException {
      return Jsoup.connect(url).get();
   }

   /**
    * TODO Fermer la connection et inpustream
    */
   public static Bitmap downloadImage(String url) throws IOException {
      URL urlImage = new URL(url);
      return BitmapFactory.decodeStream(((HttpURLConnection) urlImage.openConnection()).getInputStream());
   }

   public static void DownloadFile(String imageURL, File file) throws IOException {
      URL url = new URL(imageURL);

      URLConnection ucon = url.openConnection();
      InputStream is = ucon.getInputStream();
      BufferedInputStream bis = new BufferedInputStream(is);
      ByteArrayBuffer baf = new ByteArrayBuffer(50);
      int current = 0;
      while ((current = bis.read()) != -1)
         baf.append((byte) current);
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(baf.toByteArray());
      fos.close();
   }
}
