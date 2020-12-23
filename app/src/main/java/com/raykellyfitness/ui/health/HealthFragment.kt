package com.raykellyfitness.ui.health

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.raykellyfitness.R
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentHealthBinding
import com.raykellyfitness.model.request.*
import com.raykellyfitness.networking.Api
import com.raykellyfitness.ui.health.bloodpressure.add_bloodpressure.AddBloodPressureFragment
import com.raykellyfitness.ui.health.bloodsugar.add_bloodsugar.AddBloodSugarFragment
import com.raykellyfitness.ui.health.weight.add_weight.AddWeightFragment
import com.raykellyfitness.util.Constant
import com.raykellyfitness.util.Constant.NO_RECORD_AVAILABLE
import com.raykellyfitness.util.Util
import com.raykellyfitness.util.UtilJava
import com.raykellyfitness.util.permission.DeviceRuntimePermission
import com.raykellyfitness.util.permission.IPermissionGranted
import com.raykellyfitness.views.CommonTextView
import com.vistrav.pop.Pop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

class HealthFragment : BaseFragment() {

    val WEIGHT = 1
    val BLOOD_SUGAR = 2
    val BLOOD_PRESSURE = 3

    val range = 10.0f
    var callByClick = false
    var listWeight = MutableLiveData<ResponseAddWeight>()
    var listBloodSugar = MutableLiveData<ResponseAddBloodSugar>()
    var listBloodPressure = MutableLiveData<ResponseAddBloodPressure>()

    var popup: AlertDialog? = null
    lateinit var dialog: AlertDialog
    lateinit var mViewModel: HealthViewModel
    lateinit var mBinding: FragmentHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentHealthBinding.inflate(inflater, container, false).apply {
            clickHandler = ClickHandler()
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
        setupViewPager()
        manageClicks()
        callInitialApis()
    }

    override fun onApiRequestFailed(apiUrl: String, errCode: Int, errorMessage: String): Boolean {
        if ((apiUrl.equals(Api.LIST_WEIGHT) || apiUrl.equals(Api.LIST_BLOOD_PRESSURE) || apiUrl.equals(Api.LIST_BLOOD_SUGAR)) && !callByClick)
            return true
        else
            return super.onApiRequestFailed(apiUrl, errCode, errorMessage)
    }

