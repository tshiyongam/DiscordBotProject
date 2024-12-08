package edu.moravian;
import java.util.HashMap;
import java.util.Set;

public class PointsPerUser {

    private final HashMap<String, Integer> pointsPerUser = new HashMap<>();

    public void addPoint(String user) {
        if (pointsPerUser.containsKey(user))
            pointsPerUser.put(user, pointsPerUser.get(user) + 1);
        else
            pointsPerUser.put(user, 1);
    }

    public int getPoints(String user) {
        if (!pointsPerUser.containsKey(user))
            return 0;
        return pointsPerUser.get(user);
    }

    public Set<String> getUsers() {
        return pointsPerUser.keySet();
    }

    public void createUser(String user) {
        pointsPerUser.put(user, 0);
    }

    public void clearUsersAndPoints() {
        pointsPerUser.clear();
    }
}