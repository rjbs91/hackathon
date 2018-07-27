package org.academiadecodigo.hackathon.echo.assets;

public enum Levels {

    LEVEL_1("level1/level1.tmx", 2300, 64, 4235, 224, 5182, 32),
    LEVEL_2("level2/level2.tmx", 2765, 128, 3685, 128, 4310, 16);

    public float npcY;
    public float npcX;
    public String path;
    public float keyX;
    public float keyY;
    public float closetX;
    public float closetY;

    Levels(String path, float keyX, float keyY, float closetX, float closetY, float npcX, float npcY) {

        this.path = path;
        this.closetX = closetX;
        this.closetY = closetY;
        this.keyX = keyX;
        this.keyY = keyY;
        this.npcX = npcX;
        this.npcY = npcY;

    }
}