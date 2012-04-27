package fr.colin.topoguide.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jsoup.Jsoup;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.database.table.TopoGuideTable;
import fr.colin.topoguide.html.SkitourPageParser;
import fr.colin.topoguide.model.unknown.UnknownTopoGuide;

public class TopoGuide implements Parcelable, Model {
   public static final TopoGuide UNKNOWN_TOPOGUIDE = new UnknownTopoGuide();
   
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
   
// TODO equals des objets et listes
   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof TopoGuide) {
         final TopoGuide other = (TopoGuide) obj;
         return new EqualsBuilder()
            .append(id, other.id)
            .append(nom, other.nom)
            .append(orientation, other.orientation)
            .append(numero, other.numero)
            .append(remarques, other.remarques)
            .append(access, other.access)
            .append(type, other.type)
            .isEquals();
      } else {
         return false;
      }
   }
   
   
   @Override
   public int hashCode() {
      return new HashCodeBuilder(17, 37)
         .append(id)
         .append(nom)
         .append(orientation)
         .append(numero)
         .append(remarques)
         .append(access)
         .append(type)
         .toHashCode();
   }
   
   
   // TODO clone des objets et listes
   public TopoGuide clone() {
      TopoGuide topo = new TopoGuide();
      topo.access = access;
      topo.id = id;
      topo.imageUrls = imageUrls;
      topo.itineraire = itineraire;
      topo.nom = nom;
      topo.numero = numero;
      topo.orientation = orientation;
      topo.remarques = remarques;
      topo.sommet = sommet;
      topo.type = type;
      topo.variantes = variantes;
      topo.depart = depart;
      return topo;
   }
   
   public boolean isUnknown() {
      return false;
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
      ContentValues valeurs = new ContentValues();
      
      if (sommet != null) {
//         sommet = sommet.save(db);
         valeurs.put(TopoGuideTable.SOMMET, sommet.id);
      }
      valeurs.put(TopoGuideTable.NOM, this.nom);
      valeurs.put(TopoGuideTable.ACCES, this.access);
      valeurs.put(TopoGuideTable.ORIENTATION, this.orientation);
      valeurs.put(TopoGuideTable.NUMERO, this.numero);
      valeurs.put(TopoGuideTable.REMARQUES, this.remarques);
      long topoId = db.insert(TopoGuideTable.TABLE, null, valeurs);
      
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

   @Override
   public void setId(long id) {
      this.id = id;
   }
}
