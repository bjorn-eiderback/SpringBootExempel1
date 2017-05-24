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
		Date datum = new Date();
		lista1.setDate(datum);
		entityManager.persist(lista1);
		Query query = entityManager.getEntityManager().createQuery("SELECT item FROM Item item");

		assertThat(query.getResultList().size()).isEqualTo(1);

		List<MinLista> dateSearch = repo.findByDate(new Date());
		assertThat(dateSearch.size()).isEqualTo(1);
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
