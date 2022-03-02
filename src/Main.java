/*
Intro to Binary Files

Text files:
    (.txt, .csv)
    pros: human understandable
    cons: slow and inefficient

    To read:
        Scanner, BufferedReader
    To write:
        PrintWriter, BufferedWriter

 Binary files:
    (.bin, .dat, .exe)
    cons: machine understandable
    pros: fast and efficient

    To read:
        ObjectInputStream
    To write:
        ObjectOutputStream


*/

import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        String name, breed;
        int age;

        Dog[] dogPound = new Dog[10];
        int count = 0;

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Previously saved dogs from binary file: ");
        //Start reading from binary file
            File binaryFile = new File("Dogs.dat");

            //check to see if file exists AND non-zero size
            try {
                if (binaryFile.exists() && binaryFile.length() > 1L) //int(32bit), long(64bit)
                {
                    ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                    //Read the entire array into dogPound
                    //readObject returns Object
                    //Dog[] = object

                    //We need to typecast fileReader into an array
                    dogPound = (Dog[])fileReader.readObject();
                    //Loop through the array and print out all objects
                    while (dogPound[count] != null)
                        System.out.println(dogPound[count++]);
                    fileReader.close();
                }
                else
                    System.out.println("[None, please enter new dog data]");
            }
            catch (IOException | ClassNotFoundException e) //Bitwise 'or' if you're going to handle
                                                            // exceptions the same way.
            {
                System.out.println("Error: " + e.getMessage());
            }


        do {

            System.out.println("Please enter a dog's name, of \"quit\" to exit: ");
            name = keyboard.nextLine();
            if (name.equalsIgnoreCase("quit"))
                    break;
            System.out.println("Please enter dog's breed: ");
            breed = keyboard.nextLine();
            System.out.println("Please enter dog's age: ");
            age = keyboard.nextInt();

            //Todo: 1) create a new dog object 2)Add to array, 3) Increment count

            //count++: postfix - increments count AFTER indexing
            dogPound[count++] = new Dog(name, breed, age);

            //when we go from reading a number to reading an int, we have a 'dangling' \n
            //get rid of dangling \n
            keyboard.nextLine();

        } while (true);

        try {
            //After the user enters quit, write the dogPound array to binary file
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            fileWriter.writeObject(dogPound);
            fileWriter.close();
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

    }
}
