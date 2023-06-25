package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonClick extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        System.out.println(event.getButton().getId());
        //
    }


}
