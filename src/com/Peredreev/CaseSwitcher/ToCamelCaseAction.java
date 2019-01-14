package com.Peredreev.CaseSwitcher;

public class ToCamelCaseAction extends SwitchCase {

    protected String changeName(String nameToAlter){

        while (!nameToAlter.matches("[A-Za-z].*")) {
            nameToAlter = nameToAlter.substring(1);
        }

        String newNameDraft = "";
        String[] words = nameToAlter.split("[_-]");
        for (int i = 0; i < words.length; i++) {
            String lettersExceptFirst = words[i].substring(1);

            if (lettersExceptFirst.toUpperCase().equals(lettersExceptFirst)) {
                lettersExceptFirst = words[i].substring(1).toLowerCase();
            }

            words[i] = words[i].substring(0, 1).toUpperCase() + lettersExceptFirst;
            newNameDraft += words[i];
        }
        return newNameDraft;
    }
}
