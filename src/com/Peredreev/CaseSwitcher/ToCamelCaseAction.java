package com.Peredreev.CaseSwitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToCamelCaseAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getProject();
        final Document document = editor.getDocument();
        final SelectionModel selectionModel = editor.getSelectionModel();

        String nameToAlter = selectionModel.getSelectedText();
        String newNameDraft = "";

        String words[] = nameToAlter.split("[_-]");
        for (int i = 0; i < words.length; i++) {
            String lettersExceptFirst = words[i].substring(1);

            if (lettersExceptFirst.toUpperCase().equals(lettersExceptFirst)) {
                lettersExceptFirst = words[i].substring(1).toLowerCase();
            }

            words[i] = words[i].substring(0, 1).toUpperCase() + lettersExceptFirst;
            newNameDraft += words[i];
        }

        if (newNameDraft != "") {
            String text = document.getText();

            Pattern pattern = Pattern.compile("(^|[^A-Za-z0-9_-])"+nameToAlter+"([^A-Za-z0-9_-]|$)");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                final int start;
                final int end;

                if (matcher.start() == 0) {
                    start = matcher.start();
                } else {
                    start = matcher.start() + 1;
                }

                if (matcher.end() == text.length()) {
                    end = matcher.end();
                } else {
                    end = matcher.end() - 1;
                }

                final String newName = newNameDraft;
                Runnable r = () -> document.replaceString(start, end, newName);
                WriteCommandAction.runWriteCommandAction(project, r);

                text = document.getText();
                matcher = pattern.matcher(text);
            }
        }
    }
}
