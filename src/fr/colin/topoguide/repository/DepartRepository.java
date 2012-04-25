package fr.colin.topoguide.repository;

import static fr.colin.topoguide.model.Depart.UNKNOWN_DEPART;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.colin.topoguide.model.Depart;
import fr.colin.topoguide.repository.db.DatabaseAdapter;

public class DepartRepository extends DatabaseAdapter {

   public static final String TABLE = "depart";
   
   public static final String ID = "_id";
   public static final String NOM = "nom";
   public static final String ACCES = "acces";
   public static final String ALTITUDE = "altitude";
   
   private static String[] ALL_COLUMNS = new String[] { ID, NOM, ACCES, ALTITUDE };

   public DepartRepository(Context context) {
      super(context);
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
      if (c.getCount() > 0) {
         return cursorToDepart(c);
      } else {
         return UNKNOWN_DEPART;
      }
   }

   private Depart cursorToDepart(Cursor cursor) {
      Depart depart = new Depart();
      int i = 0;
      cursor.moveToFirst();
      depart.id = cursor.getLong(i++);
      depart.nom = cursor.getString(i++);
      depart.acces = cursor.getString(i++);
      depart.altitude = cursor.getInt(i++);
      cursor.close();
      return depart;
   }
}
