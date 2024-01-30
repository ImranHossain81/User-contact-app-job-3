package com.example.imran_47129083_job3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imran_47129083_job3.databinding.ActivityMainBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity(), ItemClickListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val db = FirebaseFirestore.getInstance()
    private val userlist = db.collection("users")
    private val userD = mutableListOf<UserD>()
    private lateinit var adapterlist: Adapterlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapterlist = Adapterlist(userD, this)
        binding.recyclerView.adapter = adapterlist
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.saveBtn.setOnClickListener {
            val name = binding.ename.text.toString()
            val email = binding.eemail.text.toString()
            val phone = binding.ephone.text.toString()
            val address = binding.eaddress.text.toString()
            if(name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()&& address.isNotEmpty()){
                addStroy(name, email, phone, address)
            }else{
                Toast.makeText(this,"Please, fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        fetchData()
    }

    private fun fetchData() {
        userlist
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                userD.clear()
                for(st in it){
                    val item = st.toObject(UserD::class.java)
                    item.id = st.id
                    userD.add(item)
                }
                adapterlist.notifyDataSetChanged()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addStroy(name: String, email: String, phone: String, address: String) {
        val useritem = UserD(name = name, email = email, phone = phone, address = address)
        userlist.add(useritem)
            .addOnSuccessListener {
                useritem.id = it.id
                userD.add(useritem)
                adapterlist.notifyDataSetChanged()
                binding.ename.text = null
                binding.eemail.text = null
                binding.ephone.text = null
                binding.eaddress.text = null
                fetchData()
                Toast.makeText(this,"User added", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to add user", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onEditItemClick(userD: UserD) {
        binding.ename.setText(userD.name)
        binding.eemail.setText(userD.email)
        binding.ephone.setText(userD.phone)
        binding.eaddress.setText(userD.address)
        binding.saveBtn.text = "save date"

        binding.saveBtn.setOnClickListener {
            val updateName = binding.ename.text.toString()
            val updateEmail = binding.eemail.text.toString()
            val updatephone = binding.ephone.text.toString()
            val updateAddress = binding.eaddress.text.toString()
            if(updateName.isNotEmpty() && updateEmail.isNotEmpty() && updatephone.isNotEmpty() && updateAddress.isNotEmpty()){
                val updateUserD = UserD(userD.id, updateName, updateEmail, updateAddress)
                userlist.document(userD.id!!)
                    .set(updateUserD)
                    .addOnSuccessListener {
                        binding.ename.text?.clear()
                        binding.eemail.text?.clear()
                        binding.ephone.text?.clear()
                        binding.eaddress.text?.clear()
                        Toast.makeText(this,"List updated", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
            }else{
                Toast.makeText(this,"Please, fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDeleteItemClick(userD: UserD) {
        userlist.document(userD.id!!)
            .delete()
            .addOnSuccessListener {
                adapterlist.notifyDataSetChanged()
                fetchData()
                Toast.makeText(this,"User deleted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed to delete user", Toast.LENGTH_SHORT).show()
            }
    }


}