    fun callInitialApis() {
        mViewModel.callWeightListApi().observe(viewLifecycleOwner, Observer {
            if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                listWeight.value = (it.data!!)
            }
        })
        mViewModel.callBloodSugarListApi().observe(viewLifecycleOwner, Observer {
            if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                listBloodSugar.value = (it.data!!)
            }
        })
        mViewModel.callBloodPressureListApi().observe(viewLifecycleOwner, Observer {
            if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                listBloodPressure.value = (it.data!!)
            }
        })
    }

    private fun initWork() {
        callByClick = false
        (activity as HomeActivity).showLogo(true)
        (activity as HomeActivity).showRightLogo(true)
        (activity as HomeActivity).setTitle("")
    }

    private fun manageClicks() {
        (activity as HomeActivity).mBinding.imgRight.push()?.setOnClickListener {

            callByClick = true
            when (mBinding.viewPager.currentItem) {
                0 -> {
                    mViewModel.callWeightListApi().observe(viewLifecycleOwner, Observer {
                        if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                            val filter = it.data!!.filter {
                                !it.weight.isEmptyy() && !it.height.isEmptyy()
                            }
                            Collections.reverse(filter!!)
                            showWeightDialog(filter!!)
                        } else NO_RECORD_AVAILABLE?.showWarning()
                    })
                }
                1 -> {
                    mViewModel.callBloodSugarListApi().observe(viewLifecycleOwner, Observer {
                        if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                            val filter = it.data!!.filter {
                                !it.blood_sugar_fasting.isEmptyy() && !it.blood_sugar_postprandial.isEmptyy()
                            }
                            Collections.reverse(filter!!)
                            showBloodSugarDialog(filter!!)
                        } else NO_RECORD_AVAILABLE?.showWarning()
                    })
                }
                2 -> {
                    mViewModel.callBloodPressureListApi().observe(viewLifecycleOwner, Observer {
                        if (!it.data.isNullOrEmpty() && it.data!!.size > 0) {
                            val filter = it.data!!.filter {
                                !it.blood_pressure_systolic.isEmptyy() && !it.blood_pressure_diastolic.isEmptyy()
                            }
                            Collections.reverse(filter!!)
                            showBloodPressureDialog(filter!!)
                        } else NO_RECORD_AVAILABLE?.showWarning()
                    })
                }
            }

        }
    }

    private fun showBloodPressureDialog(data: List<ResponseAddBloodPressureItem>) {
        if(data!=null && data.isNotEmpty()){
            popup = Pop.on(activity).with().cancelable(false).layout(R.layout.blood_pressure_list).show {

                    it?.findViewById<RecyclerView>(R.id.recycler).apply {
                        var adapter = BloodPressureListAdapter(R.layout.row_blood_pressure_list)
                        this!!.adapter = adapter
                        adapter.setNewItems(data)
                    }

                    it?.findViewById<ImageView>(R.id.img_cross).apply {
                        this?.push()?.setOnClickListener {
                            popup?.dismiss()
                        }
                    }

                }
            popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }else{
            commonCallbacks?.showAlertDialog("No record found", null)
        }
    }

    private fun showBloodSugarDialog(data: List<ResponseAddBloodSugarItem>) {
        if(data!=null && data.isNotEmpty()){
            popup = Pop.on(activity).with().cancelable(false).layout(R.layout.blood_sugar_list).show {

                it?.findViewById<RecyclerView>(R.id.recycler).apply {
                    var adapter = BloodSugarListAdapter(R.layout.row_blood_sugar_list)
                    this!!.adapter = adapter
                    adapter.setNewItems(data)
                }

                it?.findViewById<ImageView>(R.id.img_cross).apply {
                    this?.push()?.setOnClickListener {
                        popup?.dismiss()
                    }
                }

            }
            popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }else{
            commonCallbacks?.showAlertDialog("No record found", null)
        }
    }

    private fun showWeightDialog(data: List<ResponseAddWeightItem>) {

        if(data!=null && data.isNotEmpty()){
            popup = Pop.on(activity).with().cancelable(false).layout(R.layout.weight_list).show {

                it?.findViewById<RecyclerView>(R.id.recycler).apply {
                    var adapter = WeightListAdapter(R.layout.row_weight_list)
                    this!!.adapter = adapter
                    adapter.setNewItems(data)
                }

                it?.findViewById<ImageView>(R.id.img_cross).apply {
                    this?.push()?.setOnClickListener {
                        popup?.dismiss()
                    }
                }

            }

            popup?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }else{
            commonCallbacks?.showAlertDialog("No record found", null)
        }
    }

    private fun setupViewPager() {

        mBinding.viewPager.adapter = HealthPagerAdapter(childFragmentManager)

        mBinding.tvWeight.push()?.setOnClickListener {
            highlightTab(mBinding.tvWeight)
            mBinding.viewPager.currentItem = 0
        }
        mBinding.tvBloodSugar.push()?.setOnClickListener {
            highlightTab(mBinding.tvBloodSugar)
            mBinding.viewPager.currentItem = 1
        }
        mBinding.tvBloodPressure.push()?.setOnClickListener {
            highlightTab(mBinding.tvBloodPressure)
            mBinding.viewPager.currentItem = 2
        }

        mBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> highlightTab(mBinding.tvWeight)
                    1 -> highlightTab(mBinding.tvBloodSugar)
                    2 -> highlightTab(mBinding.tvBloodPressure)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }

    private fun highlightTab(textView: CommonTextView) {
        context?.setTvColor(mBinding.tvWeight, R.color.black_color)
        context?.setTvColor(mBinding.tvBloodSugar, R.color.black_color)
        context?.setTvColor(mBinding.tvBloodPressure, R.color.black_color)

        context?.setTvBGColor(mBinding.tvWeight, R.color.transparent)
        context?.setTvBGColor(mBinding.tvBloodSugar, R.color.transparent)
        context?.setTvBGColor(mBinding.tvBloodPressure, R.color.transparent)

        context?.setTvColor(textView, R.color.white)
        context?.setTvBGColor(textView, R.drawable.health_tab_bg)

        if (textView == mBinding.tvBloodPressure) {
            mBinding.horizontal.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }
        if (textView == mBinding.tvWeight) {
            mBinding.horizontal.fullScroll(HorizontalScrollView.FOCUS_LEFT)
        }
    }

    private fun setupViewModel() {
        mViewModel =
            ViewModelProviders.of(this, MyViewModelProvider(commonCallbacks as AsyncViewController))
                .get(HealthViewModel::class.java)
    }

    inner class ClickHandler : IPermissionGranted {

        override fun permissionGranted(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> {
                    findNavController().navigate(R.id.ScanQRCodeFragment)
                }
            }
        }

        override fun permissionDenied(requestCode: Int) {
            when (requestCode) {

                DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA -> (activity as BaseActivity).checkAndAskPermission(
                    DeviceRuntimePermission(DeviceRuntimePermission.REQUEST_PERMISSION_ACCESS_COARSE__FINE_LOCATION_CAMERA,
                                            null))
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        (activity as HomeActivity).showRightLogo(false)
    }

    fun floatToStringDate(date: Float, map: HashMap<Float, String>): String {
        val get = map?.get(date)
        return get ?: ""
    }

    fun setupChart(chart: LineChart, map: HashMap<Float, String>, pair: Pair<Float, Float>) {
        chart!!.description.isEnabled = false
        chart!!.setTouchEnabled(true)
        chart!!.dragDecelerationFrictionCoef = 0.9f
        chart!!.isDragEnabled = true
        chart!!.setScaleEnabled(true)
        chart!!.setDrawGridBackground(true)
        chart!!.isHighlightPerDragEnabled = true
        chart!!.setPinchZoom(false)
        chart!!.setBackgroundColor(Color.WHITE)
        chart!!.animateX(1500)
        val l = chart!!.legend

        // modify the legend ...
        l.form = Legend.LegendForm.LINE
        l.typeface = Typeface.createFromAsset(context?.getAssets(), "fonts/poppins_regular.ttf")
        l.textSize = 11f
        l.textColor = Color.BLACK
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.yOffset = 10f
        l.xOffset = 10f

        val xAxis = chart!!.xAxis
        xAxis.typeface = Typeface.createFromAsset(context?.getAssets(), "fonts/poppins_regular.ttf")
        xAxis.textSize = 8f
        xAxis.textColor = Color.BLACK
        xAxis.yOffset = -3f
        xAxis.xOffset = 20f
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.granularity = range
        xAxis.labelCount = 6
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                value.toString().log()
                return floatToStringDate(value, map)
            }
        }

        val leftAxis = chart!!.axisLeft
        leftAxis.typeface =
            Typeface.createFromAsset(context?.getAssets(), "fonts/poppins_regular.ttf")
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = pair.second
        leftAxis.axisMinimum = pair.first
        leftAxis.textSize = 10f
        leftAxis.needsOffset()
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = chart!!.axisRight
        rightAxis.typeface =
            Typeface.createFromAsset(context?.getAssets(), "fonts/poppins_regular.ttf")
        rightAxis.textColor = ColorTemplate.getHoloBlue()
        rightAxis.axisMaximum = pair.second
        rightAxis.axisMinimum = pair.first
        rightAxis.textSize = 10f
        rightAxis.needsOffset()
        rightAxis.setDrawGridLines(true)
        rightAxis.isGranularityEnabled = true
    }

    fun setChartData(listWeight: ResponseAddWeight?,
                     listBS: ResponseAddBloodSugar?,
                     listBP: ResponseAddBloodPressure?,
                     chart: LineChart,
                     map: HashMap<Float, String>,
                     graphType: Int) {

        var lable1 = ""
        var lable2 = ""

        if (chart.data != null && chart.data.dataSetCount > 0) chart.clear()

        val values1 = ArrayList<Entry>()
        val values2 = ArrayList<Entry>()

        when (graphType) {

            WEIGHT -> {
                if (listWeight?.isEmpty() ?: true) {
                    values1.add(Entry(0.0f, 0.0f))
                    values2.add(Entry(0.0f, 0.0f))
                } else {
                    var i = range
                    lable1 = "Weight (KG)"
                    lable2 = "Height (CM)"

                    listWeight?.forEach {
                        if (!it.created_at.isEmptyy() && !it.weight.isEmptyy() && !it.height.isEmptyy()) {
                            i += range
                            val convertUtcToLocal = Util.convertUtcToLocal(it.created_at)
                            map.put(i, convertUtcToLocal?.changeTimeFormat(to = "dd MMM")!!)
                            values1.add(Entry(i, it.weight.toFloat()))
                            values2.add(Entry(i, it.height.toFloat()))
                        }
                    }
                }
            }

            BLOOD_SUGAR -> {
                if (listBS?.isEmpty() ?: true) {
                    values1.add(Entry(0.0f, 0.0f))
                    values2.add(Entry(0.0f, 0.0f))
                } else {
                    var i = range
                    lable1 = "Fasting (mmol/L)"
                    lable2 = "Post fasting (mmol/L)"

                    listBS?.forEach {
                        if (!it.created_at.isEmptyy() && !it.blood_sugar_fasting.isEmptyy() && !it.blood_sugar_postprandial.isEmptyy()) {
                            i += range

                            val convertUtcToLocal = Util.convertUtcToLocal(it.created_at)
                            map.put(i, convertUtcToLocal?.changeTimeFormat(Constant.API_DATE_FORMAT, "dd MMM")!!)

                            values1.add(Entry(i, it.blood_sugar_fasting.toFloat()))

                            if(it.blood_sugar_postprandial.toFloat()>0)
                            values2.add(Entry(i, it.blood_sugar_postprandial.toFloat()))

                        }
                    }
                }
            }

            BLOOD_PRESSURE -> {
                if (listBP?.isEmpty() ?: true) {
                    values1.add(Entry(0.0f, 0.0f))
                    values2.add(Entry(0.0f, 0.0f))
                } else {
                    var i = range
                    lable1 = "Systolic (mmHg)"
                    lable2 = "Diastolic (mmHg)"

                    listBP?.forEach {
                        if (!it.created_at.isEmptyy() && !it.blood_pressure_diastolic.isEmptyy() && !it.blood_pressure_systolic.isEmptyy()) {
                            i += range
                            val convertUtcToLocal = Util.convertUtcToLocal(it.created_at)
                            map.put(i, convertUtcToLocal?.changeTimeFormat(Constant.API_DATE_FORMAT, "dd MMM")!!)
                            values1.add(Entry(i, it.blood_pressure_systolic.toFloat()))
                            values2.add(Entry(i, it.blood_pressure_diastolic.toFloat()))
                        }
                    }
                }
            }

        }

        val set1: LineDataSet
        val set2: LineDataSet

        // create a dataset and give it a type
        set1 = LineDataSet(values1, lable1)
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.color = ColorTemplate.getHoloBlue()
        set1.setCircleColor(Color.BLACK)
        set1.lineWidth = 2f
        set1.circleRadius = 3f
        set1.fillAlpha = 65
        set1.fillColor = ColorTemplate.getHoloBlue()
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.setDrawCircleHole(false)
        // create a dataset and give it a type
        set2 = LineDataSet(values2, lable2)
        set2.axisDependency = YAxis.AxisDependency.RIGHT
        set2.color = Color.RED
        set2.setCircleColor(Color.BLACK)
        set2.lineWidth = 2f
        set2.circleRadius = 3f
        set2.fillAlpha = 65
        set2.fillColor = Color.RED
        set2.setDrawCircleHole(false)
        set2.highLightColor = Color.rgb(244, 117, 117)

        // create a data object with the data sets
        val data = LineData(set1, set2/*, set3*/)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(9f)

        // set data
        chart.setData(data)
        touchGraph(1000, chart)
        touchGraph(2000, chart)
        touchGraph(3000, chart)
    }

    private fun touchGraph(time: Long, chart: LineChart) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(time)
            chart.touch()
        }
    }

    fun handleTouch(chart: LineChart, nestedScrollView: NestedScrollView) {

        chart.setOnTouchListener(View.OnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    // Disallow ScrollView to intercept touch events.
                    nestedScrollView.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> {
                    // Allow ScrollView to intercept touch events.
                    nestedScrollView.requestDisallowInterceptTouchEvent(false)
                }
                /* MotionEvent.ACTION_MOVE -> {
                    mBinding.scrollview.requestDisallowInterceptTouchEvent(true)
                }*/
            }

            false
        })
    }

    class HealthPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> AddWeightFragment()

                1 -> AddBloodSugarFragment()

                else -> AddBloodPressureFragment()
            }
        }


    }

}
