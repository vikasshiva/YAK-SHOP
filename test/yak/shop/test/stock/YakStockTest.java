package yak.shop.test.stock;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import yak.shop.exception.StockInvalidException;
import yak.shop.factory.LiveStockFactory;
import yak.shop.live.stock.ILiveStock;
import yak.shop.live.stock.impl.YakStockImpl;
import yak.shop.model.YakEntity;

public class YakStockTest extends TestCase {

	private List<YakEntity> yakList = new ArrayList<YakEntity>();
	private ILiveStock<YakEntity> stock = new YakStockImpl();

	@Before
	public void setUp() {
		YakEntity yak1 = new YakEntity("Betty1", 4, "f");
		YakEntity yak2 = new YakEntity("Betty2", 8, "f");
		YakEntity yak3 = new YakEntity("Betty3", 9.5, "f");

		yakList.add(yak1);
		yakList.add(yak2);
		yakList.add(yak3);

	}

	@Test
	public void testCalculateMilk13Days() {

		DecimalFormat df = new DecimalFormat("#.####");
		String value = df.format(stock.calculateMilk(yakList.get(0), 13));
		assertEquals(value, "491.66");
	}

	@Test
	public void testCalculateMilk14Days() {
		assertFalse(stock.calculateMilk(yakList.get(0), 14) == 491.660);
	}

	@Test
	public void testCalculateMilk1Null() {
		assertTrue(stock.calculateMilk(null, 14) == 0);
	}

	@Test
	public void testCalculateWool13Days() {

		DecimalFormat df = new DecimalFormat("#.####");
		String value = df.format(stock.calculateWool(yakList.get(0), 13));
		assertEquals(value, "1");
	}

	@Test
	public void testCalculateWool14Days() {
		assertFalse(stock.calculateWool(yakList.get(1), 14) == 491.660);
	}

	@Test
	public void testCalculateWoolNull() {
		assertTrue(stock.calculateWool(null, 14) == 0);
	}

	@Test
	public void testWithLsitValues13DAYS() {
		double milkLimt = 0;
		double woolLimt = 0;
		double totalAge = 0;
		ILiveStock<YakEntity> yakEntity;
		try {
			yakEntity = LiveStockFactory.getInstance(LiveStockFactory.STOCKTYPE.YAK.toString());

			for (YakEntity yak : yakList) {
				if ("f".equalsIgnoreCase(yak.getSex())) {
					milkLimt += yakEntity.calculateMilk(yak, 13);
				}
				woolLimt += yakEntity.calculateWool(yak, 13);
			}
			DecimalFormat df = new DecimalFormat("#.####");
			String value = df.format(milkLimt);
			assertEquals(value, "1104.48");
			
			String woolLimtValue = df.format(woolLimt);
			assertEquals(woolLimtValue, "3");

		} catch (StockInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void testWithLsitValues14DAYS() {
		double milkLimt = 0;
		double woolLimt = 0;
		ILiveStock<YakEntity> yakEntity;
		try {
			yakEntity = LiveStockFactory.getInstance(LiveStockFactory.STOCKTYPE.YAK.toString());

			for (YakEntity yak : yakList) {
				if ("f".equalsIgnoreCase(yak.getSex())) {
					milkLimt += yakEntity.calculateMilk(yak, 14);
				}
				woolLimt += yakEntity.calculateWool(yak, 14);
			}
			DecimalFormat df = new DecimalFormat("#.####");
			String value = df.format(milkLimt);
			assertEquals(value, "1188.81");
			
			String woolLimtValue = df.format(woolLimt);
			assertEquals(woolLimtValue, "4");

		} catch (StockInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
