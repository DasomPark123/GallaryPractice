package com.example.gallarypractice

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERNAL_STORAGE: Int = 0x1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }

    private fun checkPermission() {
        /* 권한 체크 */
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            /* 권한이 필요한 이유에 대해 추가적인 설명을 해줌
            * true : 추가적인 설명이 존재하는 경우
            * false : 추가적인 설명이 존재하지 않는 경우
            * */
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                alert(
                    getString(R.string.request_external_read_permission),
                    getString(R.string.reason_external_read_permission)
                ) {
                    yesButton {
                        /* 권한 부여 */
                        ActivityCompat.requestPermissions(
                            this@MainActivity
                            , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            , REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }

                    noButton { finish() }
                }.show()
            } else {
                /* 권한 부여 */
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    , REQUEST_READ_EXTERNAL_STORAGE
                )
            }

        } else {
            /* Todo : 이미지를 가져오는 메서드 호출 */
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    /* Todo : getAllPhotos() */
                } else {
                    toast(getString(R.string.permission_rejected))
                }
                return
            }
        }
    }

    private fun getAllPhotos() {
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        , null
            , null
            , null
        , MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        val uriArr = ArrayList<String>()
        if(cursor != null) {
            while(cursor.moveToNext()) {
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }

        /* Todo : 이미지 uri array list를 gridView apdapter에 넣어주어야함 */
    }
}
