package com.example.moneytracker.models;

import javax.persistence.*;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Category category;

    @ManyToOne
    @JoinColumn
    private Users user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double ammount;

    @Column
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmmount() {
        return ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
