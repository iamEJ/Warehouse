import lt.itakademija.exam.IdGenerator;
import lt.itakademija.exam.Warehouse;
import lt.itakademija.exam.test.BaseTest;

public class WarehouseTest extends BaseTest {

	@Override
	protected Warehouse createWarehouse(IdGenerator id1, IdGenerator id2, int totalSpace) {
		return new MyWarehouse(id1,id2,totalSpace);
	}

}
