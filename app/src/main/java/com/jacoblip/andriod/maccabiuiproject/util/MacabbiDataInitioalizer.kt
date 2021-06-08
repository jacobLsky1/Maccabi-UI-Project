package com.jacoblip.andriod.maccabiuiproject.util

import com.jacoblip.andriod.maccabiuiproject.dataObjects.*
import java.util.*

class MacabbiDataInitioalizer() {
    companion object {
        var macabbiDataObjcts = arrayListOf<MaccabiDataObject>(
            MaccabiDataObject(
                Date(120, 12, 23),
                DocterNotification("טופל בידי רופא", true),
                null,
                null,
                null,
                DocterRequests( Date(2020, 12, 23),"בקשה מרופא", "Doctor hiam udulman")
            ),
            MaccabiDataObject(
                Date(120, 4, 11),
                DocterNotification("טופל בידי רופא", false),
                null,
                null,
                null,
                DocterRequests(Date(2020, 4, 11),"בקשה מרופא", "Doctor hiam udulman")
            ),
            MaccabiDataObject(
                Date(120, 6, 2),
                DocterNotification("יוזם על ידי רופא", true),
                null,
                null,
                null,
                DocterRequests(Date(2020, 6, 2),"הודעה מרופא", "Doctor hiam udulman")
            ),
            MaccabiDataObject(
                Date(119, 1, 3),
                null,
                CheckUpResults(Date(2019, 1, 3),"חזה", "מרכז רפואי שיבא"),
                null,
                null,
                null
            ),
            MaccabiDataObject(
                Date(121, 2, 4),
                null,
                CheckUpResults(Date(2021, 2, 4),"חזה", "מרכז רפואי שערי צדק"),
                null,
                null,
                null
            ),
            MaccabiDataObject(
                Date(120, 3, 5),
                DocterNotification("טופל בידי רופא", true),
                CheckUpResults( Date(2020, 3, 5),"עניים", "Doctor Yuval Wien"),
                null,
                null,
                null
            ),
            MaccabiDataObject(
                Date(119, 4, 6),
                null,
                CheckUpResults(Date(2019, 4, 6),"א.ק.ג", "מרכז רפואי שערי צדק"),
                null,
                null,
                null
            ),
            MaccabiDataObject(
                Date(119, 5, 7),
                null,
                null,
                MedicanData( Date(2019, 5, 7),"WEADERMANA 20GR", "בוצעה רכישה"),
                null,
                null
            ),
            MaccabiDataObject(
                Date(121, 1, 8),
                null,
                null,
                MedicanData( Date(2021, 1, 8),"DENIAVDIJA 300GR", "בוצעה רכישה"),
                null,
                null
            ),
            MaccabiDataObject(
                Date(120, 7, 9),
                null,
                null,
                MedicanData(  Date(2020, 7, 9),"MOGAMASAR", "מרשם נגמר"),
                null,
                null
            ),
            MaccabiDataObject(
                Date(120, 10, 4),
                null,
                null,
                MedicanData(Date(2020, 10, 4),"TOVATAS", "מרשם נגמר"),
                null,
                null
            ),
            MaccabiDataObject(
                Date(119, 2, 7),
                null,
                null,
                MedicanData(Date(2019, 2, 7),"WEADERMANA 20GR", "בוצעה רכישה"),
                null,
                null
            ),
            MaccabiDataObject(
                Date(120, 8, 18),
                null,
                null,
                null,
                VisitHistory(Date(2020, 8, 18),"Doctor Haim Yudulman", "משפחה,כללית"),
                null
            ),
            MaccabiDataObject(
                Date(119, 9, 25),
                DocterNotification("טופל בידי רופא", true),
                null,
                null,
                VisitHistory( Date(2019, 9, 25),"Doctor Haim Yudulman", "משפחה,כללית"),
                null
            ),
            MaccabiDataObject(
                Date(120, 11, 21),
                null,
                null,
                null,
                VisitHistory(Date(2020, 11, 21),"Doctor Haim Yudulman", "משפחה,כללית"),
                null
            ),
            MaccabiDataObject(
                Date(120, 1, 29),
                null,
                null,
                null,
                VisitHistory( Date(2020, 1, 29),"Doctor Rui Hachimora", "משפחה,כללית"),
                null
            ),
            MaccabiDataObject(
                Date(120, 12, 26),
                null,
                null,
                null,
                VisitHistory(Date(2020, 12, 26),"Doctor Haim Yudulman", "משפחה,כללית"),
                null
            ),
            MaccabiDataObject(
                Date(121, 3, 30),
                DocterNotification("טופל בידי רופא", true),
                null,
                null,
                VisitHistory(Date(2021, 3, 30),"Doctor Moa Vagner", "משפחה,כללית"),
                null
            )

        )
    }
}