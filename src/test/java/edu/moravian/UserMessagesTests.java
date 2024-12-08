package edu.moravian;

import exceptions.GameInProgressException;
import exceptions.StorageException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserMessagesTests {

    private UserMessages createUserMessages() throws StorageException
    {
        FlashCardRaceGame flashCardRace = new FlashCardRaceGame();
        return new UserMessages(flashCardRace);
    }

    @Test
    public void testDisplayCategories() throws StorageException
    {
        UserMessages userMessages = createUserMessages();
        String response = userMessages.responses("user", "!categories");
        assertTrue(response.contains("Java"));
        assertTrue(response.contains("Python"));
        assertTrue(response.contains("SQL"));
    }

    @Test
    public void testStartGameWhenGameNotStarted()
    {
        UserMessages messages = createUserMessages();
        String response = messages.responses("user", "!start Java");
        assertEquals(BotMessages.gameStarted("Java"), response);
    }

    @Test
    public void testStartGameWithUnknownCategory()
    {
        UserMessages messages = createUserMessages();
        String response = messages.responses("user", "!start C++");
        assertEquals(BotMessages.unknownCategory("C++"), response);
    }

    @Test
    public void testStartGameWhenGameInProgress()
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        assertThrows(GameInProgressException.class, () -> messages.responses("user", "!start Java"));
    }

    @Test
    public void testJoinGameWhenGameNotStarted()
    {
        UserMessages messages = createUserMessages();
        String response = messages.responses("user", "!join");
        assertEquals(BotMessages.noGameInProgress(), response);
    }

    @Test
    public void testJoinGameWhenGameInProgress()
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        String response = messages.responses("user", "!join");
        assertEquals(BotMessages.userAdded("user"), response);
    }

    @Test
    public void testJoinGameWhenUserAlreadyAdded()
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        messages.responses("user", "!join");
        String response = messages.responses("user", "!join");
        assertEquals(BotMessages.userAlreadyAdded("user"), response);
    }

    @Test
    public void testGoGameWhenGameNotStarted()
    {
        UserMessages messages = createUserMessages();
        String response = messages.responses("user", "!go");
        assertEquals(BotMessages.noGameInProgress(), response);
    }

    @Test
    public void testGoGameWhenGameInProgress()
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        messages.responses("user", "!join");
        String response = messages.responses("user", "!go");
        assertTrue(response.contains("The definition is:"), response);
    }

    @Test
    public void testQuitGameWithNoUsers() throws StorageException
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        String response = messages.responses("user", "!quit");
        assertTrue(response.contains("There are no users in the game."), response);
    }

    @Test
    public void testGameStatusWhenGameNotStarted()
    {
        UserMessages messages = createUserMessages();
        String response = messages.responses("user", "!status");
        assertEquals(BotMessages.noGameInProgress(), response);
    }

    @Test
    public void testGameStatusWhenGameInProgress()
    {
        UserMessages messages = createUserMessages();
        messages.responses("user", "!start Java");
        String response = messages.responses("user", "!status");
        assertTrue(response.contains("There are no users in the game."), response);
    }

}
