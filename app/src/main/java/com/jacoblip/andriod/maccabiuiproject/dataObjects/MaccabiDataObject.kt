package com.jacoblip.andriod.maccabiuiproject.dataObjects

import java.util.*

data class MaccabiDataObject(
    var date: Date? = null,
    var notification:DocterNotification?= null,
    var checkResults:CheckUpResults? = null,
    var medicanData:MedicanData? = null,
    var visitHistory:VisitHistory? = null,
    var docterRequests:DocterRequests? = null
) :Comparable<MaccabiDataObject>{
    override fun compareTo(other: MaccabiDataObject): Int {
        return (other.date!!.compareTo(this.date))
    }
}