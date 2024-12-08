package edu.moravian;

import exceptions.InternalServerException;

public class UserMessages {
    private final FlashCardRaceGame flashCardRace;
    private GameStatus status;
    private String currentCategory;

    public UserMessages(FlashCardRaceGame flashCardRace) {
        this.flashCardRace = flashCardRace;
        this.status = GameStatus.NOT_STARTED;
    }

    public String responses(String user, String message) {
        try {
            if (message.equalsIgnoreCase("!categories"))
                return displayCategories();
            else if (message.startsWith("!start"))
                return startGame(message);
            else if (message.equalsIgnoreCase("!join"))
                return joinGame(user);
            else if (message.equalsIgnoreCase("!go"))
                return goGame(user);
            else if (message.equalsIgnoreCase("!status"))
                return gameStatus();
            else if (message.equalsIgnoreCase("!quit"))
                return quitGame(user);
            else if (message.equalsIgnoreCase("!help"))
                return help();
            else if (message.equalsIgnoreCase("!def"))
                return currentDefinition();
            else
                return handleTerms(user, message);
        } catch (InternalServerException e) {
            return BotMessages.messageError();
        }
    }

    private String displayCategories() throws InternalServerException {
        return BotMessages.categories(flashCardRace.getCategories());
    }

    private String startGame(String message) throws InternalServerException {
        if (status == GameStatus.IN_PROGRESS)
            return BotMessages.gameInProgress();

        String[] parts = message.split(" ");
        if (parts.length < 2)
            return BotMessages.unknownCategory(message);

        String category = parts[1];
        if (!flashCardRace.getCategories().contains(category))
            return BotMessages.unknownCategory(category);

        status = GameStatus.WAITING_FOR_PLAYERS;
        currentCategory = category;
        flashCardRace.startGame(category);
        return BotMessages.gameStarted(category);
    }

    private String joinGame(String user) throws InternalServerException {
        if (status == GameStatus.NOT_STARTED)
            return BotMessages.noGameInProgress();
        if (status == GameStatus.IN_PROGRESS)
            return BotMessages.gameInProgress();
        if (flashCardRace.getUsers().contains(user))
            return BotMessages.userAlreadyAdded(user);
        flashCardRace.addPlayer(user);
        return BotMessages.userAdded(user);
    }

    private String goGame(String user) throws InternalServerException {
        if (status == GameStatus.NOT_STARTED)
            return BotMessages.noGameInProgress();

        if (status == GameStatus.IN_PROGRESS)
            return BotMessages.gameInProgress();

        if (flashCardRace.getUsers().isEmpty())
            return BotMessages.noUsers();

        if (!flashCardRace.getUsers().contains(user))
            return BotMessages.userCannotGoGame(user);

        if (currentCategory == null || !flashCardRace.getCategories().contains(currentCategory))
            return BotMessages.unknownCategory(currentCategory);

        status = GameStatus.IN_PROGRESS;
        return BotMessages.goGame(flashCardRace.getDefinition(currentCategory));
    }

    private String gameStatus() throws InternalServerException
    {
        if (status == GameStatus.NOT_STARTED)
            return BotMessages.noGameInProgress();
        if (!flashCardRace.getUsers().isEmpty() && status == GameStatus.WAITING_FOR_PLAYERS)
            return BotMessages.usersInGameBeforeStart(flashCardRace);
        else if (status == GameStatus.WAITING_FOR_PLAYERS)
            return BotMessages.noUsers();
        return BotMessages.userPoints(flashCardRace);
    }

    private String quitGame(String user) throws InternalServerException {
        if (status == GameStatus.NOT_STARTED)
            return BotMessages.noGameQuit();

        if (flashCardRace.getUsers().isEmpty() && status == GameStatus.WAITING_FOR_PLAYERS)
            return BotMessages.noUsers();

        if (!flashCardRace.getUsers().contains(user))
            return BotMessages.userNotInGame(user);

        if (flashCardRace.getDefinition(currentCategory).isEmpty())
            return emptyCategories();

        String leaderboard = BotMessages.quitGame(flashCardRace);
        status = GameStatus.FINISHED;
        flashCardRace.endGame();

        status = GameStatus.NOT_STARTED;
        currentCategory = null;


        return leaderboard;
    }

    private String emptyCategories() {
        String leaderboard = BotMessages.quitGame(flashCardRace);
        status = GameStatus.FINISHED;
        flashCardRace.endGame();

        status = GameStatus.NOT_STARTED;
        currentCategory = null;
        return BotMessages.botRanOutOfDefinitions() + "\n" + leaderboard;
    }

    private String help()
    {
        return BotMessages.help();
    }

    private String currentDefinition() throws InternalServerException {
        if (status != GameStatus.IN_PROGRESS)
            return BotMessages.noGameInProgress();
        return BotMessages.getCurrentDefinition(flashCardRace.getDefinition(currentCategory));
    }

    public String handleTerms(String user, String term) throws InternalServerException {
        if (status != GameStatus.IN_PROGRESS)
            return BotMessages.noGameInProgress();
        if (!flashCardRace.getUsers().contains(user))
            return BotMessages.userNotInGame(user);

        if (flashCardRace.isTermCorrect(flashCardRace.getCurrentCategory(), term)) {
            flashCardRace.addPoint(user);
            if (flashCardRace.getDefinition(flashCardRace.getCurrentCategory()).isEmpty())
                return quitGame(user);
            String nextDefinition = flashCardRace.getDefinition(flashCardRace.getCurrentCategory());
            return BotMessages.userCorrect(user, term) + "\nNext definition: " + nextDefinition;
        }
        return BotMessages.userIncorrect(user, term);
    }
}