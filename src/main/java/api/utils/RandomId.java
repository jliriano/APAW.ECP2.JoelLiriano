package api.utils;

import java.util.UUID;

public class RandomId {

    private RandomId() {
        throw new IllegalStateException("Utility class");
    }

    public static String string(){
        return UUID.randomUUID().toString();
    }

}