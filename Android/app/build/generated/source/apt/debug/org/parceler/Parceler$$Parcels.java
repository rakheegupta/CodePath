
package org.parceler;

import java.util.HashMap;
import java.util.Map;
import design.semicolon.modarenta.models.Item;
import design.semicolon.modarenta.models.Item$$Parcelable;

@Generated(value = "org.parceler.ParcelAnnotationProcessor", date = "2016-03-06T02:40-0800")
@SuppressWarnings({
    "unchecked",
    "deprecation"
})
public class Parceler$$Parcels
    implements Repository<org.parceler.Parcels.ParcelableFactory>
{

    private final Map<Class, org.parceler.Parcels.ParcelableFactory> map$$0 = new HashMap<Class, org.parceler.Parcels.ParcelableFactory>();

    public Parceler$$Parcels() {
        map$$0 .put(Item.class, new Parceler$$Parcels.Item$$Parcelable$$0());
    }

    public Map<Class, org.parceler.Parcels.ParcelableFactory> get() {
        return map$$0;
    }

    private final static class Item$$Parcelable$$0
        implements org.parceler.Parcels.ParcelableFactory<Item>
    {


        @Override
        public Item$$Parcelable buildParcelable(Item input) {
            return new Item$$Parcelable(input);
        }

    }

}
