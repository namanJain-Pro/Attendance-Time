package com.example.attendancetime.screen.classes

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.attendancetime.CommonValue
import com.example.attendancetime.databinding.FragmentSubjectClassesBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class SubjectClassesFragment : Fragment() {

    private lateinit var binding: FragmentSubjectClassesBinding
    private var yData = arrayListOf(50f, 50f)
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

        if (CommonValue.attendanceList.value?.isEmpty() == true) {
            binding.emptyWarning.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        } else {
            CommonValue.attendanceList.observe(viewLifecycleOwner, {
                if(it.isNotEmpty()) {
                    calculateAverageAttendance()
                    setUpPieChart()
                    addDataSet()
                    binding.chart.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            })
        }
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

    private fun calculateAverageAttendance() {
        val attendanceList = CommonValue.attendanceList.value!!
        val totalStudent: Float = CommonValue.studentList.value?.size?.times(attendanceList.size)?.toFloat() ?: 0f
        var totalPresentStudent = 0f
        var totalAbsentStudent = 0f

        for (i in attendanceList) {
            for (j in i.attendance.keys) {
                if (i.attendance[j] == true) {
                    totalPresentStudent++
                }
            }
        }
        totalAbsentStudent = totalStudent - totalPresentStudent

        val avgPresent = (totalPresentStudent / totalStudent) * 100
        val avgAbsent = (totalAbsentStudent / totalStudent) * 100
        yData = arrayListOf(avgPresent, avgAbsent)
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

        val colors = arrayListOf(Color.LTGRAY, Color.RED)
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        pieData.setValueFormatter(PercentFormatter(binding.chart))
        binding.chart.data = pieData
        binding.chart.invalidate()
    }
}