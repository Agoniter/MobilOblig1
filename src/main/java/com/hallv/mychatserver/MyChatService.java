/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hallv.mychatserver;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hallv
 */
@Path("chat")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class MyChatService {
    
    public static String LAST_MESSAGE_QUERY = 
        "SELECT c.id, m.id, m.sender_id, m.text, m.created " +
        "FROM conversation c " +
        "JOIN message m ON c.id = m.conversation_id " +
        "LEFT JOIN message m2 " +
        "ON c.id = m2.conversation_id AND " +
        "(m.created < m2.created OR m.created = m2.created AND m.id < m2.id) " +
        "WHERE m2.id IS NULL ORDER BY c.created";
    @PersistenceContext
    EntityManager em;
    
    @Resource(mappedName="jdbc/MyChat" )
    DataSource dataSource;
    
    @GET
    @Path("contacts")
    public List<Contact> getAllContacts() {
            return em.createQuery("Select c from Contact c", Contact.class).getResultList();
    }  
  
    @GET
    @Path("messages/{contactid}")
    public List<Message> getMessages(@PathParam("contactid") Long contactid) {
        return em.createQuery("select m from Message m where m.owner.id = :ownerid or m.recipient.id = :ownerid",Message.class)
                .setParameter("ownerid", contactid)
                .getResultList();
    }
    
    @GET
    @Path("newcontact")
    public List<Contact> newContact(
            @QueryParam("contactname") String name
    ){
        List<Contact> result = em.createQuery("select c from Contact c where c.name = :conname").setParameter("conname", name).getResultList();
        if(result.isEmpty()){
            Contact newCon = new Contact(name);
            result.add(newCon);
            em.persist(newCon);
        }
        return result;
    }
    /*
    @GET
    @Path("messages/send")
    public Message sendMessage(
            @QueryParam("conversationid") Long conversationid,
            @QueryParam("senderid") Long senderId, @QueryParam("text")String text) {
        Message result = null;
        
        Conversation conversation = em.find(Conversation.class, conversationid);
        Contact sender = em.getReference(Contact.class, senderId);
        if(conversation != null && sender != null) {
            result = new Message(text, sender, conversation);
            em.persist(result);
        }
        
        return result;
    }
    */
  
    
    @GET
    @Path("createdata")
    public List<Contact> createContacts() {
             
        List<Contact> result = new ArrayList<>();
        List<Contact> contacts = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            contacts.add(new Contact("Contact #" + i));
        }
        
        for(Contact contact : contacts) {
      
                for(int j = 0; j < 3; j++ ) {
                    contact.addMessage(
                        new Message("Text from user " + contact.getName() + " #" + j,contact, getRandomContact(contacts).get(0))
                    );
                }
                em.persist(contact);
            }
       
        return result;
    }
    
    private static List<Contact> getRandomContact(List<Contact> contacts) {
        List result = new ArrayList(contacts);
        Collections.shuffle(contacts);
        return result.subList(0, 5);
    }
    
    
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class LastConversation {
        long id;
        List<LastMessage> messages;

        public LastConversation(long id, long messageid, long senderid, String text, Date created) {
            this.id = id;
            this.messages = new ArrayList<>();
            this.messages.add(new LastMessage(messageid,senderid,text,created));
        }        
    }

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)   
    public static class LastMessage {
        long id;
        long sender;
        String text;
        Date created;
        public LastMessage(long id, long sender, String text, Date created){
            this.id = id;
            this.sender = sender;
            this.text = text;
            this.created = created;

      
        }
    }
}
