package com.example.violet.hdxvideo.test;

import com.example.violet.hdxvideo.MainActivity;
import com.orhanobut.logger.Logger;

public class InstrumentedActivity extends MainActivity {
	public static String TAG = "InstrumentedActivity";

	private FinishListener mListener;

	public void setFinishListener(FinishListener listener) {
		mListener = listener;
	}


	@Override
	public void onDestroy() {

		Logger.i("ceshijieddddd");
		super.finish();
		if (mListener != null) {
			mListener.onActivityFinished();
		}

		super.onDestroy();
	}



}