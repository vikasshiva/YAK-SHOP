package yak.shop.biz.logic;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import yak.shop.exception.StockInvalidException;
import yak.shop.factory.LiveStockFactory;
import yak.shop.live.stock.ILiveStock;
import yak.shop.model.Herd;
import yak.shop.model.YakEntity;

public class YakShopApplication {

	public static void main(String[] args) {
		
		//Scanner to take file path and elapsed day input
		Scanner sc = new Scanner(System.in);
		
		//User command line interface to ask for path of XML
		System.out.println("Enter Input File Name (Absolute Path)");
		String fileName = sc.next();
		
		//User command line interface to ask for Elapsed Time in Days
		System.out.println("Enter Elapsed Time in Days");
		int elapsedTimeIndaysInput = sc.nextInt();
		//JaxB Context which will be used for parsing XML and gt the objects.
		try {
			List<YakEntity> list = parseXML(fileName);

			double milkLimt = 0;
			double woolLimt = 0;
			
			getFinalResult(elapsedTimeIndaysInput, list, milkLimt, woolLimt);

		} catch( JAXBException|StockInvalidException e) {
			e.printStackTrace();
		}
	}

	private static List<YakEntity> parseXML(String fileName) throws JAXBException {
		JAXBContext jaxbContext;
		//Read xml File
		File file = new File(fileName);
		//Create JAXB Context 
		jaxbContext = JAXBContext.newInstance(Herd.class);
		
		//UnMarshalle the xml into java Object .This converts xml into Herd Object.
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Herd herd = (Herd) jaxbUnmarshaller.unmarshal(file);

		List<YakEntity> list = herd.getLabyak();
		return list;
	}

	private static void getFinalResult(int elapsedTimeIndaysInput, List<YakEntity> list, double milkLimt,
			double woolLimt) throws StockInvalidException {
		ILiveStock<YakEntity> yakEntity = LiveStockFactory.getInstance(LiveStockFactory.STOCKTYPE.YAK.toString());

		
		//iterate through the the number of days to calculate the total number of milk an wool available
			for (YakEntity yak : list) {
				if ("f".equalsIgnoreCase(yak.getSex())) {
					milkLimt += yakEntity.calculateMilk(yak, elapsedTimeIndaysInput);
				}
				woolLimt += yakEntity.calculateWool(yak, elapsedTimeIndaysInput);
			}

		System.out.print("In Stock\n\t");
		System.out.printf("%.3f Liters of milk\n\t", milkLimt);
		System.out.printf("%.0f Skins of wool\n", woolLimt);
		yakEntity.calculateAge(list, elapsedTimeIndaysInput);
	}

	//Calculates the age of the yak till date based on the elaspsed time in days.
	private static void calculateAge(List<YakEntity> yakList, int i) {
		
	}


}
