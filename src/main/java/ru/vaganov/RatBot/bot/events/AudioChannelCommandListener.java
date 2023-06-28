package ru.vaganov.RatBot.bot.events;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.managers.AudioManager;
public class AudioChannelCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if(event.getGuild()==null)
            return;
        switch (event.getName()){
            case "conn":
                EntitySelectMenu menu = createChannelSelectionMenu();
                event.reply("").addActionRow(menu).setEphemeral(true).queue();
                break;
            case "disconn":
                event.getGuild().getAudioManager().closeAudioConnection();

                event.reply("Крыса убежала ").setEphemeral(true).queue();
                break;
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
        //event.getChannel().sendMessage("Крыса прибежала в: [" + channel.getName() + "]").queue();
    }

    private static EntitySelectMenu createChannelSelectionMenu(){
        EntitySelectMenu menu = EntitySelectMenu.create("voice-channels", EntitySelectMenu.SelectTarget.CHANNEL)
                .setPlaceholder("Тыкни на канал")
                .setChannelTypes(ChannelType.VOICE)
                .build();
        return menu;
    }
}
