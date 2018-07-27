package com.academiadecodigo.hackathon.echo.assets;

public enum Levels {

    LEVEL_1("level1/level1.tmx", 2300, 64, 4235, 224),
    LEVEL_2("level2/level2.tmx", 2260, 112, 3780, 16);

    public String path;
    public float keyX;
    public float keyY;
    public float closetX;
    public float closetY;

    Levels(String path, float keyX, float keyY, float closetX, float closetY) {

        this.path = path;
        this.closetX = closetX;
        this.closetY = closetY;
        this.keyX = keyX;
        this.keyY = keyY;

    }
}