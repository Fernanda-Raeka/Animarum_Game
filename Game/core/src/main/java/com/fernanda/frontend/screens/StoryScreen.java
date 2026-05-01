package com.fernanda.frontend.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
// import com.badlogic.gdx.graphics.Texture; // blom ad gambar
// import com.badlogic.gdx.graphics.g2d.SpriteBatch; // blm ad gambaar jg
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fernanda.frontend.Main;
import com.fernanda.frontend.ui.dialogue.DialogueLine;
import com.fernanda.frontend.ui.dialogue.DialogueManager;

public class StoryScreen implements Screen {
    private final Main game;
    private final Screen nextScreen;

    private DialogueManager dialogueManager;
    private Stage stage;
    private ShapeRenderer shapeRenderer;

    private Label speakerLabel;
    private Label textLabel;

    // blm ada gambar ama background
    // private SpriteBatch batch;
    // private Texture background;
    // private Texture characterPortrait;

    private final float DIALOG_HEIGHT = 200f;
    private final float MARGIN = 30f;

    public StoryScreen(Main game, Array<DialogueLine> lines, Screen nextScreen) {
        this.game = game;
        this.nextScreen = nextScreen;

        dialogueManager = new DialogueManager();
        dialogueManager.startDialogue(lines);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        shapeRenderer = new ShapeRenderer();

        // batch = new SpriteBatch();
        // background = new Texture("assets/bg_ruins.png"); // placeholder
        // characterPortrait = new Texture("assets/magician_portrait.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(1.5f);

        Label.LabelStyle nameStyle = new Label.LabelStyle(font, Color.YELLOW);
        Label.LabelStyle textStyle = new Label.LabelStyle(font, Color.WHITE);

        speakerLabel = new Label("", nameStyle);
        textLabel = new Label("", textStyle);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.topLeft);

        Table table = new Table();
        table.bottom().left();
        table.pad(MARGIN + 20f);
        table.setFillParent(true);

        table.add(speakerLabel).left().padBottom(15f).row();
        table.add(textLabel).width(Gdx.graphics.getWidth() - (MARGIN * 2) - 40f).height(DIALOG_HEIGHT - 60f).left().top();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        dialogueManager.update(delta);
        speakerLabel.setText("[" + dialogueManager.getCurrentSpeaker() + "]");
        textLabel.setText(dialogueManager.getDisplayedText());

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            dialogueManager.handleInput();

            if (dialogueManager.isFinished()) {
                game.setScreen(nextScreen);
                return;
            }
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1); // Warna biru gelap kusam (bisa diganti foto)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //  gambar foto buat nanti klo dh ad aset
        // batch.begin();
        // batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // batch.draw(characterPortrait, Gdx.graphics.getWidth() / 2f - 150f, 200f, 300f, 400f);
        // batch.end();
        // ---

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.85f);
        float screenW = Gdx.graphics.getWidth();
        shapeRenderer.rect(MARGIN, MARGIN, screenW - (MARGIN * 2), DIALOG_HEIGHT);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        // batch.dispose();
        // background.dispose();
        // characterPortrait.dispose();
    }
}
