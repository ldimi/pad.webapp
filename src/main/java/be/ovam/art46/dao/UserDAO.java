package be.ovam.art46.dao;

import be.ovam.art46.model.User;
import be.ovam.art46.struts.actionform.UsersForm;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {
	
	public void saveUsers(UsersForm form) throws Exception {
		List<String> sql = new ArrayList<String>();
		sql.add("delete from ovam.user_rol where user_id = ? and UPPER(user_rol) in ('ADMINART46','ADMINBOA','ADMINIVS','ADMINJD','ADMINBOEK')");
		sql.add("insert into ovam.user_rol (USER_ID, USER_ROL) values (?,?)");
		List<List<Object[]>> params = new ArrayList<List<Object[]>>();
		List<Object[]> paramsDelete = new ArrayList<Object[]>();
		params.add(paramsDelete);
		List<Object[]> paramsInsert = new ArrayList<Object[]>();
		params.add(paramsInsert);
		if (form.getUser_id() != null && form.getUser_id().length > 0) {					
			for (int t=0; t< form.getUser_id().length; t++) {
				paramsDelete.add(new String[] {form.getUser_id()[t]});		
			}
		}
		if (form.getAdminArt46() != null && form.getAdminArt46().length > 0) {
			for (int t=0; t< form.getAdminArt46().length; t++) {
				paramsInsert.add(new String[] {form.getAdminArt46()[t], "adminArt46"});					
			} 
		}	
		if (form.getAdminBOA() != null && form.getAdminBOA().length > 0) {
			for (int t=0; t< form.getAdminBOA().length; t++) {
				paramsInsert.add(new String[] {form.getAdminBOA()[t], "adminBOA"});						
			} 
		}	
		if (form.getAdminIVS() != null && form.getAdminIVS().length > 0) {
			for (int t=0; t< form.getAdminIVS().length; t++) {
				paramsInsert.add(new String[] {form.getAdminIVS()[t], "adminIVS"});						
			} 
		}	
		if (form.getAdminJD() != null && form.getAdminJD().length > 0) {
			for (int t=0; t< form.getAdminJD().length; t++) {
				paramsInsert.add(new String[] {form.getAdminJD()[t], "adminJD"});				
			} 
		}
		if (form.getAdminBoek() != null && form.getAdminBoek().length > 0) {
			for (int t=0; t< form.getAdminBoek().length; t++) {
				paramsInsert.add(new String[] {form.getAdminBoek()[t], "adminBoek"});						
			} 
		}
		executeUpdateAll(sql, params);	
	}
	
	
	public User findByUserId(String user_id) throws Exception {		
		User user = (User) getObject(User.class, user_id);
		return user;
	}
	
}
