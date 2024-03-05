package pbo.fintech.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Account")
public class Account {
    @Id
    @Column(name="owner", nullable = false, length = 15)
    private String owner;
    @Column(name="name", nullable = false, length = 15)
    private String name;
    @Column(name="balance", nullable = false)
    private double balance=0.0;

    
    
    public Account() {
        //empty
    }

    public Account(String owner, String name, double balance) {

        this.owner = owner;
        this.name = name;
        this.balance = balance;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return owner + "|" + name + "|" + balance;
    }

    public String getAccountName() {
        return name;
    }

    
}
