package com.jacoblip.andriod.maccabiuiproject.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jacoblip.andriod.maccabiuiproject.R
import com.jacoblip.andriod.maccabiuiproject.dataObjects.*
import com.jacoblip.andriod.maccabiuiproject.repository.MaccabiRepository
import com.jacoblip.andriod.maccabiuiproject.util.MacabbiDataInitioalizer
import com.jacoblip.andriod.maccabiuiproject.util.Util
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(repository: MaccabiRepository,context: Context):ViewModel() {

    var allMaccabiButtons = arrayListOf<MaccabiButton>()
    var visableMacabbiButtons = arrayListOf<MaccabiButton>()
    var maccabiActionButtons = arrayListOf<MacabbiActionButton>()
    var macabbiData = arrayListOf<MaccabiDataObject>()

    var macabbiMedacianData = arrayListOf<MaccabiDataObject>()
    var macabbiVisitHistory = arrayListOf<MaccabiDataObject>()
    var macabbiRequests = arrayListOf<MaccabiDataObject>()
    var checkUpResults = arrayListOf<MaccabiDataObject>()


    init {
        getButtons()
        maccabiActionButtons = setActionButtons()
        macabbiData = MacabbiDataInitioalizer.macabbiDataObjcts
        macabbiData.sort()

        splitData(macabbiData)
    }


    fun splitData(macabbiData:ArrayList<MaccabiDataObject>){
        for(item in macabbiData){
            if(item.checkResults!=null){
                checkUpResults.add(item)
            }
            if(item.medicanData!=null){
                macabbiMedacianData.add(item)
            }
            if(item.visitHistory!=null){
                macabbiVisitHistory.add(item)
            }
            if(item.docterRequests!=null){
                macabbiRequests.add(item)
            }
        }
    }

   fun setActionButtons():ArrayList<MacabbiActionButton> {
       var buttons = arrayListOf<MacabbiActionButton>()
       for (i in 0 until Util.macabbiActionButtons.size) {
           var button = MacabbiActionButton(Util.macabbiActionButtons[i])
           buttons.add(button)
       }
       return buttons
   }

    fun getButtons(){
        for(i in 0..18) {
            var color = if (i < 7) R.color.maccabiBlue else R.color.maccabiPurple
            if (i == 0) {
                val button = MaccabiButton(i, MaccabiStrings.buttonStrings[i],color)
                visableMacabbiButtons.add(button)
                allMaccabiButtons.add(button)
            }
            if (i != 0 && i < 3) {
                val button = MaccabiButton(i, MaccabiStrings.buttonStrings[i],color)
                visableMacabbiButtons.add(button)
                allMaccabiButtons.add(button)
            }
            if (i > 2&&i<16) {
                val button =
                    MaccabiButton(
                        i + 4,
                        MaccabiStrings.buttonStrings[i],
                        MaccabiStrings.buttonIcons[i],
                        color
                    )
                visableMacabbiButtons.add(button)
                allMaccabiButtons.add(button)
            }
            if(i>15){
                val button =
                    MaccabiButton(
                        i + 4,
                        MaccabiStrings.buttonStrings[i],
                        MaccabiStrings.buttonIcons[i],
                        color
                    )
                allMaccabiButtons.add(button)
            }
        }
    }
}