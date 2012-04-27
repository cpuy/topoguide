package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Depart.UNKNOWN_DEPART;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Depart;

public class DepartRepository {

   public static final String TABLE = "depart";

   public static final String ID = "_id";
   public static final String NOM = "nom";
   public static final String ACCES = "acces";
   public static final String ALTITUDE = "altitude";

   private static String[] ALL_COLUMNS = new String[] { ID, NOM, ACCES, ALTITUDE };

   private final SQLiteDatabase database;

   public DepartRepository(SQLiteDatabase database) {
      this.database = database;
   }

   public Depart create(Depart depart) {
      Depart d = depart.clone();
      d.id = database.insert(TABLE, null, getInsertValues(d));
      return d;
   }

   private ContentValues getInsertValues(Depart depart) {
      ContentValues insertValues = new ContentValues();
      insertValues.put(NOM, depart.nom);
      insertValues.put(ACCES, depart.acces);
      insertValues.put(ALTITUDE, depart.altitude);
      return insertValues;
   }

   protected void deleteAll() {
      database.delete(TABLE, null, null);
   }

   public Depart findById(long departId) {
      Cursor c = database.query(TABLE, ALL_COLUMNS, ID + " = " + departId, null, null, null, null);
      return cursorToDepart(c);
   }

   private Depart cursorToDepart(Cursor cursor) {
      Depart depart = UNKNOWN_DEPART;
      if (cursor.moveToFirst()) {
         int i = 0;
         depart = new Depart();
         depart.id = cursor.getLong(i++);
         depart.nom = cursor.getString(i++);
         depart.acces = cursor.getString(i++);
         depart.altitude = cursor.getInt(i++);
      }
      cursor.close();
      return depart;
   }
}
