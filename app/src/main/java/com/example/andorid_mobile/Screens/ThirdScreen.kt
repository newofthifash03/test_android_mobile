package com.example.andorid_mobile.Screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.andorid_mobile.Retrofit.Retrofit
import com.example.andorid_mobile.Retrofit.UserAPI
import com.example.andorid_mobile.Retrofit.UserAdapter
import com.example.andorid_mobile.Retrofit.OnUserClickListener
import com.example.andorid_mobile.Retrofit.Data
import com.example.andorid_mobile.Retrofit.UserResponse
import com.example.andorid_mobile.databinding.ActivityThirdScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreen : AppCompatActivity(), OnUserClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityThirdScreenBinding
    private val userList = ArrayList<Data>()
    private lateinit var adapter: UserAdapter
    private var page = 1
    private var totalPage = 1
    private var isLoading = false
    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra("name") ?: ""

        setupRecyclerView()
        setupSwipeRefresh()
        setupBackButton()

        getUsers(false)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(userList, this)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.rvUsers.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && page < totalPage) {
                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        page++
                        getUsers(false)
                    }
                }
            }
        })
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(this)
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this@ThirdScreen, SecondScreen::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
            finish()
        }
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) {
            binding.progressBar.visibility = View.VISIBLE
        }

        val retro = Retrofit.getRetroData().create(UserAPI::class.java)
        retro.getUsers(page).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.swipeRefresh.isRefreshing = false
                isLoading = false

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    totalPage = userResponse?.total_pages ?: 1
                    userResponse?.data?.let {
                        adapter.addList(it)
                    }
                } else {
                    Toast.makeText(this@ThirdScreen, "Response error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.swipeRefresh.isRefreshing = false
                isLoading = false
                Toast.makeText(this@ThirdScreen, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getUsers(true)
    }

    override fun onUserItemClicked(position: Int) {
        val intent = Intent(this, SecondScreen::class.java)
        intent.putExtra("name", name)
        val selectedUser = userList.getOrNull(position)
        selectedUser?.let {
            intent.putExtra("username", "${it.first_name} ${it.last_name}")
        }
        startActivity(intent)
    }
}
