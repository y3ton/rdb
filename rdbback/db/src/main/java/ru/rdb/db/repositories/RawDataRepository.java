package ru.rdb.db.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rdb.models.DbId;
import ru.rdb.models.RawData;

import java.util.List;

public interface RawDataRepository extends CrudRepository<RawData, DbId> {

    @Query(value = "SELECT r FROM RawData r WHERE r.id.group=:group")
    List<RawData> findByGroup(@Param("group") String group);

}