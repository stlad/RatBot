package ru.vaganov.RatBot.data.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vaganov.RatBot.data.models.Guild;

public interface GuildRepository extends CrudRepository<Guild, String> {
}
