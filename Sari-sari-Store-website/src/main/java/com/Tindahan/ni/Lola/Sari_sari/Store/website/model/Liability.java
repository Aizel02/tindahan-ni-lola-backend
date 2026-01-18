package com.Tindahan.ni.Lola.Sari_sari.Store.website.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Liability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debtorName;
    private double amount;
    private String description;
    private LocalDate dueDate;
    private String status;
    private LocalDate paidDate;

    public Liability() {}

    // getters
    public Long getId() {
        return id;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    // setters
    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
}
