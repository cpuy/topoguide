package fr.colin.topoguide.repository.db;

import static fr.colin.topoguide.model.Itineraire.TABLE_ITINERAIRE;
import static fr.colin.topoguide.model.TopoGuide.TABLE_TOPOGUIDE;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fr.colin.topoguide.model.Sommet;
import fr.colin.topoguide.utils.IOUtils;
import fr.colin.topoguide.views.R;

public class TopoGuideOpenHelper extends SQLiteOpenHelper {

   private static final String BASE_NAME = "topoguide.db";
   private static final int CURRENT_BASE_VERSION = 2;
   
   private final Context context;

   public TopoGuideOpenHelper(Context context) {
      super(context, BASE_NAME, null, CURRENT_BASE_VERSION);
      this.context = context;
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      db.execSQL(getDbRequestFromRawResources(R.raw.create_table_topoguide));
      db.execSQL(getDbRequestFromRawResources(R.raw.create_table_itineraire));
      db.execSQL(getDbRequestFromRawResources(R.raw.create_table_sommet));
      db.execSQL(getDbRequestFromRawResources(R.raw.create_table_depart));
   }

   // TODO supprimer le bout de code
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE " + TABLE_ITINERAIRE + ";");
      db.execSQL("DROP TABLE " + TABLE_TOPOGUIDE + ";");
      db.execSQL("DROP TABLE " + Sommet.TABLE_SOMMET + ";");
      onCreate(db);
   }

   private String getDbRequestFromRawResources(int id) {
      InputStream openRawResource = context.getResources().openRawResource(id);
      return IOUtils.inputStreamToString(openRawResource);
   }
}
