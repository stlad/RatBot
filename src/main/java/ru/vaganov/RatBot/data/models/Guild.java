package ru.vaganov.RatBot.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Guild {

    @Id
    private String guildId;


    public Guild(){

    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guild) {
        this.guildId = guild;
    }
}
