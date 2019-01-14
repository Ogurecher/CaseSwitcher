package com.Peredreev.CaseSwitcher;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;


public class ToCamelCaseAction extends SwitchCase {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        String nameToAlter = selectionModel.getSelectedText();

        String newName = changeName(nameToAlter);

        if (!newName.equals("")) {
            replaceAllInstances(project, document, nameToAlter, newName);
        }
    }

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
