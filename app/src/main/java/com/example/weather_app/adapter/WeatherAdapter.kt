package com.example.weather_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.databinding.WeatherItemBinding
import com.example.weather_app.model.Result

class WeatherAdapter(
    private val clickListener: (Result) -> Unit
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val dataSet = mutableListOf<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding).apply {
            itemView.setOnClickListener { clickListener.invoke(dataSet[adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.load(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    fun setList(results: List<Result>) {
        dataSet.clear()
        dataSet.addAll(results)
        notifyDataSetChanged()
    }

    class WeatherViewHolder(
        private val binding: WeatherItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun load(result: Result) = with(binding) {
            tvWeatherMain.text = result.weather.first().main
            tvTemp.text = String.format("Temp: %s", result.main.temp.toString().substringBefore("."))
        }
    }
}