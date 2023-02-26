package com.trang.ShopmeBackEnd_Admin.DTO;

public class CategoryDTO {
	private Integer id;
	private String name;
	
	/* Constructor */
	public CategoryDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/* getter and setter */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
