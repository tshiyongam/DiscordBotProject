package edu.moravian;

import exceptions.*;

import java.util.ArrayList;
import java.util.Set;

public class FlashCardRaceGame {

    private final PointsPerUser pointsPerUser = new PointsPerUser();
    private final MemoryLibrary memoryLibrary = new MemoryLibrary();
    private String currentCategory = "";
    private final RedisManager redisDB = new RedisManager();

    public void startGame(String category) throws GameInProgressException {
        if (gameStatus())
            throw new GameInProgressException();

        if (!getCategories().contains(category))
            throw new CategoryNotValidException(category);

        try {
            setCurrentCategory(category);
        }
        catch (CategoryNotValidException e) {
            throw new InternalServerException("Error starting game.");
        }
    }


    public void addPlayer(String user) {
        if (!gameStatus())
            throw new NoGameInProgressException();

        try {
            redisDB.addPlayer(user);
            pointsPerUser.createUser(user);
        }
        catch (StorageException e) {
            throw new StorageException("Error adding user to game.");
        }
    }

    public Set<String> getUsers() throws InternalServerException {
        if (!gameStatus())
            throw new NoGameInProgressException();
        try {
            return pointsPerUser.getUsers();
        }
        catch (StorageException e) {
            throw new StorageException("Error getting users.");
        }
    }

    public int getPoints(String user) {
        if (!gameStatus())
            throw new NoGameInProgressException();

        try {
            return pointsPerUser.getPoints(user);
        }
        catch (StorageException e) {
            throw new StorageException("Error getting users and points.");
        }

    }

    public void addPoint(String user) {
        if (!gameStatus())
            throw new NoGameInProgressException();

        try {
            pointsPerUser.addPoint(user);
            redisDB.addPoint(user);
        }
        catch (StorageException e) {
            throw new StorageException("Error adding point.");
        }
    }

    public ArrayList<String> getCategories() {
        try {
            return memoryLibrary.getCategories();
        }
        catch (StorageException e) {
            throw new StorageException("Error getting categories.");
        }
    }

    public String getDefinition(String category) {
        if (!gameStatus())
            throw new NoGameInProgressException();

        try {
            redisDB.getCurrentDefinition(category);
            return memoryLibrary.getDefinition(category);
        }
        catch (StorageException e) {
            throw new StorageException("Error getting definition.");
        }
    }

    public void endGame() {
        if (!gameStatus())
            throw new NoGameInProgressException();

        try {
            pointsPerUser.clearUsersAndPoints();
            redisDB.clearAll();
            currentCategory = "";
        }
            catch (StorageException e) {
            throw new StorageException("Error ending the game.");
        }
    }

    public boolean gameStatus() {
        try {
            return !currentCategory.isEmpty();
        }
        catch (StorageException e) {
            throw new StorageException("Error checking game status.");
        }
    }

    public boolean isTermCorrect(String category, String term) {
        if(!gameStatus())
            throw new NoGameInProgressException();

        try {
            return memoryLibrary.isTermCorrect(category, term);
        }
        catch (StorageException e) {
            throw new IncorrectTermException(term);
        }
    }

    public void setCurrentCategory(String category) {
        try {
            currentCategory = category;
            redisDB.setCurrentCategory(category);
        }
        catch (StorageException e) {
            throw new CategoryNotValidException(category);
        }
    }

    public String getCurrentCategory() {
        try {
            return currentCategory;
        }
        catch (StorageException e) {
            throw new StorageException("Error getting current category.");
        }
    }
}
