package dao;

public class Stage {
	public Stage parent;
	public String name;
	public boolean status;

	public Stage(Stage parent, String name, boolean status) {
		this.parent = parent;
		this.name = name;
		this.status = status;
	}
}