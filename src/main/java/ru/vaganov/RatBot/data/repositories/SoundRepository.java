package ru.vaganov.RatBot.data.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.vaganov.RatBot.data.models.Sound;

public interface SoundRepository extends CrudRepository<Sound, Long> {
}
