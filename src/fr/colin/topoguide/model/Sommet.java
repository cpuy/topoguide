package fr.colin.topoguide.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.unknown.UnknownSommet;

public class Sommet implements Parcelable {

   public static final Sommet UNKNOWN_SOMMET = new UnknownSommet();
   
   public long id;
   public String nom;
   public String massif;
   public String secteur;
   public int altitude;

   public Sommet() {
 
   }

   public boolean isUnknown() {
      return false;
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof Sommet) {
         final Sommet other = (Sommet) obj;
         return new EqualsBuilder()
            .append(id, other.id)
            .append(nom, other.nom)
            .append(massif, other.massif)
            .append(secteur, other.secteur)
            .append(altitude, other.altitude)
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
         .append(massif)
         .append(secteur)
         .append(altitude)
         .toHashCode();
   }

   /**
    * Parcelable
    */
   private Sommet(Parcel in) {
      readFromParcel(in);
   }

   public static final Parcelable.Creator<Sommet> CREATOR = new Parcelable.Creator<Sommet>() {
      public Sommet createFromParcel(Parcel in) {
         return new Sommet(in);
      }

      public Sommet[] newArray(int size) {
         return new Sommet[size];
      }
   };

   public int describeContents() {
      return 0;
   }

   public void writeToParcel(Parcel dest, int flags) {
      dest.writeLong(id);
      dest.writeString(nom);
      dest.writeString(massif);
      dest.writeString(secteur);
      dest.writeInt(altitude);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      massif = in.readString();
      secteur = in.readString();
      altitude = in.readInt();
   }
}
