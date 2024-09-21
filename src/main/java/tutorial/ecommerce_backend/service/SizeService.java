package tutorial.ecommerce_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.api.DTO.SizeDTO;
import tutorial.ecommerce_backend.dao.SizeDao;
import tutorial.ecommerce_backend.entity.Size;

@Service
public class SizeService {

	@Autowired
	private SizeDao sizeDao;

	public List<Size> getSizes() {
		return sizeDao.findAll();
	}
	
	public List<Size> getSizeByType(String type){
		return sizeDao.findByType(type);
	}

	public void addSize(SizeDTO sizeDto) {
		Size size = new Size();
		size.setSize(sizeDto.getSize());
		size.setType(sizeDto.getType());
		sizeDao.save(size);
	}

	public void deleteSize(long id) {
		Optional<Size> opSize = sizeDao.findById(id);
		if (opSize.isPresent()) {
			Size size = opSize.get();
			sizeDao.delete(size);
		}
	}

}
