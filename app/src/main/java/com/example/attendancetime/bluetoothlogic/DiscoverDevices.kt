package com.example.attendancetime.bluetoothlogic

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.attendancetime.CommonValue

/*
This class will handle the process to discover devices
 */
class DiscoverDevices(private val context: Context) {
    companion object {
        private const val TAG = "DiscoverDevices"
    }

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var deviceFound: ArrayList<BluetoothDevice> = arrayListOf()

    fun discoverDevices() {
        // If bluetooth is not enable before scanning then we will ask the user to enable bluetooth
        if (!bluetoothAdapter.isEnabled) {
            val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivity(enableBTIntent)
        }

        // Clearing the device already found so there is no redundancy
        deviceFound.clear()

        // If discovering was already started than we will cancel it and start again
        if(bluetoothAdapter.isDiscovering) {
            Log.d(TAG, "discoverDevices: Discovering was already on, Restarting Discovering")
            bluetoothAdapter.cancelDiscovery()

            bluetoothAdapter.startDiscovery()
            context.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        }
        // If not then this
        if(!bluetoothAdapter.isDiscovering) {
            Log.d(TAG, "discoverDevices: Starting discovering")
            bluetoothAdapter.startDiscovery()
            context.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        }
    }

    // A receiver will scan for the device
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    Log.d(TAG, "onReceive: ${device?.name} ${device?.address}")
                    if (device != null) {
                        deviceFound.add(device)
                        CommonValue.btDeviceList.postValue(deviceFound)
                    }
                }
            }
        }
    }

    // Name explain everything
    fun turnOffBluetooth() {
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()
        }
    }

    // Name explain everything
    fun unRegisterReceiver() {
        Log.d(TAG, "unRegisterReceiver: unregistering the receiver")
        context.unregisterReceiver(receiver)
    }
}