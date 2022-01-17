package com.alphaa.inzodiac.tabmodule.activity.categorymodule;

import java.util.ArrayList;

public class CategoryResponse {
	private ArrayList<DataItem> data;
	private int errorLine;
	private int errorCode;
	private String message;
	private boolean status;

	public ArrayList<DataItem> getData(){
		return data;
	}

	public int getErrorLine(){
		return errorLine;
	}

	public int getErrorCode(){
		return errorCode;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public class DataItem{
		private String images;
		private String name;
		private String id;

		public String getImages(){
			return images;
		}

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}
	}

}