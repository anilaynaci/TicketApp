package com.example.ticketapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String ticketNumber = UUID.randomUUID().toString();

    private Timestamp cDate = new Timestamp(System.currentTimeMillis());

    private Boolean isCancelled = false;

    @NotNull(message = "User may not be null")
    @ManyToOne
    private User user;

    @NotNull(message = "Flight may not be null")
    @ManyToOne
    private Flight flight;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Timestamp getcDate() {
        return cDate;
    }

    public Boolean getCancelled() {
        return isCancelled;
    }

    public void setCancelled(Boolean cancelled) {
        isCancelled = cancelled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
