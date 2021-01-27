package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Line
{
	float x;
	float y;
	float x1;
	float y1;
	float width;

	public Line(float x, float y, float x1, float y1, float width)
	{
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.width = width;
	}

	public void draw(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.setColor(Color.GOLD);
		shapeRenderer.rectLine(x, y, x1, y1, width);
	}

}
