package com.example.test_food_app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.test_food_app.R
import com.example.test_food_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = binding.root
        if (root!= null)
            setContentView(binding.root)
        else
            setContentView(R.layout.activity_main)
        val bottomNavigation = binding.bottomNavigation
        val findNavigation = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,findNavigation)
    }
}
//
//interface A{
//    fun a()
//}
//class AA :A{
//    override fun a() {
//        Log.d("TAGa", "a: aaaa")
//    }
//}
//class B @Inject constructor(val aa: A) {
//   fun  a() {
//       aa.a()
//    }
//}
//@Module
//@InstallIn(SingletonComponent::class)
//object m {
//    @Provides
//    @Singleton
//    fun  povidesB(a: A) :B = B(a)
//
//    @Provides
//    @Singleton
//    fun  povidesA() :A = AA()
//
//}