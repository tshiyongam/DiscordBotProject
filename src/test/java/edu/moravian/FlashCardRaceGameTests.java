package edu.moravian;

import exceptions.CategoryNotValidException;
import exceptions.GameInProgressException;
import exceptions.NoGameInProgressException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

public class FlashCardRaceGameTests {

    @Test
    public void testNothing() {
    }

    @Test
    public void addOnePlayer() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        assertEquals(Set.of("player1"), flashCardRaceGame.getUsers());
        assertEquals(0, flashCardRaceGame.getPoints("player1"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void addThreePlayers() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPlayer("player2");
        flashCardRaceGame.addPlayer("player3");
        assertEquals(Set.of("player1", "player2", "player3"), flashCardRaceGame.getUsers());
        assertEquals(0, flashCardRaceGame.getPoints("player1"));
        assertEquals(0, flashCardRaceGame.getPoints("player2"));
        assertEquals(0, flashCardRaceGame.getPoints("player3"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void addDuplicatePlayers() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Python");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPlayer("player1");
        assertEquals(1, flashCardRaceGame.getUsers().size());
        assertEquals(0, flashCardRaceGame.getPoints("player1"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void addTwoPointsOnePlayer() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("SQL");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        assertEquals(2, flashCardRaceGame.getPoints("player1"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void addFivePointsTwoPlayers() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPlayer("player2");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        assertEquals(5, flashCardRaceGame.getPoints("player1"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void userHasSevenPoints() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        flashCardRaceGame.addPoint("player1");
        assertEquals(7, flashCardRaceGame.getPoints("player1"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void addPlayerToNonExistentGame() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.endGame();
        assertThrows(NoGameInProgressException.class, () -> flashCardRaceGame.addPlayer("player2"));
        assertThrows(NoGameInProgressException.class, () -> flashCardRaceGame.getUsers().size());
        assertThrows(NoGameInProgressException.class, () -> flashCardRaceGame.getPoints("player2"));
    }

    @Test
    public void startGameValidCategory() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPoint("player1");
        assertEquals(1, flashCardRaceGame.getUsers().size());
        flashCardRaceGame.endGame();
    }

    @Test
    public void getDefinitionJava() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        String definition = flashCardRaceGame.getDefinition("Java");
        assertEquals("A principle of restricting access to an object’s data by keeping it " +
                "private and exposing only what’s necessary.", definition);
        flashCardRaceGame.endGame();
    }

    @Test
    public void getDefinitionSQL() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("SQL");
        String definition = flashCardRaceGame.getDefinition("SQL");
        assertEquals("A special marker in SQL indicating that a value is " +
                "missing or undefined, distinct from zero or an empty string.", definition);
        flashCardRaceGame.endGame();
    }
    @Test
    public void invalidCategoryThrowsCategoryNotValidException() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        assertThrows(CategoryNotValidException.class, () -> flashCardRaceGame.startGame("C++"));
        assertThrows(NoGameInProgressException.class, () -> flashCardRaceGame.endGame());
    }

    @Test
    public void endGameThrowException() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        flashCardRaceGame.addPlayer("player1");
        flashCardRaceGame.addPlayer("player2");
        assertEquals(2, flashCardRaceGame.getUsers().size());
        flashCardRaceGame.endGame();
        assertThrows(NoGameInProgressException.class, () -> flashCardRaceGame.getUsers().size());
    }

    @Test
    public void noGameInProgress() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        assertFalse(flashCardRaceGame.gameStatus());
    }

    @Test
    public void gameInProgressException() {
        FlashCardRaceGame fci = new FlashCardRaceGame();
        fci.startGame("Java");
        assertThrows(GameInProgressException.class, () -> fci.startGame("Python"));
        fci.endGame();
    }

    @Test
    public void termIsCorrectPython() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Python");
        assertTrue(flashCardRaceGame.isTermCorrect("Python","Immutable"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void termIsCorrectJava() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        assertTrue(flashCardRaceGame.isTermCorrect("Java","encapsulation"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void termIsIncorrect() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("Java");
        assertFalse(flashCardRaceGame.isTermCorrect("Java","incorrect"));
        flashCardRaceGame.endGame();
    }

    @Test
    public void termIsCorrectTwoTermsSQL() {
        FlashCardRaceGame flashCardRaceGame = new FlashCardRaceGame();
        flashCardRaceGame.startGame("SQL");
        assertTrue(flashCardRaceGame.isTermCorrect("SQL","null"));
        assertTrue(flashCardRaceGame.isTermCorrect("SQL","Query"));
        flashCardRaceGame.endGame();
    }
}
