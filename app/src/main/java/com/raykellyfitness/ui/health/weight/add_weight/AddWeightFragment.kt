package com.raykellyfitness.ui.health.weight.add_weight

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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.ColorTemplate
import com.raykellyfitness.base.*
import com.raykellyfitness.databinding.FragmentAddWeightBinding
import com.raykellyfitness.model.request.RequestAddWeight
import com.raykellyfitness.model.request.ResponseAddWeight
import com.raykellyfitness.ui.activity.HomeActivity
import com.raykellyfitness.ui.health.HealthFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddWeightFragment : BaseFragment(), OnChartValueSelectedListener {

    var range = 10.0f
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
        manageClicks()
        setupChart()
        handleTouch()
        (this.parentFragment as HealthFragment).listWeight.observe(viewLifecycleOwner, Observer {
            setChartData(it)
        })
    }

    fun floatToStringDate(date: Float): String {
        val get = map?.get(date)
        return get ?: ""
    }


    fun setupChart() {
        chart = mBinding.chart1
        chart!!.setOnChartValueSelectedListener(this)

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
        l.form = LegendForm.LINE
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
                return floatToStringDate(value)
            }
        }

        val leftAxis = chart!!.axisLeft
        leftAxis.typeface = Typeface.createFromAsset(
            context?.getAssets(),
            "fonts/poppins_regular.ttf"
        )
        leftAxis.textColor = ColorTemplate.getHoloBlue()
        leftAxis.axisMaximum = 1100f
        leftAxis.axisMinimum = 1f
        leftAxis.needsOffset()
        leftAxis.setDrawGridLines(true)
        leftAxis.isGranularityEnabled = true

        val rightAxis = chart!!.axisRight
        rightAxis.typeface = Typeface.createFromAsset(
            context?.getAssets(),
            "fonts/poppins_regular.ttf"
        )
        rightAxis.textColor = ColorTemplate.getHoloBlue()
        rightAxis.axisMaximum = 1100f
        rightAxis.axisMinimum = 1f
        rightAxis.needsOffset()
        rightAxis.setDrawGridLines(true)
        rightAxis.isGranularityEnabled = true
    }

    private fun setChartData(listWeight: ResponseAddWeight?) {

        if (mBinding.chart1.data != null && mBinding.chart1.data.dataSetCount > 0)
            mBinding.chart1.clear()

        val values1 = ArrayList<Entry>()
        val values2 = ArrayList<Entry>()


        if(listWeight?.isEmpty()?:true){
            values1.add(Entry(0.0f, 0.0f))
            values2.add(Entry(0.0f, 0.0f))
        }else{
            var i = range

//            if(!listWeight?.first()?.created_at.isEmptyy())
//            addPreviousDate(i,map, listWeight?.first()?.created_at!!, values1, values2)

            listWeight?.forEach {
                if(!it.created_at.isEmptyy() && !it.weight.isEmptyy() &&  !it.height.isEmptyy()){
                    i += range
                    map.put(i, it.created_at.changeTimeFormat("yyyy-MM-dd", "dd MMM")!!)
                    values1.add(Entry(i, it.weight.toFloat()))
                    values2.add(Entry(i, it.height.toFloat()))
                }
            }

//            addNextDate(i+100,map, listWeight?.last()?.created_at!!, values1, values2)

        }

        val set1: LineDataSet
        val set2: LineDataSet

        // create a dataset and give it a type
        set1 = LineDataSet(values1, "Weight (KG)")
        set1.axisDependency = AxisDependency.LEFT
        set1.color = ColorTemplate.getHoloBlue()
        set1.setCircleColor(Color.BLACK)
        set1.lineWidth = 2f
        set1.circleRadius = 3f
        set1.fillAlpha = 65
        set1.fillColor = ColorTemplate.getHoloBlue()
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.setDrawCircleHole(false)
        // create a dataset and give it a type
        set2 = LineDataSet(values2, "Height (CM)")
        set2.axisDependency = AxisDependency.RIGHT
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
        mBinding.chart1.setData(data)
        touchGraph(1000)
        touchGraph(2000)
        touchGraph(3000)

//        chart!!.setVisibleXRangeMaximum(5)
    }

    private fun addNextDate(
        i: Float,
        map: HashMap<Float, String>,
        date: String,
        values1: ArrayList<Entry>,
        values2: ArrayList<Entry>
    ) {
        val nextDate = getNextDate(date)
        map.put(i, nextDate)
        values1.add(Entry(i, 0.0f))
        values2.add(Entry(i, 0.0f))
    }

    private fun addPreviousDate(
        i: Float,
        map: HashMap<Float, String>,
        date: String,
        values1: ArrayList<Entry>,
        values2: ArrayList<Entry>
    ) {
        val nextDate = getPreviousDate(date)
        map.put(i, nextDate)
        values1.add(Entry(i, 0.0f))
        values2.add(Entry(i, 0.0f))
    }

    private fun getNextDate(date: String?) : String {
        val dateToIncr = date
        var nextDate = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(dateToIncr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        c.add(Calendar.DAY_OF_MONTH, 1) // number of days to add

        nextDate = SimpleDateFormat("dd MMM").format(c.time)
        return nextDate
    }

    private fun getPreviousDate(date: String?) : String {
        val dateToIncr = date
        var nextDate = ""
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(dateToIncr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        c.add(Calendar.DAY_OF_MONTH, -1) // number of days to add

        nextDate = SimpleDateFormat("dd MMM").format(c.time)
        return nextDate
    }

    private fun touchGraph(time: Long) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(time)
            mBinding.chart1.touch()
        }
    }


    fun handleTouch() {

        mBinding.chart1.setOnTouchListener(View.OnTouchListener { v, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    // Disallow ScrollView to intercept touch events.
                    mBinding.scrollview.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> {
                    // Allow ScrollView to intercept touch events.
                    mBinding.scrollview.requestDisallowInterceptTouchEvent(false)
                }
                /* MotionEvent.ACTION_MOVE -> {
                    mBinding.scrollview.requestDisallowInterceptTouchEvent(true)
                }*/
            }

            false
        })
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

    inner class ClickHandler {

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
