package com.fernanda.frontend.ui.dialogue;

import com.badlogic.gdx.utils.Array;

public class DialogueManager {
    private Array<DialogueLine> dialogueQueue;
    private int currentLineIndex;

    private String currentFullText;
    private String currentDisplayedText;
    private float typeTimer;
    private float charsPerSecond = 30f;

    private boolean isFinished;
    private boolean isTyping;

    public DialogueManager() {
        dialogueQueue = new Array<>();
        isFinished = true;
    }

    public void startDialogue(Array<DialogueLine> lines) {
        this.dialogueQueue.clear();
        this.dialogueQueue.addAll(lines);
        this.currentLineIndex = 0;
        this.isFinished = false;
        loadCurrentLine();
    }

    private void loadCurrentLine() {
        if (currentLineIndex < dialogueQueue.size) {
            currentFullText = dialogueQueue.get(currentLineIndex).getText();
            currentDisplayedText = "";
            typeTimer = 0f;
            isTyping = true;
        } else {
            isFinished = true;
        }
    }

    public void update(float delta) {
        if (isFinished || !isTyping) return;

        typeTimer += delta;
        int charsToShow = (int) (typeTimer * charsPerSecond);

        if (charsToShow < currentFullText.length()) {
            currentDisplayedText = currentFullText.substring(0, charsToShow);
        } else {
            currentDisplayedText = currentFullText;
            isTyping = false;
        }
    }

    public void handleInput() {
        if (isFinished) return;

        if (isTyping) {
            currentDisplayedText = currentFullText;
            isTyping = false;
        } else {
            currentLineIndex++;
            loadCurrentLine();
        }
    }

    public boolean isFinished() { return isFinished; }
    public String getDisplayedText() { return currentDisplayedText != null ? currentDisplayedText : ""; }
    public String getCurrentSpeaker() {
        if (isFinished || currentLineIndex >= dialogueQueue.size) return "";
        return dialogueQueue.get(currentLineIndex).getSpeaker();
    }
}
