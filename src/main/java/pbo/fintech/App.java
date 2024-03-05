package pbo.fintech;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

import pbo.fintech.model.Account;


public class App {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        factory = Persistence.createEntityManagerFactory("fintech_pu");
        entityManager = factory.createEntityManager();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            String[] tokens = command.split("#");

            if (tokens[0].equals("create-account")) {
                String owner = tokens[2];
                String accountName = tokens[1];
                double balance = 0.0;

                // Check if an account with the same owner already exists
                List<Account> accounts = entityManager.createQuery(
                        "SELECT a FROM Account a WHERE a.owner = :owner", Account.class)
                        .setParameter("owner", owner)
                        .getResultList();
                if (!accounts.isEmpty()) {
                    continue;
                }

                Account account = new Account(owner, accountName, balance);
                entityManager.getTransaction().begin();
                entityManager.persist(account); 
                entityManager.getTransaction().commit();

                String output = owner + "|" + accountName + "|" + balance;
                System.out.println(output);
            } else if (tokens[0].equals("show-accounts")) {
                List<Account> accounts = entityManager.createQuery(
                        "SELECT a FROM Account a ORDER BY a.owner ASC", Account.class)
                        .getResultList();
            
                for (Account account : accounts) {
                    String output = account.getOwner() + "|" + account.getAccountName() + "|" + account.getBalance();
                    System.out.println(output);
                }
            } else if (tokens[0].equals("remove-account")) {
                String accountName = tokens[1].toLowerCase();
                entityManager.getTransaction().begin();
                int deletedCount = entityManager.createQuery("DELETE FROM Account a WHERE a.owner = :accountName", Account.class)
                        .setParameter("accountName", accountName)
                        .executeUpdate();
                entityManager.getTransaction().commit();
    
                if (deletedCount > 0) {
                } else {
                }
            } else {
                break;
            }
        }

        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Account a").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        scanner.close();
    }
}