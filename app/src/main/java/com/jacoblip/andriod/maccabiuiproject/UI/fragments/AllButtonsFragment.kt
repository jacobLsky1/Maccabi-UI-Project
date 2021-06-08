package com.jacoblip.andriod.maccabiuiproject.UI.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jacoblip.andriod.maccabiuiproject.MainActivity
import com.jacoblip.andriod.maccabiuiproject.R
import com.jacoblip.andriod.maccabiuiproject.dataObjects.MaccabiButton
import com.jacoblip.andriod.maccabiuiproject.viewModel.MainViewModel
import kotlinx.android.synthetic.main.all_macabbi_buttons_item.view.*
import kotlinx.android.synthetic.main.fragment_all_macabbi_buttons.view.*


class AllButtonsFragment(context: Context):Fragment() {
    val myContext = context

    lateinit var viewModel: MainViewModel
    lateinit var buttonsRV:RecyclerView
    lateinit var allMacabbiButtons:ArrayList<MaccabiButton>
    lateinit var visableMacabbiButtons:ArrayList<MaccabiButton>
    lateinit var openingFragment: OpeningFragment

    interface AllButtonCallbacks {
        fun itemAdd()

    }
    private var callbacks: AllButtonCallbacks? = null

    //the callback functions
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as AllButtonCallbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        openingFragment = OpeningFragment(myContext)
       val view = inflater.inflate(R.layout.fragment_all_macabbi_buttons,container,false)
        view.apply {
            viewModel = (activity as MainActivity).viewModel
            allMacabbiButtons = viewModel.allMaccabiButtons
            visableMacabbiButtons = viewModel.visableMacabbiButtons

            setUpRecyclerView(view,allMacabbiButtons,visableMacabbiButtons)
        }
        return view
    }


    fun setUpRecyclerView(view: View,allMacabbiButtons:ArrayList<MaccabiButton>,visableMacabbiButtons:ArrayList<MaccabiButton>){
        buttonsRV = view.allMacabbiButtonsRV
        buttonsRV.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        buttonsRV.adapter = AllMacabbiButtonsAdapter(allMacabbiButtons,visableMacabbiButtons)
    }
        private inner class AllMacabbiButtonItemViewHolder(view: View):RecyclerView.ViewHolder(view)

        private inner class AllMacabbiButtonsAdapter(allButtons:ArrayList<MaccabiButton>,visableButtons:ArrayList<MaccabiButton>) :
            RecyclerView.Adapter<AllMacabbiButtonItemViewHolder>() {
            var thePosition = 0

            val allButtons = allButtons
            val visableButtons = visableButtons
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): AllMacabbiButtonItemViewHolder {
                val view = layoutInflater.inflate(R.layout.all_macabbi_buttons_item, parent, false)
                return AllMacabbiButtonItemViewHolder(view)
            }

            override fun getItemCount() = allButtons.size-6

            override fun onBindViewHolder(holder: AllMacabbiButtonItemViewHolder, position: Int) {
                thePosition = position+6
                val button = allButtons[thePosition]
                holder.itemView.apply {
                    if(visableButtons.contains(button)) {
                        val buttonIcon = button.icon
                        allMacabbiButtonIcon.setImageResource(buttonIcon)
                        allButtonsItemName.text = button.string
                        allButtonsItemCircle.setImageResource(R.drawable.round_button_gray)
                        allButtonsItemName.setTextColor(resources.getColor(R.color.maccabiGray))
                    }else{
                        val buttonIcon = button.icon
                        allMacabbiButtonIcon.setImageResource(buttonIcon)
                        allButtonsItemName.text = button.string
                        allButtonsItemCircle.setImageResource(R.drawable.round_button_purple)
                        allButtonsItemName.setTextColor(resources.getColor(R.color.black))
                        allButtonsItemName.setOnClickListener {pop(button)}
                        allButtonsItemCircle.setOnClickListener { pop(button) }
                        allMacabbiButtonIcon.setOnClickListener { pop(button) }
                    }
               }
            }
            fun pop(button: MaccabiButton){
                viewModel.visableMacabbiButtons.add(button)
                callbacks!!.itemAdd()
            }
        }
    companion object{
        fun newInstance(context: Context) =
           AllButtonsFragment(context)
    }
}