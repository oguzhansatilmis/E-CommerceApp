package com.android.ecommerceapp

import android.animation.LayoutTransition
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.ecommerceapp.databinding.ActivityMainBinding
import com.android.ecommerceapp.model.enums.MessageType
import com.android.ecommerceapp.util.heightRatio
import com.android.ecommerceapp.util.toFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isShowingDialog = false
    private var isShowingInfo = false
    private val constraintSet = ConstraintSet()
    private var currentPrice:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.exploreFragment,R.id.homeFragment,R.id.basketFragment->{
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
                R.id.detailFragment-> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
            }
        }
    }

    fun setBasketPrice(price:Double){

        currentPrice+=price

        binding.basketPriceText.text = currentPrice.toFormat()
    }

    fun showProgress() {
        isShowingDialog = true
        binding.activityProgressbarLayout.visibility = View.VISIBLE
    }

    fun hideProgress() {
        isShowingDialog = false
        binding.activityProgressbarLayout.visibility = View.GONE
    }

    fun showMessage(message: String?, type: MessageType) {
        isShowingInfo = true
        hideProgress()
        binding.wrapperLayout.visibility = View.VISIBLE

        constraintSet.apply {

            clone(binding.mainLayout)
            clear(binding.textMessage.id, ConstraintSet.BOTTOM)
            connect(
                binding.textMessage.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0.15f.heightRatio
            )
            applyTo(binding.mainLayout)
        }
        binding.mainLayout.layoutTransition = LayoutTransition().apply {
            enableTransitionType(LayoutTransition.CHANGING)
            setDuration(300)
        }

        when (type) {
            MessageType.ERROR -> binding.textMessage.background =
                ResourcesCompat.getDrawable(resources, R.drawable.message_error, null)


            MessageType.SUCCESS -> binding.textMessage.background =
                ResourcesCompat.getDrawable(resources, R.drawable.message_error, null)

            MessageType.WARNING -> binding.textMessage.background =
                ResourcesCompat.getDrawable(resources, R.drawable.message_error, null)

        }

        binding.textMessage.apply {
            text = message
            alpha = 1f
            postDelayed(
                { hideMessage() }, 1500
            )
        }

    }

    private fun hideMessage() {
        isShowingInfo = false
        binding.textMessage.animate().alpha(0f).setDuration(500).withEndAction {
            constraintSet.apply {
                clone(binding.mainLayout)

                clear(binding.textMessage.id, ConstraintSet.TOP)
                connect(
                    binding.textMessage.id,
                    ConstraintSet.BOTTOM,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP
                )
                applyTo(binding.mainLayout)

                binding.wrapperLayout.visibility = View.INVISIBLE
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isShowingDialog && isShowingInfo) {
            return true        // Dialog (progressView veya infoView) gösteriliyorken geri tuşu tıklanmasın
        }

        return super.onKeyDown(keyCode, event)
    }
}