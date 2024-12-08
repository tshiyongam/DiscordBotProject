package edu.moravian;

import exceptions.InternalServerException;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class FlashCardRaceBot
{
    public static void main(String[] args)
    {
        String token = loadToken();
        startBot(token);
    }

    private static UserMessages insertUserMessages()
    {
        FlashCardRaceGame flashCardRace = new FlashCardRaceGame();
        return new UserMessages(flashCardRace);
    }

    private static String loadToken()
    {
        try {
            Dotenv dotenv = Dotenv.load();
            return dotenv.get("DISCORD_TOKEN");
        } catch (DotenvException e) {
            System.out.println("Error loading token.");
            System.exit(1);
            return null;
        }
    }

    private static void startBot(String token) {
        JDA api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        UserMessages userMessages = insertUserMessages();

        api.addEventListener(new ListenerAdapter()
        {
            @Override
            public void onMessageReceived(MessageReceivedEvent event)
            {
                if (event.getAuthor().isBot())
                    return;

                if (!event.getChannel().getName().equals("mael-channel"))
                    return;

                String username = event.getAuthor().getName();
                String message = event.getMessage().getContentRaw();

                String response = userMessages.responses(username, message);
                try {
                    event.getChannel().sendMessage(response).queue();
                }
                catch (NullPointerException | IllegalArgumentException e) {
                    throw new InternalServerException("Users inputting invalid messages/commands.");
                }
            }
        }
        );
    }
}
