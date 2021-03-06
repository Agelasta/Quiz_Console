package pl.stanczyk.quiz.logic.admin;

import pl.stanczyk.quiz.data.User;
import pl.stanczyk.quiz.interfaces.AdminInterface;
import pl.stanczyk.quiz.logic.questions.QuestionCategory;
import pl.stanczyk.quiz.logic.user.UsersList;

import java.io.*;

public class Admin implements AdminInterface {

    private String administratorPassword;
    private UsersList usersList;
    private QuestionCategory questionManager;

    public Admin() {
        usersList = new UsersList();
        questionManager = new QuestionCategory();
    }

    private void setPassword(String password) {
        administratorPassword = password;
    }

    private boolean checkPassword(String password) {
        if (password.equals(administratorPassword))
            return true;
        else return false;
    }

    public void fetchPassword() {
        try (var ois = new ObjectInputStream(new FileInputStream("adminPassword.obj"))) {
            String password = (String) ois.readObject();
            setPassword(password);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error while reading password");
        }
    }

    public void changePassword(BufferedReader bufferedReader) {

        System.out.println("<< CHANGING PASSWORD >>\n");
        System.out.println("Please enter new password:");

        try (var oos = new ObjectOutputStream(new FileOutputStream("adminPassword.obj"))) {
            String newPassword = bufferedReader.readLine();
            setPassword(newPassword);
            oos.writeObject(newPassword);
            System.out.println("Password changed.");
        } catch (IOException e) {
            System.err.println("Error - password not changed");
        }
    }

    @Override
    public boolean validateAdmin(BufferedReader bufferedReader) {

        String password = null;
        try {
            System.out.println("Please enter administrator password:");
            password = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }
        return checkPassword(password);
    }

    public void addUser(BufferedReader bufferedReader) {

        System.out.println("<< ADDING USER >>\n");
        String login = null;
        String password = null;

        try {
            System.out.println("Enter login:");
            login = bufferedReader.readLine();
            System.out.println("Enter password:");
            password = bufferedReader.readLine();
        } catch (IOException e) {
            System.err.println("Error while reading input");
        }
        usersList.addUser(new User(login, password));
    }

    public void removeUser(BufferedReader bufferedReader) {

        System.out.println("<< REMOVING USER >>\n");

        if (usersList.getSize() == 0) {
            System.out.println("There is no user registered.");
        } else {

            System.out.println("Enter login:");
            String login = null;

            try {
                login = bufferedReader.readLine();
            } catch (IOException e) {
                System.err.println("Error while reading input");
            }
            if (usersList.isUserExist(login)) {
                usersList.removeUser(login);
                System.out.println("User " + login + " removed.");
            } else System.out.println("User does not exist.");
        }
    }

    public void showUsers() {

        System.out.println("<< USERS LIST >>\n");
        if (usersList.getSize() > 0) {
            usersList.showList();
        } else System.out.println("There is no user registered.");
    }

    public void showQuestions(BufferedReader bufferedReader) {
        questionManager.showQuestions(bufferedReader);
    }

    public void addQuestion(BufferedReader bufferedReader) {
        questionManager.saveQuestion(bufferedReader);
    }

    public void removeQuestion(BufferedReader bufferedReader) {
        questionManager.removeQuestion(bufferedReader);
    }
}
