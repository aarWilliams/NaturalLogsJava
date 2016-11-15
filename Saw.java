package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;

public class Saw {

	private double scrap = 0.0;
	private List<Lumber> lumberlist;
	private double countArray[];
	private double scrapEcon = 0.0;
	private double ttlOne = 0.0;
	private double ttlTwo = 0.0;
	private double ttlThree = 0.0;
	private double ttlScrap = 0.0;
	private double ttlVal = 0.0;
	private Lumber lOne;
	private Lumber lTwo;
	private Lumber lThree;
	private TextArea previewtxa;

	public Saw()
	{
		this.scrap = 0.0;
		this.lumberlist = new ArrayList<>();
		this.countArray = new double[3];
		this.scrapEcon = 0.0;
		this.ttlOne = 0.0;
		this.ttlTwo = 0.0;
		this.ttlThree = 0.0;
		this.ttlScrap = 0.0;
		this.ttlVal = 0.0;
		this.lOne = new Lumber(1,1,1,0);
		this.lTwo = new Lumber(1,1,1,0);
		this.lThree = new Lumber(1,1,1,0);
		this.previewtxa = new TextArea();
	}

	public double getScrap()
	{
		return scrap;
	}

	public void setScrap(double scrap)
	{
		this.scrap = scrap;
	}

	public List<Lumber> getLumberlist()
	{
		return lumberlist;
	}

	public void setLumberlist(List<Lumber> lumberlist)
	{
		this.lumberlist = lumberlist;
	}

	public double[] getCountArray()
	{
		return countArray;
	}

	public void setCountArray(double[] countArray)
	{
		this.countArray = countArray;
	}

	public void setCount0(double d)
	{
		this.countArray[0] = d;
	}

	public void setCount1(double d)
	{
		this.countArray[1] = d;
	}

	public void setCount2(double d)
	{
		this.countArray[2] = d;
	}

	public double getScrapEcon()
	{
		return scrapEcon;
	}

	public void setScrapEcon(double scrapEcon)
	{
		this.scrapEcon = scrapEcon;
	}

	public double getTtlOne()
	{
		return ttlOne;
	}

	public void setTtlOne(double ttlOne)
	{
		this.ttlOne = ttlOne;
	}

	public double getTtlTwo()
	{
		return ttlTwo;
	}

	public void setTtlTwo(double ttlTwo)
	{
		this.ttlTwo = ttlTwo;
	}

	public double getTtlThree()
	{
		return ttlThree;
	}

	public void setTtlThree(double ttlThree)
	{
		this.ttlThree = ttlThree;
	}

	public double getTtlScrap()
	{
		return ttlScrap;
	}

	public void setTtlScrap(double ttlScrap)
	{
		this.ttlScrap = ttlScrap;
	}

	public double getTtlVal()
	{
		return ttlVal;
	}

	public void setTtlVal(double ttlVal)
	{
		this.ttlVal = ttlVal;
	}

	public Lumber getlOne()
	{
		return lOne;
	}

	public void setlOne(Lumber lOne)
	{
		this.lOne = lOne;
	}

	public Lumber getlTwo()
	{
		return lTwo;
	}

	public void setlTwo(Lumber lTwo)
	{
		this.lTwo = lTwo;
	}

	public Lumber getlThree()
	{
		return lThree;
	}

	public void setlThree(Lumber lThree)
	{
		this.lThree = lThree;
	}

	public TextArea getTextArea()
	{
		return previewtxa;
	}

	public void addScrap(double a)
	{
		this.scrap = this.scrap + a;
	}

	public void addTtlOne(double a)
	{
		this.ttlOne = this.ttlOne + a;
	}

	public void addTtlTwo(double a)
	{
		this.ttlTwo = this.ttlTwo + a;
	}

	public void addTtlThree(double a)
	{
		this.ttlThree = this.ttlThree + a;
	}

	public void addTtlVal(double a)
	{
		this.ttlVal = this.ttlVal + a;
	}

	public void addTtlScrap(double a)
	{
		this.ttlScrap = this.ttlScrap + a;
	}
}
