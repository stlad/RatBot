package ru.vaganov.RatBot.bot.soundLibraries;

import ru.vaganov.RatBot.data.models.Sound;

import java.util.List;

public interface SoundLibrary {
    public List<Sound> getSoundsByGuildId(String guildId);

    public Integer getCountByGuildID(String guildID);

    public void addToGuild(Sound sound);

    public Sound getSound(String guildID, String filename);

}
