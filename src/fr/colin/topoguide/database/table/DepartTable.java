package fr.colin.topoguide.database.table;

import static fr.colin.topoguide.model.Depart.UNKNOWN_DEPART;
import android.content.ContentValues;
import android.database.Cursor;
import fr.colin.topoguide.model.Depart;

public class DepartTable extends Table<Depart> {

   public static final String TABLE = "depart";

   public static final String NOM = "nom";
   public static final String ACCES = "acces";
   public static final String ALTITUDE = "altitude";

   private static final String[] ALL_COLUMNS = new String[] { ID, NOM, ACCES, ALTITUDE };

   private static final String FIND_SAME_WHERE_CLAUSE = NOM + " = ? AND " + ACCES + " = ? AND " + ALTITUDE
         + " = ?";

   @Override
   protected ContentValues getInsertValues(Depart depart) {
      ContentValues insertValues = new ContentValues();
      insertValues.put(NOM, depart.nom);
      insertValues.put(ACCES, depart.acces);
      insertValues.put(ALTITUDE, depart.altitude);
      return insertValues;
   }

   @Override
   protected Depart cursorToModel(Cursor cursor) {
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

   @Override
   protected String getTableName() {
      return TABLE;
   }

   @Override
   protected String[] getAllColumns() {
      return ALL_COLUMNS;
   }

   public Depart get(Depart depart) {
      Cursor cursor = database.query(TABLE, ALL_COLUMNS, FIND_SAME_WHERE_CLAUSE,
            toStringArray(depart.nom, depart.acces, depart.altitude), null, null, null);
      return cursorToModel(cursor);
   }
}
