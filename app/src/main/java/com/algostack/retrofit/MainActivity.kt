package com.algostack.retrofit

import android.content.ContentValues.TAG
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.algostack.retrofit.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MAINACTIVITY"
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecylerview()

        lifecycleScope.launchWhenCreated {
            binding.rbProgress.isVisible = true

            val response = try {
                RetrofitInstance.api.getTodos()
            }catch (e: IOException){
                Log.e(TAG, "IOEception, you might not have internet connection")
                binding.rbProgress.isVisible = false
                return@launchWhenCreated


            }catch (e: HttpException){
                Log.e(TAG, "HttpEception, unexpected response")
                binding.rbProgress.isVisible = false
                return@launchWhenCreated

            }

            if(response.isSuccessful && response.body() != null){
                todoAdapter.todo = response.body()!!
            }else{
                Log.e(TAG, "Responce not susscessfull")
            }

            binding.rbProgress.isVisible = false
        }
    }


    private fun setupRecylerview() = binding.rbTodo.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

}