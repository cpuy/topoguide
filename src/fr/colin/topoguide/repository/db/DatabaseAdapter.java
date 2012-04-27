package fr.colin.topoguide.repository.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseAdapter {

   private final Context context;
   
   protected SQLiteDatabase database;
   
   public DatabaseAdapter(Context context) {
      this.context = context;
   }
   
   public boolean isOpen() {
      return database != null && database.isOpen();
   }
   
   public void open() {
      if (!isOpen()) {
         TopoGuideOpenHelper topoGuideOpenHelper = new TopoGuideOpenHelper(context);
         database = topoGuideOpenHelper.getWritableDatabase();
      }
   }
   
   public void close() {
      if (isOpen()) {
         database.close();
      }
   }
   
   protected String[] toStringArray(Object o) {
      return new String[] { String.valueOf(o) };
   }
}
