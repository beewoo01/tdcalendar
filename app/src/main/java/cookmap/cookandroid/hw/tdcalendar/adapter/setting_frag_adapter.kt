package cookmap.cookandroid.hw.tdcalendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingItemBinding

class setting_frag_adapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    constructor(){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = SettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class viewholder(viewBinding: SettingItemBinding) : RecyclerView.ViewHolder(viewBinding.root){
        var binding = viewBinding

    }
}