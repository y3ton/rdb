package ru.rdb.db.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.rdb.models.DbId;
import ru.rdb.models.RawData;

import java.util.List;

public interface RawDataRepository extends CrudRepository<RawData, DbId> {

    List<RawData> findByIdGroup(String group, Pageable pageable);

}