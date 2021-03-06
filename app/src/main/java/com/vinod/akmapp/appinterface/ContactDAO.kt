package com.vinod.akmapp.appinterface

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.vinod.akmapp.Contact

@Dao
interface ContactDAO {

    //Alphabetic order
//    @Query("SELECT * from contacts ORDER BY name ASC")
//    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * from contacts ORDER BY id")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts")
    fun all(): List<Contact>

    @Insert
    fun insert(contact: Contact)

    @Insert(onConflict = REPLACE)
    fun update(contact: Contact)

    @Query("DELETE FROM contacts")
    fun deleteAll()

    @Query("DELETE FROM contacts WHERE id = :id")
    fun delete(id: Int)
}