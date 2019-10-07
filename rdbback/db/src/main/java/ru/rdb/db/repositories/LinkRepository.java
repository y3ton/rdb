package ru.rdb.db.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;

public interface LinkRepository extends CrudRepository<Link, DbId> {

    @Query(
            nativeQuery = true,
            value =
            "SELECT id, grp, url, create_date, read_date " +
            "FROM public.links " +
            "where grp = :group and (read_date is null or read_date < :maxDate) " +
            "order by random() " +
            "limit 1")
    Link getRandom(@Param("group") String group, @Param("maxDate") Long maxDate);

}