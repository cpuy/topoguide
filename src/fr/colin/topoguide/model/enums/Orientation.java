package fr.colin.topoguide.model.enums;

import android.os.Parcel;
import android.os.Parcelable;

public enum Orientation implements Parcelable {
   N("Nord"), NE("Nord-Est"), E("Est"), SE("Sud-Est"), S("Sud"), SW("Sud-Ouest"), W("Ouest"), 
   NW("Nord-Ouest"), T("Toutes"), INCONNUE("Non renseignée");
   
   private final String value;

   private Orientation(String value) {
      this.value = value;
   }
   
   public String value() {
      return value;
   }
   
   public static Orientation fromValue(String value) {
      for (Orientation orientation : Orientation.values()) {
         if (orientation.value().equalsIgnoreCase(value)) {
            return orientation;
         }
      }
      return INCONNUE;
   }
   
   @Override
   public int describeContents() {
       return 0;
   }

   @Override
   public void writeToParcel(final Parcel dest, final int flags) {
       dest.writeInt(ordinal());
   }

   public static final Creator<Orientation> CREATOR = new Creator<Orientation>() {
       @Override
       public Orientation createFromParcel(final Parcel source) {
           return Orientation.values()[source.readInt()];
       }

       @Override
       public Orientation[] newArray(final int size) {
           return new Orientation[size];
       }
   };
}