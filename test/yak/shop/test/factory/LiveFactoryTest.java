package yak.shop.test.factory;

import junit.framework.TestCase;

import org.junit.Test;

import yak.shop.exception.StockInvalidException;
import yak.shop.factory.LiveStockFactory;
import yak.shop.live.stock.ILiveStock;
import yak.shop.live.stock.impl.YakStockImpl;
import yak.shop.model.YakEntity;

public class LiveFactoryTest extends TestCase {

	
	@Test
	public void testValidStock() throws StockInvalidException{
		ILiveStock<YakEntity> entity = LiveStockFactory.getInstance("YAK");
		assertTrue(entity instanceof YakStockImpl);
	}
	
	public void testInvalidStock() {
		try {
			LiveStockFactory.getInstance("SHEEP");
			fail("should throw exception");
		} catch (StockInvalidException e) {
			assertTrue(true);
		}
	}
	
	public void testnullStock() {
		try {
			LiveStockFactory.getInstance(null);
			fail("should throw exception");
		} catch (StockInvalidException e) {
			assertTrue(true);
		}
	}
	
}
