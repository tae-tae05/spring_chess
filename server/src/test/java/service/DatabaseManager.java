package service;

import org.junit.jupiter.api.Assertions;

import java.util.Collection;
import java.util.HashSet;

public class DatabaseManager {
    public static <T> void assertCollectionsEquals(Collection<T> first, Collection<T> second, String message) {
        Assertions.assertEquals(new HashSet<>(first), new HashSet<>(second), message);
        Assertions.assertEquals(first.size(), second.size(), "Collections not the same size");
    }
}
