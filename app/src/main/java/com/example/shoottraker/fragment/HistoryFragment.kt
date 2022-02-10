package com.example.shoottraker.fragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoottraker.R
import com.example.shoottraker.activity.ShotActivity
import com.example.shoottraker.adapter.HistoryAdapter
import com.example.shoottraker.databinding.FragmentHistoryBinding
import com.example.shoottraker.dto.HistoryDto
import com.example.shoottraker.model.HistoryModel
import com.example.shoottraker.service.HistoryService
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryFragment : Fragment() {

    private val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        HistoryAdapter(itemClickListener = {
            val intent = Intent(activity, ShotActivity::class.java)
            intent.putExtra("date", it.id)
            startActivity(intent)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(layoutInflater.context)

        getHistory()

        return binding.root
    }

    private fun getHistory() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HistoryService::class.java).also {
            it.listHistories()
                .enqueue(object : Callback<HistoryDto> {
                    override fun onResponse(
                        call: Call<HistoryDto>,
                        response: Response<HistoryDto>
                    ) {
                        if (response.isSuccessful.not()) {
                            // 예외 처리
                        }
                        response.body()?.let { historyDto ->
                            adapter.submitList(historyDto.histories)

                        }
                    }

                    override fun onFailure(call: Call<HistoryDto>, t: Throwable) {
                        showNetworkErrorMessage()
                    }
                }
                )
        }
    }

    private fun showNetworkErrorMessage() {
        binding.errorImageView.visibility = View.VISIBLE
        binding.errorTextView.visibility = View.VISIBLE

        Snackbar.make(binding.root, R.string.network_error_message, Snackbar.LENGTH_SHORT)
            .show()
    }
}