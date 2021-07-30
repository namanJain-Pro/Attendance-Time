package com.example.attendancetime.screen.classes

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.databinding.FragmentSubjectClassesBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class SubjectClassesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectClassesBinding
    private val yData = arrayListOf<Float>(50f, 10f)
    private val xData = arrayListOf("Present", "Absent")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectClassesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPieChart()
        addDataSet()
    }

    private fun setUpPieChart() {
        binding.chart.isDrawHoleEnabled = true
        binding.chart.setUsePercentValues(true)
        binding.chart.setEntryLabelTextSize(12f)
        binding.chart.setEntryLabelColor(Color.BLACK)
        binding.chart.centerText = "Attendance Stats"
        binding.chart.setCenterTextSize(12f)
        binding.chart.description.isEnabled = false

        val legend = binding.chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = true
    }

    private fun addDataSet() {
        val pieEntry = arrayListOf<PieEntry>()

        var i = 0
        while (i < yData.size) {
            pieEntry.add(PieEntry(yData[i], xData[i]))
            i++
        }

        val pieDataSet = PieDataSet(pieEntry, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextSize = 12f

        val colors = arrayListOf<Int>(Color.BLUE, Color.RED)
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        pieData.setValueFormatter(PercentFormatter(binding.chart))
        binding.chart.data = pieData
        binding.chart.invalidate()
    }
}