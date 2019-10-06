package ru.rdb.db.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;

public interface LinkRepository extends CrudRepository<Link, DbId> {

    @Query(value = "SELECT l FROM links l WHERE l.id.group=:group and (readDate is null or readDate < :maxDate)")
    Link getRandom(@Param("group") String group, @Param("maxDate") Long maxDate);

}