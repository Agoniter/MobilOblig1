/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hallv.mychatserver;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author hallv
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Message implements Serializable {
    
    @Id @GeneratedValue
    long id;
    
    String text;
    
    @XmlJavaTypeAdapter(Contact.ContactAdapter.class)
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    Contact owner;
    
    @XmlJavaTypeAdapter(Contact.ContactAdapter.class)
    @OneToOne(optional = false, cascade = CascadeType.PERSIST)
    Contact recipient;
    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date created = new Date();
    
    @Version
    Timestamp updated;
    
    public Message() {
    
    }
    public Message (String text, Contact owner, Contact recipient){
        this.text = text;
        this.owner = owner;
        this.recipient = recipient;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
    }

    
    public Contact getRecipient() {
        return recipient;
    }

    public void setRecipient(Contact recipient) {
        this.recipient = recipient;
    }

    

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
    
    
}
