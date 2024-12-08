package edu.moravian;

import exceptions.StorageException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;

public class RedisManager {

    private final Jedis jedis;

    public RedisManager() {
        try {
            jedis = new Jedis("localhost", 6379);
        } catch (JedisConnectionException e) {
            throw new StorageException("Error connecting to the server.");
        }
    }

    public void addPlayer(String user) {
        try {
            jedis.hset("Users", user, "0");
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error adding user to the database.");
        }
    }

    public void addPoint(String user) {
        try {
            jedis.hincrBy("Users", user, 1);
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error adding point to the database.");
        }
    }

    public HashMap<String, String> getPlayersPoints() {
        try {
            return (HashMap<String, String>) jedis.hgetAll("Users");
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error getting user points from the database.");
        }
    }

    public void clearUsersAndPoints() {
        try {
            jedis.del("Users");
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error clearing users and points from the database.");
        }
    }

    public void addCategory(String category, HashMap<String, String> definitions) {
        try {
            String key = "Category:" + category;
            for (String word : definitions.keySet())
                jedis.hset(key, word, definitions.get(word));
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error adding category to the database.");
        }
    }

    public void setCurrentCategory(String category) {
        try {
            jedis.set("CurrentCategory", category);
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error setting current category.");
        }
    }

    public String getCurrentCategory() {
        try {
            return jedis.get("CurrentCategory");
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error getting current category.");
        }
    }

    public void clearAll()
    {
        try {
            jedis.flushAll();
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error clearing all data from the database.");
        }
    }

    public String getCurrentDefinition(String category) {
        MemoryLibrary memoryLibrary = new MemoryLibrary();
        try {
            String key = "CurrentDefinition:" + category;
            HashMap<String, String> dict = memoryLibrary.getDictionaries().get(category);
            for (Map.Entry<String, String> entry : dict.entrySet()) {
                String term = entry.getKey();
                jedis.hset(key, term, entry.getValue());
                if (memoryLibrary.isTermCorrect(category, term)) {
                    return memoryLibrary.getDefinition(category);
                }
            }
            return "No matching terms found.";
        } catch (JedisException | NullPointerException e) {
            throw new StorageException("Error getting current definition.");
        }
    }
}


