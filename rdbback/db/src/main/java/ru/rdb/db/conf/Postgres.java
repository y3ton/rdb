package ru.rdb.db.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("ru.rdb.models")
public class Postgres {
}