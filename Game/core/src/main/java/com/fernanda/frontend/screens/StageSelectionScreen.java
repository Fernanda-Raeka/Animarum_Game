package com.fernanda.frontend.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fernanda.frontend.Main;

public class StageSelectionScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Skin skin;

    private Texture sphereTexture;
    private Texture lineTexture;
    private Texture popupBgTexture;
    private Texture bossPhotoPlaceholderTexture;

    private final int totalUnlockedStages = 3;
    private final String[] chapterTitles = { "CH 1: The Beginning", "CH 2: Deep Woods", "CH 3: The Illusion" };
    private final String[] bossNames = { "The Magician", "The Hermit", "The Wheel of Fortune" };
    private final int[] starRecords = { 3, 1, 0 };

    private final String[] bossLore = {
            "Seorang pesulap ulung yang terperangkap dalam delusi kekuasaannya sendiri. Dia adalah dalang yang mengendalikan AnimaSpark dengan trik dan ilusi rumit...",
            "Pertapa yang bersembunyi di balik kabut hutan yang dalam. Dia bukan hanya mengawasi, tetapi juga memanipulasi ruang di sekelilingnya...",
            "Roda nasib yang berputar tanpa henti. Tidak ada yang tahu apakah dia membawa keberuntungan atau malapetaka, karena nasib hanyalah siklus..."
    };

    private Table popupTable;
    private Image popupBossImage;
    private Label popupTitleLabel;
    private Label popupBossLabel;
    private Label popupStarsLabel;
    private Label popupLoreLabel;

    public StageSelectionScreen(Main game) {
        this.game = game;
        generateAssets();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Label titleLabel = new Label("CHAPTER SELECTION", skin);
        titleLabel.setColor(Color.WHITE);
        titleLabel.setFontScale(1.8f);

        TextButton backBtn = new TextButton("BACK TO MENU", skin);
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table constellationTable = new Table();
        for (int i = 0; i < totalUnlockedStages; i++) {
            final int stageIndex = i;

            Image sphereImage = new Image(sphereTexture);
            sphereImage.setOrigin(Align.center);

            sphereImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showPopup(stageIndex);
                }
            });

            constellationTable.add(sphereImage).size(64, 64).pad(30);

            if (i < totalUnlockedStages - 1) {
                Image lineImage = new Image(lineTexture);
                constellationTable.add(lineImage).width(150).height(2);
            }
        }

        ScrollPane.ScrollPaneStyle emptyScrollStyle = new ScrollPane.ScrollPaneStyle();
        ScrollPane scrollPane = new ScrollPane(constellationTable, emptyScrollStyle);
        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setFadeScrollBars(false);

        rootTable.top();
        rootTable.add(titleLabel).padTop(70).row();
        rootTable.add(scrollPane).expand().fillX().align(Align.center).row();
        rootTable.add(backBtn).padBottom(50).width(200).height(45);

        stage.addActor(rootTable);

        createPopup();
    }

    private void createPopup() {
        popupTable = new Table();
        popupTable.setBackground(new TextureRegionDrawable(new TextureRegion(popupBgTexture)));

        popupTable.setSize(600, 400);
        popupTable.setPosition((Gdx.graphics.getWidth() - 600) / 2f, (Gdx.graphics.getHeight() - 400) / 2f);
        popupTable.setVisible(false);
        popupTable.align(Align.top);

        popupBossImage = new Image(bossPhotoPlaceholderTexture);
        popupTable.add(popupBossImage).size(250, 300).pad(20);

        Table detailsTable = new Table();
        detailsTable.top().left();

        popupTitleLabel = new Label("", skin);
        popupTitleLabel.setFontScale(1.3f);
        popupTitleLabel.setColor(Color.WHITE);

        popupBossLabel = new Label("", skin);
        popupBossLabel.setColor(Color.LIGHT_GRAY);

        popupStarsLabel = new Label("", skin);
        popupStarsLabel.setColor(Color.GOLD);

        popupLoreLabel = new Label("", skin);
        popupLoreLabel.setWrap(true);
        popupLoreLabel.setColor(Color.WHITE);

        detailsTable.add(popupTitleLabel).align(Align.left).padBottom(5).row();
        detailsTable.add(popupBossLabel).align(Align.left).padBottom(20).row();
        detailsTable.add(popupStarsLabel).align(Align.left).padBottom(30).row();

        Table loreWrapperTable = new Table();
        loreWrapperTable.add(popupLoreLabel).width(280).align(Align.left);
        detailsTable.add(loreWrapperTable).align(Align.left).expandX().row();

        popupTable.add(detailsTable).expand().fillY().top().pad(20).row();

        TextButton playBtn = new TextButton("ENTER ARENA", skin);
        TextButton closeBtn = new TextButton("CLOSE", skin);

        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameplayScreen(game));
            }
        });

        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popupTable.setVisible(false);
            }
        });

        Table btnTable = new Table();
        btnTable.add(playBtn).width(150).height(40).padRight(20);
        btnTable.add(closeBtn).width(150).height(40);

        popupTable.add(btnTable).colspan(2).padBottom(20).align(Align.center);

        stage.addActor(popupTable);
    }

    private void showPopup(int index) {
        popupTitleLabel.setText(chapterTitles[index]);
        popupBossLabel.setText("Boss: " + bossNames[index]);
        popupLoreLabel.setText(bossLore[index]);

        StringBuilder starsStr = new StringBuilder();
        int obtainedStars = starRecords[index];
        for (int i = 0; i < 3; i++) {
            if (i < obtainedStars)
                starsStr.append("★ ");
            else
                starsStr.append("☆ ");
        }
        popupStarsLabel.setText(starsStr.toString());

        popupTable.setVisible(true);
    }

    private void generateAssets() {
        Pixmap pxSphere = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        pxSphere.setColor(new Color(0.1f, 0.4f, 0.5f, 0.15f));
        pxSphere.fillCircle(32, 32, 32);
        pxSphere.setColor(new Color(0.2f, 0.6f, 0.7f, 0.7f));
        pxSphere.fillCircle(32, 32, 20);
        sphereTexture = new Texture(pxSphere);
        pxSphere.dispose();

        Pixmap pxLine = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pxLine.setColor(new Color(0.6f, 0.6f, 0.6f, 0.4f));
        pxLine.fillRectangle(0, 0, 1, 1);
        lineTexture = new Texture(pxLine);
        pxLine.dispose();

        Pixmap pxBg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pxBg.setColor(new Color(0, 0, 0, 0.90f));
        pxBg.fillRectangle(0, 0, 1, 1);
        popupBgTexture = new Texture(pxBg);
        pxBg.dispose();

        Pixmap pxBoss = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pxBoss.setColor(new Color(0.1f, 0.1f, 0.15f, 1f));
        pxBoss.fillRectangle(0, 0, 1, 1);
        bossPhotoPlaceholderTexture = new Texture(pxBoss);
        pxBoss.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        sphereTexture.dispose();
        lineTexture.dispose();
        popupBgTexture.dispose();
        bossPhotoPlaceholderTexture.dispose();
    }
}