package ru.vaganov.RatBot.bot.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import ru.vaganov.RatBot.bot.RatBot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainCommandListener extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild()==null)
            return;

        switch (event.getName()){
            case "say":
                say(event, event.getOption("content").getAsString());
            case "about":
                about(event);
        }
    }


    private void say(SlashCommandInteractionEvent event, String content){
        event.reply(content).queue();
    }


    private void about(SlashCommandInteractionEvent event) {


        String resultText = " ";
        try (BufferedReader reader = new BufferedReader(new FileReader(RatBot.botEnvironment.getInfoDirPath()+"about.md"))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line+"\n");
            }
            resultText = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            resultText = "Увы, сказать то и нечего...";
        }

        List<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.link("https://github.com/stlad/RatBot","GitHub"));


        event.reply(resultText).addActionRow(buttons).queue();

    }
}
