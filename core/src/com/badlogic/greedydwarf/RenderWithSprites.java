package com.badlogic.greedydwarf;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.AfterAction;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import java.util.ArrayList;

public class RenderWithSprites extends OrthogonalTiledMapRenderer {
    private Sprite sprite;
    private ArrayList<Sprite> sprites;
    private int drawSpritesAfterLayer = 3;

    public RenderWithSprites(TiledMap map) {
        super(map);
        sprites = new ArrayList<Sprite>();
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;
        for (MapLayer layer : map.getLayers()) {
            if(layer.isVisible()) {
                renderTileLayer((TiledMapTileLayer) layer);
                currentLayer++;
                if(currentLayer == drawSpritesAfterLayer) {
                    for(Sprite sprite: sprites) {
                        sprite.draw(this.getBatch());
                    }
                }
            }else {
                renderObjects(layer);
            }
        }
        endRender();
    }
}
