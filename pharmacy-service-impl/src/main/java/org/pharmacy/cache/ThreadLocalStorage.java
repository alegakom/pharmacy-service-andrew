package org.pharmacy.cache;

import java.util.UUID;

public final class ThreadLocalStorage {

    private static final ThreadLocal<UUID> USER_ID = new ThreadLocal<>();


    public static void setUserId(UUID userId) {
        if (userId != null) {
            USER_ID.set(userId);
        }
    }

    public static UUID getUserId() {
        return USER_ID.get();
    }
}
