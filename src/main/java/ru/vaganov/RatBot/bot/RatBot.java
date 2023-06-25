package ru.vaganov.RatBot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import ru.vaganov.RatBot.bot.events.BotListener;
import ru.vaganov.RatBot.bot.events.ButtonListener;

import java.awt.*;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class RatBot{

    public void run(){
        JDA jda = JDABuilder.createDefault(BotEnvironment.getBotToken())
                .addEventListeners(new BotListener(), new ButtonListener())
                .build();
        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("say", "Заставляет крысу сказать, что ты хочешь")
                        .addOption(STRING, "content", "Что крыса скажет", true));


        // Send the new set of commands to discord, this will override any existing global commands with the new set provided here
        commands.queue();
    }


}
