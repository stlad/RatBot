package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MainCommandListener extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild()==null)
            return;

        switch (event.getName()){
            case "say":
                say(event, event.getOption("content").getAsString());

            //default:
            //    event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }


    private void say(SlashCommandInteractionEvent event, String content){
        event.reply(content).queue();
    }


    private void about(SlashCommandInteractionEvent event){

    }
}
