/*
** Copyright 2005 Huxtable.com. All rights reserved.
*/

package com.jhlabs.image;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;

public class BrushedMetalFilter implements BufferedImageOp {

	private int radius;
	private float amount = 0.1f;
	private int color = 0xff888888;
	private float shine = 0;
    private boolean monochrome = false;
	private Random randomNumbers;

    public BrushedMetalFilter() {
    }
    
    public BrushedMetalFilter( int color, int radius, float amount, boolean monochrome, float shine) {
        this.color = color;
        this.radius = radius;
        this.amount = amount;
        this.monochrome = monochrome;
        this.shine = shine;
    }
    
    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );

        int[] inPixels = new int[width];
        int[] outPixels = new int[width];

        randomNumbers = new Random(0);
        int a = color & 0xff000000;
        int r = (color >> 16) & 0xff;
        int g = (color >> 8) & 0xff;
        int b = color & 0xff;
		for ( int y = 0; y < height; y++ ) {
            for ( int x = 0; x < width; x++ ) {
                int tr = r;
                int tg = g;
                int tb = b;
                if ( shine != 0 ) {
                    int f = (int)(255*shine*Math.sin( (double)x/width*Math.PI ));
                    tr += f;
                    tg += f;
                    tb += f;
                }
                if (monochrome) {
                    int n = (int)(255 * (2*randomNumbers.nextFloat() - 1) * amount);
                    inPixels[x] = a | (clamp(tr+n) << 16) | (clamp(tg+n) << 8) | clamp(tb+n);
                } else {
                    inPixels[x] = a | (random(tr) << 16) | (random(tg) << 8) | random(tb);
                }
            }

            if ( radius != 0 ) {
                blur( inPixels, outPixels, width, radius );
                setRGB( dst, 0, y, width, 1, outPixels );
            } else
                setRGB( dst, 0, y, width, 1, inPixels );
        }
        return dst;
    }

	private int random(int x) {
		x += (int)(255*(2*randomNumbers.nextFloat() - 1) * amount);
		if (x < 0)
			x = 0;
		else if (x > 0xff)
			x = 0xff;
		return x;
	}

	private static int clamp(int c) {
		if (c < 0)
			return 0;
		if (c > 255)
			return 255;
		return c;
	}

	/**
	 * Return a mod b. This differs from the % operator with respect to negative numbers.
	 * @param a the dividend
	 * @param b the divisor
	 * @return a mod b
	 */
	private static int mod(int a, int b) {
		int n = a/b;
		
		a -= n*b;
		if (a < 0)
			return a + b;
		return a;
	}

    public void blur( int[] in, int[] out, int width, int radius ) {
        int widthMinus1 = width-1;
        int r2 = 2*radius+1;
        int tr = 0, tg = 0, tb = 0;

        for ( int i = -radius; i <= radius; i++ ) {
            int rgb = in[mod(i, width)];
            tr += (rgb >> 16) & 0xff;
            tg += (rgb >> 8) & 0xff;
            tb += rgb & 0xff;
        }

        for ( int x = 0; x < width; x++ ) {
            out[x] = 0xff000000 | ((tr/r2) << 16) | ((tg/r2) << 8) | (tb/r2);

            int i1 = x+radius+1;
            if ( i1 > widthMinus1 )
                i1 = mod( i1, width );
            int i2 = x-radius;
            if ( i2 < 0 )
                i2 = mod( i2, width );
            int rgb1 = in[i1];
            int rgb2 = in[i2];
            
            tr += ((rgb1 & 0xff0000)-(rgb2 & 0xff0000)) >> 16;
            tg += ((rgb1 & 0xff00)-(rgb2 & 0xff00)) >> 8;
            tb += (rgb1 & 0xff)-(rgb2 & 0xff);
        }
    }

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public float getAmount() {
		return amount;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setMonochrome(boolean monochrome) {
		this.monochrome = monochrome;
	}
	
	public boolean getMonochrome() {
		return monochrome;
	}
	
	public void setShine( float shine ) {
		this.shine = shine;
	}
	
	public float getShine() {
		return shine;
	}

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }
    
    public Rectangle2D getBounds2D( BufferedImage src ) {
        return new Rectangle(0, 0, src.getWidth(), src.getHeight());
    }
    
    public Point2D getPoint2D( Point2D srcPt, Point2D dstPt ) {
        if ( dstPt == null )
            dstPt = new Point2D.Double();
        dstPt.setLocation( srcPt.getX(), srcPt.getY() );
        return dstPt;
    }

    public RenderingHints getRenderingHints() {
        return null;
    }

	/**
	 * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
	 * penalty of BufferedImage.setRGB unmanaging the image.
	 */
	public void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
		int type = image.getType();
		if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
			image.getRaster().setDataElements( x, y, width, height, pixels );
		else
			image.setRGB( x, y, width, height, pixels, 0, width );
    }
}