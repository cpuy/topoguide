package fr.colin.topoguide.database.table;

import static fr.colin.topoguide.model.Depart.UNKNOWN_DEPART;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import fr.colin.topoguide.model.Depart;

public class DepartTable extends Table<Depart> {

   public static final String TABLE = "depart";

   public static final String NOM = "nom";
   public static final String ACCES = "acces";
   public static final String ALTITUDE = "altitude";

   public DepartTable(SQLiteDatabase database) {
      this.database = database;
   }

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
      return new String[] { ID, NOM, ACCES, ALTITUDE };
   }

}
