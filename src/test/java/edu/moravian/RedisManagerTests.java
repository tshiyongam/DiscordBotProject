package edu.moravian;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class RedisManagerTests {

    @Test
    public void testNothing() {
    }

    @Test
    public void testGetPlayersPoints() {
        RedisManager redisManager = new RedisManager();
        redisManager.addPlayer("player1");
        redisManager.addPlayer("player2");
        redisManager.addPoint("player1");
        redisManager.addPoint("player1");
        redisManager.addPoint("player2");
        HashMap<String, String> points = redisManager.getPlayersPoints();
        assertEquals("2", points.get("player1"));
        assertEquals("1", points.get("player2"));
        redisManager.clearUsersAndPoints();
    }

    @Test
    public void testSetCategory() {
        RedisManager redisManager = new RedisManager();
        HashMap<String, String> definitions = new HashMap<>();
        definitions.put("Orange", "A round juicy fruit that is a citrus fruit.");
        definitions.put("Apple", "A round fruit that is red or green.");
        redisManager.addCategory("Fruits", definitions);
        redisManager.setCurrentCategory("Fruits");
        assertEquals("Fruits", redisManager.getCurrentCategory());
    }

    @Test
    public void testGetCurrentCategory() {
        RedisManager redisManager = new RedisManager();
        HashMap<String, String> definitions = new HashMap<>();
        definitions.put("Orange", "A round juicy fruit that is a citrus fruit.");
        definitions.put("Apple", "A round fruit that is red or green.");
        redisManager.addCategory("Fruits", definitions);
        redisManager.setCurrentCategory("Fruits");
        assertEquals("Fruits", redisManager.getCurrentCategory());
    }

    @Test
    public void testGetCurrentDefinitionJava() {
        RedisManager redisManager = new RedisManager();
        assertEquals("A virtual environment that interprets Java bytecode, enabling Java's platform independence.", redisManager.getCurrentDefinition("Java"));
    }

    @Test
    public void testGetCurrentDefinitionPython() {
        RedisManager redisManager = new RedisManager();
        assertEquals("The concept of restricting access to certain attributes and methods of an object.", redisManager.getCurrentDefinition("Python"));
    }

    @Test
    public void testGetCurrentDefinitionSQL() {
        RedisManager redisManager = new RedisManager();
        assertEquals("A command or request used to retrieve data from one or more tables in a database.", redisManager.getCurrentDefinition("SQL"));
    }


}