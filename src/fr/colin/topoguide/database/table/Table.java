package fr.colin.topoguide.database.table;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Table<T> {

   public static String ID = "_id";
   
   protected SQLiteDatabase database;

   protected abstract String getTableName();
   protected abstract ContentValues getInsertValues(T model);
   protected abstract String[] getAllColumns();
   protected abstract T cursorToModel(Cursor cursor);
   
   public long add(T table) {
      return database.insert(getTableName(), null, getInsertValues(table));
   }

   public T get(long id) {
      Cursor cursor = database.query(getTableName(), getAllColumns(), ID + " = " + id, null, null, null, null);
      return cursorToModel(cursor);
   }
   
   public void empty() {
      database.delete(getTableName(), null, null);
   }
   
   public void setDatabase(SQLiteDatabase database) {
      this.database = database;
   }
   
   protected String[] toStringArray(Object object) {
      return new String[] { String.valueOf(object) };
   }
   
   protected String[] toStringArray(Object... objects) {
      List<String> strings = new ArrayList<String>();
      for (Object o : objects) {
         strings.add(String.valueOf(o));
      }
      return strings.toArray(new String[strings.size()]);
   }
}
