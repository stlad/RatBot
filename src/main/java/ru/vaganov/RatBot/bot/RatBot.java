package ru.vaganov.RatBot.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import ru.vaganov.RatBot.bot.audio.RatBotAudioState;
import ru.vaganov.RatBot.bot.events.AudioChannelCommandListener;
import ru.vaganov.RatBot.bot.events.MainCommandListener;
import ru.vaganov.RatBot.bot.events.SoundPadCommandListener;
import ru.vaganov.RatBot.bot.soundLibraries.DirectoryLibrary;
import ru.vaganov.RatBot.bot.soundLibraries.SoundLibrary;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class RatBot{

    public static SoundLibrary soundLibrary;
    public static RatBotAudioState ratBotAudioState;


    public void run(){
        soundLibrary = new DirectoryLibrary();
        ratBotAudioState = new RatBotAudioState();

        JDA jda = JDABuilder.createDefault(BotEnvironment.getBotToken())
                .addEventListeners(
                        new MainCommandListener(),
                        new SoundPadCommandListener(),
                        new AudioChannelCommandListener())
                .build();
        CommandListUpdateAction commands = RegisterCommands(jda.updateCommands());
        commands.queue();
    }

    private static CommandListUpdateAction RegisterCommands(CommandListUpdateAction commands){
        commands.addCommands(
                Commands.slash("say", "Заставляет крысу сказать, что ты хочешь")
                        .addOption(STRING, "content", "Что крыса скажет", true));

        commands.addCommands(Commands.slash("pad", "включает панель звуков"));
        commands.addCommands(Commands.slash("conn", "Открывает выбор голосвых каналов для подключения бота и подключает к выбранному каналу"));
        commands.addCommands(Commands.slash("disconn", "Отключает бота от голосового канала"));

        return commands;
    }


}
