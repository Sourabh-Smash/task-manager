package com.sourabh.task_manager.config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
@Slf4j
public class DevDataSeeder implements CommandLineRunner {
    @Override
    public void run(String... args) {
        log.warn("🌱 Seeding development database...");
        // add your dev DB seeding logic here
    }

}
