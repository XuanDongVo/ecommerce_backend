package tutorial.ecommerce_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tutorial.ecommerce_backend.dao.SubCategoryDao;
import tutorial.ecommerce_backend.entity.SubCategory;

@Service
public class SubCategoryService {

	@Autowired
	private SubCategoryDao subCategoryDao;
	
	public List<SubCategory> getSubCategories(){
		return subCategoryDao.findAll();
	}
}
