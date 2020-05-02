package com.bz.blueauth.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

import com.bz.blueauth.dto.pojo.UserPO;
import com.bz.blueauth.exception.BadRequestException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.DeleteResult;


public class UserDAO {

	private MongoDriver db;
	private MongoCollection<UserPO> coll;	
	
	public UserDAO() {
		this.db = MongoDriver.getInstance();	
		if(!this.db.isMongoDown()){
			this.refreshMongoConnection();
		}
	}	
	
	private void refreshMongoConnection() {
		this.db = MongoDriver.getInstance();		
		this.coll =  this.db.getDB().getCollection("User", UserPO.class);
		IndexOptions indexOptions = new IndexOptions().unique(true);
		this.coll.createIndex(Indexes.ascending("userid"), indexOptions);
	}
	
	public void create(UserPO obj) throws BadRequestException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		try {
			this.coll.insertOne(obj);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage().toString());
		}
        
	}	

	public void save(UserPO obj) {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
        System.out.println("Original Person Model: " + obj);
		//return this.coll.findOneAndReplace(eq("userid", obj.getUserid()));
		this.coll.findOneAndReplace(or(eq("userid", obj.getUserid()), eq("email", obj.getEmail())), obj);
		
	}
	
	public long delete(String userId) {
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		DeleteResult res = this.coll.deleteMany(eq("userid", userId));
		return res.getDeletedCount();
	}
	
	
	public UserPO getUser(String criteria) throws BadRequestException {	
		if(this.db.isMongoDown())
			this.refreshMongoConnection();
		MongoCursor<UserPO> cursor = this.coll.find(or(eq("userid", criteria), eq("email", criteria))).iterator();
		if(cursor.hasNext())
			return cursor.next();
		else
			throw new BadRequestException("User "+criteria+" doesn't exist. ");	
		
    }

}

