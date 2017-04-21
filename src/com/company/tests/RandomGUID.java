package com.company.tests;


import java.util.ArrayList;
import java.util.UUID;

public class RandomGUID {

    public static String createGUID() {
            UUID guid = UUID.randomUUID();
            String randomGIUD = guid.toString();
            return randomGIUD;
    }
}
