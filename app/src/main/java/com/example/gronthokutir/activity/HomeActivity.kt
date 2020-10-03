package com.example.gronthokutir.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.gronthokutir.R
import com.example.gronthokutir.fragments.LibraryFragment
import com.example.gronthokutir.fragments.NewBooksFragment
import com.example.gronthokutir.fragments.OldBooksFragment
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar_app_bar)
        toolbar.title = "গ্রন্থকুটির"
        setSupportActionBar(toolbar)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager = findViewById(R.id.viewPager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(NewBooksFragment(),"নতুন বই")
        viewPagerAdapter.addFragment(OldBooksFragment(),"পুরাতন বই")
        viewPagerAdapter.addFragment(LibraryFragment(),"লাইব্রেরি")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(
            fragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        private val fragments : ArrayList<Fragment> = ArrayList()
        private val titles : ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {

            return fragments[position]
        }

        override fun getCount(): Int {

            return fragments.size
        }

        fun addFragment(fragment: Fragment,title : String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.home_page_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         super.onOptionsItemSelected(item)

        if(item.itemId == R.id.log_out){
            val intent = Intent(this@HomeActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        return true
    }


}
