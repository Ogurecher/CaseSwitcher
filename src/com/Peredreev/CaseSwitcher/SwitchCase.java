package com.Peredreev.CaseSwitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SwitchCase extends AnAction {

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

    protected abstract String changeName(String nameToAlter);

    protected void replaceAllInstances(Project project, Document document, String nameToAlter, String draftedName) {
        String text = document.getText();

        Pattern pattern = Pattern.compile("(^|[^A-Za-z0-9_-])" + nameToAlter + "([^A-Za-z0-9_-]|$)");
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

            final String newName = draftedName;
            Runnable r = () -> document.replaceString(start, end, newName);
            WriteCommandAction.runWriteCommandAction(project, r);

            text = document.getText();
            matcher = pattern.matcher(text);
        }
    }
}
