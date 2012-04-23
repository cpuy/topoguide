package fr.colin.topoguide.utils;

import java.io.BufferedReader;
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
}
