package com.example.ticketapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters long")
    private String name;

    @NotNull(message = "Quota may not be null")
    private Integer quota;

    @NotNull(message = "Start date may not be null")
    private Timestamp startDate;

    @NotNull(message = "End date may not be null")
    private Timestamp endDate;

    @NotNull(message = "Price may not be null")
    private BigDecimal price;

    @NotNull(message = "Company may not be null")
    @ManyToOne
    private Company company;

    @NotNull(message = "Route may not be null")
    @ManyToOne
    private Route route;

    @NotNull(message = "Airport may not be null")
    @ManyToOne
    private Airport airport;

    @OneToMany
    private Set<Ticket> tickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuota() { return quota; }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
