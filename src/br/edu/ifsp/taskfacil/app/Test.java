package br.edu.ifsp.taskfacil.app;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.edu.ifsp.taskfacil.dao.UserDAO;
import br.edu.ifsp.taskfacil.db.JPAUtil;
import br.edu.ifsp.taskfacil.models.User;
import br.edu.ifsp.taskfacil.tools.SHAUtil;

public class Test {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//UserDAO uD = new UserDAO();		
		User user = new User("Mateus","mateusk@yahoo.com.br","999890");
		String senhaDigitada = "999891";
		
		System.out.println(user.getEmail().matches("\\w+@\\w+\\.\\w{2,3}(\\.\\w{2,3})?"));
		
		System.out.println(user.toString());
		//uD.insert(user);
		
		System.out.println(SHAUtil.geraHash(user.getPassword()));
		System.out.println(SHAUtil.geraHash(senhaDigitada));
		
		System.out.println(SHAUtil.geraHash(user.getPassword()).equals(SHAUtil.geraHash(senhaDigitada)));
		
	}

}
