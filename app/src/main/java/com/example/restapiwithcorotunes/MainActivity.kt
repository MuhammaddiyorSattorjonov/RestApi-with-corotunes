package com.example.restapiwithcorotunes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restapiwithcorotunes.adapters.RvAdapter
import com.example.restapiwithcorotunes.databinding.ActivityMainBinding
import com.example.restapiwithcorotunes.databinding.ItemDialogBinding
import com.example.restapiwithcorotunes.models.MyPostToDoRequest
import com.example.restapiwithcorotunes.models.MyTodo
import com.example.restapiwithcorotunes.repository.ToDoRepository
import com.example.restapiwithcorotunes.retrofit.ApiClient
import com.example.restapiwithcorotunes.utils.Status
import com.example.restapiwithcorotunes.viewmodel.ToDoViewModel
import com.example.restapiwithcorotunes.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), RvAdapter.RvClick {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var todoViewModel: ToDoViewModel
    private val TAG = "MainActivity"
    private lateinit var toDoRepository: ToDoRepository
    private lateinit var adapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toDoRepository = ToDoRepository(ApiClient.getApiService())
        todoViewModel =
            ViewModelProvider(this, ViewModelFactory(toDoRepository)).get(ToDoViewModel::class.java)
        adapter = RvAdapter(rvClick = this)
        binding.rv.adapter = adapter

        todoViewModel.getAllTodo()
            .observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d(TAG, "onCreate: Loading")
                        binding.progress.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "onCreate: Error ${it.message}")
                        binding.progress.visibility = View.GONE
                        Toast.makeText(this, "Error${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    Status.SUCCES -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        adapter.list = it?.data!!
                        adapter.notifyDataSetChanged()
                        binding.progress.visibility = View.GONE
                    }
                }
            }

        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialog.setView(itemDialogBinding.root)

            itemDialogBinding.btnAdd.setOnClickListener {
                itemDialogBinding.apply {
                    val myPostToDoRequest = MyPostToDoRequest(
                        spinnerStatus.selectedItem.toString(),
                        edtAbout.text.toString().trim(),
                        edtDeadline.text.toString().trim(),
                        edtTitle.text.toString().trim()
                    )
                    todoViewModel.addMyTodo(myPostToDoRequest).observe(this@MainActivity) {
                        when (it.status) {
                            Status.LOADING -> {

                            }
                            Status.ERROR -> {
                                Toast.makeText(this@MainActivity,
                                    "${it.message}",
                                    Toast.LENGTH_SHORT).show()
                            }
                            Status.SUCCES -> {
                                Toast.makeText(this@MainActivity,
                                    "${it.data?.sarlavha}${it.data?.id} ga saqlandi",
                                    Toast.LENGTH_SHORT).show()
                                dialog.cancel()
                            }
                        }
                    }
                    dialog.cancel()
                }

            }

            dialog.show()
        }
    }

    override fun menuCklick(image: ImageView, myTodo: MyTodo) {
        val popupMenu = PopupMenu(this, image)
        popupMenu.inflate(R.menu.todomenu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    editToDo(myTodo)
                }
                R.id.menu_delete -> {
                    deleteToDo(myTodo)
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun editToDo(myTodo: MyTodo) {

        val dialog = AlertDialog.Builder(this).create()
        val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)

        itemDialogBinding.apply {
            edtTitle.setText(myTodo.sarlavha)
            edtAbout.setText(myTodo.matn)
            edtDeadline.setText(myTodo.oxirgi_muddat)

            when (myTodo.holat) {
                "Yangi" -> spinnerStatus.setSelection(0)
                "Bajarilmoqda" -> spinnerStatus.setSelection(1)
                "Tuggalandi" -> spinnerStatus.setSelection(2)
            }
            btnAdd.setOnClickListener {
                val myPostToDoRequest = MyPostToDoRequest(
                    spinnerStatus.selectedItem.toString(),
                    edtAbout.text.toString().trim(),
                    edtDeadline.text.toString().trim(),
                    edtTitle.text.toString().trim()
                )

                todoViewModel.updateMyToDo(myTodo.id, myPostToDoRequest)
                    .observe(this@MainActivity) {
                        when (it.status) {
                            Status.ERROR -> {
                                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            Status.LOADING -> {
                            }
                            Status.SUCCES -> {
                                Toast.makeText(this@MainActivity, "Updated", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.cancel()
                            }
                        }
                    }
            }
        }
        dialog.setView(itemDialogBinding.root)
        dialog.show()
    }
    private fun deleteToDo(myTodo: MyTodo){
        todoViewModel.deleteTodo(myTodo.id)
    }
}