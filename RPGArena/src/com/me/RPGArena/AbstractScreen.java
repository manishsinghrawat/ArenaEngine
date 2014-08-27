package com.me.RPGArena;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
/**
 * The base class for all game screens.
 */
public abstract class AbstractScreen implements Screen{
    protected final RPGArena game;
    protected final Stage stage;

    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;

    public AbstractScreen(RPGArena game)
    {
        this.game = game;
      //  this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.stage = new Stage();
    }

    protected String getName()
    {
        return getClass().getSimpleName();
    }

    // Screen implementation

    public BitmapFont getFont()
    {
        if( font == null ) {
            font = new BitmapFont();
        }
        return font;
    }

    public SpriteBatch getBatch()
    {
        if( batch == null ) {
            batch = new SpriteBatch();
        }
        return batch;
    }

    public TextureAtlas getAtlas(String atlasfile){
        if( atlas == null ) {
            atlas = new TextureAtlas(Gdx.files.internal(atlasfile));
        }
        return atlas;
    }

    protected Skin getSkin(){
        if( skin == null ) {
            //FileHandle skinFile = Gdx.files.internal(str);
            skin = new Skin();
        }
        return skin;
    }

    protected Table getTable() {
    	if( table == null ) {
            table = new Table();
            table.setFillParent( true );
           // if( RPGArena.DEV_MODE ) {
           //     table.debug();
           // }
            stage.addActor( table );
        }	
        return table;
    }

    @Override
    public void show()
    {
        Gdx.app.log(RPGArena.LOG, "Showing screen: " + getName() );
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(
        int width,
        int height )
    {
        Gdx.app.log(RPGArena.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(
        float delta )
    {
         stage.act( delta );     
         
         // the following code clears the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // update and draw the stage actors

        
        stage.draw();
        Table.drawDebug(stage);
    }

    @Override
    public void hide()
    {
        Gdx.app.log(RPGArena.LOG, "Hiding screen: " + getName() );
    }

    @Override
    public void pause()
    {
        Gdx.app.log(RPGArena.LOG, "Pausing screen: " + getName() );
    }

    @Override
    public void resume()
    {
        Gdx.app.log(RPGArena.LOG, "Resuming screen: " + getName() );
    }

    @Override
    public void dispose()
    {
        Gdx.app.log(RPGArena.LOG, "Disposing screen: " + getName() );

        // dispose the collaborators
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
        stage.dispose();
    }
}

