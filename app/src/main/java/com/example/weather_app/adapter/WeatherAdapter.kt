package com.example.weather_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.databinding.WeatherItemBinding
import com.example.weather_app.model.Result

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val dataSet = mutableListOf<Result>()
    private lateinit var clickListener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.load(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    fun setAdapter(results: List<Result>, listener: ClickListener) {
        dataSet.clear()
        dataSet.addAll(results)
        clickListener = listener
        notifyDataSetChanged()
    }

    class WeatherViewHolder(
        private val binding: WeatherItemBinding,
        private val listener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun load(result: Result) = with(binding) {
            tvWeatherMain.text = result.weather.first().main
            tvTemp.text = "Temp: ${result.main.temp.toString().substringBefore(".")}"
            root.setOnClickListener {
                listener.itemClicked(result)
            }
        }
    }
}