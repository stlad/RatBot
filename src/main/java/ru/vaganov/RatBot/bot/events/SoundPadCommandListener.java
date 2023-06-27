package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import ru.vaganov.RatBot.bot.RatBot;
import ru.vaganov.RatBot.bot.soundLibraries.SoundLibrary;
import ru.vaganov.RatBot.data.models.Guild;
import ru.vaganov.RatBot.data.models.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundPadCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;

        switch (event.getName()) {
            case "pad":
                //event.getChannel().sendMessage("крысиные звуки").setActionRow(sendAudioButtons(event)).queue();
                event.reply("крысиные звуки").addActionRow(sendAudioButtons(event)).setEphemeral(true).queue();
        }

    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        event.deferEdit().queue();
        if(event.getButton().getId().equals("add")){
            addSound(event);
        }
        else{
            playSound(event, RatBot.soundLibrary.getSound(event.getGuild().getId() ,event.getButton().getId()));
        }
    }

    private static void addSound(ButtonInteractionEvent event){
        SoundLibrary lib = RatBot.soundLibrary;
        lib.addToGuild(event.getGuild().getId());
    }

    private static void playSound(ButtonInteractionEvent event, Sound sound){
        event.getChannel().sendMessage("Сейчас играет: "+ sound.getTitle()).queue();
    }

    private static List<Button> sendAudioButtons(SlashCommandInteractionEvent event){
        SoundLibrary lib = RatBot.soundLibrary;
        List<Sound> sounds = lib.getSoundsByGuildId(event.getGuild().getId());

        List<Button> buttons = new ArrayList<Button>();
        for(Sound sound:sounds){
            buttons.add(Button.primary(sound.getFilename(), sound.getTitle()).withStyle(ButtonStyle.PRIMARY));
        }
        buttons.add(Button.primary("add","+").withStyle(ButtonStyle.SECONDARY));
        return buttons;
    }
}
