package tutorial.ecommerce_backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tutorial.ecommerce_backend.entity.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
	public Role findByName(String name);

}
