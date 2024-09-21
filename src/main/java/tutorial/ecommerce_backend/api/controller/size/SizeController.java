package tutorial.ecommerce_backend.api.controller.size;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tutorial.ecommerce_backend.api.DTO.SizeDTO;
import tutorial.ecommerce_backend.entity.Size;
import tutorial.ecommerce_backend.service.SizeService;

@RestController
@RequestMapping("size")
public class SizeController {
	@Autowired
	private SizeService sizeService;

	@GetMapping
	public List<Size> getSizes() {
		return sizeService.getSizes();
	}

	@PostMapping("/add")
	public void addSize(@AuthenticationPrincipal @Valid @RequestBody SizeDTO dto) {
		sizeService.addSize(dto);
	}

	@PostMapping("/delete/{id}")
	public void addSize(@AuthenticationPrincipal @PathVariable long id) {
		sizeService.deleteSize(id);
	}
}
