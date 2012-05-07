package fr.colin.topoguide.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class IOUtils {

   public static String inputStreamToString(InputStream inputStream) {
      InputStreamReader streamReader = new InputStreamReader(inputStream);
      StringWriter writer = new StringWriter();
      BufferedReader buffer = new BufferedReader(streamReader);
      String line = "";
      try {
         while (null != (line = buffer.readLine())) {
            writer.write(line);
         }
      } catch (IOException e) {
         throw new RuntimeException("Error when converting inpustream to string", e);
      }
      return writer.toString();
   }
   
   public static void writeInFile(byte[] b, File file) throws IOException {
      FileOutputStream o = new FileOutputStream(file);
      o.write(b);
      o.flush();
      o.close();
   }

   public static byte[] inputStreamToBytes(InputStream is) throws IOException
   {
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      int nRead;
      byte[] data = new byte[16384];
      while ((nRead = is.read(data, 0, data.length)) != -1) {
        buffer.write(data, 0, nRead);
      }
      buffer.flush();
      is.close();
      return buffer.toByteArray();
   }
   
   public static void writeInFile(InputStream in, File file) {
      InputStreamReader streamReader = new InputStreamReader(in);
      FileWriter writer;
      try {
         writer = new FileWriter(file);
         BufferedReader buffer = new BufferedReader(streamReader);
         String line = "";
         while (null != (line = buffer.readLine())) {
            writer.write(line);
         }
      } catch (IOException e) {
         throw new RuntimeException("Error when writing file " + file.getAbsolutePath(), e);
      }
   }
}
