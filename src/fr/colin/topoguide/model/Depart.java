package fr.colin.topoguide.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.os.Parcel;
import android.os.Parcelable;
import fr.colin.topoguide.model.unknown.UnknownDepart;

public class Depart implements Parcelable {

   public static final Depart UNKNOWN_DEPART = new UnknownDepart();
   
   public long id;
   public String nom;
   public String acces;
   public int altitude;
   
   public Depart() {

   }
   
   public boolean isUnknown() {
      return false;
   }
   
   @Override
   public boolean equals(final Object obj) {
      if (obj instanceof Depart) {
         final Depart other = (Depart) obj;
         return new EqualsBuilder()
            .append(id, other.id)
            .append(nom, other.nom)
            .append(acces, other.acces)
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
         .append(acces)
         .append(altitude)
         .toHashCode();
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
      dest.writeString(acces);
      dest.writeInt(altitude);
   }

   private void readFromParcel(Parcel in) {
      id = in.readLong();
      nom = in.readString();
      acces = in.readString();
      altitude = in.readInt();
   }
}
