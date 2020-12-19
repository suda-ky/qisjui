package entity;

public class Menu {
	private int id;				//菜单id
	private String name;		//菜单名字
	private int pId;			//菜单的父节点id
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Menu(int id, String name, int pId) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
	}
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
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", pId=" + pId + "]";
	}
	
}	
