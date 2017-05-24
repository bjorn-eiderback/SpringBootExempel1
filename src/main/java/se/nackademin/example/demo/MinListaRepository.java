package se.nackademin.example.demo;


import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface MinListaRepository extends CrudRepository<MinLista, Long> {
    List<MinLista> findByName(String name);
    List<MinLista> findByDate(Date date);
    List<MinLista> findByTime(Date time);
    List<MinLista> findByDateAndTime(Date date, Date time);
    List<MinLista> findByDateAndTimeAfter(Date date, Date time);
    List<MinLista> findByDateBetween(Date date1, Date date2);
}
