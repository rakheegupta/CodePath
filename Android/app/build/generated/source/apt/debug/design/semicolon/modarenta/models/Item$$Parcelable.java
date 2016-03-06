
package design.semicolon.modarenta.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.maps.model.LatLng;
import org.parceler.Generated;
import org.parceler.ParcelWrapper;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-03-06T02:40-0800")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Item$$Parcelable
    implements Parcelable, ParcelWrapper<design.semicolon.modarenta.models.Item>
{

    private design.semicolon.modarenta.models.Item item$$0;
    @SuppressWarnings("UnusedDeclaration")
    public final static Item$$Parcelable.Creator$$0 CREATOR = new Item$$Parcelable.Creator$$0();

    public Item$$Parcelable(android.os.Parcel parcel$$0) {
        design.semicolon.modarenta.models.Item item$$2;
        if (parcel$$0 .readInt() == -1) {
            item$$2 = null;
        } else {
            item$$2 = readdesign_semicolon_modarenta_models_Item(parcel$$0);
        }
        item$$0 = item$$2;
    }

    public Item$$Parcelable(design.semicolon.modarenta.models.Item item$$4) {
        item$$0 = item$$4;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel$$1, int flags) {
        if (item$$0 == null) {
            parcel$$1 .writeInt(-1);
        } else {
            parcel$$1 .writeInt(1);
            writedesign_semicolon_modarenta_models_Item(item$$0, parcel$$1, flags);
        }
    }

    private design.semicolon.modarenta.models.Item readdesign_semicolon_modarenta_models_Item(android.os.Parcel parcel$$2) {
        design.semicolon.modarenta.models.Item item$$1;
        item$$1 = new design.semicolon.modarenta.models.Item();
        item$$1 .mPoint = ((LatLng) parcel$$2 .readParcelable(Item$$Parcelable.class.getClassLoader()));
        item$$1 .mID = parcel$$2 .readString();
        item$$1 .mLocation = parcel$$2 .readString();
        item$$1 .mPrice = parcel$$2 .readString();
        item$$1 .mDesc = parcel$$2 .readString();
        item$$1 .mName = parcel$$2 .readString();
        int int$$0 = parcel$$2 .readInt();
        java.lang.Float[] float$$0;
        if (int$$0 < 0) {
            float$$0 = null;
        } else {
            float$$0 = new java.lang.Float[int$$0 ] ;
            for (int int$$1 = 0; (int$$1 <int$$0); int$$1 ++) {
                int int$$2 = parcel$$2 .readInt();
                java.lang.Float float$$1;
                if (int$$2 < 0) {
                    float$$1 = null;
                } else {
                    float$$1 = parcel$$2 .readFloat();
                }
                float$$0 [int$$1 ] = float$$1;
            }
        }
        item$$1 .mCoordinates = float$$0;
        item$$1 .mURL = parcel$$2 .readString();
        return item$$1;
    }

    private void writedesign_semicolon_modarenta_models_Item(design.semicolon.modarenta.models.Item item$$3, android.os.Parcel parcel$$3, int flags$$0) {
        parcel$$3 .writeParcelable(item$$3 .mPoint, flags$$0);
        parcel$$3 .writeString(item$$3 .mID);
        parcel$$3 .writeString(item$$3 .mLocation);
        parcel$$3 .writeString(item$$3 .mPrice);
        parcel$$3 .writeString(item$$3 .mDesc);
        parcel$$3 .writeString(item$$3 .mName);
        if (item$$3 .mCoordinates == null) {
            parcel$$3 .writeInt(-1);
        } else {
            parcel$$3 .writeInt(item$$3 .mCoordinates.length);
            for (java.lang.Float float$$2 : item$$3 .mCoordinates) {
                if (float$$2 == null) {
                    parcel$$3 .writeInt(-1);
                } else {
                    parcel$$3 .writeInt(1);
                    parcel$$3 .writeFloat(float$$2);
                }
            }
        }
        parcel$$3 .writeString(item$$3 .mURL);
    }

    @Override
    public int describeContents() {
        return  0;
    }

    @Override
    public design.semicolon.modarenta.models.Item getParcel() {
        return item$$0;
    }

    public final static class Creator$$0
        implements Creator<Item$$Parcelable>
    {


        @Override
        public Item$$Parcelable createFromParcel(android.os.Parcel parcel$$4) {
            return new Item$$Parcelable(parcel$$4);
        }

        @Override
        public Item$$Parcelable[] newArray(int size) {
            return new Item$$Parcelable[size] ;
        }

    }

}
