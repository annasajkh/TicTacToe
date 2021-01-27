package com.github.annasajkh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Column extends Rectangle
{
	char value;

	public Column(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.value = ' ';
	}

	private void drawEx(ShapeRenderer shapeRenderer, Vector2 position, float radius)
	{
		for (int i = 1; i < 5; i++)
		{
			shapeRenderer.rectLine(position,
					new Vector2(position).add(new Vector2(1, 0).rotate(-45).rotate(i * 90).scl(radius)), 2);
		}
	}

	public static int minimax(Column[][] borders, int depth, boolean isMaximizing)
	{
		int result = Game.checkWinner(false, borders);

		if (result != 2)
		{
			return result;
		}

		if (isMaximizing)
		{
			int bestScore = Integer.MIN_VALUE;

			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					if (borders[i][j].value == ' ')
					{
						borders[i][j].value = 'x';
						int score = minimax(borders, depth + 1, false);
						borders[i][j].value = ' ';
						bestScore = Math.max(score, bestScore);
					}
				}
			}
			return bestScore;
		}
		else
		{
			int bestScore = Integer.MAX_VALUE;

			for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					if (borders[i][j].value == ' ')
					{
						borders[i][j].value = 'o';
						int score = minimax(borders, depth + 1, true);
						borders[i][j].value = ' ';
						bestScore = Math.min(score, bestScore);
					}
				}
			}
			return bestScore;
		}
	}

	public static void bestMove()
	{
		int bestScore = Integer.MIN_VALUE;
		Column bestMove = null;

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (Game.borders[i][j].value == ' ')
				{
					Game.borders[i][j].value = 'x';
					int score = minimax(Game.borders, 0, false);
					Game.borders[i][j].value = ' ';
					if (score > bestScore)
					{
						bestScore = score;
						bestMove = Game.borders[i][j];
					}
				}
			}
		}
		bestMove.value = 'x';

		if (Game.checkWinner(true, Game.borders) != 2)
		{
			Game.over = true;
			return;
		}
	}

	public void update(ShapeRenderer shapeRenderer)
	{
		if (contains(Game.mousePos.x, Game.mousePos.y) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)
				&& value == ' ')
		{

			value = 'o';
			if (Game.checkWinner(true, Game.borders) != 2)
			{
				Game.over = true;
				return;
			}
			bestMove();
		}

	}

	public void render(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(x, y, width, height);
		switch (value)
		{
		case 'o':
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.circle(x + width / 2, y + height / 2, width / 3);
			break;
		case 'x':
			shapeRenderer.setColor(Color.RED);
			drawEx(shapeRenderer, new Vector2(x + width / 2, y + height / 2), width / 3);
		}
	}

}
