package edu.moravian;

import exceptions.CategoryNotValidException;
import exceptions.InternalServerException;
import exceptions.StorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class MemoryLibrary {
    private final ArrayList<String> categories = new ArrayList<>();
    private final HashMap<String, HashMap<String, String>> dictionaries = new HashMap<>();
    private boolean isDictsLoaded = false;
    private String currentDefinition = "";
    private final RedisManager redisDB = new RedisManager();

    public MemoryLibrary() {
        try {
        addCategory("Python");
        addCategory("Java");
        addCategory("SQL");
        loadDictionaries();
        }
        catch(InternalServerException e) {
            throw new InternalServerException("Error creating MemoryLibrary.");
        }
    }

    private void loadDictionaries() {
        try {
            if (isDictsLoaded) return;
            dictionaries.put("Python", loadDictionariesFromFile("data/pythonDict.txt"));
            dictionaries.put("Java", loadDictionariesFromFile("data/javaDict.txt"));
            dictionaries.put("SQL", loadDictionariesFromFile("data/sqlDict.txt"));
            redisDB.addCategory("Python", dictionaries.get("Python"));
            redisDB.addCategory("Java", dictionaries.get("Java"));
            redisDB.addCategory("SQL", dictionaries.get("SQL"));
            isDictsLoaded = true;
        }
        catch(InternalServerException e) {
            throw new InternalServerException("Error loading dictionaries.");
        }
    }

    private HashMap<String, String> loadDictionariesFromFile(String filename) {
        HashMap<String, String> dict = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(":");
                dict.put(line[0], line[1]);
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            throw new InternalServerException("Error loading dictionaries from file.");
        }
        return dict;
    }

    public void addCategory(String category) {
        try {
            categories.add(category);
        }
        catch (InternalServerException e) {
            throw new InternalServerException("Error adding category.");
        }
    }

    public ArrayList<String> getCategories() {
        try {
            return categories;
        }
        catch (InternalServerException e) {
            throw new InternalServerException("Error getting categories.");
        }
    }

    public HashMap<String, HashMap<String, String>> getDictionaries() {
        try {
            return dictionaries;
        }
        catch (InternalServerException e) {
            throw new InternalServerException("Error getting dictionaries.");
        }
    }

    public String getDefinition(String category) {
        try {
            HashMap<String, String> dict = dictionaries.get(category);
            for (String key : dict.keySet()) {
                currentDefinition = dict.get(key);
                if (!currentDefinition.isEmpty()) {
                    return currentDefinition;
                }
            }
            return "No definition found.";
        }
            catch(InternalServerException | NullPointerException e) {
                throw new CategoryNotValidException(category);
            }
        }

    public boolean isTermCorrect(String category, String term) {
        try {
            HashMap<String, String> dict = dictionaries.get(category);
            Iterator<Map.Entry<String, String>> iterator = dict.entrySet().iterator();
            Map.Entry<String, String> entry = iterator.next();
            String dictTerm = entry.getKey();
            if (checkCorrectTerm(term, dictTerm, iterator))
                return true;
        }
        catch (StorageException | NullPointerException e) {
            throw new StorageException("Error checking term.");
        }
        return false;
    }

    private boolean checkCorrectTerm(String term, String dictTerm, Iterator<Map.Entry<String, String>> iterator) {
        if (term.equalsIgnoreCase(dictTerm)) {
            iterator.remove();
            if (iterator.hasNext())
                currentDefinition = iterator.next().getValue();
            return true;
        }
        return false;
    }
}