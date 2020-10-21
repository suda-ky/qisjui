package entity;

public class DownloadList {
	private int id;
	private String name;//鍚嶇О
	private String path;//瀛樻斁璺緞鍙婃枃浠跺悕
	private String description;//鏂囦欢鎻忚堪
	private long size;//鏂囦欢澶у皬锛屽瓧鑺備负鍗曚綅锛坆锛�
	private int star;//鏄熺骇
	private String image;//鍥剧墖璺緞
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
