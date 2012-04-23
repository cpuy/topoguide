package fr.colin.topoguide.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.html.SkitourPageParser;

public class TopoGuide implements Parcelable {
   public static final String TABLE_TOPOGUIDE = "topoguide";
   public static final String TOPOGUIDE_ID = "_id";
   public static final String TOPOGUIDE_NOM = "nom";
   public static final String TOPOGUIDE_ACCES = "access";
   public static final String TOPOGUIDE_ORIENTATION = "orientation";
   public static final String TOPOGUIDE_NUMERO = "numero";
   public static final String TOPOGUIDE_REMARQUES = "remarques";
   public static final String TOPOGUIDE_SOMMET = "sommet";
   
   /**
    * TODO clean tous ces champs nons utilisés
    */
   public long id;
   public String nom;
   public String orientation;
   public String numero;
   public String remarques;
   public Sommet sommet;
   public Depart depart;
   public List<String> imageUrls;

   // TODO : a mettre dans depart
   public String access;
   
   // TODO
   public String type;
   
   // TODO juste une liste d'itineraire
   public Itineraire itineraire;
   public List<Itineraire> variantes;
   
   
   // public String cartes; TODO

   public TopoGuide() {

   }
   
   public static TopoGuide fromUrl(String url) throws IOException {
      return new SkitourPageParser(Jsoup.connect(url).get()).parsePage();
   }

   public static TopoGuide fromId(String id) throws IOException {
      TopoGuide topo = new SkitourPageParser(Jsoup.connect("http://www.skitour.fr/topos/," + id + ".html").get()).parsePage();
      topo.numero = id;
      return topo;
   }

   /**
    * DB 
    */
   public long save(SQLiteDatabase db) {
      sommet = sommet.save(db);
      
      ContentValues valeurs = new ContentValues();
      valeurs.put(TOPOGUIDE_SOMMET, sommet.id);
      valeurs.put(TOPOGUIDE_NOM, this.nom);
      valeurs.put(TOPOGUIDE_ACCES, this.access);
      valeurs.put(TOPOGUIDE_ORIENTATION, this.orientation);
      valeurs.put(TOPOGUIDE_NUMERO, this.numero);
      long topoId = db.insert(TABLE_TOPOGUIDE, null, valeurs);
      
      if (variantes != null) {
         for (Itineraire itineraire : variantes) {
            itineraire.topoId = topoId;
            itineraire.save(db);
         }
      }
      return topoId; 
   }
   
   /**
    * Parcelable
    */
   private TopoGuide(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<TopoGuide> CREATOR = new Parcelable.Creator<TopoGuide>() {
      public TopoGuide createFromParcel(Parcel in) {
         return new TopoGuide(in);
      }

      public TopoGuide[] newArray(int size) {
         return new TopoGuide[size];
      }
   };

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(id);
      dest.writeString(nom);
      dest.writeString(access);
      dest.writeString(remarques);
      dest.writeString(orientation);
      dest.writeString(numero);
      dest.writeParcelable(sommet, flags);
      dest.writeParcelable(depart, flags);
      dest.writeStringList(imageUrls);
      dest.writeTypedList(variantes);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      access = in.readString();
      remarques = in.readString();
      orientation = in.readString();
      numero = in.readString();
      sommet = in.readParcelable(Sommet.class.getClassLoader());
      depart = in.readParcelable(Depart.class.getClassLoader());
      
      if (imageUrls == null) {
         imageUrls = new ArrayList<String>();
      }
      in.readStringList(imageUrls);
      
      if (variantes == null) {
         variantes = new ArrayList<Itineraire>();
      }
      in.readTypedList(variantes, Itineraire.CREATOR);
   }
}
