package com.raykellyfitness.ui.health.bloodpressure.add_bloodpressure

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.raykellyfitness.R
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentAddBloodPressureBinding
import com.raykellyfitness.model.request.RequestAddBloodPressure
import com.raykellyfitness.model.request.ResponseAddBloodPressure
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.health.HealthFragment
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AddBloodPressureFragment : BaseFragment(), OnChartValueSelectedListener {

    var range = 10.0f
    var map = HashMap<Float,String>()
    private var chart: LineChart? = null
    lateinit var dialog: AlertDialog
    lateinit var mViewModel: AddBloodPressureViewModel
    lateinit var mBinding: FragmentAddBloodPressureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAddBloodPressureBinding.inflate(inflater, container, false).apply {
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
        (this.parentFragment as HealthFragment).listBloodPressure.observe(
            viewLifecycleOwner,
            Observer {
                setChartData(it)
            })
    }

    private fun manageClicks() {
        mBinding.tvAdd.push()?.setOnClickListener {
            if (mViewModel.validateInput()) {
                mViewModel.callAddBllodPressureApi().observe(viewLifecycleOwner, Observer {
                    if (it != null && it.responseCode == 200) {
                        mViewModel.requestAddBloodPressure.set(RequestAddBloodPressure())
                        (this.parentFragment as HealthFragment).listBloodPressure.value = it.data
                        showSuccessBar(it.message)
                    } else
                        showErrorBar(it.message)
                })
            }
        }
    }




    fun setupChart() {
        (this.parentFragment as HealthFragment).setupChart(mBinding.chart1, map)
        chart = mBinding.chart1
        chart!!.setOnChartValueSelectedListener(this)
    }

    private fun setChartData(list: ResponseAddBloodPressure?) {
        (this.parentFragment as HealthFragment).setChartData(null, null,list, mBinding.chart1, map, (this.parentFragment as HealthFragment).BLOOD_PRESSURE)
    }


    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(AddBloodPressureViewModel::class.java)
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

}
