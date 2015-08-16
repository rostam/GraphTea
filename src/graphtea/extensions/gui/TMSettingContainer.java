package graphtea.extensions.gui;

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
	
	
}