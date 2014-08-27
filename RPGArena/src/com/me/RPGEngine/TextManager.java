package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;

public class TextManager{
	Matrix4 normalProjection;
	private SpriteBatch batch;
	private BitmapFont font;
	private StringBuilder strbuild;
	private int status;
	FreeTypeFontGenerator generator;
	public TextManager(){
		
		batch = new SpriteBatch();
		normalProjection=batch.getProjectionMatrix();
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
		
		generator = new FreeTypeFontGenerator(Gdx.files.internal("data/Fonts/arial.ttf"));
		font = generator.generateFont(20); // font size 12 pixels
		//font=new BitmapFont();
		
		strbuild=new StringBuilder();
		status=1;
	}
	public void begin(){
		if(status!=0){
			strbuild.setLength(0);
			strbuild.append("__CONSOLE__");
			status=0;
		}else
			System.out.println("begin before end");
	}
	public void add(String fieldname,Object data){
		if(status==0){
			strbuild.append("\n");
			strbuild.append(fieldname);
			strbuild.append(" : ");
			strbuild.append(data);
		}else
			System.out.println("Invalid placement");			
	}
	public void end(){
		if(status==0){
			batch.begin();
			font.setColor(1.0f, 0.0f,1.0f, 1.0f);
			font.drawMultiLine(batch, strbuild.toString(), 50,700);
			batch.end();
			status=1;
		}else
			System.out.println("End before begin");
	}
	public void dispose() {
		generator.dispose(); 
		font.dispose();
		batch.dispose();
	}
	public void resize(int width, int height) {
		//normalProjection = new Matrix4().setToOrtho2D(0, 0, width, height);
		//batch.setProjectionMatrix(normalProjection);
	}
}
