package com.tezcan.survivorflag;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class
SurvivorFlag extends ApplicationAdapter {
	SpriteBatch batch;
	Texture back;
	Texture flag;
	Texture flagenemy;
	Texture flagenemy1;
	Texture flagenemy2;
	Texture plane1;
	float flagX=0;
	float flagY=0;
	int gamestate=0;
	float velocity = 0;
	float gravity = 0.3f;
	Random random;
	Circle flagcircle;
	int score;
	int scoredEnemy;

	BitmapFont font;
	BitmapFont font2;


	float enemyvelocity = 8;

	int numberofenemies= 4;
	float[] enemyX = new float[numberofenemies];
	float[] enemyOffSet = new float[numberofenemies];
	float[] enemyOffSet1 = new float[numberofenemies];
	float[] enemyOffSet2 = new float[numberofenemies];
	float[] planeX = new float[numberofenemies];

	Circle[] enemy;
	Circle[] enemy1;
	Circle[] enemy2;

	float distence = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		back= new Texture(	"pi.png");
		flag = new Texture("turkey1.png");
		flagenemy = new Texture("unitedstates3.png");
		flagenemy1 = new Texture("unitedstates3.png");
		flagenemy2 = new Texture("unitedstates3.png");
		plane1 = new Texture("plane1.png");
		random = new Random();

		distence = Gdx.graphics.getWidth()/2;

		flagX = Gdx.graphics.getWidth()/5;
		flagY= Gdx.graphics.getHeight()/2;

		flagcircle = new Circle();

		enemy = new Circle[numberofenemies];
		enemy1 = new Circle[numberofenemies];
		enemy2 = new Circle[numberofenemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		font2= new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(8);

		for (int i=0 ; i < numberofenemies ; i++){

			enemyOffSet[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
			enemyOffSet1[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);

			enemyX[i] = Gdx.graphics.getWidth() - flagenemy.getWidth()/2 + i*distence;
			planeX[i] = Gdx.graphics.getWidth() - plane1.getWidth()/2 + i*distence;

			enemy[i]= new Circle();
			enemy1[i]= new Circle();
			enemy2[i]= new Circle();
		}



	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		if (gamestate == 1){

		    if (enemyX[scoredEnemy]<Gdx.graphics.getWidth()/5){
		        score++;

		        if (scoredEnemy < numberofenemies -1 ){
		            scoredEnemy++;
                }else {
		            scoredEnemy = 0;
                }
            }

			if (Gdx.input.justTouched()){
				velocity= -9;
			}

			for (int i=0 ; i <numberofenemies; i++){


				if (enemyX[i] < -flagenemy.getWidth()){

					enemyX[i]=  enemyX[i]+ numberofenemies*distence;
					planeX[i]= planeX[i]+ numberofenemies*distence;

					enemyOffSet[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
					enemyOffSet1[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);

				}else {
					enemyX[i] = enemyX[i] - enemyvelocity;
					planeX[i]= planeX[i] - enemyvelocity;
				}



				batch.draw(flagenemy,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffSet[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/9);
				batch.draw(flagenemy1,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffSet1[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/9);
				batch.draw(flagenemy2,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffSet2[i],Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/9);
                batch.draw(plane1,planeX[i],Gdx.graphics.getHeight()-100,Gdx.graphics.getWidth()/13,Gdx.graphics.getHeight()/9);


				enemy[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2+ enemyOffSet[i]+Gdx.graphics.getHeight()/26,Gdx.graphics.getWidth()/26);
				enemy1[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2+ enemyOffSet1[i]+Gdx.graphics.getHeight()/26,Gdx.graphics.getWidth()/26);
				enemy2[i] = new Circle(enemyX[i]+Gdx.graphics.getWidth()/26,Gdx.graphics.getHeight()/2+ enemyOffSet2[i]+Gdx.graphics.getHeight()/26,Gdx.graphics.getWidth()/26);


			}






			if (flagY > 0 && flagY < Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/15){
				velocity= velocity+ gravity;
				flagY = flagY - velocity;
			}else {
				gamestate=2;
			}


		}else if (gamestate == 0){
			if (Gdx.input.justTouched()){
				gamestate = 1;
			}
		}else if (gamestate == 2){

			font2.draw(batch,"Game Over",Gdx.graphics.getWidth()/3+75,Gdx.graphics.getHeight()/2+100);
			font.draw(batch,"Score : "+score,Gdx.graphics.getWidth()/3 +250 ,Gdx.graphics.getHeight()/2-20);
			if (Gdx.input.justTouched()){
				gamestate = 1;

				flagY= Gdx.graphics.getHeight()/2;

				for (int i=0 ; i < numberofenemies ; i++){

					enemyOffSet[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
					enemyOffSet1[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f)* (Gdx.graphics.getHeight()- 200);

					enemyX[i] = Gdx.graphics.getWidth() - flagenemy.getWidth()/2 + i*distence;

					enemy[i]= new Circle();
					enemy1[i]= new Circle();
					enemy2[i]= new Circle();
				}

				velocity = 0;
				scoredEnemy = 0;
				score=0;


			}


		}



		batch.draw(flag,flagX,flagY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/9);
		font.draw(batch,String.valueOf("Score = "+ score),75,75);

		batch.end();

		flagcircle.set(flagX+Gdx.graphics.getWidth()/30,flagY+Gdx.graphics.getHeight()/30,Gdx.graphics.getWidth()/30);


		for (int i = 0; i<numberofenemies; i++){

			if (Intersector.overlaps(flagcircle,enemy[i]) || Intersector.overlaps(flagcircle,enemy1[i]) || Intersector.overlaps(flagcircle,enemy2[i])){
				gamestate = 2;

			}
		}

	}
	
	@Override
	public void dispose () {
	}
}
