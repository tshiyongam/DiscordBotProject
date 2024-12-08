package edu.moravian;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class PointsPerUserTests {

    @Test
    public void testNothing() {
    }

    @Test
    public void addOnePoint() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.addPoint("user1");
        assertEquals(1, pointsPerUser.getPoints("user1"));
    }

    @Test
    public void addThreePoints() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        assertEquals(3, pointsPerUser.getPoints("user1"));
    }

    @Test
    public void addTwoPointsTwoUsers() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user2");
        assertEquals(2, pointsPerUser.getPoints("user1"));
        assertEquals(2, pointsPerUser.getPoints("user2"));
    }

    @Test
    public void fivePointsOneUser() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        assertEquals(5, pointsPerUser.getPoints("user1"));
    }

    @Test
    public void fivePointsUserOneAndTwo() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user1");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user2");
        pointsPerUser.addPoint("user1");
        assertEquals(5, pointsPerUser.getPoints("user1"));
        assertEquals(5, pointsPerUser.getPoints("user2"));
    }

    @Test
    public void createOneUser() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.createUser("user1");
        assertEquals(0, pointsPerUser.getPoints("user1"));
    }

    @Test
    public void createThreeUsers() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        pointsPerUser.createUser("user1");
        pointsPerUser.createUser("user2");
        pointsPerUser.createUser("user3");
        assertEquals(0, pointsPerUser.getPoints("user1"));
        assertEquals(0, pointsPerUser.getPoints("user2"));
        assertEquals(0, pointsPerUser.getPoints("user3"));
    }

    @Test
    public void getTwoUsers() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        HashSet<String> users = new HashSet<>();
        pointsPerUser.createUser("user1");
        pointsPerUser.createUser("user2");
        users.add("user1");
        users.add("user2");
        assertEquals(users, pointsPerUser.getUsers());
    }

    @Test
    public void noPointsNoUsers() {
        PointsPerUser pointsPerUser = new PointsPerUser();
        assertEquals(0, pointsPerUser.getPoints("user1"));
    }
}

