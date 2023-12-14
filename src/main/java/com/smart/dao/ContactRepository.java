/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smart.dao;

import com.smart.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author usama-saeed-424
 */
public interface ContactRepository extends JpaRepository<Contact, Integer>{
    //pagination..
    @Query("from Contact as c where c.user.id=:userId")
    //Current page 
    //Page-size
    public Page<Contact> findContactsByUser(@Param("userId")int userId,Pageable pageable);
}
