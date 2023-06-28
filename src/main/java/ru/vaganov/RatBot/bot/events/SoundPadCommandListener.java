package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import ru.vaganov.RatBot.bot.RatBot;
import ru.vaganov.RatBot.bot.soundLibraries.SoundLibrary;
import ru.vaganov.RatBot.data.models.Sound;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SoundPadCommandListener extends ListenerAdapter {

    private static final Integer MAX_BUTTON_COUNT = 5;


    private static final int MAX_FIT_FILE_SIZE = 10485760 ; //10 мб

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;

        switch (event.getName()) {
            case "pad":
                if(event.getGuild().getAudioManager().isConnected()) {
                    sendAudioButtons(event);
                }
                else{
                    event.reply("Ты крысу то в канал подключи сначала, гений... (/conn)").setEphemeral(true).queue();
                };
                break;
            case "add":
                addSound(event);
        }
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        event.deferEdit().queue();
        playSound(event, RatBot.soundLibrary.getSound(event.getGuild().getId() ,event.getButton().getId()));
    }

    private static void addSound(SlashCommandInteractionEvent event){
        SoundLibrary lib = RatBot.soundLibrary;
        var attachment = event.getOption("soundfile").getAsAttachment();
        var soundname=  event.getOption("name").getAsString();

        if(!isAttchmentFine(attachment)){
            event.reply("Ты как посмел эту дрянь кинуть").setEphemeral(true).queue();
            return;
        }

        Sound sound = new Sound(event.getGuild().getId(), soundname +"." + attachment.getFileExtension());
        lib.addToGuild(sound);
        //TODO исправить на загрузку через lib.addToGuild. Избавиться от явного скачаивания файла в этом методе.
        attachment.getProxy().downloadToFile(new File(sound.getAbsolutePath()));

        event.reply("Звук добавлен крысе").setEphemeral(true).queue();
    }

    private static boolean isAttchmentFine(Message.Attachment attachment){
        //TODO Перенести проверку файла в SoundLibrary. Избавить от проверки файла в этом классе.
        String extention = attachment.getFileExtension();
        String[] extArr = {"mp3","m4a","waw"};
        HashSet<String> audioExtentions  = new HashSet<>(Arrays.asList(extArr));

        return attachment.getSize()<=MAX_FIT_FILE_SIZE && audioExtentions.contains(extention);

    }
    private static void playSound(ButtonInteractionEvent event, Sound sound){
        RatBot.ratBotAudioState.loadAndPlay(event.getGuild(), sound.getAbsolutePath());
    }

    private static void sendAudioButtons(SlashCommandInteractionEvent event){
        SoundLibrary lib = RatBot.soundLibrary;
        List<Sound> sounds = lib.getSoundsByGuildId(event.getGuild().getId());
        var reply= event.reply("крысиные звуки");


        List<Button> bufferButtons = new ArrayList<Button>();
        for(Sound sound:sounds){
            if(bufferButtons.size()>= MAX_BUTTON_COUNT){
                reply.addActionRow(bufferButtons);
                bufferButtons = new ArrayList<>();
            }
            bufferButtons.add(Button.primary(sound.getFilename(), sound.getTitle()).withStyle(ButtonStyle.PRIMARY));
        }
        reply.addActionRow(bufferButtons);

        reply.setEphemeral(true).queue();
    }
}
