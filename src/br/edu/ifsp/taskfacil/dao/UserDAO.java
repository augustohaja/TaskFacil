package br.edu.ifsp.taskfacil.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.edu.ifsp.taskfacil.db.JPAUtil;
import br.edu.ifsp.taskfacil.models.User;

public class UserDAO {
	private EntityManager manager;
	
	public UserDAO (){
		this.manager = JPAUtil.getEntityManager();
	}
	
	public void finalize(){
		manager.close();
	}
	
	public void insert(User user){
		this.manager.getTransaction().begin();
    	this.manager.persist(user);
    	this.manager.getTransaction().commit();
	}
	
	public void update(User user){
		this.manager.getTransaction().begin();
    		this.manager.merge(user);
    	this.manager.getTransaction().commit();
	}
	
	public void delete(User user){
		this.manager.getTransaction().begin();
       	user = this.manager.find(User.class, user.getId());
    	this.manager.remove(user);
    	this.manager.getTransaction().commit();
	}
	
	public List<User> searchByEmail(String eMail){
		Query query = manager.createQuery("SELECT s FROM User s WHERE email = '" + eMail.toString() + "'");
		List<User> list = query.getResultList();
		return list;
	}
	
	public List<User> all() {
    	Query query = manager.createQuery("select s from User s"); 
        List<User> list = query.getResultList();
    	return list;
    }
}
