package cookmap.cookandroid.hw.tdcalendar.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import cookmap.cookandroid.hw.tdcalendar.MainActivity
import cookmap.cookandroid.hw.tdcalendar.R
import cookmap.cookandroid.hw.tdcalendar.databinding.SettingParentBinding

class Setting_profile_Fragment : DialogFragment() {

    lateinit var ft : FragmentTransaction
    val mainChildTag = "settin_main"
    val imageChildTag = "Image_fragment"
    lateinit var bind : SettingParentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = SettingParentBinding.inflate(inflater, container, false)
        ft = childFragmentManager.beginTransaction()
        (activity as MainActivity?)?.getSupportActionBar()?.setTitle("Setting")


        return bind.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
    }


    inner class Imageadater : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }


    }

}