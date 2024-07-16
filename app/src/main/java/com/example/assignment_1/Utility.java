package com.example.assignment_1;

import java.util.concurrent.ThreadLocalRandom;

public class Utility {

    public static String getRandomLetter()
    {
        char random_char;

        random_char = (char) ThreadLocalRandom.current().nextInt(65,92);

        return String.valueOf(random_char);

    }

}
