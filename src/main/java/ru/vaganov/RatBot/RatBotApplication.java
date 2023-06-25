package ru.vaganov.RatBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.vaganov.RatBot.bot.RatBot;

@SpringBootApplication
@ComponentScan
public class RatBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(RatBotApplication.class, args);
		RatBot bot = new RatBot();
		bot.run();
	}

}
