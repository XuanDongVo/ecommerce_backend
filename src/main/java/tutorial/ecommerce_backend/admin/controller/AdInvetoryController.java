package tutorial.ecommerce_backend.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tutorial.ecommerce_backend.entity.Inventory;
import tutorial.ecommerce_backend.service.InventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin/inventory")
public class AdInvetoryController {
	@Autowired
	private InventoryService inventoryService;

	@GetMapping
	public ResponseEntity<?> getInventories() {
		try {
			List<Inventory> inventories = inventoryService.getInvetories();
			return ResponseEntity.ok(inventories);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getInventoryById(@PathVariable long id) {
		try {
			Inventory inventory = inventoryService.getInventoryById(id);
			return ResponseEntity.ok(inventory);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> removeInventoryById(@PathVariable long id) {
		try {
			 inventoryService.removeInvetoryById(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	
	@PostMapping("/edit/{inventoryId}")
	public ResponseEntity<?> modifyQuantityInInventoryById(@PathVariable long inventoryId , @RequestParam("quantity") int quantity) {
		try {
			 inventoryService.modifyQuantityInInventoryById(inventoryId , quantity);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
}
