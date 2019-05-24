package com.ubatis.circleserver.util.generator;

public class GenFieldBean {
	
	private String name;
	private String comment;
	private String type;
	
	public GenFieldBean(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public GenFieldBean(String name, String comment, String type) {
		this.name = name;
		this.comment = comment;
		this.type = type;
	}

	public GenFieldBean() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
		
	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String genGetter(){
		StringBuilder getter = new StringBuilder();
		getter.append("    public "+getType()+" "+"get"+ GenUtil.genderateGetterSetterMethod(getName())+"() { ").append("\n");
		getter.append("        return "+getName()).append(";").append("\n");
		getter.append("    }");
		return getter.toString();
	}
	
	public String genSetter(GenConfig genConfig){
		StringBuilder getter = new StringBuilder();
		getter.append("    public void "+"set"+ GenUtil.genderateGetterSetterMethod(getName())+"("+getType()+" "+getName()+") { ").append("\n");
		if(!genConfig.isNormal()){
			getter.append("        put(\""+getName()+"\","+getName()+")").append(";").append("\n");	
		}
		getter.append("        this."+getName()+" = "+getName()).append(";").append("\n");
		getter.append("    }");
		return getter.toString();
	}

}
