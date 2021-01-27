package com.github.annasajkh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class Game extends ApplicationAdapter
{
	static ShapeRenderer shapeRenderer;
	static Column[][] borders;
	static Vector3 mousePos;
	static OrthographicCamera cam;
	static boolean over;
	static Line line;

	public static int checkWinner(boolean notStatus, Column[][] borders)
	{
		int status = 2;
		// -10 == o win
		// 10 == x win
		// 0 == tie
		// 2 == it's still continue
		for (int i = 0; i < borders.length; i++)
		{
			if (("" + borders[i][0].value + borders[i][1].value + borders[i][2].value).equals("ooo"))
			{

				// vertical
				if (!over && notStatus)
				{
					line = new Line(borders[i][0].x + borders[i][0].width / 2,
							borders[i][0].y + borders[i][0].height / 2, borders[i][2].x + borders[i][2].width / 2,
							borders[i][2].y + borders[i][2].height / 2, 5);
				}
				status = -10;
			}
			else if (("" + borders[0][i].value + borders[1][i].value + borders[2][i].value).equals("ooo"))
			{
				// horizontal
				if (!over && notStatus)
				{
					line = new Line(borders[0][i].x + borders[0][i].width / 2,
							borders[0][i].y + borders[0][i].height / 2, borders[2][i].x + borders[2][i].width / 2,
							borders[2][i].y + borders[2][i].height / 2, 5);
				}
				status = -10;
			}
			else if (("" + borders[i][0].value + borders[i][1].value + borders[i][2].value).equals("xxx"))
			{
				// vertical
				if (!over && notStatus)
				{
					line = new Line(borders[i][0].x + borders[i][0].width / 2,
							borders[i][0].y + borders[i][0].height / 2, borders[i][2].x + borders[i][2].width / 2,
							borders[i][2].y + borders[i][2].height / 2, 5);
				}
				status = 10;
			}
			else if (("" + borders[0][i].value + borders[1][i].value + borders[2][i].value).equals("xxx"))
			{
				// horizontal
				if (!over && notStatus)
				{
					line = new Line(borders[0][i].x + borders[0][i].width / 2,
							borders[0][i].y + borders[0][i].height / 2, borders[2][i].x + borders[2][i].width / 2,
							borders[2][i].y + borders[2][i].height / 2, 5);
				}
				status = 10;
			}
		}

		Column one = borders[0][0];
		Column two = borders[1][1];
		Column three = borders[2][2];

		Column one1 = borders[0][2];
		Column two1 = borders[1][1];
		Column three1 = borders[2][0];
		if (("" + one.value + two.value + three.value).equals("ooo")
				|| ("" + one.value + two.value + three.value).equals("xxx"))
		{
			if (!over && notStatus)
			{
				line = new Line(one.x + one.width / 2, one.y + one.height / 2, three.x + three.width / 2,
						three.y + three.height / 2, 5);
			}
			if (one.value == 'o')
			{

				status = -10;
			}
			else
			{
				status = 10;
			}
		}
		else if (("" + one1.value + two1.value + three1.value).equals("ooo")
				|| ("" + one1.value + two1.value + three1.value).equals("xxx"))
		{
			if (!over && notStatus)
			{
				line = new Line(one1.x + one1.width / 2, one1.y + one1.height / 2, three1.x + three1.width / 2,
						three1.y + three1.height / 2, 5);
			}
			if (one1.value == 'o')
			{

				status = -10;
			}
			else
			{
				status = 10;
			}
		}
		boolean containsEmptySpot = false;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (Game.borders[i][j].value == ' ')
				{
					containsEmptySpot = true;
				}

			}
		}
		if (!containsEmptySpot)
		{
			status = 0;
		}
		return status;
	}

	@Override
	public void create()
	{
		over = false;
		Gdx.gl.glLineWidth(3);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.x = Gdx.graphics.getWidth() / 2;
		cam.position.y = Gdx.graphics.getHeight() / 2;
		mousePos = new Vector3();
		borders = new Column[3][3];
		float borderSize = Gdx.graphics.getWidth() / 3;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i == 0)
				{
					borders[i][j] = new Column(i * borderSize + 1, j * borderSize, borderSize, borderSize);
				}
				else if (j == 0)
				{
					borders[i][j] = new Column(i * borderSize, j * borderSize + 1, borderSize, borderSize);
				}
				else
				{
					borders[i][j] = new Column(i * borderSize, j * borderSize, borderSize, borderSize);
				}

			}
		}
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render()
	{
		shapeRenderer.setProjectionMatrix(cam.combined);
		cam.update();
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		cam.unproject(mousePos);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (!over)
				{
					borders[i][j].update(shapeRenderer);
				}
				borders[i][j].render(shapeRenderer);
			}
		}
		if (over && line != null)
		{
			line.draw(shapeRenderer);
		}
		if (over)
		{
			if (Gdx.input.isKeyJustPressed(Keys.SPACE))
			{
				create();
				line = null;
			}
		}
		shapeRenderer.end();
	}

	public static Column[][] cloneBorders()
	{
		Column[][] borders = new Column[3][3];
		float borderSize = Gdx.graphics.getWidth() / 3;
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i == 0)
				{
					borders[i][j] = new Column(i * borderSize + 1, j * borderSize, borderSize, borderSize);
				}
				else if (j == 0)
				{
					borders[i][j] = new Column(i * borderSize, j * borderSize + 1, borderSize, borderSize);
				}
				else
				{
					borders[i][j] = new Column(i * borderSize, j * borderSize, borderSize, borderSize);
				}

			}
		}
		return borders;
	}

	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
	}
}
