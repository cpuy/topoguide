package fr.colin.topoguide.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Depart implements Parcelable {

   public static final String TABLE_DEPART = "depart";
   public static final String NOM = "nom";
   public static final String ACCES = "acces";
   public static final String ALTITUDE = "altitude";
   
   public long id;
   public String nom;
   public String access;
   public int altitude;
   
   public Depart() {

   }
   
   /**
    * DB
    */
   public Depart save(SQLiteDatabase db) {
      ContentValues valeurs = new ContentValues();
      valeurs.put(NOM, this.nom);
      valeurs.put(ACCES, this.access);
      valeurs.put(ALTITUDE, this.altitude);
      this.id = db.insert(TABLE_DEPART, null, valeurs);
      return this;
   }
   
   /**
    * Parcelable
    */
   private Depart(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<Depart> CREATOR = new Parcelable.Creator<Depart>() {
      public Depart createFromParcel(Parcel in) {
         return new Depart(in);
      }

      public Depart[] newArray(int size) {
         return new Depart[size];
      }
   };
   
   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int arg1) {
      dest.writeLong(id);
      dest.writeString(nom);
      dest.writeString(access);
      dest.writeInt(altitude);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      access = in.readString();
      altitude = in.readInt();
   }
}
