package com.Peredreev.CaseSwitcher;

public class ToKebabCaseAction extends SwitchCase {

    protected String changeName(String nameToAlter) {

        nameToAlter = removePrefix(nameToAlter);

        String newNameDraft = "";
        String[] words = nameToAlter.split("(_)|(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
        for (int i = 0; i < words.length; i++) {
            newNameDraft += words[i] + "-";
        }
        return newNameDraft.substring(0, newNameDraft.length() - 1).toLowerCase();
    }
}
