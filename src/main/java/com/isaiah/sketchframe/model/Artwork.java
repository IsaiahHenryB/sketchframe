package com.isaiah.sketchframe.model;

import javax.persistence.*;

@Entity
@Table(name = "artwork")
public class Artwork {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", nullable = false)
	private String title;
//	Since as of now my site saves images as dataURL, increased the length to accommodate for the tremendously long strings that are generated
	@Column(name = "image", nullable = false, length = 2000000)
	private String image;
	@Column(name = "isAccessible", nullable = false)
	private Boolean isAccessible;
	@Column(name = "artist", nullable = false)
	private String username;
	@Column(name = "parameters", nullable=false)
	private String params;
//	For saving the parameters used to create the artwork
	@Column(nullable = false)
	private String showOutlines;
	@Column(nullable = false)
	private String outlineWidth;
	@Column(nullable = false)
	private String outlineColor;
	@Column(nullable = false)
	private String opacity;
	@Column(nullable = false)
	private String minStrokeWidth;
	@Column(nullable = false)
	private String maxStrokeWidth;
	@Column(nullable = false)
	private String strokeAngle;
	@Column(nullable = false)
	private String layers;
	@Column(nullable = false)
	private String colorFill;
	@Column(nullable = false)
	private String colorSelection;
	@Column(nullable = false)
	private String palette;

	public Artwork(){}

	public Artwork(String title, String image, Boolean isAccessible, String username, String params, String showOutlines, String outlineWidth, String outlineColor, String opacity, String minStrokeWidth, String maxStrokeWidth, String strokeAngle, String layers, String colorFill, String colorSelection, String palette) {
		this.title = title;
		this.image = image;
		this.isAccessible = isAccessible;
		this.username = username;
		this.params = params;
		this.showOutlines = showOutlines;
		this.outlineWidth = outlineWidth;
		this.outlineColor = outlineColor;
		this.opacity = opacity;
		this.minStrokeWidth = minStrokeWidth;
		this.maxStrokeWidth = maxStrokeWidth;
		this.strokeAngle = strokeAngle;
		this.layers = layers;
		this.colorFill = colorFill;
		this.colorSelection = colorSelection;
		this.palette = palette;
	}

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getImage() {return image;}
	public void setImage(String image) {this.image = image;}
	public Boolean getIsAccessible() {return isAccessible;}
	public void setIsAccessible(Boolean Accessible) {isAccessible = Accessible;}
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	public String getParams() {return params;}
	public void setParams(String params) {this.params = params;}
	public String getShowOutlines() {return showOutlines;}
	public void setShowOutlines(String showOutlines) {this.showOutlines = showOutlines;}
	public String getOutlineWidth() {return outlineWidth;}
	public void setOutlineWidth(String outlineWidth) {this.outlineWidth = outlineWidth;}
	public String getOutlineColor() {return outlineColor;}
	public void setOutlineColor(String outlineColor) {this.outlineColor = outlineColor;}
	public String getOpacity() {return opacity;}
	public void setOpacity(String opacity) {this.opacity = opacity;}
	public String getMinStrokeWidth() {return minStrokeWidth;}
	public void setMinStrokeWidth(String minStrokeWidth) {this.minStrokeWidth = minStrokeWidth;}
	public String getMaxStrokeWidth() {return maxStrokeWidth;}
	public void setMaxStrokeWidth(String maxStrokeWidth) {this.maxStrokeWidth = maxStrokeWidth;}
	public String getStrokeAngle() {return strokeAngle;}
	public void setStrokeAngle(String strokeAngle) {this.strokeAngle = strokeAngle;}
	public String getLayers() {return layers;}
	public void setLayers(String layers) {this.layers = layers;}
	public String getColorFill() {return colorFill;}
	public void setColorFill(String colorFill) {this.colorFill = colorFill;}
	public String getColorSelection() {return colorSelection;}
	public void setColorSelection(String colorSelection) {this.colorSelection = colorSelection;}
	public String getPalette() {return palette;}
	public void setPalette(String palette) {this.palette = palette;}
}
