package ru.vaganov.RatBot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import ru.vaganov.RatBot.bot.events.InfoCommandsListener;
import ru.vaganov.RatBot.bot.events.SoundPadCommandListener;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class RatBot{

    public void run(){
        JDA jda = JDABuilder.createDefault(BotEnvironment.getBotToken())
                .addEventListeners(new InfoCommandsListener(), new SoundPadCommandListener())
                .build();
        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("say", "Заставляет крысу сказать, что ты хочешь")
                        .addOption(STRING, "content", "Что крыса скажет", true));

        commands.addCommands(Commands.slash("pad", "включает панель звуков"));
        commands.queue();
    }


}
