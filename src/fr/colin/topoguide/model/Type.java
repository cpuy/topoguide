package fr.colin.topoguide.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum Type implements Parcelable {
   ALLER_RETOUR("Aller/Retour"), BOUCLE("Boucle"), TRAVERSEE("Traversée"), INCONNU("Non renseigné");
   
   private final String value;

   private Type(String value) {
      this.value = value;
   }
   
   public String value() {
      return value;
   }
   
   public static Type fromValue(String value) {
      for (Type type : Type.values()) {
         if (type.value().equalsIgnoreCase(value)) {
            return type;
         }
      }
      return INCONNU;
   }
   
   @Override
   public int describeContents() {
       return 0;
   }

   @Override
   public void writeToParcel(final Parcel dest, final int flags) {
       dest.writeInt(ordinal());
   }

   public static final Creator<Type> CREATOR = new Creator<Type>() {
       @Override
       public Type createFromParcel(final Parcel source) {
           return Type.values()[source.readInt()];
       }

       @Override
       public Type[] newArray(final int size) {
           return new Type[size];
       }
   };
}