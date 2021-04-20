package pl.lukakani.homebudget.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private Long id;
    private Type type;
    private String description;
    private BigDecimal amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public Transaction(Type type, String description, BigDecimal amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(Long id, Type type, String description, BigDecimal amount, LocalDate date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public enum Type {
        INCOME, EXPENSE;
    }
}
