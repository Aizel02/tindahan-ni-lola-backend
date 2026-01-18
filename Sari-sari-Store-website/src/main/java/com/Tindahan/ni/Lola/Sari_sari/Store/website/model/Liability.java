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

    private String status;     // Pending | Paid
    private LocalDate paidDate; // when debt was paid

    // ✅ REQUIRED empty constructor
    public Liability() {}

    // Optional constructor
    public Liability(String debtorName, double amount, String description,
                     LocalDate dueDate, String status) {
        this.debtorName = debtorName;
        this.amount = amount;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // ✅ REQUIRED FOR CONTROLLER
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ✅ NEW FIELD FOR PAYMENT DATE
    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
}
