package com.raykellyfitness.ui.health.bloodsugar.add_bloodsugar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.raykellyfitness.base.AsyncViewController
import com.raykellyfitness.base.BaseFragment
import com.raykellyfitness.base.MyViewModelProvider
import com.raykellyfitness.base.push
import com.raykellyfitness.databinding.FragmentAddBloodSugarBinding
import com.raykellyfitness.model.request.RequestAddBloodSugar
import com.raykellyfitness.model.request.ResponseAddBloodSugar
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.health.HealthFragment
import com.raykellyfitness.util.Constant
import java.util.*

class AddBloodSugarFragment : BaseFragment(), OnChartValueSelectedListener {

    var map = HashMap<Float,String>()
    private var chart: LineChart? = null
    lateinit var dialog : AlertDialog
    lateinit var mViewModel: AddBloodSugarViewModel
    lateinit var mBinding: FragmentAddBloodSugarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentAddBloodSugarBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
        manageClicks()
        (this.parentFragment as HealthFragment).handleTouch(mBinding.chart1, mBinding.scrollview)
        setupChart()

        (this.parentFragment as HealthFragment).listBloodSugar.observe(viewLifecycleOwner, Observer {
            setChartData(it)
        })
    }

    private fun manageClicks() {
        mBinding.tvAdd.push()?.setOnClickListener {
            if(mViewModel.validateInput()){
                mViewModel.callAddBloodSugarApi().observe(viewLifecycleOwner, Observer {
                    if (it != null && it.responseCode == 200){
                        mViewModel.requestAddBloodSugar.set(RequestAddBloodSugar())
                        (this.parentFragment as HealthFragment).listBloodSugar.value = it.data
                        showSuccessBar(it.message)
                    }
                    else
                        showErrorBar(it.message)
                })
            }
        }
    }

    fun setupChart() {
        (this.parentFragment as HealthFragment).setupChart(mBinding.chart1, map,
                                                           Pair(Constant.FASTING_SUGAR_RANGE_MIN.toFloat(), Constant.FASTING_SUGAR_RANGE_MAX+5.toFloat())
        )
        chart = mBinding.chart1
        chart!!.setOnChartValueSelectedListener(this)
    }

    private fun setChartData(listWeight: ResponseAddBloodSugar?) {
        (this.parentFragment as HealthFragment).setChartData(null, listWeight,null, mBinding.chart1, map, (this.parentFragment as HealthFragment).BLOOD_SUGAR)
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController)).get(AddBloodSugarViewModel::class.java)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        mBinding.chart1.centerViewToAnimated(e!!.x, e.y, mBinding.chart1.getData().getDataSetByIndex(h!!.dataSetIndex).getAxisDependency(), 500)
    }

    override fun onNothingSelected() {

    }

}