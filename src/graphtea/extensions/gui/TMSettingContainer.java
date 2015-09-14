package graphtea.extensions.gui;

import java.awt.Color;

/**
 * @author Sebastian Glass
 * @since 04.05.2015
 * 
 * 
 *
 */
public class TMSettingContainer {
	private String map;
	private int mappingCode;
	private int K;
	private int norm;
	private double factor;
	private Color color;

	public int getMappingCode() {
		return mappingCode;
	}

	public void setMappingCode(int mappingCode) {
		this.mappingCode = mappingCode;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public int getK() {
		return K;
	}
	public void setK(int K) {
		this.K= K;
	}

	@Override
	public String toString() {
		return "TMSettingContainer [map=" + map + ", mappingCode=" + mappingCode + ", K=" + K + "]";
	}

	public void setNorm(int norm) {
		this.norm=norm;
		
	}
	
	public int getNorm(){
		return norm;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setColor(Color color) {
		this.color=color;
	}

	public Color getColor() {
		return color;
	}

	
}