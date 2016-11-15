package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Optimizer extends Main{

	static void optimization(File logs, File econ, Saw saw) throws FileNotFoundException
	{
		List<Log> loglist = new ArrayList<>();

		String text = "";
		String trunc;

		Scanner scan = new Scanner(logs);

		while(scan.hasNext()){
			String height = scan.next();
			String width = scan.next();
			String length = scan.next();


			double h = Double.parseDouble(height);
			double w = Double.parseDouble(width);
			double l = Double.parseDouble(length);


			Log log = new Log(h,w,l);
			loglist.add(log);

		}

		scan.close();

		scan = new Scanner(econ);

		while(scan.hasNext()){

			String temp = scan.next();

			if(scan.hasNext() == false)
			{
				saw.setScrapEcon(Double.parseDouble(temp));
			}
			else
			{
				String height = temp;
				String width = scan.next();
				String length = scan.next();
				String value = scan.next();

				double h = Double.parseDouble(height);
				double w = Double.parseDouble(width);
				double l = Double.parseDouble(length);
				double v = Double.parseDouble(value);


				Lumber lumber = new Lumber(h,w,l,v);
				saw.getLumberlist().add(lumber);
			}
		}

		scan.close();

		for(Log l: loglist)
		{
			//We need to implement a method for if the file only contains one type of lumber.
			saw.setlOne(saw.getLumberlist().get(0));
			saw.setlTwo(saw.getLumberlist().get(1));
			saw.setlThree(saw.getLumberlist().get(2));
			double lScrap = saw.getScrapEcon();
			Lumber[] priceOrder = new Lumber[3];

			saw.setScrap(0.0);

			double priceOne = saw.getlOne().getValue() / saw.getlOne().calculateArea() ;
			double priceTwo = saw.getlTwo().getValue() / saw.getlTwo().calculateArea() ;
			double priceThree = saw.getlThree().getValue() / saw.getlThree().calculateArea() ;

			if(priceOne >= priceTwo && priceOne >= priceThree)
			{
				priceOrder[0] = saw.getlOne();
				if(priceTwo >= priceThree)
				{
					priceOrder[1] = saw.getlTwo();
					priceOrder[2] = saw.getlThree();
				}
				else
				{
					priceOrder[1] = saw.getlThree();
					priceOrder[2] = saw.getlTwo();
				}
			}
			else if(priceTwo >= priceOne && priceTwo >= priceThree)
			{
				priceOrder[0] = saw.getlTwo();
				if(priceOne >= priceThree)
				{
					priceOrder[1] = saw.getlOne();
					priceOrder[2] = saw.getlThree();
				}
				else
				{
					priceOrder[1] = saw.getlThree();
					priceOrder[2] = saw.getlOne();
				}
			}
			else
			{
				priceOrder[0] = saw.getlThree();
				if(priceTwo >= priceOne)
				{
					priceOrder[1] = saw.getlTwo();
					priceOrder[2] = saw.getlOne();
				}
				else
				{
					priceOrder[1] = saw.getlOne();
					priceOrder[2] = saw.getlTwo();
				}
			}
			saw.setlOne(priceOrder[0]);
			saw.setlTwo(priceOrder[1]);
			saw.setlThree(priceOrder[2]);
			saw.getLumberlist().clear();
			saw.getLumberlist().add(saw.getlOne());
			saw.getLumberlist().add(saw.getlTwo());
			saw.getLumberlist().add(saw.getlThree());


			int multi = (int) (l.getLength() / (int) saw.getlOne().getLength());

			double scrapInches = l.getHeight() * l.getWidth() * (l.getLength() - saw.getlOne().getLength() * multi);
			saw.addScrap(scrapInches / 1728);	// 12" x 12" x 12" = 1728

			double maxHeight = l.getHeight() / saw.getlOne().getHeight();
			double maxWidth = l.getWidth() / saw.getlOne().getWidth();
			int lOneCount = (int) maxHeight * (int) maxWidth * multi;
			double[] boards = new double[3];

			double heightLeft = l.getHeight() - saw.getlOne().getHeight() * maxHeight;
			double widthLeft = l.getWidth() - saw.getlOne().getWidth() * maxWidth;

			if(heightLeft > widthLeft)
			{
				Log h = new Log(heightLeft, l.getWidth(), 0.0);
				Log w = new Log(l.getHeight() - heightLeft, widthLeft, 0.0 );
				boards = Optimization(h, w, saw.getlTwo(), saw.getlThree(), boards, saw);
			}
			else
			{
				Log h = new Log(heightLeft, l.getWidth() - widthLeft, 0.0);
				Log w = new Log(l.getHeight(), widthLeft, 0.0);
				boards = Optimization(h, w, saw.getlTwo(), saw.getlThree(), boards, saw);
			}

			int lTwoCount = (int) boards[0] * multi;
			int lThreeCount = (int) boards[1] * multi;
			saw.addScrap(boards[2] * multi);

			double lOneValue = lOneCount * saw.getlOne().getValue();
			double lTwoValue = lTwoCount * saw.getlTwo().getValue();
			double lThreeValue = lThreeCount * saw.getlThree().getValue();
			double scrapValue = saw.getScrap() * lScrap;

			saw.addTtlOne(lOneCount);
			saw.addTtlTwo(lTwoCount);
			saw.addTtlThree(lThreeCount);
			saw.addTtlScrap(saw.getScrap());
			saw.addTtlVal(lOneValue + lTwoValue + lThreeValue + scrapValue);

			trunc = (String) String.format("%.2f", saw.getScrap());
			saw.setScrap(Double.parseDouble(trunc));
			trunc = (String) String.format("%.2f", scrapValue);
			scrapValue = Double.parseDouble(trunc);

			text += "" + saw.getlOne().getHeight() + "x" + saw.getlOne().getWidth() + "x" + saw.getlOne().getLength() +
					": " + lOneCount + " $" + lOneValue + "\n" + saw.getlTwo().getHeight() + "x" + saw.getlTwo().getWidth() + "x" + saw.getlTwo().getLength() +
					": " + lTwoCount + " $" + lTwoValue + "\n" + saw.getlThree().getHeight() + "x" + saw.getlThree().getWidth() + "x" + saw.getlThree().getLength() +
					": " + lThreeCount + " $" + lThreeValue + "\n" + "Scrap: " + saw.getScrap() + " $" + scrapValue + "\n\n";
		}
		trunc = (String) String.format("%.2f", saw.getTtlScrap());
		saw.setTtlScrap(Double.parseDouble(trunc));

		trunc = (String) String.format("%.2f", saw.getTtlVal());
		saw.setTtlVal(Double.parseDouble(trunc));

		saw.setCount0(saw.getTtlOne());
		saw.setCount1(saw.getTtlTwo());
		saw.setCount2(saw.getTtlThree());

		text += "Total number of " + saw.getlOne().getHeight() + "x" + saw.getlOne().getWidth() + "x" + saw.getlOne().getLength() + ": " + saw.getTtlOne() + "\n" +
				"Total number of " + saw.getlTwo().getHeight() + "x" + saw.getlTwo().getWidth() + "x" + saw.getlTwo().getLength() + ": " + saw.getTtlTwo() + "\n" +
				"Total number of " + saw.getlThree().getHeight() + "x" + saw.getlThree().getWidth() + "x" + saw.getlThree().getLength() + ": " + saw.getTtlThree() + "\n" +
				"Total number of scrap: " + saw.getTtlScrap() + "\n" + "Total value: $" + saw.getTtlVal();
		saw.getTextArea().setText(text);
	}

	private static double[] Optimization(Log h, Log w, Lumber one, Lumber two, double[] boards, Saw saw)
	{
		int result;
		if(one.getHeight() < h.getHeight())
		{
			if(one.getWidth() < h.getWidth())
			{
				double maxHeight = h.getHeight() / one.getHeight();
				double maxWidth = h.getWidth() / one.getWidth();
				result = (int) maxHeight * (int) maxWidth;
				boards[0] = (double)result;

				double heightLeft = h.getHeight() - one.getHeight() * maxHeight;
				double widthLeft = h.getWidth() - one.getWidth() * maxWidth;

				if(heightLeft > widthLeft)
				{
					Log hTwo = new Log(heightLeft, h.getWidth(), 0.0);
					Log wTwo = new Log(h.getHeight() - heightLeft, widthLeft, 0.0 );
					boards = Optimization(hTwo, wTwo, two, boards, saw);
				}
				else
				{
					Log hTwo = new Log(heightLeft, h.getWidth() - widthLeft, 0.0);
					Log wTwo = new Log(h.getHeight(), widthLeft, 0.0);
					boards = Optimization(hTwo, wTwo, two, boards, saw);
				}
			}
		}
		else if(two.getHeight() < h.getHeight())
		{
			if(two.getWidth() < h.getWidth())
			{
				double maxHeight = h.getHeight() / two.getHeight();
				double maxWidth = h.getWidth() / two.getWidth();
				boards[0] = 0.0;
				result = (int) maxHeight * (int) maxWidth;
				boards[1] = (double)result;

				double heightLeft = h.getHeight() - two.getHeight() * maxHeight;
				double widthLeft = h.getWidth() - two.getWidth() * maxWidth;

				boards[2] = heightLeft * widthLeft * two.getLength() / 1728;
			}
		}
		if(one.getHeight() < w.getHeight())
		{
			if(one.getWidth() < w.getWidth())
			{
				if(one.getWidth() < w.getWidth())
				{
					double maxHeight = w.getHeight() / one.getHeight();
					double maxWidth = w.getWidth() / one.getWidth();
					result = (int) maxHeight * (int) maxWidth;
					boards[0] = (double)result;

					double heightLeft = w.getHeight() - one.getHeight() * maxHeight;
					double widthLeft = w.getWidth() - one.getWidth() * maxWidth;

					if(heightLeft > widthLeft)
					{
						Log hTwo = new Log(heightLeft, w.getWidth(), 0.0);
						Log wTwo = new Log(w.getHeight() - heightLeft, widthLeft, 0.0 );
						boards = Optimization(hTwo, wTwo, two, boards, saw);
					}
					else
					{
						Log hTwo = new Log(heightLeft, h.getWidth() - widthLeft, 0.0);
						Log wTwo = new Log(h.getHeight(), widthLeft, 0.0);
						boards = Optimization(hTwo, wTwo, two, boards, saw);
					}
				}
			}
		}
		else if(two.getHeight() < w.getHeight())
		{
			if(two.getWidth() < w.getWidth())
			{
				if(two.getWidth() < w.getWidth())
				{
					double maxHeight = w.getHeight() / two.getHeight();
					double maxWidth = w.getWidth() / two.getWidth();
					result = (int) maxHeight * (int) maxWidth;
					boards[0] = 0.0;
					boards[1] = (double)result;

					double heightLeft = w.getHeight() - two.getHeight() * maxHeight;
					double widthLeft = w.getWidth() - two.getWidth() * maxWidth;

					boards[2] = heightLeft * widthLeft * two.getLength() / 1728;
				}
			}
		}
		return boards;
	}

	private static double[] Optimization(Log h, Log w, Lumber one, double[] boards, Saw saw)
	{
		if(one.getHeight() < h.getHeight())
		{
			if(one.getWidth() < h.getWidth())
			{
				double maxHeight = h.getHeight() / one.getHeight();
				double maxWidth = h.getWidth() / one.getWidth();

				boards[1] = (int) maxHeight * (int) maxWidth;

				double heightLeft = h.getHeight() - one.getHeight() * maxHeight;
				double widthLeft = h.getWidth() - one.getWidth() * maxWidth;

				boards[2] = heightLeft * widthLeft * one.getLength() / 1728;
			}
			else
			{
				boards[1] = 0.0;
				boards[2] = h.getHeight() * h.getWidth() * one.getLength() / 1728;
			}
		}
		else
		{
			boards[1] = 0.0;
			boards[2] = h.getHeight() * h.getWidth() * one.getLength() / 1728;
		}
		if(one.getHeight() < w.getHeight())
		{
			if(one.getWidth() < w.getWidth())
			{
				double maxHeight = w.getHeight() / one.getHeight();
				double maxWidth = w.getWidth() / one.getWidth();

				boards[1] = (int) maxHeight * (int) maxWidth;

				double heightLeft = w.getHeight() - one.getHeight() * maxHeight;
				double widthLeft = w.getWidth() - one.getWidth() * maxWidth;

				boards[2] = heightLeft * widthLeft * one.getLength() / 12;
			}
			else
			{
				boards[1] = 0.0;
				boards[2] = w.getHeight() * w.getWidth() * one.getLength() / 12;
			}
		}
		else
		{
			boards[1] = 0.0;
			boards[2] = w.getHeight() * w.getWidth() * one.getLength() / 12;
		}
		return boards;
	}
}
