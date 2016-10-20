/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hallv.mychatserver;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author hallv
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "ChatContact")
public class Contact implements Serializable{
    @Id @GeneratedValue
    Long id;
    String name;
    
    @Version
    Timestamp updated;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date created = new Date();
    
    @XmlTransient
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    List<Message> msgs;

    
    public Contact(){
    }
    public Contact(String name){
        this.name  = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
    public List<Message> getMsgs() {
        if(msgs == null) {
            msgs = new ArrayList<>();
        }
        return msgs;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }
    public void addMessage(Message message){
        if(msgs == null) {
          msgs = new ArrayList<>();
        }
        msgs.add(message);
    }
  
    
  
    public static class ContactAdapter extends XmlAdapter<Long, Contact> {

        @Override
        public Contact unmarshal(Long v) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Long marshal(Contact v) throws Exception {
            return v != null ? v.getId() : null;
        }
        
    }
}
