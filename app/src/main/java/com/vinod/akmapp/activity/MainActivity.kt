package com.vinod.akmapp.activity

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.vinod.akmapp.Contact
import com.vinod.akmapp.model.ContactViewModel
import com.vinod.akmapp.adapter.ContactsListAdapter
import com.vinod.akmapp.adapter.ContactsListAdapter.ChangeClickListener
import com.vinod.akmapp.adapter.ContactsListAdapter.DeleteClickListener
import com.vinod.akmapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private lateinit var wordViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ContactsListAdapter(this)

        adapter.deleteClickListener = object : DeleteClickListener {
            override fun onBtnClick(id: Int) {
                wordViewModel.delete(id)
            }
        }

        adapter.changeClickListener = object : ChangeClickListener {
            override fun onBtnClick(contact: Contact) {
                val intent = Intent(this@MainActivity, EditContactActivity::class.java)
                var bundle = Bundle()
                bundle.putParcelable("contact", contact)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }

        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        wordViewModel.allContacts.observe(this, android.arch.lifecycle.Observer { contacts ->
            contacts.let {
                adapter.setContacts(it!!)
            }
        })



        btAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NewContactActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }





    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val contact = Contact(null, data.getStringExtra(NewContactActivity.EXTRA_REPLY_NAME), data.getStringExtra(NewContactActivity.EXTRA_REPLY_NUMBER))
                wordViewModel.insert(contact)
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    "Contact not saved because it is empty.",
                    Toast.LENGTH_LONG
            ).show()
        }
    }
}
