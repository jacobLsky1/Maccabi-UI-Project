package com.jacoblip.andriod.maccabiuiproject.UI.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.jacoblip.andriod.maccabiuiproject.MainActivity
import com.jacoblip.andriod.maccabiuiproject.R
import com.jacoblip.andriod.maccabiuiproject.dataObjects.MacabbiActionButton
import com.jacoblip.andriod.maccabiuiproject.dataObjects.MaccabiDataObject
import com.jacoblip.andriod.maccabiuiproject.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_maccabi_actions.view.*
import kotlinx.android.synthetic.main.macabbi_action_data_item.view.*
import kotlinx.android.synthetic.main.macabbi_action_rv_button.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat


class MacabbiActionsFragment(name:String,context: Context,buttonClicked:Int):Fragment() {

    val buttonClicked = buttonClicked
    val userName = name
    var first = true
    lateinit var viewModel: MainViewModel
    lateinit var buttonsRV:RecyclerView
    lateinit var dataRV:RecyclerView
    lateinit var macabbiData:ArrayList<MaccabiDataObject>
    lateinit var macabbiCheckUpResults: ArrayList<MaccabiDataObject>
    lateinit var macabbiMedicanData : ArrayList<MaccabiDataObject>
    lateinit var macabbiDoctorRequests: ArrayList<MaccabiDataObject>
    lateinit var macabbiVisitHistory: ArrayList<MaccabiDataObject>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


       val view = inflater.inflate(R.layout.fragment_maccabi_actions,container,false)
        view.apply {
            maccabi_action_textView.text = userName
            viewModel = (activity as MainActivity).viewModel
            macabbiData = viewModel.macabbiData
            macabbiCheckUpResults = viewModel.checkUpResults
            macabbiDoctorRequests = viewModel.macabbiRequests
            macabbiMedicanData = viewModel.macabbiMedacianData
            macabbiVisitHistory = viewModel.macabbiVisitHistory

            setRecyclerViews(view)

            //code for putting a child fragment in another fragment
            /*
            val childFragMan: FragmentManager = childFragmentManager
            val childFragTrans: FragmentTransaction =
                childFragMan.beginTransaction()
            val fragB = MacabbiDataFragment()
            childFragTrans.add(R.id.macabbi_actions_fragment_container, fragB)
            childFragTrans.addToBackStack("B")
            childFragTrans.commit()
             */

        }

        return view
    }

    fun setRecyclerViews(view: View){
        var buttons: ArrayList<MacabbiActionButton> = viewModel.maccabiActionButtons
        buttonsRV = view.maccabi_action_RV
        buttonsRV.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,true)
        val itemSnapHelper: SnapHelper = LinearSnapHelper()
        itemSnapHelper.attachToRecyclerView(buttonsRV)
        buttonsRV.adapter = ButtonAdapter(buttons,buttonClicked)


        dataRV = view.macabbi_data_rv
        dataRV.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        when(buttonClicked){
            0->{
                dataRV.adapter = DataAdapter(macabbiData)
                buttonsRV.scrollToPosition(1)
            }
            1->{
                dataRV.adapter = DataAdapter(macabbiVisitHistory)
                buttonsRV.scrollToPosition(5)
            }
            2->{
                dataRV.adapter = DataAdapter(macabbiDoctorRequests)
                buttonsRV.scrollToPosition(2)
            }
        }


    }


