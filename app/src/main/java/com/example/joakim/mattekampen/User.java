package com.example.joakim.mattekampen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	private String name;
	private List<Result> results = new ArrayList<Result>();
	private int numberOfCorrect=0, numberOfWrong=0;
	
	User(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public List<Result> getResults()
	{
		return this.results;
	}

	public int getNumberOfCorrect(){
		return numberOfCorrect;
	}

	public int getNumberOfWrong(){
		return numberOfWrong;
	}

	public void correct(){
		numberOfCorrect++;
	}

	public void wrong(){
		numberOfWrong++;
	}

	public String getPercentage(){
		int percentCorrect = Math.round(100*(numberOfCorrect/(float)(numberOfCorrect+numberOfWrong)));
		return Integer.toString(percentCorrect);
	}

	public void addResult()
	{
		Result r = new Result(numberOfCorrect,numberOfWrong,new Date());
		this.results.add(r);
		numberOfCorrect =0;
		numberOfWrong = 0;
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.name + "\n\n");
		for (Result r : this.results)
		{
			sb.append(r.toString() + "\n");
		}
		return sb.toString();
	}
}
