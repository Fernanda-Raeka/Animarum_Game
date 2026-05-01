package com.fernanda.frontend;

import com.badlogic.gdx.Game;
import com.fernanda.frontend.screens.GameplayScreen;
import com.fernanda.frontend.singleton.SessionManager;
// import com.fernanda.frontend.screens.LoginScreen;

public class Main extends Game {

    @Override
    public void create() {
        SessionManager.getInstance().setSession(999, "Tester");
        setScreen(new com.fernanda.frontend.screens.MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
