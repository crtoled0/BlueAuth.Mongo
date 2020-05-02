package com.bz.blueauth.model;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.util.Properties;

import com.bz.blueauth.exception.GeneralException;
import com.bz.blueauth.tools.AppProperties;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



public class MongoDriver {	   
	private static MongoDriver _instance;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private CodecRegistry pojoCodecRegistry;
	private boolean isMongoDown = true;
	private static Logger logger = LoggerFactory.getLogger(MongoDriver.class);
	//@Value("${db.connStr}")
	private String localUrl;	
	//@Value("${db.name}")
	private String dbName;
	
	public void setEnvVars() {
			
		AppProperties props = AppProperties.getInstance();
		//	throw new GeneralException(String.format("can't find any property for example %s that ", env.getProperty("db.user")));
		
			//logger.info(props.get("db"));
			if(props.get("db.user") != null && !"".equalsIgnoreCase(props.get("db.user")))
				this.localUrl = String.format("mongodb://%s:%s@%s:%s",props.get("db.user"),
																  	  props.get("db.password"),
																	  props.get("db.host"),
																	  props.get("db.port"));
			else
				this.localUrl = String.format("mongodb://%s:%s",props.get("db.host"),
																props.get("db.port"));
			this.dbName = props.get("db.name");
			logger.info("localUrl1 ["+this.localUrl+"] dbName1 ["+this.dbName+"]");
			
	}
    private void setEnvVarsOLD() {
       
		try{
			Properties prop = new Properties();
			prop.load(MongoDriver.class.getClassLoader().getResourceAsStream("application.properties"));
			if(prop.getProperty("db.user") != null && !"".equalsIgnoreCase(prop.getProperty("db.user")))
				this.localUrl = String.format("mongodb://%s:%s@%s:%s",prop.getProperty("db.user"),
																	  prop.getProperty("db.password"),
																	  prop.getProperty("db.host"),
																	  prop.getProperty("db.port"));
			else
				this.localUrl = String.format("mongodb://%s:%s",prop.getProperty("db.host"),
																prop.getProperty("db.port"));
			this.dbName = prop.getProperty("db.name");
		} catch (IOException e) {
			throw new GeneralException("No properties available");
		} 
     }
	
	 @Autowired
	private MongoDriver() {
		//String localUrl = "mongodb://localhost:27017", dbName = "parking-db";
		this.stablishConnection();
		
	}

	private  void stablishConnection(){
		this.setEnvVars();
		logger.info("localUrl ["+this.localUrl+"] dbName ["+this.dbName+"]");
		try {
			this.mongoClient = MongoClients.create(this.localUrl);
			// create codec registry for POJOs
			this.pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
									fromProviders(PojoCodecProvider.builder().automatic(true).build()));
			this.database = this.mongoClient.getDatabase(this.dbName).withCodecRegistry(pojoCodecRegistry);
			this.isMongoDown = false;
		} catch (Exception e) {
			this.mongoClient.close();
			this.mongoClient = null;
			this.isMongoDown = true;
			logger.info(String.format("Not Connected To database [%s]", this.localUrl));
		}
			
		
	}

	public void refreshConnection(){
		this.stablishConnection();
	}
	
	public boolean isMongoDown() {
		return isMongoDown;
	}

	public static MongoDriver getInstance() {
		if(MongoDriver._instance == null || MongoDriver._instance.mongoClient == null || MongoDriver._instance.isMongoDown())
			return new MongoDriver();
		else {
			return MongoDriver._instance;
		}	
	}

	public MongoDatabase getDB() {
		return database;
	}
	
	@Override
	public void finalize() {
	    this.mongoClient.close();
	    this.mongoClient = null;
	    this.isMongoDown = true;
	}
}
