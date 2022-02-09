package com.apiclient;

import java.util.Scanner;

public class MyProgram {
    private ApiClient myApiClient;

    // Vår konstruktor som skapar ett nytt ApiClient-objekt
    public MyProgram() {
        myApiClient = new ApiClient("http://127.0.0.1:8080/api/v1");
    }

    // Där vårt program startar
    public void start() {
        boolean programRunning = true;

        while (programRunning) {
            System.out.println();
            System.out.println("=========================================");
            System.out.println("What would you like to do?");
            System.out.println("1. Add a actor to the list");
            System.out.println("2. Get list of actors");
            System.out.println("3. Get a specific actor");
            System.out.println("4. update an actor");
            System.out.println("5. delete an actor");
            System.out.println("6. Clear list of actors");
            System.out.println("7. Exit program");
            System.out.println("=========================================");
            System.out.println();

            int userChoice = getUserInt();


            switch (userChoice) {
                case 1:
                    addActor();
                    break;
                case 2:
                    getListOfActors();
                    break;
                case 3:
                    getActorById();
                    break;
                case 4:
                    updateActor();
                    break;
                case 5:
                    deleteActor();
                    break;
                case 6:
                    deleteAllActors();
                    break;

                case 7:
                    System.out.println("Goodbye.");
                    programRunning = false;
            }

        }
    }


    //CRUD - Create
    public void addActor() {
        System.out.println("What's the actor's name?");
        String name = getUserString();

        System.out.println("What is the age of the actor?");
        int age = getUserInt();

        Actor newActor = new Actor(name, age);

        boolean success = myApiClient.addActor(newActor);

        if (success) {
            System.out.println("Actor added!");
        } else {
            System.out.println("Issue adding actor.");
        }

    }

    //CRUD - Read
    public void getListOfActors() {
        Actor[] actors = myApiClient.getActors();

        System.out.println("Actors");
        System.out.println("-----------------------------------------");

        if (actors.length > 0) {
            for (int i = 0; i < actors.length; i++) {
                int id = actors[i].id;
                String name = actors[i].name;
                int age = actors[i].age;

                System.out.printf("-> %d %s %d SEK\n", id, name, age);
            }
        } else {
            System.out.println("No actors on the list ");
        }
    }

    //CRUD - Read a specific actor by Id
    public void getActorById() {
        System.out.println("What is the actor Id you want to see?");
        int actorId = getUserInt();
        Actor actorObject = myApiClient.getActorById(actorId);

        if(actorObject.name != null){
            System.out.println("-----------------------------------------");
            System.out.println(actorObject);

            System.out.println("Details of Actor Id [" + actorId + "] is: " + actorObject.name +" " + actorObject.age);
        } else {
            System.out.printf("There is no actor with id %d in the list", actorId);
        }

    }

    //CRUD - Update
    public void updateActor() {
        System.out.println("What is the actor Id to update?");
        int actorId = getUserInt();
        Actor actorObject = myApiClient.getActorById(actorId);
        if(actorObject.name != null){
            System.out.println("What's the new actor name?");
            String name = getUserString();
            System.out.println("What is the new age ?");
            int age = getUserInt();

            Actor actorChanges = new Actor(name, age);
            System.out.println("actor changes " + actorChanges);
            boolean success = myApiClient.updateActor(actorId, actorChanges);

            if (success) {
                System.out.println("Actor updated!");
            } else {
                System.out.println("Issue updating actor.");
            }
        } else {
            System.out.printf("There is no actor with id %d in the list", actorId);
        }

    }


    // CRUD - Delete blog by Id
    public void deleteActor() {
        System.out.println("What is the actor Id to delete?");
        int actorId = getUserInt();

        Actor actorObject = myApiClient.getActorById(actorId);

        if(actorObject.name != null){
            if (myApiClient.deleteActor(actorId)) {
                System.out.println("Actor is deleted!");
            } else {
                System.out.println("Issue deleting the actor");
            }
        } else {
            System.out.printf("There is no actor with id %d in the list", actorId);
        }


    }
    //CRUD - Delete all the list
    public void deleteAllActors() {
        if (myApiClient.deleteAllActors()) {
            System.out.println("List of actors cleared!");
        } else {
            System.out.println("Issue clearing list of actors.");
        }
    }


    public String getUserString() {
        Scanner myScanner = new Scanner(System.in);

        String myString;

        while (true) {
            try {
                System.out.print("> ");
                myString = myScanner.nextLine();
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myString;
    }
    public int getUserInt() {
        Scanner myScanner = new Scanner(System.in);

        int myInteger;

        while (true) {
            try {
                System.out.print("> ");
                myInteger = Integer.parseInt(myScanner.nextLine());
                break;
            } catch (Exception e) {
                //System.out.println("Exception: " + e);
                System.out.println("Felaktig indata");
            }
        }

        return myInteger;
    }
}
