import java.util.*;

public class Addresses {                         // 64-bitni adresni prostor -> svaka adresa se pretdstavlja sa 64 bita sto znaci da ukupno imamo
                                                 // 2^64 razlicitih kombinacija tj. 2^64 razlicitih adresa.
    public static final Map<Long, Byte> ADDRESSES = new HashMap<>();     // ADRESA - SADRZAJ MEMORIJSKE ADRESE

    public static void print() {
        ADDRESSES.forEach((address, value)-> System.out.println("["+address+"] = "+value));
    }

    public static Byte getValue(Long address){
        if (ADDRESSES.containsKey(address) == false)
            ADDRESSES.put(address, (byte)0);
        return ADDRESSES.get(address);
    }

    public static void setValue(Long address, Byte value){
        if (ADDRESSES.containsKey(address))
            ADDRESSES.replace(address, value);
        else
            ADDRESSES.put(address, value);
    }

}


