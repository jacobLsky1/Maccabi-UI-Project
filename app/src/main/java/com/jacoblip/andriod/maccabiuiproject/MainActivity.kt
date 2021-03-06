package com.jacoblip.andriod.maccabiuiproject

import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jacoblip.andriod.maccabiuiproject.UI.*
import com.jacoblip.andriod.maccabiuiproject.UI.fragments.AllButtonsFragment
import com.jacoblip.andriod.maccabiuiproject.UI.fragments.MacabbiActionsFragment
import com.jacoblip.andriod.maccabiuiproject.UI.fragments.OpeningFragment
import com.jacoblip.andriod.maccabiuiproject.repository.MaccabiRepository
import com.jacoblip.andriod.maccabiuiproject.util.IonBackPressed
import com.jacoblip.andriod.maccabiuiproject.util.Util
import com.jacoblip.andriod.maccabiuiproject.util.Util.Companion.REQUEST_CODE_FOR_VERAFACTION
import com.jacoblip.andriod.maccabiuiproject.viewModel.MainViewModel
import com.jacoblip.andriod.maccabiuiproject.viewModel.viewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawerLayout

class MainActivity : AppCompatActivity(),
    OpeningFragment.Callbacks,AllButtonsFragment.AllButtonCallbacks {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewModel: MainViewModel
   //https://datadashboard.health.gov.il/COVID-19/general


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("git","1 change")
        Log.i("git","change 2")
        Log.i("git","this line will be merged")
        Log.i("git","this line is from github")
        val toolbar:Toolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar)


        val repository = MaccabiRepository()
        val viewModelProviderFactory = viewModelProviderFactory(repository,applicationContext)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        setUpActionDrawer()

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
           val fragment = OpeningFragment.newInstance(applicationContext)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    fun setUpActionDrawer(){
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            if(Util.isVerifiedUser) {
                when (it.itemId) {
                    R.id.quickView -> { handleMenuItemPressed("?????? ????????") }
                    R.id.medicalRecords -> { handleMenuItemPressed("?????? ??????????") }
                    R.id.apiontmants -> { handleMenuItemPressed("?????????? ???????????? ??????????") }
                    R.id.notifications -> { handleMenuItemPressed("?????????? ?????????????? ??????????????")}
                    R.id.findHelp -> { handleMenuItemPressed("?????????? ??????????") }
                    R.id.SOS -> { handleMenuItemPressed("?????????? ??????????") }
                    R.id.nature -> { handleMenuItemPressed("??????????\n????????????") }
                    R.id.new_apointmant -> { handleMenuItemPressed("?????????? ?????? ??????") }
                    R.id.forms -> { handleMenuItemPressed("???????????? ??????????????????????") }
                    R.id.childDocter -> { handleMenuItemPressed("???????? ???? ???????? ????????????") }
                    R.id.docterWithoutCard -> { handleMenuItemPressed(" ?????????? ?????? ?????????? ") }
                    R.id.trips -> { handleMenuItemPressed("?????????? ???????????? ????????") }
                    R.id.contact -> { handleMenuItemPressed("?????? ??????") }
                    R.id.help -> { handleMenuItemPressed("????????")
                    }
                }
            }else{
                verifyUser(applicationContext,Util.greeting,Util.userName)
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
    fun verifyUser(context: Context, greeting:String, name:String){
        val intent = Intent(context, UserVarificationActivity::class.java)
        intent.putExtra("greeting", Util.greeting)
        intent.putExtra("name", Util.userName)
        startActivityForResult(intent,REQUEST_CODE_FOR_VERAFACTION)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_CODE_FOR_VERAFACTION&&resultCode== AppCompatActivity.RESULT_OK) {
            val isVarified = data!!.getBooleanExtra("result",false)
            Util.isVerifiedUser = isVarified
            Toast.makeText(applicationContext, Util.isVerifiedUser.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    override fun onButtonSelected(text:String) {
        if(Util.isVerifiedUser){
            handleMenuItemPressed(text)
        }else{
            verifyUser(applicationContext,Util.greeting,Util.userName)
        }
    }
    private fun handleMenuItemPressed(text: String) {
        var fragment:Fragment? = null
        when(text){
            "?????? ????????"->{fragment =   MacabbiActionsFragment.newInstance(Util.userName,applicationContext,0)}
            "1"->{fragment = AllButtonsFragment.newInstance(applicationContext)}
            "3"->{Toast.makeText(applicationContext,"???????? ????????????", Toast.LENGTH_SHORT).show()}
            "?????? ??????????"->{fragment =   MacabbiActionsFragment.newInstance(Util.userName,applicationContext,1)}
            "?????????? ???????????? ??????????"->{Toast.makeText(applicationContext,"?????????? ???????????? ??????????", Toast.LENGTH_SHORT).show()}
            "?????????? ?????????????? ??????????????"->{fragment =   MacabbiActionsFragment.newInstance(Util.userName,applicationContext,2)}
            "?????????? ??????????"->{Toast.makeText(applicationContext,"?????????? ??????????", Toast.LENGTH_SHORT).show()}
            "?????????? ??????????"->{Toast.makeText(applicationContext,"?????????? ??????????", Toast.LENGTH_SHORT).show()}
            "??????????\n????????????"->{Toast.makeText(applicationContext,"?????????? ????????????", Toast.LENGTH_SHORT).show()}
            "?????????? ?????? ??????"->{Toast.makeText(applicationContext,"?????????? ?????? ??????", Toast.LENGTH_SHORT).show()}
            "???????????? ??????????????????????"->{Toast.makeText(applicationContext,"???????????? ??????????????????????", Toast.LENGTH_SHORT).show()}
            "???????? ???? ???????? ????????????"->{Toast.makeText(applicationContext,"???????? ???? ???????? ????????????", Toast.LENGTH_SHORT).show()}
            " ?????????? ?????? ?????????? "->{Toast.makeText(applicationContext,"?????????? ?????? ??????????", Toast.LENGTH_SHORT).show()}
            "?????????? ???????????? ????????"->{Toast.makeText(applicationContext,"?????????? ???????????? ????????", Toast.LENGTH_SHORT).show()}
            "?????? ??????"->{Toast.makeText(applicationContext,"?????? ??????", Toast.LENGTH_SHORT).show()}
            "????????"->{Toast.makeText(applicationContext,"????????", Toast.LENGTH_SHORT).show()}
            "??????????????"->{Toast.makeText(applicationContext,"??????????????", Toast.LENGTH_SHORT).show()}
            "???????? ??????????"->{Toast.makeText(applicationContext, "???????? ??????????", Toast.LENGTH_SHORT).show()}
            "?????????????? ??????????????"->{Toast.makeText(applicationContext, "?????????????? ??????????????", Toast.LENGTH_SHORT).show()}

        }

        if (fragment != null) {
            switchFragments(fragment)
        }

    }

    private fun switchFragments(fragmnet:Fragment){
        val myFragment = fragmnet
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, myFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun itemAdd() {
      supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
        val fragment = OpeningFragment.newInstance(applicationContext)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
