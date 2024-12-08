package edu.moravian;

import java.util.ArrayList;

public class BotMessages {

    public static String messageError() {
        return "Something went wrong with the bot. Please try again.";
    }

    public static String categories(ArrayList<String> categories) {
        return "The available categories are: " + String.join(", ", categories);
    }

    public static String gameStarted(String category) {
        return "The game will start with the category: " + category + ".\n" +
                "Players can join the game by typing `!join.`";
    }

    public static String unknownCategory(String category) {
        return "The category " + category + " is not valid. Please choose a valid category.";
    }

    public static String gameInProgress() {
        return "A game is already in progress. Please wait for the current game to finish.";
    }

    public static String noGameInProgress() {
        return "There is no game in progress. Please start a game by typing `!start` followed by the category.";
    }

    public static String userAdded(String user) {
        return user + " has been added to the game. Start the game with `!go.`";
    }

    public static String userAlreadyAdded(String user) {
        return user + " has already been added to the game.";
    }

    public static String goGame(String definition) {
        return "The game has started!\nThe definition is: " + definition + "\n" +
                "Players can guess the term by typing the term in the chat.";
    }

    public static String userCorrect(String user, String term) {
        return user + " has guessed " + term + " correctly!" + "\n" + user + " has earned 1 point.";
    }

    public static String userIncorrect(String user, String term) {
        return user + " has guessed " + term + " incorrectly.";
    }

    public static String noUsers() {
        return "There are no users in the game. Please add users using the `!join` command.";
    }

    public static String usersInGameBeforeStart(FlashCardRaceGame flashCardRace)
    {
        StringBuilder message = new StringBuilder("The players currently in the game are:\n");
        for (String user : flashCardRace.getUsers())
            message.append("* ").append(user).append("\n");
        message.append("Start the game with `!go.`");
        return message.toString();
    }

    public static String userPoints(FlashCardRaceGame flashCardRace) {
        StringBuilder message = new StringBuilder("Current Leaderboard:\n");
        for (String user : flashCardRace.getUsers())
            message.append("* ").append(user).append(": ").append(flashCardRace.getPoints(user)).append(" points\n");
        return message.toString();
    }

    public static String quitGame(FlashCardRaceGame flashCardRace) {
        StringBuilder message = new StringBuilder("The game has ended. Here are the results in the category: \"" + flashCardRace.getCurrentCategory() + "\"\n");
        for (String user : flashCardRace.getUsers())
            message.append("* ").append(user).append(": ").append(flashCardRace.getPoints(user)).append(" points\n");
        return message.toString();
    }

    public static String botRanOutOfDefinitions()
    {
        return "The bot has run out of definitions, so the game is forced to quit.\n" +
                "Play again with another category typing `!start` followed by the category.";
    }
    public static String getCurrentDefinition(String definition)
    {
        return "The definition is: " + definition;
    }

    public static String noGameQuit()
    {
        return "There is no game to quit. Please start a game by typing `!start` followed by the category.";
    }

    public static String userNotInGame(String user)
    {
        return "Game currently in progress. " + user + " is not in the game.";
    }

    public static String userCannotGoGame(String user)
    {
        return "User cannot begin the game. " + user + " is not in the game.";
    }

    public static String help()
    {
        return "Flashcard Race Commands:\n" +
                "* `!start <category>` - Start a session with a specified category, allowing players to join.\n*Categories are case-sensitive.\n" +
                "* `!categories` - View the available categories.\n" +
                "* `!join` - Join the game.\n" +
                "* `!go` - Start the game with the first definition.\n" +
                "* `!status` - Checks the current status of the game.\n" +
                "* `!quit` - Quit the game.\n" +
                "* `!def` - View the current definition.\n" +
                "* `!help` - View the list of commands.";
    }
}