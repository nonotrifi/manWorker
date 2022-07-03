package com.example.demo;

public class Utils {
    /* This function gets a value for a field ( for example " Alice " for " name " ) and if the value is blank ( for example
     * field = "" it would return a message saying the text field name ( which is " name " in the example ) and returns
     * " name field cannot be blank "
     *
     * If we don't put static I have to create a utils object to use the method
     * */
    public static String checkIfBlank(String field, String fieldName){
        if (field.isEmpty()) {
            return fieldName + " field cannot be blank";
        }

        return "Confirm";
    }

    public static String checkLength(String field, String fieldName){
        if(field.length() < 5 || field.length() > 25){
            return fieldName + " cannot be less than 5 and greator than 25 characters.";
        }

        return "Confirm";
    }

    // We created this for name in the planning
    public static String checkField(String[] field){
        String message1, message2;

        // grace a la method static dans utils
        message1 = Utils.checkIfBlank(field[1], field[0]);
        message2 = Utils.checkLength(field[1], field[0]);

        if(message1.compareTo("Confirm") != 0)
            return message1;
        else if(message2.compareTo("Confirm") != 0)
            return message2;

        return "Confirm";
    }

    // Checkfields it's used for many fields (javafx)
    public static String checkFields(String[][] fields){
        String message;

        for(String[] field: fields){
            // grace a la method static dans utils
            message = checkField(field);

            if(message.compareTo("Confirm") != 0)
                return message;
        }

        return "Confirm";
    }

    public static boolean isNumeric(String string) {

        if(string == null || string.equals("")) {
            return false;
        }

        // Avant de parse c'est un string l'objectf avec parseInt est de le transformer en Integer
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
