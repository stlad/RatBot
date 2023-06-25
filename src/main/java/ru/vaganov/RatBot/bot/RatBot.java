package ru.vaganov.RatBot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.beans.factory.annotation.Autowired;
import ru.vaganov.RatBot.bot.events.ButtonClick;
import ru.vaganov.RatBot.bot.events.InfoCommandsListener;
import ru.vaganov.RatBot.bot.events.SoundPadCommandListener;
import ru.vaganov.RatBot.bot.soundLibraries.DirectoryLibrary;
import ru.vaganov.RatBot.bot.soundLibraries.SoundLibrary;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class RatBot{


    public static SoundLibrary soundLibrary;
    public void run(){
        soundLibrary = new DirectoryLibrary();

        JDA jda = JDABuilder.createDefault(BotEnvironment.getBotToken())
                .addEventListeners(new InfoCommandsListener(),
                        new SoundPadCommandListener(),
                        new ButtonClick())
                .build();
        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("say", "Заставляет крысу сказать, что ты хочешь")
                        .addOption(STRING, "content", "Что крыса скажет", true));

        commands.addCommands(Commands.slash("pad", "включает панель звуков"));
        commands.queue();
    }


}
