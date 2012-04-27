package fr.colin.topoguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseAdapter {

   protected final Context context;
   
   protected static SQLiteDatabase database;
   
   public DatabaseAdapter(Context context) {
      this.context = context;
   }
   
   public boolean isOpen() {
      return database != null && database.isOpen();
   }
   
   public void open() {
      if (!isOpen()) {
         DatabaseOpenHelper topoGuideOpenHelper = new DatabaseOpenHelper(context);
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
