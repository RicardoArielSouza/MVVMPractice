package br.com.racstech.mvvmexample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.racstech.mvvmexample.adapter.RecyclerAdapter
import br.com.racstech.mvvmexample.model.Blog
import br.com.racstech.mvvmexample.viewModel.MainViewModel
import br.com.racstech.mvvmexample.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecycler: RecyclerView
    private lateinit var but: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        but = findViewById(R.id.button)
        but.setOnClickListener{
            addData()
        }

        initialiseAdapter()
    }

    private fun initialiseAdapter(){
        mainRecycler.layoutManager = viewManager
        observeData()
    }

    fun observeData(){
        viewModel.lst.observe(this, Observer {
            Log.i("data", it.toString())
            mainRecycler.adapter = RecyclerAdapter(viewModel, it, this)
        })
    }

    fun addData(){
        var txtPlace = findViewById<EditText>(R.id.titleTxt)
        var title = txtPlace.text.toString()
        if (title.isNullOrBlank()){
            Toast.makeText(this, "Entre com o valor!", Toast.LENGTH_LONG).show()
        } else{
            var blog = Blog(title)
            viewModel.add(blog)
            txtPlace.text.clear()
            mainRecycler.adapter?.notifyDataSetChanged()
        }
    }
}
