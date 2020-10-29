package cookmap.cookandroid.hw.tdcalendar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cookmap.cookandroid.hw.tdcalendar.databinding.ScheduleItemBinding
import cookmap.cookandroid.hw.tdcalendar.model.Contents

class main_frag_adapter(var arraylist : ArrayList<Contents>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ScheduleItemBinding.inflate(LayoutInflater.from(parent.context)
            , parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //( holder as Viewholder).binding
        //TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    inner class Viewholder( viewBinding : ScheduleItemBinding)
        : RecyclerView.ViewHolder(viewBinding.root){
        val binding = viewBinding
        fun bind(item: Contents){
        }
    }
}