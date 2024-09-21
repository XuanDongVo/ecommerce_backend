package tutorial.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.dao.InvetoryDao;
import tutorial.ecommerce_backend.entity.Inventory;

@Service
public class InventoryService {
	@Autowired
	private InvetoryDao invetoryDao;

	public List<Inventory> getInvetories() {
		return invetoryDao.findAll();
	}

	public Inventory getInventoryById(Long id) {
		Inventory inventory = invetoryDao.findById(id)
				.orElseThrow(() -> new RuntimeException("can not get inventory by id:" + id));
		return inventory;
	}

	public void removeInvetoryById(Long id) {
		invetoryDao.deleteById(id);
	}

	public void modifyQuantityInInventoryById(Long id, int quantity) {
		Inventory inventory = invetoryDao.findById(id)
				.orElseThrow(() -> new RuntimeException("can not get inventory by id:" + id));
		
		inventory.setQuantity(quantity);
		invetoryDao.save(inventory);
	}

}
