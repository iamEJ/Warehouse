import java.util.ArrayList;
import java.util.List;

import lt.itakademija.exam.Client;
import lt.itakademija.exam.ClientPredicate;
import lt.itakademija.exam.IdGenerator;
import lt.itakademija.exam.InsufficientSpaceException;
import lt.itakademija.exam.Package;
import lt.itakademija.exam.Warehouse;

public class MyWarehouse extends Warehouse{
	
	private List<Package> packs = new ArrayList<>();
	private List<Client> clients = new ArrayList<>();

	public MyWarehouse(IdGenerator clientIdGenerator, IdGenerator packageIdGenerator, int totalSpace) {
		super(clientIdGenerator, packageIdGenerator, totalSpace);
		if(totalSpace <= 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Package createPackage(String name, int space) {
		Package p = new Package(this.packageIdGenerator.generateId(),name,space);
		if(space <= 0) {
			throw new IllegalArgumentException();
		}
		packs.add(p);
		return p;
	}

	@Override
	public List<Client> findClientsBy(ClientPredicate predicate) {
		List<Client> newList = new ArrayList<>();
		
		for(Client li: clients) {
			if(predicate.test(li)) {
				newList.add(li);
			}
		}
		return newList;
	}

	@Override
	public int getAvailableSpace() {

		return totalSpace - clients.stream()
					          	   .mapToInt(Client::getAvailableSpace)
					               .sum();
	}

	@Override
	public Client getClientById(int id) {

		return clients.stream()
					  .filter(c -> c.getId() == id)
					  .findAny()
					  .orElse(null);
	}

	@Override
	public int getReservedSpace() {

		return clients.stream()
		          	  .mapToInt(Client::getReservedSpace)
		              .sum();
	}

	@Override
	public int getTotalAvailableSpace() {

		return totalSpace - packs.stream()
	          	  				 .mapToInt(Package::getSpace)
	          	  				 .sum();
	}

	@Override
	public int getTotalSpace() {
		
		return totalSpace;
	}

	@Override
	public boolean hasClientById(int id) {
		
		return clients.stream()
					  .anyMatch(c -> c.getId() == id);
	}

	@Override
	public Client registerClient(String name, int reservingSpace) {
		
		Client c = new Client(this.clientIdGenerator.generateId(),name,reservingSpace);
		
			if(reservingSpace <= 0) {
				throw new IllegalArgumentException();
			}
			if(reservingSpace > getAvailableSpace()) {
				throw new InsufficientSpaceException("no space");
			}
		
		clients.add(c);
		return c;
	}

	@Override
	public void storePackage(Client client, Package packa) {
		
		if(client.getAvailableSpace() < packa.getSpace() ) {
			throw new InsufficientSpaceException("no space");
		}
		 client.addPackage(packa);
		
	}

}
