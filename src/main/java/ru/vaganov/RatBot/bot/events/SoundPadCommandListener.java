package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.ArrayList;
import java.util.List;

public class SoundPadCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getGuild() == null)
            return;

        switch (event.getName()) {
            case "pad":
                event.getChannel().sendMessage("крысиные звуки").setActionRow(sendButtons()).queue();
        }

    }



    private static List<Button> sendButtons(){
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.primary("first", "first btn").withStyle(ButtonStyle.PRIMARY));
        return buttons;
    }
}
