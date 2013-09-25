package com.shady.viennalocator.jsonSchemas.offline;

import java.util.HashSet;
import java.util.Set;

public class ObjectFeature {

	private String type;
	private Feature[] features;
	private Crs crs;
	private float[] bbox;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Feature[] getFeatures() {
		return features;
	}
	public void setFeatures(Feature[] features) {
		this.features = features;
	}
	public Crs getCrs() {
		return crs;
	}
	public void setCrs(Crs crs) {
		this.crs = crs;
	}
	public float[] getBbox() {
		return bbox;
	}
	public void setBbox(float[] bbox) {
		this.bbox = bbox;
	}
	
}
