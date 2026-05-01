package com.fernanda.frontend.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fernanda.frontend.Main;
import com.fernanda.frontend.entities.Player;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.SharedStats;
import com.fernanda.frontend.entities.MagicianBoss;
import com.fernanda.frontend.entities.projectiles.BaseProjectile;
import com.fernanda.frontend.observer.GameObserver;
import com.badlogic.gdx.math.Intersector;

public class GameplayScreen implements Screen, GameObserver {
    private final Main game;
    private Stage stage;
    private Skin skin;
    private ShapeRenderer shapeRenderer;

    private final float ARENA_SIZE = 500f;
    private float arenaX, arenaY;

    private com.badlogic.gdx.utils.Array<AnimaSpark> sparks;

    private Player player1;
    private Player player2;

    private SharedStats sharedStats;
    private Label playerHpLabel;
    private float currentHpRatio = 1f;
    private Color hpColor = Color.GREEN;

    private MagicianBoss boss;
    private float bossHpRatio = 1f;

    private boolean isPaused = false;
    private Table pauseTable;
    private Table confirmExitTable;

    public GameplayScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        shapeRenderer = new ShapeRenderer();

        arenaX = (Gdx.graphics.getWidth() - ARENA_SIZE) / 2f;
        arenaY = (Gdx.graphics.getHeight() - ARENA_SIZE) / 2f;

        sharedStats = new SharedStats(100f);
        sharedStats.addObserver(this);

        player1 = new Player(
                arenaX + 100f, arenaY + 300f, Color.BLUE,
                Input.Keys.W, Input.Keys.S, Input.Keys.A, Input.Keys.D, sharedStats);

        player2 = new Player(
                arenaX + 500f, arenaY + 300f, Color.RED,
                Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, sharedStats);

        boss = new MagicianBoss(arenaX, arenaY, ARENA_SIZE);
        boss.addObserver(this);

        sparks = new com.badlogic.gdx.utils.Array<>();

        Table uiTable = new Table();
        uiTable.setFillParent(true);

        TextButton menuBtn = new TextButton("MENU", skin);
        
        menuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!confirmExitTable.isVisible()) {
                    isPaused = true;
                    pauseTable.setVisible(true);
                }
            }
        });

        Label bossNameLabel = new Label("BOSS: THE MAGICIAN", skin);

        Table bossTable = new Table();
        bossTable.add(bossNameLabel).row();

        playerHpLabel = new Label("SOULS HP", skin);
        playerHpLabel.setColor(Color.WHITE);

        uiTable.top().left();
        uiTable.add(menuBtn).width(100).height(40).pad(20);
        uiTable.add(bossTable).expandX().align(Align.center).padTop(20).padRight(120).row();
        uiTable.add(playerHpLabel).colspan(2).expand().align(Align.bottom).padBottom(50);

        stage.addActor(uiTable);

        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(createTransparentPixmap()))));
        pauseTable.setVisible(false);

        Label pauseLabel = new Label("PAUSED", skin);
        pauseLabel.setFontScale(2f);
        pauseLabel.setColor(Color.YELLOW);

        TextButton continueBtn = new TextButton("CONTINUE", skin);
        TextButton exitToMenuBtn = new TextButton("EXIT TO MENU", skin);

        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPaused = false;
                pauseTable.setVisible(false);
            }
        });

        exitToMenuBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseTable.setVisible(false);
                confirmExitTable.setVisible(true);
            }
        });

        pauseTable.add(pauseLabel).padBottom(30).row();
        pauseTable.add(continueBtn).width(200).height(50).padBottom(10).row();
        pauseTable.add(exitToMenuBtn).width(200).height(50);
        stage.addActor(pauseTable);

        confirmExitTable = new Table();
        confirmExitTable.setFillParent(true);
        confirmExitTable.setBackground(pauseTable.getBackground());
        confirmExitTable.setVisible(false);

        Label warnLabel = new Label("Apakah anda yakin ingin keluar?\nJika keluar maka progress chapter ini tidak akan tersimpan.", skin);
        warnLabel.setAlignment(Align.center);
        warnLabel.setColor(Color.RED);

        TextButton yesBtn = new TextButton("YES", skin);
        TextButton noBtn = new TextButton("NO", skin);

        yesBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StageSelectionScreen(game));
            }
        });

        noBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                confirmExitTable.setVisible(false);
                pauseTable.setVisible(true);
            }
        });

        confirmExitTable.add(warnLabel).padBottom(30).row();
        Table confirmBtnTable = new Table();
        confirmBtnTable.add(yesBtn).width(100).height(40).padRight(20);
        confirmBtnTable.add(noBtn).width(100).height(40);
        confirmExitTable.add(confirmBtnTable);
        stage.addActor(confirmExitTable);
    }

    private Pixmap createTransparentPixmap() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f));
        pixmap.fillRectangle(0, 0, 1, 1);
        return pixmap;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (!confirmExitTable.isVisible()) {
                isPaused = !isPaused;
                pauseTable.setVisible(isPaused);
            }
        }

        if (!isPaused) {
            player1.update(delta, arenaX, arenaY, ARENA_SIZE);
            player2.update(delta, arenaX, arenaY, ARENA_SIZE);
            boss.update(delta, arenaX, arenaY, ARENA_SIZE, sparks);

            for (int i = sparks.size - 1; i >= 0; i--) {
                AnimaSpark spark = sparks.get(i);

                boolean p1Inside = Intersector.overlaps(player1.getBounds(), spark.getBounds());
                boolean p2Inside = Intersector.overlaps(player2.getBounds(), spark.getBounds());

                spark.update(delta, p1Inside, p2Inside);

                if (spark.isCollected()) {
                    sparks.removeIndex(i);
                    boss.takeDamage(100f);
                    com.fernanda.frontend.factory.SparkFactory.freeSpark(spark);
                } else if (spark.isExpired()) {
                    sparks.removeIndex(i);
                    sharedStats.takeDamage(5f);
                    com.fernanda.frontend.factory.SparkFactory.freeSpark(spark);
                }
            }

            com.badlogic.gdx.utils.Array<BaseProjectile> projectiles = boss.getProjectiles();
            for (int i = projectiles.size - 1; i >= 0; i--) {
                BaseProjectile p = projectiles.get(i);
                p.update(delta);

                if (p.active) {
                    p.checkCollision(player1, sharedStats);
                    p.checkCollision(player2, sharedStats);
                }

                if (!p.active) {
                    projectiles.removeIndex(i);
                    com.fernanda.frontend.factory.ProjectileFactory.freeProjectile(p);
                }
            }
        }

        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (AnimaSpark spark : sparks) {
            spark.render(shapeRenderer);
        }

        player1.render(shapeRenderer);
        player2.render(shapeRenderer);
        boss.render(shapeRenderer);

        for (BaseProjectile p : boss.getProjectiles()) {
            p.render(shapeRenderer);
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(arenaX, arenaY, ARENA_SIZE, ARENA_SIZE);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float barWidth = 400f;
        float barHeight = 20f;
        float barX = (Gdx.graphics.getWidth() - barWidth) / 2f;
        float barY = 20f;

        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        shapeRenderer.setColor(hpColor);
        shapeRenderer.rect(barX, barY, barWidth * currentHpRatio, barHeight);

        float bossBarY = Gdx.graphics.getHeight() - 100f;
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(barX, bossBarY, barWidth, barHeight);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(barX, bossBarY, barWidth * bossHpRatio, barHeight);

        shapeRenderer.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        arenaX = (width - ARENA_SIZE) / 2f;
        arenaY = (height - ARENA_SIZE) / 2f;
    }

    @Override
    public void onPlayerHPChanged(float currentHp, float maxHp) {
        currentHpRatio = currentHp / maxHp;

        if (currentHpRatio <= 0.3f) {
            hpColor = Color.RED;
        } else if (currentHpRatio <= 0.6f) {
            hpColor = Color.ORANGE;
        } else {
            hpColor = Color.GREEN;
        }
    }

    @Override
    public void onBossHPChanged(float currentHp, float maxHp) {
        bossHpRatio = currentHp / maxHp;
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
        shapeRenderer.dispose();
    }
}