//_____________________________________________button adapter
    private inner class ButtonViewHolder(view: View):RecyclerView.ViewHolder(view)

    private inner class ButtonAdapter(buttons:ArrayList<MacabbiActionButton>,num:Int):RecyclerView.Adapter<ButtonViewHolder>() {
        val buttons = buttons
        val buttonPushed = num
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
            val view = layoutInflater.inflate(R.layout.macabbi_action_rv_button, parent, false)
            return ButtonViewHolder(view)
        }

        override fun getItemCount() = buttons.size


        override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
            val button = buttons[position]
            if(first) {
                holder.itemView.apply {
                    if(buttonPushed==0&&position==2) {
                        maccabi_action_rv_button.setBackgroundColor(resources.getColor(R.color.lightBlue))
                        first=false
                    }
                    if(buttonPushed==1&&position==1) {
                        maccabi_action_rv_button.setBackgroundColor(resources.getColor(R.color.lightBlue))
                        first=false
                    }
                    if(buttonPushed==2&&position==3) {
                        maccabi_action_rv_button.setBackgroundColor(resources.getColor(R.color.lightBlue))
                        first=false
                    }
                }
            }
            holder.itemView.apply {
                maccabi_action_rv_button.text = button.string
                maccabi_action_rv_button.setOnClickListener {
                    resetButtons()
                    maccabi_action_rv_button.setBackgroundColor(resources.getColor(R.color.lightBlue))
                    upDateUI(position)
                }
            }

        }

        fun resetButtons() {
            for (i in 0 until buttonsRV.getChildCount()) {
                val holder: View? = buttonsRV.getChildAt(i)
                if (holder != null) {
                    holder.maccabi_action_rv_button.setBackgroundColor(resources.getColor(R.color.white))
                }
            }

        }
        fun upDateUI(num:Int){
            when(num){
                4->{ dataRV.adapter = DataAdapter(macabbiCheckUpResults)}
                3->{ dataRV.adapter = DataAdapter(macabbiDoctorRequests)}
                2->{ dataRV.adapter = DataAdapter(macabbiData)}
                1->{ dataRV.adapter = DataAdapter(macabbiVisitHistory)}
                0->{ dataRV.adapter = DataAdapter(macabbiMedicanData)
                }
            }
            scroll(num)
        }

        fun scroll(num:Int){
            buttonsRV.scrollToPosition(num)
        }
    }

//_____________________________________________data adapter
private inner class DataViewHolder(view: View):RecyclerView.ViewHolder(view){
    fun bind(holder: DataViewHolder,item:MaccabiDataObject){
        var obj:Any? = null
        if(item.checkResults!=null){
            holder.itemView.apply {
                data_item_title_by_category.text = item.checkResults!!.checkingDocter
                data_item_subtitle_by_category.text = item.checkResults!!.type
                val df: DateFormat = SimpleDateFormat("dd / MM / yyyy")
                val str = df.format(item.date)
                data_item_date.text = str
              checkItem(holder,item)
            }
        }
        if(item.medicanData!=null){
            holder.itemView.apply {
                data_item_title_by_category.text = item.medicanData!!.name
                data_item_subtitle_by_category.text = item.medicanData!!.staus
                val df: DateFormat = SimpleDateFormat("dd / MM / yyyy")
                val str = df.format(item.date)
                data_item_date.text = str
                checkItem(holder,item)
            }
        }
        if(item.visitHistory!=null){
            holder.itemView.apply {
                data_item_title_by_category.text = item.visitHistory!!.docterName
                data_item_subtitle_by_category.text =item.visitHistory!!.docterType
                val df: DateFormat = SimpleDateFormat("dd / MM / yyyy")
                val str = df.format(item.date)
                data_item_date.text = str
                checkItem(holder,item)
            }
        }
        if(item.docterRequests!=null){
            holder.itemView.apply {
                data_item_title_by_category.text = item.docterRequests!!.title
                data_item_subtitle_by_category.text = item.docterRequests!!.docterName
                val df: DateFormat = SimpleDateFormat("dd / MM / yyyy")
                val str = df.format(item.date)
                data_item_date.text = str
                checkItem(holder,item)
            }
        }
    }

    fun checkItem(holder: DataViewHolder,item:MaccabiDataObject){
        holder.itemView.apply {
            if (item.notification!=null){
                data_item_notification_to_user.visibility = View.VISIBLE
                data_item_notification_to_user.text = item.notification!!.text
                if(item.notification!!.fileAttached){
                    data_item_file_attached.visibility = View.VISIBLE
                }
            }
        }
    }
}

    private inner class DataAdapter(data:ArrayList<MaccabiDataObject>):RecyclerView.Adapter<DataViewHolder>(){
        val data  = data
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            val view = layoutInflater.inflate(R.layout.macabbi_action_data_item,parent,false)
            return DataViewHolder(view)
        }

        override fun getItemCount() = data.size


        override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
            val item = data[position]
            holder.bind(holder,item)
        }

    }


    companion object{
        fun newInstance(name: String,context: Context,num: Int) =
            MacabbiActionsFragment(
                name,
                context,
                num
            )
    }
}