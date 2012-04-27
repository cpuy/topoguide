package fr.colin.topoguide.database.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Model;

public abstract class Table<T extends Model> {

   protected static String ID = "_id";
   
   protected SQLiteDatabase database;

   protected abstract String getTableName();
   protected abstract ContentValues getInsertValues(T model);
   protected abstract String[] getAllColumns();
   protected abstract T cursorToModel(Cursor cursor);
   
   @SuppressWarnings("unchecked")
   public T create(T table) {
      T t = (T) table.clone();
      t.setId(database.insert(getTableName(), null, getInsertValues(t)));
      return t;
   }

   public T findById(long id) {
      Cursor cursor = database.query(getTableName(), getAllColumns(), ID + " = " + id, null, null, null, null);
      return cursorToModel(cursor);
   }
   
   public void deleteAll() {
      database.delete(getTableName(), null, null);
   }
   
   public void setDatabase(SQLiteDatabase database) {
      this.database = database;
   }
   
   protected String[] toStringArray(Object o) {
      return new String[] { String.valueOf(o) };
   }
}
