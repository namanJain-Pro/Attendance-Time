package com.example.attendancetime.screen.classes

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.databinding.FragmentSubjectClassesBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class SubjectClassesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectClassesBinding
//    private val yData = arrayListOf<Float>(50f, 10f)
//    private val xData = arrayListOf("Present", "Absent")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubjectClassesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.chart.description.text = "Attendance stats"
//        binding.chart.setTransparentCircleAlpha(0)
    }

//    private fun addDataSet() {
//        val yEntry = arrayListOf<PieEntry>()
//        val xEntry = arrayListOf<String>()
//
//        var i = 0
//        while (i < yData.size) {
//            yEntry.add(PieEntry(yData[i], i))
//            i++
//        }
//
//        for (s in xData) {
//            xEntry.add(s)
//        }
//
//        val pieDataSet = PieDataSet(yEntry, "Attendance")
//        pieDataSet.sliceSpace = 2f
//        pieDataSet.valueTextSize = 12f
//        pieDataSet.setColor(Color.BLUE, Color.RED)
//
//        val pieData = PieData(pieDataSet)
//        binding.chart.data = pieData
//        binding.chart.invalidate()
//    }
}