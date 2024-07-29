package com.target.targetcasestudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.target.targetcasestudy.ui.DealListFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    supportFragmentManager.beginTransaction()
      .replace(R.id.container, DealListFragment())
      .commit()
  }
}
