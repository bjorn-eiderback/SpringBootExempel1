package se.nackademin.example.demo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class MinLista {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    //Vi skapar en date för datumet. Vi markerar det som TemporalType.DATE så att det bara tolkas som date
    //(vi väljer att sätta datum och klocklslag på listan och inte på item i detta exempel)
    @Temporal(value = TemporalType.DATE)
    private Date date;
    //och vi hanterar klockslaget i ett Date med typen TemporalType.TIME
    @Temporal(value = TemporalType.TIME)
    private Date time;

    //Vi vill att ändringar ska propagera till delarna och att "items" ska hämtas (EAGER) då vi hämtar en lista
    //Observera att relationen är enkelriktad från listan till dess delar
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    public MinLista() {
    }

    public MinLista(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
