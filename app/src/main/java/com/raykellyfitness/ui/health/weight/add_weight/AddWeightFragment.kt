package com.raykellyfitness.ui.health.weight.add_weight

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
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentAddWeightBinding
import com.raykellyfitness.model.request.RequestAddWeight
import com.raykellyfitness.model.request.ResponseAddWeight
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.health.HealthFragment
import com.raykellyfitness.util.Constant
import java.util.*

class AddWeightFragment : BaseFragment(), OnChartValueSelectedListener {

    var map = HashMap<Float, String>()
    private var chart: LineChart? = null
    lateinit var dialog: AlertDialog
    lateinit var mViewModel: AddWeightViewModel
    lateinit var mBinding: FragmentAddWeightBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddWeightBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel

        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWork()
        otherWork()
    }

    private fun otherWork() {
        manageClicks()
        setupChart()
        (this.parentFragment as HealthFragment).handleTouch(mBinding.chart1, mBinding.scrollview)
        (this.parentFragment as HealthFragment).listWeight.observe(viewLifecycleOwner, Observer {
            setChartData(it)
            setBMI(it)
        })
    }

    private fun setBMI(list: ResponseAddWeight?) {
        if(!list.isEmptyyThenHide(mBinding.tvBmi)){
            val last = list?.last()

            if(last?.height.isEmptyy() || last?.weight.isEmptyy()){
                mBinding.tvBmi.gone()
                return
            }

            val bmi = calculateBMI(last?.weight?.toDouble()!!, last?.height?.toDouble()!!)
            val interpretBMI = bmi?.let { interpretBMI(it) }

            mBinding.tvBmi.text = "BMI = $bmi ($interpretBMI)"
        }
    }


    fun setupChart() {
        (this.parentFragment as HealthFragment).setupChart(mBinding.chart1, map,
                                                           Pair(Constant.WEIGHT_RANGE_MIN.toFloat(), Constant.WEIGHT_RANGE_MAX+50.toFloat())
        )
        chart = mBinding.chart1
        chart!!.setOnChartValueSelectedListener(this)
    }

    private fun setChartData(listWeight: ResponseAddWeight?) {
        (this.parentFragment as HealthFragment).setChartData(
            listWeight,
            null,
            null,
            mBinding.chart1,
            map,
            (this.parentFragment as HealthFragment).WEIGHT
        )
    }

    private fun initWork() {
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).setTitle("")
    }

    private fun manageClicks() {

        mBinding.tvAdd.push()?.setOnClickListener {
            if (mViewModel.validateInput()) {
                mViewModel.callAddWeightApi().observe(viewLifecycleOwner, Observer {
                    if (it != null && it.responseCode == 200) {
                        mViewModel.requestAddWeight.set(RequestAddWeight())
                        (this.parentFragment as HealthFragment).listWeight.value = it.data
                        showSuccessBar(it.message)
                    } else
                        showErrorBar(it.message)
                })
            }
        }
    }

    private fun setupViewModel() {
        mViewModel = ViewModelProviders.of(
            this,
            MyViewModelProvider(commonCallbacks as AsyncViewController)
        ).get(AddWeightViewModel::class.java)
    }


    override fun onValueSelected(e: Entry?, h: Highlight?) {
        mBinding.chart1.centerViewToAnimated(
            e!!.x, e.y, mBinding.chart1.getData().getDataSetByIndex(
                h!!.dataSetIndex
            ).getAxisDependency(), 500
        )
    }

    override fun onNothingSelected() {

    }

    private fun calculateBMI(weight: Double, height: Double): Double? {
        val heightMM = height?.div(100)
        val bmi = weight?.div((heightMM * heightMM))
        return String.format("%.2f", bmi).toDouble()
    }

    private fun interpretBMI(bmiValue: Double): String? {
        return when {
            bmiValue < 18.5 -> getString(R.string.underweight)

            bmiValue < 25 -> getString(R.string.normal)

            bmiValue < 30 -> getString(R.string.overweight)

            else -> getString(R.string.obese)
        }
    }

}
