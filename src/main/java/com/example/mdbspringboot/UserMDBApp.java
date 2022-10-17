package com.example.mdbspringboot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.mdbspringboot.model.User;
import com.example.mdbspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;




@SpringBootApplication
@EnableMongoRepositories
public class UserMDBApp implements CommandLineRunner {

    @Autowired
    UserRepository userRepo;

    List<User> userList = new ArrayList<User>();

    public static void main(String[] args) {
        SpringApplication.run(UserMDBApp.class, args);
    }

    public void run(String... args) {

            deleteAllUsers();

            System.out.println("-------------CREATE USER-------------------------------\n");

            createUser();

            System.out.println("\n--------------GET USER BY USERNAME-----------------------------------\n");

            getUserByUserName("DillonScott");

            System.out.println("\n-----------GET USER BY EMAIL---------------------------------\n");

            getUserByEmail("dillon@gmail.com");

            System.out.println("\n-----------UPDATE USERNAME----------------\n");

            updateUserName("Dillon21", "Dillon2");

            System.out.println("\n-----------DELETE User BY USERNAME-----------------------------------\n");

            deleteUser("Dillon2");

            System.out.println("\n-----------SHOW ALL USERS--------------------------------\n");

            createUser();
            createUser();
            showAllUsers();

            //System.out.println("\n-----------DELETE ALL USERS--------------------------------------\n");

            //deleteAllUsers();
    }

    private void createUser() {
        Random rand = new Random();

        User user = new User(String.valueOf(rand.nextInt(1000)), "Dillon21", "hello", "dillon@gmail.com", "Dillon Scott");
        User user2 = new User(String.valueOf(rand.nextInt(1000)), "DillonScott", "hello", "dillon2@gmail.com", "Dillon Scott");
        userRepo.save(user);
        userRepo.save(user2);
        System.out.println("user " + user.getName() + " added to database");
    }

    private void getUserByUserName(String username) {
        System.out.println("Getting user by username: " + username);
        User user = userRepo.findUserByUsername(username);
        System.out.println(getUserDetails(user));
    }

    private String getUserDetails(User user) {
        return  "\nId: " + user.getId() +
                "\nUsername: " + user.getUsername() +
                "\nPassword: " + user.getPassword() +
                "\nEmail: " + user.getEmail() +
                "\nName: " + user.getName();
    }

    private void getUserByEmail(String email) {
        System.out.println("Getting user by name: " + email);
        User user = userRepo.findUserByEmail(email);
        System.out.println(getUserDetails(user));
    }

    private void updateUserName(String userName, String newUserName) {

        String newUsername = newUserName;

        // Find user with the given username
        User user = userRepo.findUserByUsername(userName);

        User newUser = user;
        newUser.setUsername(newUsername);

        //remove old username
        userRepo.delete(user);
        // Save all the items in database
        userRepo.save(newUser);

        if(user != null)
            System.out.println("Successfully updated username to" + newUserName);
    }


    private void deleteUser(String username) {
        User user = userRepo.findUserByUsername(username);
        userRepo.delete(user);
        System.out.println("Successfully deleted user " + username);
    }


    private void showAllUsers() {
        List<User> users = userRepo.findAll();
        for (User user : users) {
            System.out.println(getUserDetails(user));
        }
    }

    private void deleteAllUsers() {
        userRepo.deleteAll();
        System.out.println("Successfully deleted all users");
    }

}
