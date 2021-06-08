package com.jacoblip.andriod.maccabiuiproject.UI.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.jacoblip.andriod.maccabiuiproject.MainActivity
import com.jacoblip.andriod.maccabiuiproject.R
import com.jacoblip.andriod.maccabiuiproject.dataObjects.MaccabiButton
import com.jacoblip.andriod.maccabiuiproject.util.IonBackPressed
import com.jacoblip.andriod.maccabiuiproject.util.LinearLayoutHelper
import com.jacoblip.andriod.maccabiuiproject.util.Util
import com.jacoblip.andriod.maccabiuiproject.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_opening.*
import kotlinx.android.synthetic.main.fragment_opening.view.*
import kotlinx.android.synthetic.main.maccabi_button.view.*
import kotlinx.android.synthetic.main.maccabi_corona_button.view.*
import kotlinx.android.synthetic.main.maccabi_plus_button.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


private const val TYPE_EMPTY = 0
const val TYPE_PLUS = 1
const val TYPE_CORONA = 2
const val TYPE_RAGULAR = 3

class OpeningFragment(context: Context):Fragment(), IonBackPressed {

    val myContext = context
    var deleteButton:Button? = null

    lateinit var viewModel: MainViewModel
    lateinit var buttonRV:RecyclerView
    lateinit var visableButtons:ArrayList<MaccabiButton>

    interface Callbacks {
        fun onButtonSelected(text:String)

    }
    private var callbacks: Callbacks? = null

    //the callback functions
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_opening,container,false)
        buttonRV = view.maccabiButtonRV
        viewModel = (activity as MainActivity).viewModel
        visableButtons = viewModel.visableMacabbiButtons

            buttonRV.adapter = MaccabiButtonAdapter(visableButtons)
            buttonRV.layoutManager = LinearLayoutHelper(myContext)
            val itemSnapHelper: SnapHelper = LinearSnapHelper()
            itemSnapHelper.attachToRecyclerView(maccabiButtonRV)
        view.greetingTV.text = getGreeting()
        view.userNameTV.text = getName()
        view.greetingTV.setOnClickListener { clearDeleteButton() }
        view.userNameTV.setOnClickListener { clearDeleteButton() }
        Util.userName = getName()
        Util.greeting = getGreeting()
        Util.userName = view.userNameTV.text.toString()
        view.quickViewButton.setOnClickListener {
            clearDeleteButton()
           callbacks!!.onButtonSelected("מבט מהיר")
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getGreeting():String{
        var str = ""
        var currentDateTime= LocalDateTime.now()
        var time= currentDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        var num = (""+time[0]).toInt()
        if(time[1]!=':')
            num = (""+time[0]+time[1]).toInt()
        if(time[time.length-2]=='P'&&num!=12)
            num+=12
        if(num>4&&num<12){
            str = "בוקר טוב"
        }
        if(num>11&&num<18){
            str = "צהריים טובים"
        }
        if(num>17&&num<22){
            str = "ערב טוב"
        }
        if(num>21||num<5){
            str = "לילה טוב"
        }
        Util.greeting = str
        return str
    }


    fun clearDeleteButton(){
       if(deleteButton!=null){
           deleteButton!!.visibility = View.GONE
       }
    }

    fun removeButton(position: Int){
        viewModel.visableMacabbiButtons.removeAt(position)
        visableButtons = viewModel.visableMacabbiButtons
        updateUI(visableButtons)
    }

    fun updateUI(buttons: ArrayList<MaccabiButton>){
        buttonRV.adapter = MaccabiButtonAdapter(buttons)
        buttonRV.layoutManager = LinearLayoutHelper(myContext)
        //
        (buttonRV.layoutManager as LinearLayoutHelper).setReverseLayout(true)
        //
    }


    fun getName() = "פלוני אלמוני"


    companion object{
        fun newInstance(context: Context) =
            OpeningFragment(
                context
            )
    }

    override fun onBackPressed(): Boolean {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        startActivity(intent)
        System.exit(0)

        return false
    }

    inner class MaccabiButtonAdapter(buttons:ArrayList<MaccabiButton>) :
        RecyclerView.Adapter<MaccabiButtonAdapter.ActivityViewHolder>() {


        val maccabiButtons = buttons


        inner class ActivityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
            when(viewType){
                1->{return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.maccabi_plus_button, parent, false))}
                0->{return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.empty_space_button, parent, false))}
                2->{return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.maccabi_corona_button, parent, false))}
            }
            return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.maccabi_button, parent, false))
        }

        override fun getItemCount() = maccabiButtons.size


        override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
            if(position==0||position==2){
                val button = maccabiButtons[position]
                holder.itemView.apply {
                    when(position){
                        0->{maccabiPlusButton.setOnClickListener { callbacks!!.onButtonSelected(button.string) }}
                        2->{maccabiCoronaButton.setOnClickListener { gotoWeb() }
                        }
                    }
                }
            }
            if(position>2) {
                val button = maccabiButtons[position]
                holder.itemView.apply {
                    val textColor = resources.getColor(R.color.white)
                    val buttonIcon = button.icon
                    val blue = resources.getDrawable(R.drawable.round_button_blue)
                    val purple = resources.getDrawable(R.drawable.round_button_purple)

                    theMaccabiButton.text = button.string
                    theMaccabiButton.setCompoundDrawablesWithIntrinsicBounds(0,buttonIcon,0,0)
                    theMaccabiButton.setTextColor(textColor)
                    theMaccabiButton.background = (if (position < 6) blue else purple)
                    theMaccabiButton.setOnClickListener{
                        callbacks!!.onButtonSelected(button.string)
                        clearDeleteButton()
                        deleteButton = null

                    }
                    theMaccabiButton.setOnLongClickListener {
                            view ->  if (theMaccabiButton.background==purple){
                        clearDeleteButton()
                        deleteButton_button.visibility = View.VISIBLE
                        deleteButton = deleteButton_button

                    }
                        true
                    }
                    deleteButton_button.setOnClickListener {
                        removeButton(position)
                    }
                }
            }
        }

        override fun getItemViewType(position: Int): Int {
            when(position){
                0->return TYPE_PLUS

                1->return TYPE_EMPTY

                2-> return TYPE_CORONA
            }
            return TYPE_RAGULAR
        }

        fun gotoWeb(){
            val intent:Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://datadashboard.health.gov.il/COVID-19/general"))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.let { ContextCompat.startActivity(it, intent, null) }
        }




    }

}