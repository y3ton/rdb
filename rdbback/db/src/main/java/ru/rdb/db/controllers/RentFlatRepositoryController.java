package ru.rdb.db.controllers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.rdb.models.RentFlat;

@RepositoryRestResource(collectionResourceRel = "rentflat", path = "rentflat")
public interface RentFlatRepositoryController extends MongoRepository<RentFlat, String> {
}

