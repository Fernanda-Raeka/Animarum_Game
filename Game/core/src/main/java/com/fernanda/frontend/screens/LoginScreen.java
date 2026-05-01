package com.fernanda.frontend.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton; 
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginScreen implements Screen {

    private com.fernanda.frontend.Main game;
    private Stage stage;
    private Skin skin;

    public LoginScreen(com.fernanda.frontend.Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Sistem: Memasuki Layar Login!");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        com.badlogic.gdx.scenes.scene2d.ui.Table table = new com.badlogic.gdx.scenes.scene2d.ui.Table();
        table.setFillParent(true);
        //table.setDebug(true); // Debug

        com.badlogic.gdx.scenes.scene2d.ui.TextField usernameField = new com.badlogic.gdx.scenes.scene2d.ui.TextField("", skin);
        usernameField.setMessageText("Masukkan Username");

        com.badlogic.gdx.scenes.scene2d.ui.TextField passwordField = new com.badlogic.gdx.scenes.scene2d.ui.TextField("", skin);
        passwordField.setMessageText("Masukkan Password");
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        com.badlogic.gdx.scenes.scene2d.ui.TextButton loginButton = new com.badlogic.gdx.scenes.scene2d.ui.TextButton("LOGIN", skin);

        loginButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, com.badlogic.gdx.scenes.scene2d.Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                System.out.println("Sistem: Mengirim data ke server...");

                com.fernanda.frontend.singleton.NetworkManager.getInstance().login(username, password, new com.fernanda.frontend.singleton.NetworkManager.NetworkCallback() {

                    @Override
                    public void onSuccess(String response) {
                        System.out.println("Login Berhasil! Balasan Server: " + response);

                        com.badlogic.gdx.utils.JsonReader jsonReader = new com.badlogic.gdx.utils.JsonReader();
                        com.badlogic.gdx.utils.JsonValue jsonResponse = jsonReader.parse(response);

                        int id = jsonResponse.getInt("accountId");
                        String user = jsonResponse.getString("username");

                        com.fernanda.frontend.singleton.SessionManager.getInstance().setSession(id, user);
                        System.out.println("Sistem: Sesi disimpan untuk User ID: " + id);

                        game.setScreen(new MainMenuScreen(game));
                    }

                    @Override
                    public void onFailure(String error) {
                        System.out.println("Login Gagal: " + error);
                    }
                });
            }
        });

        table.add(new com.badlogic.gdx.scenes.scene2d.ui.Label("ANIMARUM", skin)).padBottom(30).row();
        table.add(usernameField).width(250).padBottom(15).row();
        table.add(passwordField).width(250).padBottom(20).row();
        table.add(loginButton).width(150).height(40);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (skin != null) skin.dispose();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
