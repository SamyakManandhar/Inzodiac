package com.alphaa.inzodiac.tabmodule.fragment.filtermodule;

import java.util.ArrayList;

public class CountryResponse{
	private ArrayList<DataItem> data;
	private int errorLine;
	private int errorCode;
	private String message;
	private int status;

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

	public int getStatus(){
		return status;
	}
	public static class DataItem{
		private String code;
		private String name;
		private String id;


		public DataItem(String code, String name, String id) {
			this.code = code;
			this.name = name;
			this.id = id;
		}

		public String getCode(){
			return code;
		}

		public String getName(){
			return name;
		}

		public String getId(){
			return id;
		}
	}
}