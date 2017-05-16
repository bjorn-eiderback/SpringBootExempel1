package se.nackademin.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(MinListaRepository repository) {
        return (args) -> {
            MinLista lista1 = new MinLista("Lista1");
            Item item1 = new Item("Ett item");
            lista1.getItems().add(item1);
            repository.save(lista1);

            repository.findAll().forEach(lista -> {
                System.out.println("Listans namn: " + lista.getName());
                lista.getItems().forEach(item -> System.out.println("Item: " + item.getName()));
            });
        };
    }
}
