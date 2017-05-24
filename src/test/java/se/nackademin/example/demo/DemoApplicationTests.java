package se.nackademin.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import javax.persistence.*;

import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;


//För enkelhets skull använder vi Date direkt trots att vissa av Date:s konstruktorer är deprikerade!

@RunWith(SpringRunner.class)
@DataJpaTest
public class DemoApplicationTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MinListaRepository repo;

	@Before
	public void init() {
		clear();
	}

	@Test
	public void testFindFirstName() {
		MinLista lista1 = new MinLista("Lista1");
		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		entityManager.persist(lista1);

		List<MinLista> findByNameLista = repo.findByName(lista1.getName());

		assertThat(findByNameLista).extracting(MinLista::getName).containsOnly(lista1.getName());
	}

	@Test
	public void testQuery() {
		//Enkelt test av query
		MinLista lista1 = new MinLista("Lista1");
		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		entityManager.persist(lista1);
		Query query = entityManager.getEntityManager().createQuery("SELECT item FROM Item item");

		assertThat(query.getResultList().size()).isEqualTo(1);
	}

	@Test
	public void testDate() {
		String lista1sNamn = "Lista1";
		MinLista lista1 = new MinLista(lista1sNamn);
		String lista2sNamn = "Lista2";
		MinLista lista2 = new MinLista(lista2sNamn);

		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		Date datum1 = new Date(117, 4, 5);
		Date datum2 = new Date(117, 4, 7);
		lista1.setDate(datum1);
		lista2.setDate(datum2);

		Item item1Lista2 = new Item("Ett item lista2");
		lista2.getItems().add(item1Lista2);
		entityManager.persist(lista1);
		entityManager.persist(lista2);
		Date searchDatum = new Date(117, 4, 5);
		List<MinLista> dateSearch = repo.findByDate(searchDatum);
		assertThat(dateSearch.size()).isEqualTo(1);
	}

	@Test
	public void testTime() {
		String lista1sNamn = "Lista1";
		MinLista lista1 = new MinLista(lista1sNamn);
		String lista2sNamn = "Lista2";
		MinLista lista2 = new MinLista(lista2sNamn);

		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		Date datum1 = new Date(117, 4, 5);
		Date datum2 = new Date(117, 4, 7);
		Date time1 = new Date(0, 0, 0, 10, 20);
		Date time2 = new Date(0, 0, 0, 8, 10);

		lista1.setDate(datum1);
		lista1.setTime(time1);
		lista2.setDate(datum2);
		lista2.setTime(time2);

		Item item1Lista2 = new Item("Ett item lista2");
		lista2.getItems().add(item1Lista2);
		entityManager.persist(lista1);
		entityManager.persist(lista2);
		Date searchDatum = new Date(117, 4, 5);
		List<MinLista> dateSearch = repo.findByDate(searchDatum);
		assertThat(dateSearch.size()).isEqualTo(1);
		List<MinLista> timeSearch = repo.findByTime(time1);
		assertThat(timeSearch.size()).isEqualTo(1);
	}

	@Test
	public void testTimeAndDate() {
		String lista1sNamn = "Lista1";
		MinLista lista1 = new MinLista(lista1sNamn);
		String lista2sNamn = "Lista2";
		MinLista lista2 = new MinLista(lista2sNamn);

		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		Date datum1 = new Date(117, 4, 5);
		Date time1 = new Date(0, 0, 0, 10, 20);
		Date time2 = new Date(0, 0, 0, 8, 10);

		lista1.setDate(datum1);
		lista1.setTime(time1);
		lista2.setDate(datum1);
		lista2.setTime(time2);

		Item item1Lista2 = new Item("Ett item lista2");
		lista2.getItems().add(item1Lista2);
		entityManager.persist(lista1);
		entityManager.persist(lista2);
		Date searchDatum = new Date(117, 4, 5);
		Date searchTime = new Date(0, 0, 0, 10, 20);

		List<MinLista> timeSearch = repo.findByTime(time1);
		assertThat(timeSearch.size()).isEqualTo(1);

		List<MinLista> searchResult = repo.findByDateAndTime(searchDatum, searchTime);
		assertThat(searchResult.size()).isEqualTo(1);
	}

	@Test
	public void testDateAndTimeAfter() {
		String lista1sNamn = "Lista1";
		MinLista lista1 = new MinLista(lista1sNamn);
		String lista2sNamn = "Lista2";
		MinLista lista2 = new MinLista(lista2sNamn);

		Item item1 = new Item("Ett item");
		lista1.getItems().add(item1);
		Date datum = new Date(117, 4, 5);
		Date time1 = new Date(0, 0, 0, 10, 20);
		Date time2 = new Date(0, 0, 0, 8, 10);

		lista1.setDate(datum);
		lista1.setTime(time1);
		lista2.setDate(datum);
		lista2.setTime(time2);

		Item item1Lista2 = new Item("Ett item lista2");
		lista2.getItems().add(item1Lista2);
		entityManager.persist(lista1);
		entityManager.persist(lista2);
		Date searchDatum = new Date(117, 4, 5);
		Date searchTime = new Date(0, 0, 0, 10, 0);

		List<MinLista> timeSearch = repo.findByTime(time1);
		assertThat(timeSearch.size()).isEqualTo(1);

		List<MinLista> searchResult = repo.findByDateAndTimeAfter(searchDatum, searchTime);
		assertThat(searchResult.size()).isEqualTo(1);
		Date searchTime2 = new Date(0, 0, 0, 10, 21);

		List<MinLista> searchResult2 = repo.findByDateAndTimeAfter(searchDatum, searchTime2);
		assertThat(searchResult2.size()).isEqualTo(0);
	}

	private void clear() {
		clearMinLista();
	}

	private void clearMinLista() {
		repo.deleteAll();
		//Enklare än
		//repo.delete(repo.findAll());
		//som är enklare än:
		/*List<MinLista> resultList = (List<MinLista>) repo.findAll();
		try {
			for (MinLista next : resultList) {
				repo.delete(next);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
