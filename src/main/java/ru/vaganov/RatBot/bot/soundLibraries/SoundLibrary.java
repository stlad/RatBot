package ru.vaganov.RatBot.bot.soundLibraries;

import jakarta.persistence.criteria.CriteriaBuilder;
import ru.vaganov.RatBot.data.models.Sound;

import java.util.List;

public interface SoundLibrary {
    public List<Sound> getSoundsByGuildId(String guildId);

    public Integer getCount();

    public Integer getCountByGuildID(String guildID);


}
