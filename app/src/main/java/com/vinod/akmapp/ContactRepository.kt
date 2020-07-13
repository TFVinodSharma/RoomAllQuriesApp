package com.vinod.akmapp

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.vinod.akmapp.appinterface.ContactDAO

class ContactRepository(private val contactDao: ContactDAO) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    @WorkerThread
    fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    @WorkerThread
    fun delete(id: Int) {
        contactDao.delete(id)
    }

    @WorkerThread
    fun update(contact: Contact) {
        contactDao.update(contact)
    }
}