package br.edu.ifsp.taskfacil.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.edu.ifsp.taskfacil.db.JPAUtil;
import br.edu.ifsp.taskfacil.models.Task;

public class TaskDAO {
	private EntityManager manager;
	
	public TaskDAO(){
		this.manager = JPAUtil.getEntityManager();
	}
	
	public void finalize(){
		manager.close();
	}
	
	public void insert(Task task){
		this.manager.getTransaction().begin();
    	this.manager.persist(task);
    	this.manager.getTransaction().commit();
	}
	
	public void update(Task task){
		this.manager.getTransaction().begin();
    		this.manager.merge(task);
    	this.manager.getTransaction().commit();
	}
	
	public void delete(Task task){
		this.manager.getTransaction().begin();
       	task = this.manager.find(Task.class, task.getId());
    	this.manager.remove(task);
    	this.manager.getTransaction().commit();
	}
	
	public List<Task> all() {
    	Query query = manager.createQuery("select s from Task s"); 
        List<Task> list = query.getResultList();
    	return list;
    }
	
	public List<Task> allbyUser(){
		Query query = manager.createQuery("select s from Task s"); 
        List<Task> list = query.getResultList();
    	return list;
	}
}
