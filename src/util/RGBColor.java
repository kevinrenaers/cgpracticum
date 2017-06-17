package util;

import java.awt.*;

public class RGBColor {
	
	public static RGBColor convertToRGBColor(Color color){
		return new RGBColor(color.getRGBColorComponents(null)[0],
				color.getRGBColorComponents(null)[1],
				color.getRGBColorComponents(null)[2]);
	}
	
	private float R;
	private float G;
	private float B;
	
	public RGBColor(float R, float G, float B){
		if(R < 0 || R > 1){
			throw new IllegalArgumentException("the value of R should be between 0 and 1.0");
		}
		this.R=R;
		
		if(G < 0 || G > 1){
			throw new IllegalArgumentException("the value of R should be between 0 and 1.0");
		}
		this.G=G;
		
		if(B < 0 || B > 1){
			throw new IllegalArgumentException("the value of R should be between 0 and 1.0");
		}
		this.B=B;
	}
	
	public RGBColor(float i){
		this(i,i,i);
	}
	
	public RGBColor add(RGBColor rgbColor){
	
		float tempR = (float)(this.R + rgbColor.R);
		float tempG= (float)(this.G + rgbColor.G);
		float tempB = (float)(this.B + rgbColor.B);
		return format( tempR, tempG, tempB);
	}

	public RGBColor multiply(RGBColor rgbColor){
		float tempR = (float)(this.R * rgbColor.R);
		float tempG= (float)(this.G * rgbColor.G);
		float tempB = (float)(this.B * rgbColor.B);
		return format( tempR, tempG, tempB);
	}

	public RGBColor format(RGBColor color){
		return color;
	}

	public RGBColor format(float R, float G, float B){
		return new RGBColor( R, G,  B);
	}

	public RGBColor format(){
		return this;
	}
	
	public RGBColor scale(double s){
		float tempR = (float) s*R;
		float tempG = (float) s*G;
		float tempB = (float) s*B;
		return format( tempR, tempG, tempB);
	}
	
	public Color convertToColor(){
		return new Color(R,G,B);
	}

	public float getR() {
		return R;
	}

	public float getG() {
		return G;
	}

	public float getB() {
		return B;
	}
}
