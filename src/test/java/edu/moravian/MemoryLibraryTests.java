package edu.moravian;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class MemoryLibraryTests {

    @Test
    public void testNothing() {
    }

    @Test
    public void addOneCategory() {
        MemoryLibrary memoryLibrary = new MemoryLibrary();
        memoryLibrary.addCategory("C++");
        ArrayList<String> categories = memoryLibrary.getCategories();
        assertEquals(4, categories.size());
    }

    @Test
    public void addThreeCategories() {
        MemoryLibrary memoryLibrary = new MemoryLibrary();
        memoryLibrary.addCategory("C++");
        memoryLibrary.addCategory("JavaScript");
        memoryLibrary.addCategory("C#");
        ArrayList<String> categories = memoryLibrary.getCategories();
        assertEquals(6, categories.size());
    }
}

