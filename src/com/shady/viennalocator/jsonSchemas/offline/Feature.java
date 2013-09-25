package com.shady.viennalocator.jsonSchemas.offline;

public class Feature {

	private String type;
	private String id;
	private Geometry geometry;
	private String geometry_name;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public String getGeometry_name() {
		return geometry_name;
	}
	public void setGeometry_name(String geometry_name) {
		this.geometry_name = geometry_name;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	private Properties properties;
	

}
