package ru.vaganov.RatBot.bot.events;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Widget;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.List;

public class AudioChannelCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if(event.getGuild()==null)
            return;
        switch (event.getName()){
            case "conn":
                //event.reply("conn").setEphemeral(true).queue();
                EntitySelectMenu menu = createChannelSelectionMenu();
                event.reply("Выберите канал для подключения").addActionRow(menu).setEphemeral(true).queue();

        }
    }


    @Override
    public void onEntitySelectInteraction(@Nonnull EntitySelectInteractionEvent event)
    {
        event.deferEdit().queue();
        if (event.getComponentId().equals("voice-channels")){
            event.editSelectMenu(event.getSelectMenu().asDisabled()).queue();
            connectToVoiceChanel(event);
        }

    }

    public static void connectToVoiceChanel(EntitySelectInteractionEvent event){
        VoiceChannel channel = (VoiceChannel) event.getMentions().getChannels().get(0);

        AudioManager audioManager = event.getGuild().getAudioManager();
        audioManager.openAudioConnection(channel);

        event.getChannel().sendMessage("Крыса прибежала в: [" + channel.getName() + "]").queue();
    }

    private static EntitySelectMenu createChannelSelectionMenu(){
        EntitySelectMenu menu = EntitySelectMenu.create("voice-channels", EntitySelectMenu.SelectTarget.CHANNEL)
                .setChannelTypes(ChannelType.VOICE)
                .build();
        return menu;
        //
    }
}
